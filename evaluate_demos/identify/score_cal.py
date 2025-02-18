# 计算目标检测平均精度以评分，从Iou，Ap，mAP为参考计算
import json
import numpy as np
from collections import defaultdict

# teacher_answer: 标准答案文件路径 student_results: 学生提交的结果（json格式）
teacher_answer = "./data/answer.json"
student_results = "./data/student_results.json"
##计算Iou
def compute_iou(box1, box2):
    # 将xywh转换为xyxy格式
    x1, y1, w1, h1 = box1
    box1 = [x1, y1, x1 + w1, y1 + h1]
    x2, y2, w2, h2 = box2
    box2 = [x2, y2, x2 + w2, y2 + h2]

    # 交集区域
    inter_left = max(box1[0], box2[0])
    inter_top = max(box1[1], box2[1])
    inter_right = min(box1[2], box2[2])
    inter_bottom = min(box1[3], box2[3])

    if inter_right < inter_left or inter_bottom < inter_top:
        return 0.0

    inter_area = (inter_right - inter_left) * (inter_bottom - inter_top)
    area1 = (box1[2] - box1[0]) * (box1[3] - box1[1])
    area2 = (box2[2] - box2[0]) * (box2[3] - box2[1])

    # 并集区域
    union_area = area1 + area2 - inter_area
    
    # 交集与并集的比值
    if union_area != 0:
        return (inter_area / union_area)
    else:
        return 0.0
    
# 计算AP mAp
def compute_ap(gt_dict, detections, iou_threshold, category_id):
    tp = []
    fp = []
    total_gt = 0

    # 统计真实框
    for img_id in gt_dict:
        total_gt += len(gt_dict[img_id].get(category_id, []))

    # 置信度降序排序检测结果
    sorted_detections = sorted(
        [det for det in detections if det['category_id'] == category_id],
        key=lambda x: -x['score']
    )

    # 初始化真实框的匹配状态
    gt_matched = defaultdict(list)
    for img_id in gt_dict:
        gt_matched[img_id] = [False] * len(gt_dict[img_id].get(category_id, []))

    # 遍历每个检测结果
    for det in sorted_detections:
        img_id = det['image_id']
        max_iou = 0.0
        matched_idx = -1

        # 处理当前类别的真实框
        gts = gt_dict[img_id].get(category_id, [])
        for idx, gt in enumerate(gts):
            if gt_matched[img_id][idx]:
                continue
            iou = compute_iou(det['bbox'], gt['bbox'])
            if iou > max_iou and iou >= iou_threshold:
                max_iou = iou
                matched_idx = idx

        if matched_idx != -1:
            tp.append(1)
            fp.append(0)
            gt_matched[img_id][matched_idx] = True
        else:
            tp.append(0)
            fp.append(1)

    # 累积精确率和召回率
    tp_cum = np.cumsum(tp)
    fp_cum = np.cumsum(fp)
    recall = tp_cum / (total_gt + 1e-6)
    precision = tp_cum / (tp_cum + fp_cum + 1e-6)

    # 计算AP
    ap = 0.0
    for i in range(1, len(precision)):
        ap += (recall[i] - recall[i-1]) * precision[i]
    return ap

def get_score(teacher_answer, student_results):
    # 教师答案文件转换为字典
    gt_dict = defaultdict(lambda: defaultdict(list))
    for ann in teacher_answer['annotations']:
        img_id = ann['image_id']
        cat_id = ann['category_id']
        gt_dict[img_id][cat_id].append({'bbox': ann['bbox']})

    # 获取所有类别和IoU阈值
    categories = set(ann['category_id'] for ann in teacher_answer['annotations'])

    # IoU从0.5到0.95的平均精度均值,每次步进0.05
    iou_thresholds = np.arange(0.5, 0.95, 0.05)
    aps = []

    for iou in iou_thresholds:
        category_aps = []
        for cat_id in categories:
            ap = compute_ap(gt_dict, student_results, iou, cat_id)
            category_aps.append(ap)
        mean_ap = np.mean(category_aps) if category_aps else 0
        aps.append(mean_ap)

    mAP = np.mean(aps)
    return round(mAP, 4)

if __name__ == '__main__':
    with open(teacher_answer, 'r') as f:
        teacher_answer = json.load(f)
    with open(student_results, 'r') as f:
        student_results = json.load(f)
    score = get_score(teacher_answer, student_results)
    print('mAP:', score)
