package medium;

public class IntegerBreak {
    public int integerBreak(int n) {
        int[] dp = new int[n+1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++)
            for (int j = 1; j <= i-1; j++) {
                int max = Math.max(j * (i - j), Math.max(dp[j] * dp[i - j], Math.max(j * dp[i-j], dp[j] * (i-j))));
                dp[i] = Math.max(dp[i], max);
            }
        return dp[n];
    }

    public static void main(String[] args) {
        IntegerBreak I = new IntegerBreak();
        System.out.println(I.integerBreak(8));
    }
}
