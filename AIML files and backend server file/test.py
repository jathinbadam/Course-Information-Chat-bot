import aiml
import os
from flask import Flask, request, jsonify
import time

app = Flask(__name__)
kernel = aiml.Kernel()
if os.path.isfile("bot_brain.brn"):
    kernel.bootstrap(brainFile = "bot_brain.brn")
else:
    kernel.bootstrap(learnFiles = "std-startup.xml", commands = "load aiml b")
    kernel.saveBrain("bot_brain.brn")


employees = [{"result" : ""}]
@app.route('/get/data', methods=['GET', 'POST'])
def add_message():
    return jsonify({'employees' : employees})

@app.route('/post/data', methods=['GET', 'POST'])
def post_message():
    content = request.json
    var = content['Test']
    res = kernel.respond(var)
    print (res)
    employees = [{"result": res}]
    time.sleep(1)
    return jsonify({'employees' : employees})


if __name__ == '__main__':
    app.run(host= '0.0.0.0',debug=True)    