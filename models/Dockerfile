FROM ubuntu:20.04

ARG DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get upgrade -y && apt-get autoremove && apt-get autoclean
RUN apt-get update && apt-get install -y git
RUN apt-get install -y -q apt-utils python3-pip

COPY requirements.txt .
RUN pip install -r requirements.txt

ARG PROJECT=models
ARG PROJECT_DIR=/${PROJECT}
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
