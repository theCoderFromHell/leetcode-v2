package medium;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/inverse-coin-change/
public class InverseCoinChange {
    public List<Integer> findCoins(int[] numWays) {
        int size = numWays.length;
        List<Integer> coins = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int count = countWays(coins, i+1);
            if (numWays[i] == count)
                continue;
            if (numWays[i] == count + 1)
                coins.add(i+1);
            else
                return new ArrayList<>();
        }
        return coins;
    }

    private int countWays(List<Integer> coins, int amount) {
        if (coins.isEmpty())
            return 0;
        int size = coins.size();
        int[] dp = new int[amount+1];
        dp[0] = 1;
        for (int j = 0; j < size; j++) {
            int value = coins.get(j);
            for (int sum = value; sum <= amount; sum++)
                dp[sum] += dp[sum - value];
        }
        return dp[amount];
    }

    /*
     * Revision Note — Inverse Coin Change (Medium)
     *
     * Pattern: rebuild the unknown input one amount at a time, running the forward problem (Coin
     * Change II, 518) on what's been recovered so far and comparing it with what's given.
     *
     * Key insight: walk the amounts upward. At amount i, every coin smaller than i is already known,
     * and a coin larger than i can't be used for i. So the only unknown is whether i itself is a coin,
     * and a coin adds exactly one way: the coin on its own. Compare count = ways(known coins, i) with
     * numWays[i]:
     *   gap 0  → i is not a coin
     *   gap 1  → i is a coin
     *   other  → no coin set can produce this array, return []
     *
     * Gotchas:
     *  - Coin Change II's loop order matters. Coins outside and sums inside counts each combination
     *    once. Sums outside counts orderings, so 2+4 and 4+2 are two ways, and amount 6 in example 1
     *    looks like it isn't a coin (first draft: Test 1 gave [2, 4], Test 2 gave [1, 2]).
     *  - Every amount needs the gap check, including amounts where numWays[i] is 0. Skipping zeros
     *    let [1, 0] through as [1], but coin 1 makes 2 one way (Test 6).
     *  - "Impossible" has to be an exit of its own. Only ever adding coins can't return [] (Test 3).
     *  - numWays[i] == 1 doesn't make i a coin: in Test 5, 4 = 2 + 2 already has one way.
     *  - No overflow. For amounts up to 100, any coin set's count is at most p(100) = 190,569,292
     *    (every coin 1..100), which is below 2^31. Counting orderings would overflow: that grows like
     *    2^n.
     *
     * Complexity: O(n^2 · k) time, where k is the number of coins found, so O(n^3) = 10^6 at most.
     * countWays rebuilds the table from scratch for each amount. O(n) space.
     *
     * Template:
     *   coins = []
     *   for i in 1..n:
     *       count = coinChangeII(coins, i)          // coins outer, sums inner
     *       if numWays[i] == count: continue
     *       if numWays[i] == count + 1: coins.add(i)
     *       else: return []
     *   return coins
     *
     * Alternative: keep one dp array for the whole walk instead of rebuilding it. When i turns out to
     * be a coin, fold it in once: for s in i..n: dp[s] += dp[s - i]. dp[i] then already holds the
     * count for amount i when the walk reaches it, so the whole thing is O(n · k) = O(n^2).
     */
    public static void main(String[] args) {
        InverseCoinChange I = new InverseCoinChange();

        // Test 1: LeetCode example 1 — at 6, coins {2, 4} make 2 combinations ({2,2,2}, {2,4}) and the
        // array says 3, so 6 is a coin. Counting orderings gives 3 (2+4 and 4+2) and misses it
        System.out.println("Test 1: " + I.findCoins(new int[]{0, 1, 0, 2, 0, 3, 0, 4, 0, 5}) + " (Expected: [2, 4, 6])");

        // Test 2: LeetCode example 2 — 1s and 2s make 5 three ways ({1x5}, {1x3, 2}, {1, 2, 2}) and
        // the array says 4, so 5 is a coin
        System.out.println("Test 2: " + I.findCoins(new int[]{1, 2, 2, 3, 4}) + " (Expected: [1, 2, 5])");

        // Test 3: LeetCode example 3 — {1, 2, 3} make 5 five ways but the array says 15, a gap of 10,
        // so no coin set fits
        System.out.println("Test 3: " + I.findCoins(new int[]{1, 2, 3, 4, 15}) + " (Expected: [])");

        // Test 4: a single amount — 1 way to make 1, so 1 is a coin
        System.out.println("Test 4: " + I.findCoins(new int[]{1}) + " (Expected: [1])");

        // Test 5: several amounts with exactly one way — only 2 and 3 are coins; 4 = 2+2 and 5 = 2+3
        // each already have their one way, so a 1 in the array isn't enough to call something a coin
        System.out.println("Test 5: " + I.findCoins(new int[]{0, 1, 1, 1, 1, 2}) + " (Expected: [2, 3])");

        // Test 6: a count of 1 followed by 0 — coin 1 makes 2 one way (1+1), so 0 at 2 is impossible.
        // Skipping zero entries without the check returned [1]
        System.out.println("Test 6: " + I.findCoins(new int[]{1, 0}) + " (Expected: [])");

        // Test 7: all zeros — no coins at all is a valid set, and it prints as []
        System.out.println("Test 7: " + I.findCoins(new int[]{0, 0, 0}) + " (Expected: [])");
    }
}
