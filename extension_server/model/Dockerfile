FROM nvidia/cuda:12.3.2-runtime-ubuntu22.04

RUN mkdir /home/flask_server
COPY . /home/flask_server
RUN pip install --upgrade pip
WORKDIR /home/flask_server
RUN pip install -r requirements.txt
EXPOSE 8050
CMD ["python", "summary_getter_service.py"]