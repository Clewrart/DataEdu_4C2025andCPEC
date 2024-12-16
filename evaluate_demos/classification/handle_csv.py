import pandas as pd

def read_csv(file_path):

    df = pd.read_csv(file_path, header=None, names=['ID', 'CLASS'])
    dict_list = df.to_dict(orient='records')
    dict_result = {item['ID']: item['CLASS'] for item in dict_list}
    return dict_result

if __name__ == "__main__":
    print(read_csv("answer.csv"))