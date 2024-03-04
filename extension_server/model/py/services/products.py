import os
from flask import Flask, request

app = Flask(__name__)
port = int(os.environ.get('PORT', 5000))


@app.route("/python-service", methods = ['POST'])
def home():
    data = request.json
    with open("E:/projects/vs_code_ext/extension_serve/blabla.txt", "w") as f:
        f.write(data)
    print(data)
if __name__ == "__main__":
    app.run(debug=True, host="0.0.0.0", port=port)
