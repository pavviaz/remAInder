import time
import os

from pydub import AudioSegment
import numpy as np
import redis

from nanosemantics_asr import recognize_audio
from llm import get_chatgpt_output
from loading_audio import load_audio, load_binary_audio


SEP = "|"
SAMPLE_RATE = 16000
CHANNELS = 1
SAMPLE_WIDTH = 2
BATCH_SIZE = 32


def parse_tasks(data_str):
    """Парсер ответов от chatGPT

    Args:
        data_str (str): Ответ от chatGPT

    Returns:
        _type_: _description_
    """
    tasks = []
    task_strings = data_str.strip().split("\n")
    for task_str in task_strings:
        desc, date_str = task_str.split(SEP)
        if len(desc.strip()) and len(date_str.strip()):
            tasks.append(
                {"desc": desc[desc.find(".") + 2 :].strip(), "date": date_str.strip()}
            )
    return tasks


def create_tasks_pipeline(queries):
    """Функция запускает всё необходимое для получения инфы
    о задачах пользователя и передачи их в БД

    Args:
        queries (_type_): _description_

    Returns:
        _type_: _description_
    """
    audios = list(queries.values())

    asr_texts = {k: v.strip() for k, v in zip(queries, recognize_audio(audios))}

    llm_outputs = {k: get_chatgpt_output(v) for k, v in asr_texts.items()}

    parser_outputs = {k: parse_tasks(v) for k, v in llm_outputs.items()}

    return parser_outputs


def launch_broker_listening():
    """Простукивание redis на наличие запросов в очереди.
    Тут все очень плохо, но это поменяется

    Raises:
        Exception: _description_
    """
    db = redis.from_url(os.getenv("REDIS_HOST"))

    while True:
        try:
            queries = {
                k: AudioSegment(
                    db.get(k),
                    frame_rate=SAMPLE_RATE,
                    sample_width=SAMPLE_WIDTH,
                    channels=CHANNELS,
                )
                for k in db.keys()[:BATCH_SIZE]
            }
            if not queries:
                print("BROKER IS EMPTY")
                time.sleep(os.getenv("EMPTY_REDIS_OFFSET"))
                continue

            parser_output = create_tasks_pipeline(queries)

            [db.delete(k) for k in parser_output]
            [db.set(k, v) for k, v in parser_output.items()]

        except:
            raise Exception()


def test_pipeline():
    q = {
        "1": load_audio("test_audios/test3.wav", SAMPLE_RATE, CHANNELS),
        "2": load_audio("test_audios/test2.wav", SAMPLE_RATE, CHANNELS),
    }

    r = create_tasks_pipeline(q)
    print(r)


if __name__ == "__main__":
    test_pipeline()
