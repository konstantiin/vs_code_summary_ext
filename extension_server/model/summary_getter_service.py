from flask import Flask, request
import py_eureka_client.eureka_client as eureka_client
import json

rest_port = 8050
eureka_client.init(eureka_server="http://localhost:8761/eureka",
                   app_name="get-summary-service",
                   instance_port=rest_port)
app = Flask(__name__)

@app.route("/get-summary", methods=['POST'])
def hello():
    #data = request.json
    
    response = '''{ "modelName":"default","textToSummarize":"lol"}'''
    return response

if __name__ == "__main__":
    app.run(host='0.0.0.0', port = rest_port)
