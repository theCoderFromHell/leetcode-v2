package hard;

public class BestTimeToBuyAndSellStockIII {
    public int maxProfit(int[] prices) {
        int N = prices.length;
        Integer[][][] dp = new Integer[N][3][2];
        return solve(prices, N, dp, 0, 0, 2);
    }

    private int solve(int[] prices, int N, Integer[][][] dp, int index, int holding, int transactions) {
        if (index == N || transactions == 0)
            return 0;
        if (dp[index][transactions][holding] != null)
            return dp[index][transactions][holding];
        switch (holding) {
            case 0:
                dp[index][transactions][holding] =  Math.max(solve(prices, N, dp, index+1, 0, transactions),
                        -prices[index] + solve(prices, N, dp, index+1, 1, transactions));
                break;
            case 1:
                dp[index][transactions][holding] =  Math.max(solve(prices, N, dp, index+1, 1, transactions),
                        prices[index] + solve(prices, N, dp, index+1, 0, transactions-1));
        }
        return dp[index][transactions][holding];
    }

    public static void main(String[] args) {
        BestTimeToBuyAndSellStockIII b = new BestTimeToBuyAndSellStockIII();
        System.out.println(b.maxProfit(new int[]{3,3,5,0,0,3,1,4}));
    }
}
