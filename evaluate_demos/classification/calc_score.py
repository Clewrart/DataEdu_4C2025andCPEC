from handle_csv import read_csv



def calc_score(answer:dict,real:dict):
    base=len(answer)
    count=0
    for r in real:
        if r in answer and answer[r]==real[r]:
            count=count+1
    
    return round(count/base * 100,2)

if __name__ == "__main__":
    answer = read_csv("./answer.csv")
    real = read_csv("./student_test.csv")
    print(calc_score(answer,real))