package hard;

public class BestTimeToBuyAndSellStockIV {
    public int maxProfit(int k, int[] prices) {
        int N = prices.length;
        Integer[][][] dp = new Integer[N][k+1][2];
        return solve(k, prices, N, 0, k, 0, dp);
    }

    private int solve(int k, int[] prices, int N, int index, int transactionsRemaining, int holding, Integer[][][] dp) {
        if (transactionsRemaining == 0 || index >= N)
            return 0;
        if (dp[index][transactionsRemaining][holding] != null)
            return dp[index][transactionsRemaining][holding];
        switch (holding) {
            case 0 :
                dp[index][transactionsRemaining][holding] =  Math.max(solve(k, prices, N, index+1, transactionsRemaining, 0, dp),
                        -prices[index] + solve(k, prices, N, index+1, transactionsRemaining, 1, dp));
                break;
            case 1:
                dp[index][transactionsRemaining][holding] = Math.max(solve(k, prices, N, index+1, transactionsRemaining, 1, dp),
                        prices[index] + solve(k, prices, N, index+1, transactionsRemaining-1, 0, dp));
        }
        return dp[index][transactionsRemaining][holding];
    }

    public static void main(String[] args) {
        BestTimeToBuyAndSellStockIV b = new BestTimeToBuyAndSellStockIV();
        System.out.println(b.maxProfit(2, new int[]{2,4,1}));
        System.out.println(b.maxProfit(2, new int[]{3,2,6,5,0,3}));
    }


}
