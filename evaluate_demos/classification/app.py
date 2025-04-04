from flask import Flask,request
from flask_cors import CORS

from handle_csv import read_csv
from calc_score import calc_score
app = Flask(__name__)
CORS(app)


answer = read_csv("./answer.csv")

@app.route('/classify',methods=["POST"])
def judge():
   csv = request.files.get("files")
   real=read_csv(csv)
   score = calc_score(answer,real)
   return {
       "score": score
   }

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=6001,debug=True)