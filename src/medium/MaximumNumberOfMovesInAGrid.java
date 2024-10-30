package medium;

public class MaximumNumberOfMovesInAGrid {
    int[] dx = {-1, 0, 1};
    int[] dy = {1, 1, 1};
    public int maxMoves(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int result = 0;
        Integer[][] dp = new Integer[rows][columns];
        for (int i = 0; i < rows; i++) {
            result = Math.max(result, dfs(grid, rows, columns, dp, i, 0));
        }
        return result - 1;
    }

    private int dfs(int[][] grid, int rows, int columns, Integer[][] dp, int i, int j) {
        if (dp[i][j] != null)
            return dp[i][j];
        int count = 0;
        for (int k = 0; k < 3; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (!outOfBounds(x, y, rows, columns) && grid[i][j] < grid[x][y] )
                count = Math.max(count, dfs(grid, rows, columns, dp, x, y));
        }
        dp[i][j] = 1 + count;
        return dp[i][j];
    }

    private boolean outOfBounds(int i, int j, int rows, int columns) {
        return (i < 0 || i >= rows || j < 0 || j >= columns);
    }

    public static void main(String[] args) {
        MaximumNumberOfMovesInAGrid M = new MaximumNumberOfMovesInAGrid();
        System.out.println(M.maxMoves(new int[][]{{2,4,3,5},{5,4,9,3},{3,4,2,11},{10,9,13,15}}));
        System.out.println(M.maxMoves(new int[][]{{3,2,4},{2,1,9},{1,1,7}}));
    }
}
