import openai as novaai
from dotenv import load_dotenv
import os

load_dotenv()

novaai.api_base = "https://api.nova-oss.com/v1"
novaai.api_key = os.getenv("OPENAI_KEY")


def get_output(prompt, model):
    try:
        response = novaai.ChatCompletion.create(
            model=model,
            temperature = 0.1,
            messages=[{"role": "user", "content": prompt}],
        )
    except Exception as exc:
        raise exc
    
    return response.choices[0].message.content


if __name__ == "__main__":
    print(get_output("Поставь встречу с Иваном Ивановичем на завтра лишь не день, а утюг выключить через 5 минут."))
