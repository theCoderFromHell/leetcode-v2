package medium;

public class NumberOfEnclaves {
    public int numEnclaves(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i == 0 || i == rows-1 || j == 0 || j == columns-1) {
                    if (grid[i][j] == 1) {
                        dfs(grid, rows, columns, i, j);
                    }
                }
            }
        }
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] == 1)
                    count++;
            }
        }
        return count;
    }

    private void dfs(int[][] grid, int rows, int columns, int i, int j) {
        if (i < 0 || i >= rows || j < 0 || j >= columns)
            return;
        if (grid[i][j] != 1)
            return;
        grid[i][j] = 2;
        dfs(grid, rows, columns, i, j-1);
        dfs(grid, rows, columns, i, j+1);
        dfs(grid, rows, columns, i-1, j);
        dfs(grid, rows, columns, i+1, j);
    }
}
