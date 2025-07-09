package medium;

public class MaximumNumberOfFishInAGrid {
    int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
    public int findMaxFish(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int maxFish = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] > 0)
                    maxFish = Math.max(maxFish, dfs(i, j, rows, columns, grid));
            }
        }
        return maxFish;
    }

    private int dfs(int i, int j, int rows, int columns, int[][] grid) {
        int count = grid[i][j];
        grid[i][j] = 0;
        for (int[] direction : directions) {
            int x = i + direction[0];
            int y = j + direction[1];
            if (x >= 0 && x < rows && y >= 0 && y < columns && grid[x][y] > 0)
                count += dfs(x, y, rows, columns, grid);
        }
        return count;
    }

    public static void main(String[] args) {
        MaximumNumberOfFishInAGrid M = new MaximumNumberOfFishInAGrid();
        System.out.println(M.findMaxFish(new int[][]{{0,2,1,0},{4,0,0,3},{1,0,0,4},{0,3,2,0}}));
        System.out.println(M.findMaxFish(new int[][]{{1,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,1}}));
//        System.out.println(M.findMaxFish(new int[][]));
//        System.out.println(M.findMaxFish(new int[][]));
    }
}
