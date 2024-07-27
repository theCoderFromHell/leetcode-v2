package hard;

import java.util.Arrays;

public class LongestIncreasingPathInAMatrix {
    int[] dx = {0,0,-1,1};
    int[] dy = {-1,1,0,0};
    int result;
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
            return 0;
        int rows = matrix.length;
        int columns = matrix[0].length;
        Integer[][] table = new Integer[rows][columns];
        for (Integer[] row : table)
            Arrays.fill(row, null);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result = Math.max(result, dfs(matrix, rows, columns, table, i, j, Integer.MIN_VALUE));
            }
        }
        return result;
    }

    private int dfs(int[][] matrix, int rows, int columns, Integer[][] table, int i, int j, Integer previous) {
        if (matrix[i][j] <= previous)
            return 0;
        if (table[i][j] != null)
            return table[i][j];
        int result = 0;
        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x >= 0 && x < rows && y >= 0 && y < columns)
                result = Math.max(result, dfs(matrix, rows, columns, table, x, y, matrix[i][j]));
        }
        table[i][j] = 1 + result;
        return table[i][j];
    }

    public static void main(String[] args) {
        LongestIncreasingPathInAMatrix L = new LongestIncreasingPathInAMatrix();
//        System.out.println(L.longestIncreasingPath(new int[][]{
//                {9,9,4},
//                {6,6,8},
//                {2,1,1}
//        }));
        System.out.println(L.longestIncreasingPath(new int[][]{
                {3,4,5},
                {3,2,6},
                {2,2,1}
        }));
    }
}
