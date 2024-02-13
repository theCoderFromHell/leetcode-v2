package medium;

public class IntegerBreak {
    public static int integerBreak(int n) {
        int[] dp = new int[n+1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++)
            for (int j = i - 1; j >= 1; j--) {
                int max = Math.max(dp[j] * dp[i - j], Math.max(j * dp[i-j], dp[j] * (i-j)));
                dp[i] = Math.max(dp[i], Math.max(j * (i - j), max));
            }
        return dp[n];
    }

    public static void main(String[] args) {
        System.out.println(integerBreak(8));
    }
}
