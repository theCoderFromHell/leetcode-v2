package medium;

public class BestTimeToBuyAndSellStockWithTransactionFee {
    public int maxProfit(int[] prices, int fee) {
        int N = prices.length;
        Integer[][] dp = new Integer[N][2];
        return solve(prices, N, fee, dp, 0, 0);
    }

    private int solve(int[] prices, int N, int fee, Integer[][] dp, int index, int holding) {
        if (index == N)
            return 0;
        if (dp[index][holding] != null)
            return dp[index][holding];
        switch (holding) {
            case 0:
                dp[index][holding] = Math.max(
                        solve(prices, N, fee, dp, index+1, 0),
                        -prices[index] + solve(prices, N, fee, dp, index+1, 1)
                );
                break;
            case 1:
                dp[index][holding] = Math.max(
                        solve(prices, N, fee, dp, index+1, 1),
                        prices[index] - fee + solve(prices, N, fee, dp, index+1, 0)
                );
        }
        return dp[index][holding];
    }
}
