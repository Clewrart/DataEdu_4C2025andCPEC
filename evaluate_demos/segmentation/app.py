from flask import Flask, request
from flask_cors import CORS
from segmentation import *

app = Flask(__name__)
CORS(app, resources={r"/*": {"origins": "*"}})


@app.route('/segmentation', methods=["POST"])
def seg():

    total_dice = 0
    imgs = request.files.getlist("files")

    student_file_dict = getStudentFileDict(imgs)
    answer_file_dict = getAnswerFileDict("./answer")

    for key in answer_file_dict:

        if key in student_file_dict:
            dice = dice_coefficient(
                answer_file_dict[key],
                student_file_dict[key]
            )
    #
            total_dice += dice
            print(dice)
    # 百分制化分数
    #score = round(total_dice/len(answer_file_dict) * 10000 + 50)
    #score = (1.0 * total_dice / len(answer_file_dict)) * 100
    score = round(total_dice / len(answer_file_dict) * 100,2)
    return {
        "score": score
    }


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=6002, debug=True)
