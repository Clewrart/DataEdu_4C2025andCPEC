import json

def read_json(file):
    if isinstance(file, str):
        with open(file, 'r') as f:
            return json.load(f)
    else:
        return json.load(file)

if __name__ == "__main__":
    data = read_json("./answer.json")
    print(data)
