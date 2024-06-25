package medium;

public class LongestPalindromicSubsequence {
    public int longestPalindromeSubseq(String s) {
        int N = s.length();
        int[][] dp = new int[N][N];
        int maxLength = 1;
        for (int i = 0; i < N; i++) {
            dp[i][i] = 1;
            if (i+1 < N) {
                dp[i][i+1] = (s.charAt(i) == s.charAt(i+1)) ? 2 : 1;
                maxLength = Math.max(maxLength, dp[i][i+1]);
            }
        }

        for (int gap = 3; gap <= N; gap++) {
            for (int i = 0; i < N - gap + 1; i++) {
                int j = i + gap - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = 2 + dp[i + 1][j - 1];
                } else
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                maxLength = Math.max(maxLength, dp[i][j]);
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        LongestPalindromicSubsequence L = new LongestPalindromicSubsequence();
        System.out.println(L.longestPalindromeSubseq("bbbab"));
        System.out.println(L.longestPalindromeSubseq("cbbd"));
        System.out.println(L.longestPalindromeSubseq("bbxyzxbytbv"));

    }
}
