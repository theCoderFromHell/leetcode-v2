package medium;

public class MaximumSubarraySumAfterOneOperation {
    Integer maxSubArraySum;
    public int maxSumAfterOperation(int[] nums) {
        int size = nums.length;
        if (nums.length == 0)
            return 0;
        maxSubArraySum = nums[0];
        Integer[][] dp = new Integer[size][2];
        solve(nums, 0, size, 0, dp);
        return maxSubArraySum;
    }

    private int solve(int[] nums, int index, int size, int alreadySquared, Integer[][] dp) {
        if (index == size)
            return 0;
        if (dp[index][alreadySquared] != null)
            return dp[index][alreadySquared];
        int maxHere = Integer.MIN_VALUE;
        if (alreadySquared == 0)
            maxHere = Math.max(nums[index] * nums[index], nums[index] * nums[index] + solve(nums, index+1, size, 1, dp));
        maxHere = Math.max(maxHere, Math.max(nums[index], nums[index] + solve(nums, index+1, size, alreadySquared, dp)));
        dp[index][alreadySquared] = maxHere;
        maxSubArraySum = Math.max(maxSubArraySum, dp[index][alreadySquared]);
        return dp[index][alreadySquared];
    }

    public static void main(String[] args) {
        MaximumSubarraySumAfterOneOperation M = new MaximumSubarraySumAfterOneOperation();
        System.out.println(M.maxSumAfterOperation(new int[]{2,-1,-4,-3}));
        System.out.println(M.maxSumAfterOperation(new int[]{1,-1,1,1,-1,-1,1}));
    }
}
