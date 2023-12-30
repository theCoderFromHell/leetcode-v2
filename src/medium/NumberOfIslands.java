package medium;

public class NumberOfIslands {
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    public static int numIslands(char[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int[][] visited = new int[rows][columns];
        int result = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] == '1' && visited[i][j] == 0) {
                    result++;
                    dfs(grid, i, j, rows, columns, visited);
                }
            }
        }
        return result;
    }

    private static void dfs(char[][] grid, int i, int j, int rows, int columns, int[][] visited) {
        visited[i][j] = 1;
        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (valid(x, y, rows, columns) && grid[x][y] == '1' && visited[x][y] == 0)
                dfs(grid, x, y, rows, columns, visited);
        }
    }

    private static boolean valid(int x, int y, int rows, int columns) {
        if (x < 0 || x >= rows)
            return false;
        if (y < 0 || y >= columns)
            return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println(numIslands(new char[][]{
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        }));
    }
}
