package medium;

public class TargetSum {
    public int findTargetSumWays(int[] nums, int target) {
        int N = nums.length;
        int sum = 0;
        for (int num : nums)
            sum += num;
        Integer[][] dp = new Integer[N][2*sum+1];
        return solve(nums, N, 0, 0,  target, dp, sum);
    }

    private int solve(int[] nums, int N, int index, int total, int target, Integer[][] dp, int sum) {
        if (index == N)
            return (total == target) ? 1 : 0;
        if (dp[index][total + sum] != null)
            return dp[index][total + sum];
        int negativeCase = solve(nums, N, index+1, total - nums[index], target, dp, sum);
        int positiveCase = solve(nums, N, index+1, total + nums[index], target, dp, sum);

        return dp[index][total + sum] = negativeCase + positiveCase;
    }

    public static void main(String[] args) {
        TargetSum T = new TargetSum();
        System.out.println(T.findTargetSumWays(new int[]{1,1,1,1,1}, 3));
    }
}
