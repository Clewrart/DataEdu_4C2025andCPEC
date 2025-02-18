from flask import Flask, request, jsonify
from flask_cors import CORS
from read_result import read_result
from score_cal import get_score

app = Flask(__name__)
CORS(app)

# 加载标准答案
answer = read_result("./answer.json")

@app.route('/identify', methods=["POST"])
def evaluate():
    # 获取学生上传的结果json文件
    student_file = request.files.get("file")
    if not student_file:
        return jsonify({"error": "没有文件！"})

    # 读取结果
    student_results = read_result(student_file)

    # 计算分数
    score = get_score(answer, student_results)
    scorep = round(score * 100, 2)

    # 返回分数
    return jsonify({
        "score": scorep
    })

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8000, debug=True)