package medium;

public class PartitionEqualSubsetSum {
    public boolean canPartitionV2(int[] nums) {
        int size = nums.length;
        int sum = 0;
        for (int i = 0; i < size; i++)
            sum += nums[i];
        if (sum % 2 == 1)
            return false;
        for (int i = 0; i < size; i++)
            if (nums[i] == sum/2)
                return true;
        boolean[][] dp = new boolean[size+1][sum/2+1];
        for (int i = 0; i <= size; i++)
            dp[i][0] = true;
        for (int total = 0; total <= sum/2; total++)
            dp[0][total] = false;
        for (int total = 1; total <= sum/2; total++) {
            for (int j = 1; j <= size; j++) {
                dp[j][total] = dp[j-1][total];
                if (nums[j-1] <= total && dp[j-1][total - nums[j-1]])
                    dp[j][total] = true;
            }
        }
        return dp[size][sum/2];
    }

    public boolean canPartition(int[] nums) {
        int size = nums.length;
        int sum = 0;
        for (int i = 0; i < size; i++)
            sum += nums[i];
        if (sum % 2 == 1)
            return false;
        for (int i = 0; i < size; i++)
            if (nums[i] == sum/2)
                return true;
        boolean[][] dp = new boolean[size][sum+1];
        for (int i = 0; i < size; i++) {
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
        PartitionEqualSubsetSum P = new PartitionEqualSubsetSum();
        System.out.println(P.canPartition(new int[]{1,2,5}));
        System.out.println(P.canPartition(new int[]{1,5,11,5}));
        System.out.println(P.canPartition(new int[]{1,2,3,5}));
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
