package medium;

public class VisitArrayPositionsToMaximizeScore {
    public long maxScore(int[] nums, int x) {
        int size = nums.length;
        Long[][] dp = new Long[size][2];
        return nums[0] + solve(nums, size, 1, dp, 0, x);
    }

    private long solve(int[] nums, int size, int index, Long[][] dp, int previous, int x) {
        if (index == size)
            return 0;
        int previousParity = nums[previous] % 2 == 0 ? 0 : 1;
        if (dp[index][previousParity] != null)
            return dp[index][previousParity];
        long bestScore = solve(nums, size, index + 1, dp, previous, x);
        if (Math.abs(nums[index] - nums[previous]) % 2 == 0)
            bestScore = Math.max(bestScore, nums[index] + solve(nums, size, index + 1, dp, index, x));
        else
            bestScore = Math.max(bestScore, nums[index] - x + solve(nums, size, index + 1, dp, index, x));
        dp[index][previousParity] = bestScore;
        return dp[index][previousParity];
    }

    public static void main(String[] args) {
        VisitArrayPositionsToMaximizeScore V = new VisitArrayPositionsToMaximizeScore();
        System.out.println(V.maxScore(new int[]{2,3,6,1,9,2}, 5));
        System.out.println(V.maxScore(new int[]{2,4,6,8}, 3));
    }
}
