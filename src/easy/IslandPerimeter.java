package easy;

public class IslandPerimeter {
    int[] dx = {0,0,-1,1};
    int[] dy = {-1,1,0,0};
    int perimeter = 0;
    public int islandPerimeter(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        boolean[][] visited = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!visited[i][j] && grid[i][j] == 1) {
                    dfs(grid, i, j, rows, columns, visited);
                }
            }
        }
        return perimeter;
    }

    private void dfs(int[][] grid, int i, int j, int rows, int columns, boolean[][] visited) {
        visited[i][j] = true;
        int count = 4;
        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x >= 0 && y >= 0 && x < rows && y < columns && grid[x][y] == 1) {
                count--;
                if (!visited[x][y])
                    dfs(grid, x, y, rows, columns, visited);
            }
        }
        perimeter += count;
    }
}
