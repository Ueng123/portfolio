import math
from functools import reduce
from primefac import primefac as primeFactorzaze

class NumberError(Exception):
    pass

def getDivisor(num:int):

    result = (list(map(lambda x: x if (num%x==0) else 0, range(1, math.floor(math.sqrt(num)) + 1))))
    result = list(set(list(result)))
    result.remove(0)

    result2 = []
    for i in result:
        result2.append(int(num/i))

    return set(result + result2 + [1, num])

class Number():

    def __init__(self, num:float, divBy:float=1):

        if (divBy == 0): raise NumberError("U CANNOT DIVIDE BY 0 U IDIOT??")

        mulN = 1

        if (int(num) != num):
            zNum = len(str(num))-2
            print(f"zNum of N = {zNum}")
            mulN = 1 if zNum==0 else 10 ** zNum

        if (int(divBy) != divBy):
            zNum = len(str(divBy))-2
            print(f"zNum of DB = {zNum}")
            mulN = max(mulN, 1 if zNum==0 else 10 ** zNum)

        num *= mulN
        divBy *= mulN

        self.value = abs(int(num))
        self.divBy = abs(int(divBy))
        self.sign = int((num*divBy)/math.fabs(num*divBy))

    def reduce(self):

        primes_A = list(primeFactorzaze(self.value))
        primes_B = list(primeFactorzaze(self.divBy))

        resultA = primes_A.copy()
        resultB = primes_B.copy()

        for i in primes_A:
            try:resultB.remove(i)
            except:pass

        for i in primes_B:
            try:resultA.remove(i)
            except:pass

        self.value = 1 if len(resultA) == 0 else reduce(lambda x, y: x*y, resultA)
        self.divBy = 1 if len(resultB) == 0 else reduce(lambda x, y: x*y, resultB)

        return self

    def __str__(self):

        return f"{'-(' if self.sign == -1 else ''}{self.value}{'' if self.jisu[0] == 1 else f'^{self.jisu[0]}'}{'' if self.divBy == 1 else f'/{self.divBy}'}{'' if self.jisu[1] == 1 else f'^{self.jisu[1]}'}{')' if self.sign == -1 else ''}"

    def __add__(self, other):

        if not isinstance(other, Number): raise NumberError("Only Number can be operated with Number")

        return Number(self.value*other.divBy + other.value*self.divBy, self.divBy*other.divBy).reduce()

    def __sub__(self, other):

        if not isinstance(other, Number): raise NumberError("Only Number can be operated with Number")

        return Number(self.value*other.divBy - other.value*self.divBy, self.divBy*other.divBy).reduce()

    def pow(self, atUP:int, atDOWN:int = None):

        self.value **= atUP
        self.divBy **= atDOWN if atDOWN else atUP

        return self

    def __mul__(self, other):

        if not isinstance(other, Number): raise NumberError("Only Number can be operated with Number")

        return Number(self.value*other.value, self.divBy*other.divBy).reduce()

    def __truediv__(self, other):

        if not isinstance(other, Number): raise NumberError("Only Number can be operated with Number")

        return Number(self.value*other.divBy, self.divBy*other.value).reduce()


# class VariableNumber(Number):

# class Term():
