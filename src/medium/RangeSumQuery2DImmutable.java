package medium;

public class RangeSumQuery2DImmutable {
    public static void main(String[] args) {
        NumMatrix N = new NumMatrix(new int[][]{
                {3, 0, 1, 4, 2},
                {5, 6, 3, 2, 1},
                {1, 2, 0, 1, 5},
                {4, 1, 0, 1, 7},
                {1, 0, 3, 0, 5}});
        System.out.println(N.sumRegion(2, 1, 4, 3));
        System.out.println(N.sumRegion(1, 1, 2, 2));
        System.out.println(N.sumRegion(1, 2, 2, 4));
    }
}

class NumMatrix {
    int[][] prefixSum;
    int rows;
    int columns;

    public NumMatrix(int[][] matrix) {
        this.rows = matrix.length;
        this.columns = matrix[0].length;
        prefixSum = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            int sum = 0;
            for (int j = 0; j < columns; j++) {
                sum += matrix[i][j];
                prefixSum[i][j] = sum;
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int total = 0;
        for (int i = row1; i <= row2; i++) {
            int sum = prefixSum[i][columns-1];
            if (col1 > 0)
                sum -= prefixSum[i][col1 - 1];
            if (col2 < columns-1)
                sum -= (prefixSum[i][columns-1] - prefixSum[i][col2]);
            total += sum;
        }
        return total;
    }
}
