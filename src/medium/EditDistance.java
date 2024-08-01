package medium;

public class EditDistance {
    public int minDistance(String word1, String word2) {
        int M = word1.length();
        int N = word2.length();
        Integer[][] dp = new Integer[M+1][N+1];
        return minOperations(word1, word2, M, N, dp);
    }

    private int minOperations(String word1, String word2, int M, int N, Integer[][] dp) {
        if (M == 0) {
            dp[M][N] = N;
            return dp[M][N];
        }
        if (N == 0) {
            dp[M][N] = M;
            return dp[M][N];
        }
        if (dp[M][N] != null)
            return dp[M][N];

        if (word1.charAt(M-1) == word2.charAt(N-1))
            return dp[M][N] = minOperations(word1, word2, M-1, N-1, dp);
        int insert = minOperations(word1, word2, M, N-1, dp);
        int delete = minOperations(word1, word2, M-1, N, dp);
        int replace = minOperations(word1, word2, M-1, N-1, dp);
        dp[M][N] = 1 + Math.min(replace, Math.min(insert, delete));
        return dp[M][N];
    }
}
