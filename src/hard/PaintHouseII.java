package hard;

public class PaintHouseII {
    public int minCostII(int[][] costs) {
        int N = costs.length;
        int k = costs[0].length;
        int[][] dp = new int[N][k];
        for (int i = 0; i < k; i++)
            dp[0][i] = costs[0][i];
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < k; j++) {
                int minimum = Integer.MAX_VALUE;
                for (int l = 0; l < k; l++) {
                    if (l == j)
                        continue;
                    minimum = Math.min(minimum, dp[i-1][l]);
                }
                dp[i][j] = costs[i][j] + minimum;
            }
        }
        int minimum = Integer.MAX_VALUE;
        for (int l = 0; l < k; l++)
            minimum = Math.min(minimum, dp[N - 1][l]);
        return minimum;
    }
}
