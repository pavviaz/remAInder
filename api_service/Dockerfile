FROM python:3.10-slim
ENV PYTHONUNBUFFERED=1
ENV PYTHON /code:/base
ENV PROJECT_DIRECTORY ./api_service/

RUN mkdir -p /code
WORKDIR /code

COPY ${PROJECT_DIRECTORY}requirements.txt /code/requirements.txt

RUN python -m pip install --upgrade pip
RUN pip install -r requirements.txt