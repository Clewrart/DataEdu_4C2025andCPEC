import json
file = '/www/datasci/uploads/stuwork.json'
# 读学生提交的结果（JSON 格式）
def read_result(file):
    if isinstance(file, str): # 确认文件路径
        with open(file, 'r') as f:
            return json.load(f)
    else:
        raise TypeError('提交错误')
