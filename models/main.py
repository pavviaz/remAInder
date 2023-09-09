import multiprocessing
import time
import os
import requests
import pickle

# from pydub import AudioSegment
import numpy as np
import redis
from dotenv import load_dotenv

from nanosemantics_asr import recognize_audio, test_recognize_audio
from llm import get_chatgpt_output
from loading_audio import load_audio, load_binary_audio
from embedder import get_embedding


SEP = "|"
# SAMPLE_RATE = 16000
# CHANNELS = 1
# SAMPLE_WIDTH = 2
# BATCH_SIZE = 32

UPDATE_KEYWORDS = ["перенеси", "передвинь", "измени"]
DELETE_KEYWORDS = ["удали", "отмени"]

ASR_TASKS_PATTERN = "asr:*"


def get_task_type(task_raw_text: str):
    if any(el in task_raw_text.lower() for el in UPDATE_KEYWORDS):
        return "upd"
    if any(el in task_raw_text.lower() for el in DELETE_KEYWORDS):
        return "del"
    return "crt"


def get_task_embedding(task):
    return get_embedding(task)


def find_most_similar(user_id, task):
    new_task_emb = get_embedding(task)

    all_user_tasks = requests.post(
        url=os.getenv("DEFAULT_API_IP") + "/api_service/task/get_tasks",
        params={"user_id": user_id},
    ).json()

    if not len(all_user_tasks):
        return False

    distance = 999
    for task in all_user_tasks:
        task_emb = get_task_embedding(task["description"])

        l2_dist = np.linalg.norm(new_task_emb - task_emb)
        if l2_dist < distance:
            distance = l2_dist
            sim_task_id = task["id"]

    return sim_task_id


def parse_tasks(data_str):
    """Парсер ответов от chatGPT

    Args:
        data_str (str): Ответ от chatGPT

    Returns:
        _type_: _description_
    """
    tasks = []

    gpt_response = data_str.strip().split("\n")
    for task_str in gpt_response:
        desc, date_str, raw_text = task_str.split(SEP)
        tasks.append(
            {
                "task_type": get_task_type(raw_text),
                "desc": desc[desc.find(".") + 2 :].strip(),
                "date": date_str.strip(),
            }
        )
    return tasks


def test_asr_pipeline(queries):
    """Функция запускает всё необходимое для получения инфы
    о задачах пользователя и передачи их в БД

    Args:
        queries (_type_): _description_

    Returns:
        _type_: _description_
    """
    audios = list(queries.values())

    asr_texts = {k: v.strip() for k, v in zip(queries, test_recognize_audio(audios))}
    print(asr_texts)

    llm_outputs = {k: get_chatgpt_output(v) for k, v in asr_texts.items()}
    print(llm_outputs)

    parser_outputs = {k: parse_tasks(v) for k, v in llm_outputs.items()}

    return parser_outputs


def asr_pipeline(queries):
    """Функция запускает всё необходимое для получения инфы
    о задачах пользователя и передачи их в БД

    Args:
        queries (_type_): _description_

    Returns:
        _type_: _description_
    """
    asr_texts = [v.strip() for v in recognize_audio(queries)]

    llm_outputs = [get_chatgpt_output(v) for v in asr_texts]

    parser_outputs = [parse_tasks(v) for v in llm_outputs]

    return parser_outputs


def asr_broker_listening():
    """Простукивание redis на наличие запросов в очереди.
    Тут все очень плохо, но это поменяется

    Raises:
        Exception: _description_
    """
    db = redis.from_url(os.getenv("REDIS_HOST"))
    while True:
        try:
            redis_keys = db.keys(pattern=ASR_TASKS_PATTERN)[: int(os.getenv("BATCH_SIZE"))]
            print(f"TOOK {len(redis_keys)} keys!")
            queries = [pickle.loads(db.get(k)) for k in redis_keys]
            if not queries:
                print("ASR BROKER IS EMPTY")
                time.sleep(int(os.getenv("EMPTY_REDIS_OFFSET")))
                continue

            parser_output = asr_pipeline([el["req"] for el in queries])

            for q, tasks in zip(queries, parser_output):
                for task in tasks:
                    if task["task_type"] == "crt":
                        r = requests.post(
                            url=os.getenv("DEFAULT_API_IP") + "/api_service/task/",
                            json={
                                "datetime": task["date"],
                                "description": task["desc"],
                                "user_id": str(q["user_id"])
                            },
                        )
                        print(f"ADDED! {r.text}")

                    elif task["task_type"] == "upd":
                        most_similar_task_id = find_most_similar(
                            user_id=str(q["user_id"]), task=task["desc"]
                        )

                        if most_similar_task_id:
                            r = requests.put(
                                url=os.getenv("DEFAULT_API_IP") + "/api_service/task/",
                                json={"id": most_similar_task_id, "datetime": task["date"]},
                            )
                            print(f"UPD! {r.text}")

                    elif task["task_type"] == "del":
                        most_similar_task_id = find_most_similar(
                            user_id=str(q["user_id"]), task=task["desc"]
                        )

                        if most_similar_task_id:
                            r = requests.delete(
                                url=os.getenv("DEFAULT_API_IP")
                                + "/api_service/task/"
                                + most_similar_task_id,
                                params={"task_id": most_similar_task_id},
                            )
                            print(f"DEL! {r.text}")
            # break

            [db.delete(el) for el in redis_keys]

        except:
            raise Exception("Something went wrong")


def start_all_pipelines():
    asr_broker_listening()

    # tasks_types = [asr_broker_listening]

    # multiprocessing.set_start_method("spawn")
    # threads = [multiprocessing.Process(target=func, args=()) for func in tasks_types]
    # for t in threads:
    #     t.start()
    # for t in threads:
    #     t.join()


def test_pipeline():
    q = {
        "1": load_audio("test_audios/sv_1.wav", SAMPLE_RATE, CHANNELS),
        "2": load_audio("test_audios/sv_2.wav", SAMPLE_RATE, CHANNELS),
    }

    r = test_asr_pipeline(q)
    print(r)


load_dotenv()
if __name__ == "__main__":
    # test_pipeline()

    start_all_pipelines()
