from datetime import datetime
from novaai_chatgpt import get_output


MODEL = "gpt-3.5-turbo"


PROMPT = f"Вычлени из этого списка задач отдельные зададачи в формате \
          Текст задачи | Дата задачи в формате '%Y-%m-%dT%H:%M:%S.%f%z'. \
          Сегодняшняя дата - DATETIME. Задачи в списке должны начинаться с существительного. \
          Каждая строка списка должна быть пронумерована. \
          Список: "


def get_chatgpt_output(asr_text: str):
    inputs = (
        PROMPT.replace("DATETIME", datetime.now().strftime("%Y-%m-%dT%H:%M:%S.%f%z"))
        + f"'{asr_text}'"
    )
    return get_output(inputs, MODEL)


if __name__ == "__main__":
    print(
        get_chatgpt_output(
            "Поставь встречу с Иваном Ивановичем на завтршний день в 8 утра, утюг выключить через 5 минут, а стиралку разгрузить через час"
        )
    )
