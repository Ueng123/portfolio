class MatrixError(Exception):
    pass

# =========== I N F O =========== #
# the first index of matrix is 1. #
# =============================== #
class Matrix():

    def __init__(self, row:int, column:int):

        self.items = [[0] * column for _ in range(row)]

        self.row    = row
        self.column = column

    def I(size:int):

        result = Matrix(size, size)

        for i in range(size):
            result.items[i][i] = 1

        return result

    def T(self):

        result = Matrix(self.column, self.row)

        for i in range(self.row):
            for j in range(self.column):
                result(j, i, self(i, j))

        return result

    def det(self):
        if self.row != self.column:
            raise MatrixError("The matrix should be square matrix to get determinant")
        if self.row == 1:
            return self(1, 1)
        if self.row == 2:
            return self(1, 1) * self(2, 2) - self(1, 2) * self(2, 1)
        else:
            determinant = 0
            for j in range(1, self.column + 1):
                sub_matrix = Matrix(self.row - 1, self.column - 1)
                sub_rows = 0
                for row in range(1, self.row + 1):
                    if row != 1:
                        sub_cols = 0
                        for col in range(1, self.column + 1):
                            if col != j:
                                sub_matrix(sub_rows + 1, sub_cols + 1, self(row, col))
                                sub_cols += 1
                        sub_rows += 1
                sign = (-1)**(1 + j)
                determinant += sign * self(1, j) * sub_matrix.det()
            return determinant

    def __call__(self, row:int, column:int, value:float = None):

        if (value): self.items[row-1][column-1] = value

        return self.items[row-1][column-1]

    def setValues(self, value:list):
        vRow = len(value)
        vColumn = len(value[0])
        for row in value:
            newVColumn = len(row)

            if vColumn != newVColumn: raise MatrixError("The length of each row should be same")

            vColumn = newVColumn

        if not (self.row == vRow and self.column == vColumn): raise MatrixError("The size of value and target matrix should be same")


        self.items = value

    def __str__(self):
    
        result = ""

        for row in self.items:

            result += "( "
            result += "  ".join(map(str, row))
            result += " )\n"

        return result[:-1]

    def __add__(self, other):
        if not isinstance(other, Matrix): raise TypeError()
        if not (self.row == other.row and self.column == other.column): raise MatrixError("The size of each matrix should be same")

        result = Matrix(self.row, self.column)
        for i in range(self.row):
            for j in range(self.column):
                result(i, j, self(i, j) + other(i, j))

        return result

    def __mul__(self, other:float):
        if isinstance(other, float) or isinstance(other, int):
            
            result = Matrix(self.row, self.column)
            for i in range(self.row):
                for j in range(self.column):
                    result(i, j, self(i, j) * other)

            return result

        if isinstance(other, Matrix):
            if not (self.column == other.row): raise MatrixError("When A * B, column of A should be same with row of B")

            result = Matrix(self.row, other.column)
            for i in range(self.row):
                for j in range(other.column):
                    result(i, j, sum([self(i, x+1) * other(x+1, j) for x in range(self.column)]))

            return result
