package medium;

public class MaximumSumOfAnHourglass {
    public int maxSum(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int maxHourGlass = Integer.MIN_VALUE;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (i + 2 < rows && j + 2 < columns)
                    maxHourGlass = Math.max(maxHourGlass, thisHourGlass(grid, i, j));
            }
        }
        return maxHourGlass;
    }

    private int thisHourGlass(int[][] grid, int i, int j) {
        return grid[i][j] + grid[i][j+1] + grid[i][j+2] + grid[i+1][j+1] + grid[i+2][j] + grid[i+2][j+1] + grid[i+2][j+2];
    }

    public static void main(String[] args) {
        MaximumSumOfAnHourglass M = new MaximumSumOfAnHourglass();
        System.out.println(M.maxSum(new int[][]{{6,2,1,3},{4,2,1,5},{9,2,8,7},{4,1,2,9}}));
        System.out.println(M.maxSum(new int[][]{{1,2,3},{4,5,6},{7,8,9}}));
        System.out.println(M.maxSum(new int[][]{{7,7,7,7},{7,7,7,7},{7,7,7,7}}));
        System.out.println(M.maxSum(new int[][]{{0,0,0,0},{0,1,0,0},{0,0,0,0}}));
        System.out.println(M.maxSum(new int[][]{{1,1,1,1,1},{1,1,1,1,1},{1,1,2,1,1},{1,1,1,1,1},{1,1,1,1,1}}));
    }
}
