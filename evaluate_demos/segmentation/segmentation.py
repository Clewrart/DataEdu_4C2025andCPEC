import os
import numpy as np
from skimage.io import imread
import io
from skimage.color import rgb2gray

def read_image(file):
    img = imread(file)
    if img.shape[-1] == 4:#如果是RGBA（4通道），去掉Alpha通道
        img = img[:,:,:3]#取RGB三通道
    if len(img.shape) == 3:#如果是RGB
        img = rgb2gray(img)#转换为灰度图
    return (img > 0.5).astype(np.uint8)#二值化

def getAnswerFileDict(directory):
    file_dict = {}
    for root, _, files in os.walk(directory):
        for file in files:
            file_path = os.path.join(root, file)
            with open(file_path, 'rb') as f:  # 以二进制读模式打开文件
                file_dict[file] = read_image(f)
    return file_dict


def getStudentFileDict(imgs):
    res = {}
    for img in imgs:
        img_stream = io.BytesIO(img.read())
        res[img.filename] = read_image(img_stream)

    return res


def dice_coefficient(y_true, y_pred):
    #如果是灰度图，则二值化处理
    if y_true.max() > 1:
        y_true = (y_true > 128).astype(np.uint8)
        y_pred = (y_pred > 128).astype(np.uint8)

    intersection = np.sum(y_true * y_pred)#计算像素交集
    union = np.sum(y_true) + np.sum(y_pred)#计算并集
    return 2.0 * intersection / union if union > 0 else 1.0


if __name__ == "__main__":
    print(dice_coefficient(imread("./answer/71.png"), imread("./answer/77.png")))