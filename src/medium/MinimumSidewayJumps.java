package medium;

public class MinimumSidewayJumps {
    public int minSideJumps(int[] obstacles) {
        int size = obstacles.length;
        Integer[][] dp = new Integer[size][4];
        return solve(obstacles, size, dp, 0, 2);
    }

    private int solve(int[] obstacles, int size, Integer[][] dp, int index, int lane) {
        if (index == size-1)
            return 0;
        if (dp[index][lane] != null)
            return dp[index][lane];
        if (obstacles[index+1] != lane)
            return solve(obstacles, size, dp, index + 1, lane);
        int lane1 = (lane % 3) + 1;
        int lane2 = ((lane+1) % 3) + 1;
        int minJump = Integer.MAX_VALUE;
        if (obstacles[index] != lane1)
            minJump = Math.min(minJump, 1 + solve(obstacles, size, dp, index+1, lane1));
        if (obstacles[index] != lane2)
            minJump = Math.min(minJump, 1 + solve(obstacles, size, dp, index+1, lane2));
        dp[index][lane] = minJump;
        return dp[index][lane];
    }

    public static void main(String[] args) {
        MinimumSidewayJumps M = new MinimumSidewayJumps();
        System.out.println(M.minSideJumps(new int[]{0,2,2,1,0,3,0,3,0,1,0}));
        System.out.println(M.minSideJumps(new int[]{0,1,2,3,0}));
        System.out.println(M.minSideJumps(new int[]{0,1,1,3,3,0}));
        System.out.println(M.minSideJumps(new int[]{0,2,1,0,3,0}));
    }
}
