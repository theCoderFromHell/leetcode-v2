package medium;

public class BestTimeToBuyAndSellStockWithCooldown {
    public int maxProfit(int[] prices) {
        int size = prices.length;
        Integer[][][] dp = new Integer[size][2][2];
        return solve(prices, size, dp, 0, 0, 0);
    }

    private int solve(int[] prices, int size, Integer[][][] dp, int index, int holding, int cooldown) {
        if (index == size)
            return 0;
        if (dp[index][holding][cooldown] != null)
            return dp[index][holding][cooldown];
        switch (holding) {
            case 0:
                dp[index][holding][cooldown] = solve(prices, size, dp, index+1, 0, 0);
                if (cooldown == 0)
                    dp[index][holding][cooldown] = Math.max(dp[index][holding][cooldown],
                        -prices[index] + solve(prices, size, dp, index+1, 1, 0));
                break;
            case 1:
                dp[index][holding][cooldown] = Math.max(solve(prices, size, dp, index+1, 1, 0),
                        prices[index] + solve(prices, size, dp, index+1, 0, 1));
        }
        return dp[index][holding][cooldown];
    }

    public static void main(String[] args) {
        BestTimeToBuyAndSellStockWithCooldown B = new BestTimeToBuyAndSellStockWithCooldown();
        System.out.println(B.maxProfit(new int[]{1,2,3,0,2}));
        System.out.println(B.maxProfit(new int[]{1}));
    }
}
