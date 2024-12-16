import os
import numpy as np
from skimage.io import imread
import io


def getAnswerFileDict(directory):
    file_dict = {}
    for root, _, files in os.walk(directory):
        for file in files:
            file_path = os.path.join(root, file)
            with open(file_path, 'rb') as f:  # 以二进制读模式打开文件
                file_dict[file] = imread(f)
    return file_dict


def getStudentFileDict(imgs):
    res = {}
    for img in imgs:
        img_stream = io.BytesIO(img.read())
        res[img.filename] = imread(img_stream)

    return res


def dice_coefficient(y_true, y_pred):
    intersection = np.sum(y_true * y_pred)
    union = np.sum(y_true) + np.sum(y_pred)
    return 2.0 * intersection / union if union > 0 else 1.0


if __name__ == "__main__":
    print(dice_coefficient(imread("./answer/71.png"), imread("./answer/77.png")))
