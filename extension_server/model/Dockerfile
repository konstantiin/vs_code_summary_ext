#FROM nvidia/cuda:11.8.0-runtime-ubuntu22.04
FROM python:3.10
RUN mkdir /home/flask_server
COPY requirements.txt /home/flask_server
RUN apt-get update
#RUN apt-get install -y python3.10 python3-pip sudo
#RUN sudo apt install python3-pip
WORKDIR /home/flask_server
RUN pip3 install torch --index-url https://download.pytorch.org/whl/cu118
RUN pip install -r requirements.txt
COPY summary_getter_service.py /home/flask_server
EXPOSE 8050
CMD ["python3", "-u", "summary_getter_service.py"]