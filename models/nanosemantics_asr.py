import requests
import json
from typing import List
import os
import shutil

from dotenv import load_dotenv
import pydub

from loading_audio import load_audio, load_binary_audio


TMP_DIR = "tmp_audios"


def asr(audio_files, token):
    url = "https://asr.nanosemantics.ai/asr"

    files = [("audio_blob", (el, open(el, "rb"), "audio/wav")) for el in audio_files]

    headers = {
        "Authorization": "Basic {}".format(token),
    }

    payload = {
        "language": "ru",
        "sample_rate": "16000",
        "convert_digits": "1",
        "use_punctuation": "1",
        "include_breaks": "0",
        "translate": "0",
        "restore_case": "1",
        "profanity_filter": "0",
        "classify": "0",
        "decoder_name": "general",
        "use_vad": 0,
    }

    response = requests.request("POST", url, headers=headers, data=payload, files=files)

    if response:
        return json.loads(response.text)

    else:
        print(response)
        return False


def create_tmp_files(audios: List[pydub.AudioSegment]):
    tmp_audios = []
    for idx, audio in enumerate(audios):
        tmp_path = os.path.join(TMP_DIR, f"tmp_{idx}.wav")
        tmp_audios.append(tmp_path)

        audio.export(tmp_path, format="wav")
    return tmp_audios


def recognize_audio(audios):
    tmp_dir = TMP_DIR
    os.makedirs(TMP_DIR, exist_ok=False)

    # На реальном сервере в docker-compose надо использовать это
    # tmp_dir = os.path.join(os.environ.get("STATIC_FILES", "/code/static"), TMP_DIR)
    # os.makedirs(tmp_dir,exist_ok=False)

    tmp_audios = create_tmp_files(audios)
    response = asr(tmp_audios, os.getenv("NANOSEMANTICS_ASR_TOKEN"))

    shutil.rmtree(tmp_dir)
    return [el["response"][0]["text"] for el in response["r"]]


load_dotenv()
if __name__ == "__main__":
    r = recognize_audio(
        [load_audio("test_audios/test2.wav"), load_audio("test_audios/test3.wav")]
    )
    print(r)
