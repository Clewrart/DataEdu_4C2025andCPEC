from flask import Flask, request, jsonify
from flask_cors import CORS
from calc_map import get_score
import json

app = Flask(__name__)
CORS(app)

# 加载教师答案
with open("./answer.json", "r") as f:
    teacher_answer = json.load(f)

@app.route('/detect', methods=["POST"])
def detect():
    file = request.files.get("file")
    if not file:
        return jsonify({"error": "未上传文件"}), 400
    try:
        student_results = json.load(file)
    except Exception as e:
        return jsonify({"error": "无效的 JSON 文件"}), 400

    score = get_score(teacher_answer, student_results)
    scorepp = round(score * 100, 2)
    return {
        "score": scorepp
    }

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=6000, debug=True)
