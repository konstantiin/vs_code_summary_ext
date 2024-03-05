from flask import Flask, request
import py_eureka_client.eureka_client as eureka_client
import json

rest_port = 8050
eureka_client.init(eureka_server="http://registration:8761/eureka",
                   app_name="get-summary-service",
                   instance_port=rest_port)
app = Flask(__name__)



from transformers import pipeline
summarizer = pipeline("summarization", model="sshleifer/distilbart-cnn-12-6")


@app.route("/get-summary", methods=['POST'])
def hello():
    print(request.get_json()["textToSummarize"])
    data = request.get_json()["textToSummarize"]
    
    response = summarizer(data, max_length=130, min_length=30, do_sample=False)[0]['summary_text']
    return response

if __name__ == "__main__":
    app.run(host='0.0.0.0', port = rest_port)
