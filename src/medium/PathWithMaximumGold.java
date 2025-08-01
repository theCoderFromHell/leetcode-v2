package medium;

public class PathWithMaximumGold {
    int[][] directions = new int[][]{{0,1},{0,-1},{1,0},{-1,0}};
    public int getMaximumGold(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int maxGold = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boolean[][] visited = new boolean[rows][columns];
                if (grid[i][j] > 0)
                    maxGold = Math.max(maxGold, dfs(grid, i, j, rows, columns, visited));
            }
        }
        return maxGold;

    }

    private int dfs(int[][] grid, int i, int j, int rows, int columns, boolean[][] visited) {
        visited[i][j] = true;
        int gold = 0;
        for (int[] direction : directions) {
            int x = i + direction[0];
            int y = j + direction[1];
            if (x >= 0 && x < rows && y >= 0 && y < columns && !visited[x][y] && grid[x][y] > 0)
                gold = Math.max(gold, dfs(grid, x, y, rows, columns, visited));
        }
        visited[i][j] = false;
        return grid[i][j] + gold;
    }

    public static void main(String[] args) {
        PathWithMaximumGold P = new PathWithMaximumGold();
        System.out.println(P.getMaximumGold(new int[][]{{0,6,0},{5,8,7},{0,9,0}}));
        System.out.println(P.getMaximumGold(new int[][]{{1,0,7},{2,0,6},{3,4,5},{0,3,0},{9,0,20}}));
        System.out.println(P.getMaximumGold(new int[][]{{0,0,0,0,0,0,32,0,0,20},{0,0,2,0,0,0,0,40,0,32},{13,20,36,0,0,0,20,0,0,0},{0,31,27,0,19,0,0,25,18,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,18,0,6},{0,0,0,25,0,0,0,0,0,0},{0,0,0,21,0,30,0,0,0,0},{19,10,0,0,34,0,2,0,0,27},{0,0,0,0,0,34,0,0,0,0}}));
    }
}
