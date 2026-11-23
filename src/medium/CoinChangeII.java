package medium;

// https://leetcode.com/problems/coin-change-ii/
public class CoinChangeII {
    public int change(int amount, int[] coins) {
        if(amount < 0 || coins == null || coins.length == 0)
            return 0;
        if(amount == 0)
            return 1;
        int dp[] = new int[amount+1];
        dp[0] = 1;
        int N = coins.length;
        for(int i = 0; i < N; i++) {
            for(int value = coins[i]; value <= amount; value++) {
                dp[value] += dp[value - coins[i]];
            }
        }
        return dp[amount];
    }

    /*
     * Revision Note — Coin Change II (Medium)
     *
     * Pattern: unbounded knapsack, counting combinations. dp[v] = the number of ways to make v
     * from the coins processed so far, each usable any number of times.
     *
     * Key insight: the loop order decides what gets counted.
     *  - Coins outer, amounts inner (this solution): every combination is built in one fixed order,
     *    all of coin 0 first, then all of coin 1, and so on. So {1, 2} and {2, 1} are one way.
     *    After the i-th pass, dp[v] counts the ways to make v using only coins 0..i.
     *  - Amounts outer, coins inner is 377 (Combination Sum IV). Any coin can come last at any
     *    point, so every ordering counts separately. For amount 4 and coins [1, 2, 3] that gives 7
     *    instead of 4 (Test 5).
     *  - The inner loop runs upward, so dp[value - coin] already includes this coin, and the coin
     *    can be reused. Running it downward would allow each coin once (0/1 counting, as in 2787).
     *
     * Gotchas:
     *  - dp[0] = 1: the empty combination. Amount 0 returns 1, not 0 (Test 4). The amount == 0 early
     *    return is redundant (dp[0] already is 1). The null / empty / negative check can't trigger
     *    under the constraints.
     *  - A coin larger than the amount skips the inner loop on its own; no special case needed
     *    (Test 6).
     *  - Overflow: only the final answer is guaranteed to fit in an int. Other cells can exceed it.
     *    In Test 8 there are 13,704,918,139 ways to make 4,998 from the even coins. That's harmless
     *    here: every cell that feeds dp[amount] is a non-negative part of the sum, so it is at most
     *    the answer, and cells that overflow never reach it. Java's int also wraps mod 2^32, so the
     *    sums stay exact mod 2^32. C++ solutions need unsigned or long long, because signed overflow
     *    is undefined behaviour there.
     *
     * Complexity: O(N · amount) time, at most 300 · 5,000 = 1.5M steps. O(amount) space.
     *
     * Template:
     *   dp[0] = 1
     *   for each coin c:                 // items outer: combinations
     *       for v in c..amount:          // upward: unlimited reuse
     *           dp[v] += dp[v - c]
     *   return dp[amount]
     *
     * Alternative: the 2D form, dp[i][v] = dp[i - 1][v] (skip coin i) + dp[i][v - coin_i] (use it
     * again). The 1D array is this table with its rows overwritten in place. Or top-down:
     * ways(i, remaining) = ways(i, remaining - coins[i]) + ways(i + 1, remaining), memoised.
     */
    public static void main(String[] args) {
        CoinChangeII C = new CoinChangeII();

        // Test 1: LeetCode example 1 — 5, 2+2+1, 2+1+1+1 and 1+1+1+1+1
        System.out.println("Test 1: " + C.change(5, new int[]{1, 2, 5}) + " (Expected: 4)");

        // Test 2: LeetCode example 2 — an odd amount can't be made from 2s
        System.out.println("Test 2: " + C.change(3, new int[]{2}) + " (Expected: 0)");

        // Test 3: LeetCode example 3 — a single coin equal to the amount
        System.out.println("Test 3: " + C.change(10, new int[]{10}) + " (Expected: 1)");

        // Test 4: amount == 0 — the empty combination is one way
        System.out.println("Test 4: " + C.change(0, new int[]{7}) + " (Expected: 1)");

        // Test 5: order doesn't matter — {1,1,1,1}, {1,1,2}, {2,2}, {1,3}. Swapping the loops
        // counts sequences, as in 377, and gives 7
        System.out.println("Test 5: " + C.change(4, new int[]{1, 2, 3}) + " (Expected: 4)");

        // Test 6: every coin is larger than the amount — the inner loop never runs
        System.out.println("Test 6: " + C.change(3, new int[]{5, 10}) + " (Expected: 0)");

        // Test 7: a larger count — 500 from seven coins, unsorted gaps between denominations
        System.out.println("Test 7: " + C.change(500, new int[]{3, 5, 7, 8, 9, 10, 11}) + " (Expected: 35502874)");

        // Test 8: even coins plus one coin equal to the odd amount — the even cells pass
        // Integer.MAX_VALUE, but none of them ever feeds dp[4999]
        System.out.println("Test 8: " + C.change(4999, new int[]{2, 4, 6, 8, 10, 4999}) + " (Expected: 1)");
    }
}
