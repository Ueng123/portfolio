import re

print("="*20)
print("   PROGRAM STARTED")
print("="*20)
print()

fileLines = []

dataTypes = [
    ["int", "-?[0-9]*"],
    ["float", "-?[0-9]*\.[0-9]*"],
    ["string", '".*"'],
    ["bool", "(true)|(false)"]
]

var = {}
func = {}

isStoppedByError = False

def ERR(text, ln):
    global isStoppedByError
    isStoppedByError = True
    print(f"ERROR IN LINE {ln}\n\t{text}")

def checkIsVar(line:str):
    pattern = "\w+ \w+ *=.+"

    return bool(re.fullmatch(pattern, line))

def checkIsCalling(line:str):
    pattern = "\w+\([^()]*\)"

    return bool(re.fullmatch(pattern, line))

def checkIsPutting(line:str):
    pattern = "\w+ *<< *\w+\([^()]*\)"

    return bool(re.fullmatch(pattern, line))

def checkDataType(data:str):
    for datatype in dataTypes:
        if re.fullmatch(datatype[1], data):
            return datatype[0]
    return "ERR"

with open("anAwesomeText.txt", "r", encoding="UTF-8") as f:
    for line in f.readlines():
        line = line.strip()

        fileLines.append(line[:-2] if line.strip().endswith("\\n") else line)

ln = 0
for line in fileLines:
    ln+=1

    if not line: continue
    if line.startswith("//"): continue

    line:str
    if checkIsVar(line):
        spl = line.split(" ")
        while True: 
            try: spl.remove(' ')
            except: break
        if not (spl[0] == checkDataType(spl[3])): ERR("var type missed", ln); break
        var[spl[1]] = (spl[3], spl[0])
        continue

    if checkIsCalling(line): # 'A' '<<' 'B'('C')
        spl = line[:-1].split("(")
        while True: 
            try: spl.remove(' ')
            except: break
        spl[1] = spl[1].split(",")

        # spl = [[A, B], [C ... ]]

        j=0
        for t in spl[1]: spl[1][j] = t.strip(); j+=1

        if spl[0]=="log":
            if checkDataType(spl[1][0]) != "ERR": print(spl[1][0])
            elif var.get(spl[1][0]): print(var.get(spl[1][0])[0])
            else: ERR(f"bro waht did u put???", ln); break

        if spl[0]=="type":
            if checkDataType(spl[1][0]) != "ERR": print(checkDataType(spl[1][0]))
            elif var.get(spl[1][0]): print(var.get(spl[1][0])[1])
            else: ERR(f"bro waht did u put???", ln); break

        if spl[0]=="add":
            toADD = []
            for i in spl[1]:
                if checkDataType(i) in ["int", "float"]: toADD.append(float(i))
                elif var.get(i):
                    if var.get(i)[1] in ["int", "float"]: toADD.append(float(var.get(i)[0]))

            print(sum(toADD))

        if spl[0]=="mul":
            toMUL = []
            for i in spl[1]:
                if checkDataType(i) in ["int", "float"]: toMUL.append(float(i))
                elif var.get(i):
                    if var.get(i)[1] in ["int", "float"]: toMUL.append(float(var.get(i)[0]))

            result = 1
            for item in toMUL:result*=item
            print(str(result))

        continue

    if checkIsPutting(line):

        spl = line[:-1].split("(")
        while True: 
            try: spl.remove(' ')
            except: break
        spl[0] = spl[0].split("<<")
        spl[1] = spl[1].split(",")

        j=0
        for t in spl[0]: spl[0][j] = t.strip(); j+=1
        j=0
        for t in spl[1]: spl[1][j] = t.strip(); j+=1

        if spl[0][1]=="log":
            if checkDataType(spl[1][0]) != "ERR": print(spl[1][0]); var[spl[0][0]]=(spl[1][0], checkDataType(spl[1][0]))
            elif var.get(spl[1][0]): print(var.get(spl[1][0])[0]); var[spl[0][0]]=(var.get(spl[1][0])[0], checkDataType(spl[1][0]))
            else: ERR(f"bro waht did u put???", ln)

        if spl[0][1]=="add":
            toADD = []
            for i in spl[1]:
                if checkDataType(i) in ["int", "float"]: toADD.append(float(i))
                elif var.get(i):
                    if var.get(i)[1] in ["int", "float"]: toADD.append(float(var.get(i)[0]))

            var[spl[0][0]]=(str(sum(toADD)), checkDataType(str(sum(toADD))))

        if spl[0][1]=="mul":
            toMUL = []
            for i in spl[1]:
                if checkDataType(i) in ["int", "float"]: toMUL.append(float(i))
                elif var.get(i):
                    if var.get(i)[1] in ["int", "float"]: toMUL.append(float(var.get(i)[0]))

            result = 1
            for item in toMUL:result*=item
            var[spl[0][0]]=(str(result), checkDataType(str(result)))

        continue

    ERR(f"unknown command : \n\t{line}", ln)
    break

print()
print("="*20)

# print("\nresult of executation:")
# if not isStoppedByError:
#     i = 0
#     for v in var.values():
#         print(f"{v[1]} {list(var.keys())[i]} = {v[0]}")
#         i+=1
# else:
#     print("error occured")
