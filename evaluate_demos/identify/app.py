from flask import Flask, request, jsonify
from flask_cors import CORS
from calc_map import get_score
import json

app = Flask(__name__)

# 允许所有来源的跨域请求
CORS(app, resources={r"/*": {"origins": "*"}}, supports_credentials=True)

# 加载教师答案
with open("./answer.json", "r") as f:
    teacher_answer = json.load(f)

@app.route('/identify', methods=["POST", "OPTIONS"])
def detect():
    if request.method == "OPTIONS":
        return jsonify({"message": "CORS 跨域ok"}), 200

    file = request.files.get("files")
    if not file:
        return jsonify({"error": "未上传文件"}), 400
    try:
        student_results = json.load(file)
    except Exception as e:
        return jsonify({"error": "无效的JSON文件", "detail": str(e)}), 400

    score = get_score(teacher_answer, student_results)
    scorepp = round(score * 400, 2)
    return jsonify({"score": scorepp})

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=6000, debug=True)
