package medium;

public class PaintHouse {
    public int minCost(int[][] costs) {
        int N = costs.length;
        int[][] dp = new int[N][3];
        for (int i = 0; i < 3; i++)
            dp[0][i] = costs[0][i];
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < 3; j++) {
                switch (j) {
                    case 0:
                        dp[i][j] = costs[i][j] + Math.min(dp[i-1][1], dp[i-1][2]);
                        break;
                    case 1:
                        dp[i][j] = costs[i][j] + Math.min(dp[i-1][0], dp[i-1][2]);
                        break;
                    case 2:
                        dp[i][j] = costs[i][j] + Math.min(dp[i-1][0], dp[i-1][1]);
                        break;
                }
            }
        }
        return Math.min(dp[N-1][0], Math.min(dp[N-1][1], dp[N-1][2]));
    }
}
