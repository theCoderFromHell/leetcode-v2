package easy;

import java.util.Arrays;

public class ModifyTheMatrix {
    public int[][] modifiedMatrix(int[][] matrix) {
        int M = matrix.length;
        int N = matrix[0].length;
        for (int j = 0; j < N; j++) {
            int maximum = Integer.MIN_VALUE;
            for (int i = 0; i < M; i++)
                maximum = Math.max(maximum, matrix[i][j]);
            for (int i = 0; i < M; i++)
                if (matrix[i][j] == -1)
                    matrix[i][j] = maximum;
        }
        return matrix;
    }
}
