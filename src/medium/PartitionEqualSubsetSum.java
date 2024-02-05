package medium;

import java.util.Arrays;

public class PartitionEqualSubsetSum {
    public static boolean canPartition(int[] nums) {
        int N = nums.length;
        int sum = 0;
        for (int num : nums)
            sum += num;
        if (sum % 2 == 1)
            return false;
        for (int x : nums)
            if (x == sum/2)
                return true;
        boolean[][] dp = new boolean[N+1][sum+1];
        for (int i = 0; i < N; i++) {
            dp[i][nums[i]] = true;
            for (int j = 0; j <=sum ; j++) {
                if (i > 0 && dp[i - 1][j]) {
                    dp[i][j] = true;
                    dp[i][j + nums[i]] = true;
                    if (j + nums[i] == sum/2)
                        return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(canPartition(new int[]{1,5,11,5}));
        System.out.println(canPartition(new int[]{1,2,3,5}));
    }
}
/*
Example 1:

Input: nums = [1,5,11,5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].
Example 2:

Input: nums = [1,2,3,5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.

 */
