import whisper
import numpy as np


model = whisper.load_model("small")


def recognize_audio(audio):
    result = model.transcribe(audio)
    return [el["text"] for el in result]


if __name__ == "__main__":
    print(recognize_audio(["test_audios/test3.wav", "test_audios/test2.wav"]))
