package medium;

public class StoneGameVII {
    public int stoneGameVII(int[] stones) {
        int size = stones.length;
        int sum = 0;
        for (int i = 0; i < size; i++)
            sum += stones[i];
        Integer[][] dp = new Integer[size][size];
        return solve(stones, dp, sum, 0, size-1);
    }

    private int solve(int[] stones, Integer[][] dp, int sum, int start, int end) {
        if (start == end)
            return 0;
        if (dp[start][end] != null)
            return dp[start][end];
        int left = (sum - stones[start]) - solve(stones, dp, sum - stones[start], start+1, end);
        int right = (sum - stones[end]) - solve(stones, dp, sum - stones[end], start, end-1);
        dp[start][end] = Math.max(left, right);
        return dp[start][end];
    }

    public static void main(String[] args) {
        StoneGameVII S = new StoneGameVII();
        System.out.println(S.stoneGameVII(new int[]{5,3,1,4,2}));
        System.out.println(S.stoneGameVII(new int[]{7,90,5,1,100,10,10,2}));
    }
}
