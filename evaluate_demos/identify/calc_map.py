import numpy as np
from collections import defaultdict
from handle_json import read_json

def compute_iou(box1, box2):
    # 将 xywh 格式转换为 xyxy 格式
    x1, y1, w1, h1 = box1
    box1 = [x1, y1, x1 + w1, y1 + h1]
    x2, y2, w2, h2 = box2
    box2 = [x2, y2, x2 + w2, y2 + h2]

    # 计算交集区域
    inter_left = max(box1[0], box2[0])
    inter_top = max(box1[1], box2[1])
    inter_right = min(box1[2], box2[2])
    inter_bottom = min(box1[3], box2[3])

    if inter_right < inter_left or inter_bottom < inter_top:
        return 0.0

    inter_area = (inter_right - inter_left) * (inter_bottom - inter_top)
    area1 = (box1[2] - box1[0]) * (box1[3] - box1[1])
    area2 = (box2[2] - box2[0]) * (box2[3] - box2[1])
    union_area = area1 + area2 - inter_area
    if union_area == 0:
        return 0.0
    return inter_area / union_area

def compute_ap(gt_dict, detections, iou_threshold, category_id):
    tp = []
    fp = []
    total_gt = 0
    # 统计该类别所有图像中的真实标注数
    for img_id in gt_dict:
        total_gt += len(gt_dict[img_id].get(category_id, []))
    
    # 按置信度降序排序检测结果
    sorted_detections = sorted(
        [det for det in detections if det['category_id'] == category_id],
        key=lambda x: -x['score']
    )

    # 初始化真实标注的匹配状态
    gt_matched = defaultdict(list)
    for img_id in gt_dict:
        gt_matched[img_id] = [False] * len(gt_dict[img_id].get(category_id, []))
    
    # 遍历每个检测结果
    for det in sorted_detections:
        img_id = det['image_id']
        max_iou = 0.0
        matched_idx = -1
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
    
    # 累计 TP 和 FP，计算召回率和精确率
    tp_cum = np.cumsum(tp)
    fp_cum = np.cumsum(fp)
    recall = tp_cum / (total_gt + 1e-6)
    precision = tp_cum / (tp_cum + fp_cum + 1e-6)
    
    # 计算 AP
    ap = 0.0
    for i in range(1, len(precision)):
        ap += (recall[i] - recall[i-1]) * precision[i]
    return ap

def get_score(teacher_answer, student_results):
    """
    根据教师答案和学生提交结果计算 mAP 得分
    teacher_answer: 标准答案（dict格式，包含 'annotations' 字段）
    student_results: 学生提交的检测结果（dict格式，包含检测框、类别和置信度等信息）
    """
    # 构造以图像和类别为层级的真实标注字典
    gt_dict = defaultdict(lambda: defaultdict(list))
    for ann in teacher_answer['annotations']:
        img_id = ann['image_id']
        cat_id = ann['category_id']
        gt_dict[img_id][cat_id].append({'bbox': ann['bbox']})
    
    # 获取所有类别
    categories = set(ann['category_id'] for ann in teacher_answer['annotations'])
    
    # 设定 IoU 阈值，从 0.5 到 0.95，步长 0.05
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
    teacher_answer = read_json("./answer.json")
    student_results = read_json("./student_results.json")
    score = get_score(teacher_answer, student_results)
    print('mAP:', score)
 