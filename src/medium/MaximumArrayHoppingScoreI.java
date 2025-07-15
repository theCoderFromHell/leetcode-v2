package medium;

import java.util.Arrays;

public class MaximumArrayHoppingScoreI {
    public int maxScore(int[] nums) {
        int size = nums.length;
        int[] dp = new int[size];
        Arrays.fill(dp, -1);
        return solve(nums, size, 0, dp);
    }

    private int solve(int[] nums, int size, int index, int[] dp) {
        if (index == size-1)
            return 0;
        if (dp[index] != -1)
            return dp[index];
        int maxScore = 0;
        for (int i = index+1; i < size; i++)
            maxScore = Math.max(maxScore, nums[i] * (i - index) + solve(nums, size, i, dp));
        dp[index] = maxScore;
        return maxScore;
    }

    public static void main(String[] args) {
        MaximumArrayHoppingScoreI M = new MaximumArrayHoppingScoreI();
        System.out.println(M.maxScore(new int[]{1,5,8}));
        System.out.println(M.maxScore(new int[]{4,5,2,8,9,1,3}));
    }
}
