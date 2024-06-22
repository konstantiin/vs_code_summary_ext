from flask import Flask, request
import py_eureka_client.eureka_client as eureka_client
import json
import logging

rest_port = 8050
eureka_client.init(eureka_server="http://registration:8761/eureka",
                   app_name="model",
                   instance_port=rest_port)
app = Flask(__name__)
app.logger.setLevel(logging.INFO)


from transformers import pipeline
app.logger.info("facebook/bart-large-cnn model loading")
summarizer_facebook = pipeline("summarization", model="facebook/bart-large-cnn")

app.logger.info("sshleifer/distilbart-cnn-12-6 model loading")
summarizer_sshleifer = pipeline("summarization", model="sshleifer/distilbart-cnn-12-6")




@app.route("/get-summary", methods=['POST'])
def summ():
    data = request.get_json()["textToSummarize"]
    model = request.get_json()["modelName"]
    if model == "facebook":
        summarizer = summarizer_facebook
    else:
        summarizer = summarizer_sshleifer
        
    app.logger.info("recieved data: %s", data)
    response = summarizer(data, max_length=130, min_length=30, do_sample=False)[0]['summary_text']
    app.logger.info("summarized data: %s", response)
    return response

if __name__ == "__main__":
    
    app.logger.info("can accept queries")
    app.run(host='0.0.0.0', port = rest_port)

