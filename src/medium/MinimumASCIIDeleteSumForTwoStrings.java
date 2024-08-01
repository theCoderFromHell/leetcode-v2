package medium;

public class MinimumASCIIDeleteSumForTwoStrings {
    public int minimumDeleteSum(String s1, String s2) {
        int M = s1.length();
        int N = s2.length();
        Integer[][] dp = new Integer[M+1][N+1];
        return solve(s1, s2, M, N, dp);
    }

    private int solve(String s1, String s2, int M, int N, Integer[][] dp) {
        if (M == 0) {
            dp[M][N] = asciiSum(s2, 0, N - 1);
            return dp[M][N];
        }
        if (N == 0) {
            dp[M][N] = asciiSum(s1, 0, M - 1);
            return dp[M][N];
        }

        if (dp[M][N] != null)
            return dp[M][N];
        if (s1.charAt(M-1) == s2.charAt(N-1)) {
            dp[M][N] = solve(s1, s2, M - 1, N - 1, dp);
            return dp[M][N];
        }

        int deleteInFirst = s1.charAt(M - 1) + solve(s1, s2, M - 1, N, dp);
        int deleteInSecond = dp[M][N] = s2.charAt(N - 1) + solve(s1, s2, M, N - 1, dp);
        dp[M][N] = Math.min(deleteInFirst, deleteInSecond);
        return dp[M][N];
    }

    private int asciiSum(String text, int start, int end) {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum += (text.charAt(i));
        }
        return sum;
    }

    public static void main(String[] args) {
        MinimumASCIIDeleteSumForTwoStrings M = new MinimumASCIIDeleteSumForTwoStrings();
        System.out.println(M.minimumDeleteSum("sea", "eat"));
    }
}
