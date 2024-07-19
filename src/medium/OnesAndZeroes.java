package medium;


public class OnesAndZeroes {
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (String str : strs) {
            int zeros = 0, ones = 0;
            for (Character c : str.toCharArray()) {
                if (c.equals('0'))
                    zeros++;
                else
                    ones++;
            }
            for (int i = m; i >= 0; i--) {
                for (int j = n; j >= 0; j--) {
                    if (zeros <= i && ones <= j) {
                        dp[i][j] = Math.max(dp[i][j], 1 + dp[i - zeros][j - ones]);
                    }
                }
            }
        }
        return dp[m][n];
    }
}
