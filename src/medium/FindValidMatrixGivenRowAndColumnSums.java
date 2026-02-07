package medium;

import common.Util;

public class FindValidMatrixGivenRowAndColumnSums {
    public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
        int rows = rowSum.length;
        int columns = colSum.length;
        int[][] result = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int value = Math.min(rowSum[i], colSum[j]);
                result[i][j] = value;
                rowSum[i] -= value;
                colSum[j] -= value;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        FindValidMatrixGivenRowAndColumnSums solver = new FindValidMatrixGivenRowAndColumnSums();
        int[] rowSum = {5, 7, 10};
        int[] colSum = {8, 6, 8};
        Util.printMatrix(solver.restoreMatrix(rowSum, colSum));
    }
}
