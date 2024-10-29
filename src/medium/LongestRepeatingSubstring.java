package medium;

public class LongestRepeatingSubstring {
    public int longestRepeatingSubstring(String s) {
        int length = s.length();
        char[] sChars = s.toCharArray();
        int[][] dp = new int[length+1][length+1];
        int result = 0;
        for (int i = 1; i <= length; i++) {
            for (int j = i+1; j <= length; j++) {
                if (sChars[i-1] == sChars[j-1]) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                    result = Math.max(result, dp[i][j]);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        LongestRepeatingSubstring L = new LongestRepeatingSubstring();
        System.out.println(L.longestRepeatingSubstring("abcd"));
        System.out.println(L.longestRepeatingSubstring("abbaba"));
        System.out.println(L.longestRepeatingSubstring("aabcaabdaab"));
    }
}
