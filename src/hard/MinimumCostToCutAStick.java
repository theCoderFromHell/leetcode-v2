package hard;

import java.util.Arrays;

public class MinimumCostToCutAStick {
    public int minCost(int N, int[] cuts) {
        int M = cuts.length;
        int[] sortedCuts = new int[M + 2];
        sortedCuts[0] = 0;
        sortedCuts[M+1] = N;
        for (int i = 1; i < M+1; i++)
            sortedCuts[i] = cuts[i - 1];
        Arrays.sort(sortedCuts);
        Integer[][] dp = new Integer[M+2][M+2];
        return solve(sortedCuts, 0, M+1, dp);
    }

    private int solve(int[] sortedCuts, int start, int end, Integer[][] dp) {
        if (dp[start][end] != null)
            return dp[start][end];
        if (end - start == 1)
            return 0;
        int value = Integer.MAX_VALUE;
        for (int i = start+1; i < end; i++) {
            int current = (sortedCuts[end] - sortedCuts[start])
                    + solve(sortedCuts, start, i, dp)
                    + solve(sortedCuts, i, end, dp);
            value = Math.min(value, current);
        }
        dp[start][end] = value;
        return dp[start][end];
    }
}
