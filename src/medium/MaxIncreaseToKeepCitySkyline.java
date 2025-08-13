package medium;

public class MaxIncreaseToKeepCitySkyline {
    public int maxIncreaseKeepingSkyline(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int[] maxRows = new int[rows];
        int[] maxColumns = new int[columns];
        for (int i = 0; i < rows; i++) {
            int max = grid[i][0];
            for (int j = 0; j < columns; j++)
                max = Math.max(max, grid[i][j]);
            maxRows[i] = max;
        }
        for (int j = 0; j < columns; j++) {
            int max = grid[0][j];
            for (int i = 0; i < rows; i++)
                max = Math.max(max, grid[i][j]);
            maxColumns[j] = max;
        }
        int result = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int updatedValue = Math.min(maxRows[i], maxColumns[j]);
                result += updatedValue - grid[i][j];
            }
        }
        return result;
    }
}
