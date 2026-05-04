package medium;

// https://leetcode.com/problems/uncrossed-lines/
public class UncrossedLines {
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int size1 = nums1.length;
        int size2 = nums2.length;
        int[][] dp = new int[size1+1][size2+1];
        for (int i = 0; i < size1; i++) {
            for (int j = 0; j < size2; j++) {
                if (nums1[i] == nums2[j])
                    dp[i + 1][j + 1] = 1 + dp[i][j];
                else
                    dp[i+1][j+1] = Math.max(dp[i][j+1], dp[i+1][j]);
            }
        }
        return dp[size1][size2];
    }

    /*
     * Revision Note — Uncrossed Lines (Medium)
     *
     * Pattern: Longest Common Subsequence (LCS) — classic 2D DP
     *
     * Key Insight: "Uncrossed lines" is exactly LCS in disguise — two lines cross
     * if and only if their index pairs are out of order in one array, which is
     * precisely the condition that breaks a common subsequence.
     *
     * Gotchas:
     * - Recognising the LCS disguise is the entire problem — once seen, the code is standard
     * - Use 1-indexed DP table (size+1) to avoid i-1/j-1 boundary checks
     *
     * Template:
     *   int[][] dp = new int[m+1][n+1];
     *   for i in 0..m-1:
     *       for j in 0..n-1:
     *           if nums1[i]==nums2[j]: dp[i+1][j+1] = dp[i][j] + 1
     *           else: dp[i+1][j+1] = max(dp[i][j+1], dp[i+1][j])
     *   return dp[m][n]
     */
    public static void main(String[] args) {
        UncrossedLines U = new UncrossedLines();
        System.out.println(U.maxUncrossedLines(new int[]{1, 4, 2}, new int[]{1, 2, 4}));           // 2
        System.out.println(U.maxUncrossedLines(new int[]{2, 5, 1, 2, 5}, new int[]{10, 5, 2, 1, 5, 2})); // 3
        System.out.println(U.maxUncrossedLines(new int[]{1, 3, 7, 1, 7, 5}, new int[]{1, 9, 2, 5, 1})); // 2
        System.out.println(U.maxUncrossedLines(new int[]{1}, new int[]{1}));                        // 1
        System.out.println(U.maxUncrossedLines(new int[]{1}, new int[]{2}));                        // 0
    }
}
