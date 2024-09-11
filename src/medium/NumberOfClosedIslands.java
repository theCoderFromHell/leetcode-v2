package medium;

public class NumberOfClosedIslands {
    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};
    public int closedIsland(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int[][] visited = new int[rows][columns];
        int result = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] == 0 && visited[i][j] == 0 && dfs(grid, i, j, rows, columns, visited))
                    result++;
            }
        }
        return result;
    }

    private boolean dfs(int[][] grid, int i, int j, int rows, int columns, int[][] visited) {
        if (i == 0 || j == 0 || i == rows-1 || j == columns-1)
            return false;
        visited[i][j] = 1;
        boolean isClosed = true;
        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (valid(x, y, rows, columns) && grid[x][y] == 0 && visited[x][y] == 0)
                if (!dfs(grid, x, y, rows, columns, visited))
                    isClosed = false;
        }
        return isClosed;
    }

    private boolean valid(int x, int y, int rows, int columns) {
        if (x < 0 || x >= rows)
            return false;
        if (y < 0 || y >= columns)
            return false;
        return true;
    }
}
