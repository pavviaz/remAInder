import pydub
import numpy as np


def load_audio(file, sample_rate=16000, n_channels=1):
    """Функция для загрузки аудио из файла

    Args:
        file (str): Путь до файла
        sample_rate (int, optional): Частота дискретизации. Defaults to 16000.
        n_channels (int, optional): Каналы. Defaults to 1.

    Returns:
        _type_: _description_
    """
    audio = pydub.AudioSegment.from_file(file).set_sample_width(2)
    if sample_rate is not None:
        audio = audio.set_frame_rate(sample_rate)
    audio = audio.set_channels(n_channels)

    return audio

    # нормализация аудио, временно отключена
    # audio = np.array(audio.get_array_of_samples(), dtype=np.float32).reshape(
    #     (-1, audio.channels)
    # ) / (1 << (8 * audio.sample_width - 1))

    # return np.squeeze(audio)


def load_binary_audio(file, sample_rate=16000, n_channels=1):
    """Загрузка аудио с переводом в байтовую строку

    Args:
        file (str): Путь до файла
        sample_rate (int, optional): Частота дискретизации. Defaults to 16000.
        n_channels (int, optional): Каналы. Defaults to 1.

    Returns:
        _type_: _description_
    """
    audio = load_audio(file, sample_rate, n_channels)
    return (
        np.array(audio.get_array_of_samples(), dtype=np.float32)
        .reshape((-1, audio.channels))
        .tobytes()
    )
    # return audio.tobytes()
