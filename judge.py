from flask import Flask,request
from flask_cors import CORS
import random

app = Flask(__name__)
CORS(app)

@app.route('/judge',methods=["POST"])
def judge():
   file= request.files.get("file")
   return {
       "score": random.randint(50,100),
       "filename":file.filename
   }

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8000,debug=True)