package interviews.others;

public class Tekion {

    public static int timeToRot(int[][] matrix) {
        if(matrix == null)
            return -1;
        int rows = matrix.length;
        if(rows == 0)
            return -1;
        int columns = matrix[0].length;
        if(columns == 0)
            return -1;
        int[][] dp = new int[rows][columns];
        for(int i=0; i<rows; i++) {
            for(int j=0;j<columns; j++) {
                if(matrix[i][j] == 0)
                    dp[i][j] = Integer.MAX_VALUE;
                else if(matrix[i][j] == 2)
                    dp[i][j] = 0;
            }
        }
        for(int i=0; i<rows; i++) {
            for(int j=0;j<columns; j++) {
                if(matrix[i][j] == 1) {
                    int minimumNeighbour = minimumNeighbour(i, j, dp, rows, columns);
                    if(minimumNeighbour == Integer.MAX_VALUE)
                        return -1;
                    else {
                        dp[i][j] = minimumNeighbour + 1;
                    }
                }
            }
        }
        int result = Integer.MIN_VALUE;
        for(int i=0; i<rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(dp[i][j] != Integer.MAX_VALUE)
                    result = Math.max(result, dp[i][j]);
            }
        }
        return (result == Integer.MAX_VALUE) ? -1 : result;

    }

    private static int minimumNeighbour(int i, int j, int[][] dp, int rows, int columns) {
        int top = Integer.MAX_VALUE;
        int bottom = Integer.MAX_VALUE;
        int left = Integer.MAX_VALUE;
        int right = Integer.MAX_VALUE;
        if(i-1 >=0)
            top = Math.min(top, dp[i-1][j]);
        if(i+1 < rows)
            bottom = Math.min(bottom, dp[i+1][j]);
        if(j-1 >= 0)
            left = Math.min(left, dp[i][j-1]);
        if(j+1 < columns)
            right = Math.min(right, dp[i][j+1]);
        return Math.min(top, Math.min(bottom, Math.min(left, right)));
    }

    public static void main(String[] args) {
        System.out.println(timeToRot(new int[][] {
                {2, 1, 0, 2, 1},
                {0, 0, 1, 2, 1},
                {1, 0, 0, 2, 1}
        }));

    }
}
