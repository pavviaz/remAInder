from datetime import datetime
from novaai_chatgpt import get_output


MODEL = "gpt-3.5-turbo"
# MODEL = "gpt-4"


# PROMPT = f"Вычлени из этого списка задач отдельные зададачи в формате \
#           Текст задачи | Дата задачи в формате '%Y-%m-%dT%H:%M:%S.%f%z'. \
#           Сегодняшняя дата - DATETIME. Задачи в списке должны начинаться с существительного. \
#           Каждая строка списка должна быть пронумерована. \
#           Список: "

# PROMPT = f"Вычлени из этого списка задач отдельные зададачи, \
#           а также определи тип каждой задачи. \
#           Тип должен быть один из \
#           'Редактирование' (если в тексте есть слова Передвинь, Измени, Перенеси), \
#           или 'Удаление' (если в тексте есть слова Отмени, Удали), \
#           или 'Создание' (если текст задачи начинается с любого другого существительного или глагола). \
#           Сегодняшняя дата - DATETIME. \
#           Задачи в выходном списке должны начинаться с существительного. \
#           В выходном описании задачи не должно быть указания времени (например 'через 5 минут' или 'на следующий день'). \
#           Каждая строка выходного списка должна быть пронумерована. \
#           В выходном описании задачи не должно быть указания сроков, только задача. \
#           Выходной формат: Текст задачи | Дата задачи в формате '%Y-%m-%dT%H:%M:%S.%f%z' | Тип задачи \
#           Список: "

PROMPT = f"Вычлени из этого списка задач отдельные зададачи. \
          Сегодняшняя дата - DATETIME. \
          Изначальный вид задач должен быть изменен по следующим условиям: \
          1) Задача должна начинаться с существительного, \
          2) В задаче не должно быть повелительных глаголов (например Перенеси), \
          3) В задаче не должно быть указания времени и сроков (например 'через 5 минут' или 'на следующий день'). \
          Каждая строка выходного списка должна быть пронумерована с точкой. \
          Выходной формат: Модифицированный по правилам текст задачи | Дата задачи относительно сегодняшней в формате '%Y-%m-%dT%H:%M:%S.%f' | Изначальный текст этой одной задачи \
          Список: "


def get_chatgpt_output(asr_text: str):
    inputs = (
        PROMPT.replace("DATETIME", datetime.now().strftime("%Y-%m-%dT%H:%M:%S.%f"))
        + f"'{asr_text}'"
    )
    return get_output(inputs, MODEL)


if __name__ == "__main__":
    print(
        get_chatgpt_output(
            "Перенеси встречу с Иваном Ивановичем на завтршний день в 8 утра, и скажи выключить стиралку через 20 минут"
        )
    )
