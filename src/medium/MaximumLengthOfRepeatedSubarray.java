package medium;

public class MaximumLengthOfRepeatedSubarray {
    public static int findLength(int[] nums1, int[] nums2) {
        int M = nums1.length;
        int N = nums2.length;
        int[][] dp = new int[M+1][N+1];
        for (int i = 0; i <= M; i++)
            for (int j = 0; j <= N; j++)
                if (i == 0 || j == 0)
                    dp[i][j] = 0;
        int result = 0;
        for (int i = 1; i <= M; i++) {
            for (int j = 1; j <= N; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    result = Math.max(result, dp[i][j]);
                }
            }
        }
        return result;
    }
    public static void main(String[] args) {
        System.out.println(findLength(new int[]{1,2,3,2,1}, new int[]{3,2,1,4,7}));
        System.out.println(findLength(new int[]{0,0,0,0,0}, new int[]{0,0,0,0,0}));
        System.out.println(findLength(new int[]{1,2,3,4,5}, new int[]{5,3,4,2,1}));
    }
}
