package medium;

// https://leetcode.com/problems/count-ways-to-build-good-strings/
public class CountWaysToBuildGoodStrings {
    final int MOD = 1000000007;
    public int countGoodStrings(int low, int high, int zero, int one) {
        int[] dp = new int[high+1];
        dp[0] = 1;
        int result = 0;
        for (int i = 1; i <= high; i++) {
            if (i >= zero)
                dp[i] = (dp[i] + dp[i - zero]) % MOD;
            if (i >= one)
                dp[i] = (dp[i] + dp[i - one]) % MOD;
            if (i >= low && i <=high) {
                result = (result + dp[i]) % MOD;
            }
        }
        return result;
    }

    /*
     * Revision Note — Count Ways to Build Good Strings (Medium)
     *
     * Pattern: Bottom-up DP — unbounded knapsack / coin-change counting variant
     *
     * Key Insight: dp[i] = number of ways to build a string of exactly length i;
     * each step appends `zero` zeros OR `one` ones, so dp[i] += dp[i-zero] + dp[i-one].
     * Accumulate result for all i in [low, high].
     *
     * Gotchas:
     * - Seed dp[0] = 1 (empty string = one valid starting point, not zero)
     * - Apply MOD at every accumulation step to avoid overflow
     * - When zero == one, both branches add to the same dp[i] — that's correct (doubles it)
     *
     * Template:
     *   int[] dp = new int[high+1]; dp[0] = 1;
     *   for i in 1..high:
     *       if i >= zero: dp[i] = (dp[i] + dp[i-zero]) % MOD
     *       if i >= one:  dp[i] = (dp[i] + dp[i-one])  % MOD
     *       if i >= low:  result = (result + dp[i]) % MOD
     *   return result
     */
    public static void main(String[] args) {
        CountWaysToBuildGoodStrings C = new CountWaysToBuildGoodStrings();
        System.out.println(C.countGoodStrings(3, 3, 1, 1)); // 8  (all 3-char binary strings)
        System.out.println(C.countGoodStrings(2, 3, 1, 2)); // 5
        System.out.println(C.countGoodStrings(1, 1, 1, 1)); // 2  ("0" and "1")
        System.out.println(C.countGoodStrings(1, 1, 2, 3)); // 0  (can't build length 1 with zero=2, one=3)
        System.out.println(C.countGoodStrings(2, 2, 2, 2)); // 2  ("00" or "11")
    }
}
