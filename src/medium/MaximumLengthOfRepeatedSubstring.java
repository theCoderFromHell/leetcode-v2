package medium;

public class MaximumLengthOfRepeatedSubstring {
    public static int findLength(String[] strings1, String[] strings2) {
        int M = strings1.length;
        int N = strings2.length;
        int[][] dp = new int[M+1][N+1];
        for (int i = 0; i <= M; i++)
            for (int j = 0; j <= N; j++)
                if (i == 0 || j == 0)
                    dp[i][j] = 0;
        int result = 0;
        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                if ((strings1[i - 1] == null && strings2[j - 1] == null) || (strings1[i - 1] != null && strings2[j - 1] != null && strings1[i - 1].equals(strings2[j - 1]))) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    result = Math.max(result, dp[i][j]);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(findLength(new String[]{"hi", "bye", "hello", "leetcode", "start", "end"}, new String[]{"hi", "stop", "leetcode", "start", "end", "bye"}));
        System.out.println(findLength(new String[]{}, new String[]{}));
        System.out.println(findLength(new String[]{}, new String[]{}));
    }
}
