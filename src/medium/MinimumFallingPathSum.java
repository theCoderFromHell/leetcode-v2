package medium;

public class MinimumFallingPathSum {
    public int minFallingPathSum(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        Integer[][] dp = new Integer[rows][columns];
        int minSum = Integer.MAX_VALUE;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int sum = dfs(matrix, rows, columns, i, j, dp);
                if (i == 0)
                    minSum = Math.min(minSum, sum);
            }
        }
        return minSum;
    }

    private int dfs(int[][] matrix, int rows, int columns, int i, int j, Integer[][] dp) {
        if (dp[i][j] != null)
            return dp[i][j];
        if (i == rows-1) {
            dp[i][j] = matrix[i][j];
            return  dp[i][j];
        }
        int left = (j-1 < 0) ? Integer.MAX_VALUE : dfs(matrix, rows, columns, i+1, j-1, dp);
        int right = (j+1 >= columns) ? Integer.MAX_VALUE : dfs(matrix, rows, columns, i+1, j+1, dp);
        int bottom = dfs(matrix, rows, columns, i+1, j, dp);
        dp[i][j] = matrix[i][j] + Math.min(bottom, Math.min(left, right));
        return dp[i][j];
    }

    public static void main(String[] args) {
        MinimumFallingPathSum M = new MinimumFallingPathSum();
        System.out.println(M.minFallingPathSum(new int[][]{
                {2,1,3},
                {6,5,4},
                {7,8,9}
        }));
    }

}
