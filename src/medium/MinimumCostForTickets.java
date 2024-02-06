package medium;

public class MinimumCostForTickets {
    public static int mincostTickets(int[] days, int[] costs) {
        int N = days.length;
        if (N == 1)
            return Math.min(Math.min(costs[0], costs[1]), costs[2]);
        int[] dp = new int[N];
        dp[0] = Math.min(Math.min(costs[0], costs[1]), costs[2]);
        for (int i = 1; i < N; i++) {
            int minCost = dp[i-1] + Math.min(Math.min(costs[0], costs[1]), costs[2]);
            for (int j = 0; j < i; j++) {
                if (days[j] + 7 > days[i])
                    minCost = Math.min(minCost, ((j == 0) ? 0 : dp[j-1]) + costs[1]);
                if (days[j] + 30 > days[i])
                    minCost = Math.min(minCost, ((j == 0) ? 0 : dp[j-1]) + costs[2]);
            }
            dp[i] = minCost;
        }
        return dp[N-1];
    }

    public static void main(String[] args) {
//        System.out.println(mincostTickets(new int[]{1,4,6,7,8,20}, new int[]{2,7,15}));
//        System.out.println(mincostTickets(new int[]{1,2,3,4,5,6,7,8,9,10,30,31}, new int[]{2,7,15}));
        System.out.println(mincostTickets(new int[]{1,4,6,7,8,20}, new int[]{7,2,15}));
    }
}
