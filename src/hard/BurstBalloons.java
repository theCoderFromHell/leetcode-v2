package hard;

public class BurstBalloons {
    public int maxCoins(int[] nums) {
        int size = nums.length;
        int[] balloons = new int[size+2];
        balloons[0] = 1;
        balloons[size+1] = 1;
        for (int i = 0; i < size; i++)
            balloons[i + 1] = nums[i];
        Integer[][] dp = new Integer[size+2][size+2];
        return solve(balloons, dp, 1, size);
    }

    private int solve(int[] balloons, Integer[][] dp, int start, int end) {
        if (start > end)
            return 0;
        if (dp[start][end] != null)
            return dp[start][end];
        int maxCoins = 0;
        for (int i = start; i <= end; i++) {
            // Burst this balloon after all other balloons in the range [start, end] have already been burst
            int coinsFromThisBalloon = balloons[start-1] * balloons[i] * balloons[end+1];
            int left = solve(balloons, dp, start, i-1);
            int right = solve(balloons, dp, i+1, end);
            maxCoins = Math.max(maxCoins, left + coinsFromThisBalloon + right);
        }
        dp[start][end] = maxCoins;
        return dp[start][end];
    }

    public static void main(String[] args) {
        BurstBalloons B = new BurstBalloons();
        System.out.println(B.maxCoins(new int[]{3,1,5,8}));
        System.out.println(B.maxCoins(new int[]{1,5}));
        System.out.println(B.maxCoins(new int[]{3,7,4}));
    }
}
