FROM python:3.10
RUN mkdir /usr/src/flask_server
COPY . /usr/src/flask_server
RUN pip install --upgrade pip
WORKDIR /usr/src/flask_server
RUN pip install -r requirements.txt
EXPOSE 8050
CMD ["python", "summary_getter_service.py"]