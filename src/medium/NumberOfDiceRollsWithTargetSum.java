package medium;

// https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/
public class NumberOfDiceRollsWithTargetSum {
    private static final int MOD = 1000000007;
    public int numRollsToTarget(int n, int k, int target) {
        if (target > n * k)
            return 0;
        int[][] dp = new int[n][n*k + 1];
        for (int i = 1; i <= k; i++)
            dp[0][i] = 1;
        for (int dice = 1; dice < n; dice++) {
            for (int faceValue = 1; faceValue <= k; faceValue++) {
                for (int sum = 0; sum <= n * k - faceValue; sum++)
                    dp[dice][faceValue + sum] = (dp[dice][faceValue + sum] + dp[dice - 1][sum]) % MOD;
            }
        }
        return dp[n-1][target];
    }

    /*
     * Revision Note — Number of Dice Rolls With Target Sum (Medium)
     *
     * Pattern: layered counting DP. dp[dice][sum] = the number of ways the first dice + 1 dice add up
     * to sum. Each layer adds one die to every sum of the previous layer.
     *
     * Key insight: the dice are distinct, so 1+6 and 6+1 are different rolls, as in 377 (order
     * matters). But there are exactly n of them, so the outer loop runs over the dice, one layer per
     * die. That layer structure fixes how many values are used, which neither 377 nor 518 has.
     *
     * Gotchas:
     *  - Take the mod at every addition. (a + b) % MOD with a, b < MOD is at most 2,000,000,012,
     *    which still fits in an int. Summing k terms before taking the mod would overflow
     *    (Tests 3 and 8).
     *  - target > n·k is impossible: return 0 early, which also keeps the index inside the table
     *    (Test 5). target < n comes out as 0 on its own, since every die shows at least 1 (Test 4).
     *  - Base case: one die gives each face 1..k exactly one way.
     *  - With k = 1 there is exactly one roll, summing to n (Test 6). With target = n·k there is
     *    exactly one roll, every die on its top face (Test 7).
     *
     * Complexity: O(n · k · n·k) time, at most 30 · 30 · 900 = 810k steps. O(n · n·k) space. Each
     * layer reads only the previous one, so two rows of size n·k (or of size target) are enough.
     *
     * Template:
     *   dp[0][f] = 1 for f in 1..k
     *   for each later die d, face f, sum s: dp[d][s + f] = (dp[d][s + f] + dp[d - 1][s]) % MOD
     *   return dp[n - 1][target]
     *
     * Alternative: sliding-window sums. dp[d][s] = dp[d-1][s-1] + ... + dp[d-1][s-k], a window of
     * the previous row. Keep a running window sum (add the new term, drop the one k back) for
     * O(n · target) time. Or top-down: ways(diceLeft, remaining), memoised.
     */
    public static void main(String[] args) {
        NumberOfDiceRollsWithTargetSum N = new NumberOfDiceRollsWithTargetSum();

        // Test 1: LeetCode example 1 — one die, only face 3 works
        System.out.println("Test 1: " + N.numRollsToTarget(1, 6, 3) + " (Expected: 1)");

        // Test 2: LeetCode example 2 — six ordered pairs; 1+6 and 6+1 count separately
        System.out.println("Test 2: " + N.numRollsToTarget(2, 6, 7) + " (Expected: 6)");

        // Test 3: LeetCode example 3 — the true count is far beyond int, so the mod is needed
        System.out.println("Test 3: " + N.numRollsToTarget(30, 30, 500) + " (Expected: 222616187)");

        // Test 4: target smaller than n — every die shows at least 1, so the smallest sum is 3
        System.out.println("Test 4: " + N.numRollsToTarget(3, 6, 2) + " (Expected: 0)");

        // Test 5: target larger than n * k — the largest sum is 12; the early return keeps the index in range
        System.out.println("Test 5: " + N.numRollsToTarget(2, 6, 13) + " (Expected: 0)");

        // Test 6: k == 1 — every die shows 1, so there is exactly one way to make 5
        System.out.println("Test 6: " + N.numRollsToTarget(5, 1, 5) + " (Expected: 1)");

        // Test 7: target == n * k at the maximum size — every die must show 30
        System.out.println("Test 7: " + N.numRollsToTarget(30, 30, 900) + " (Expected: 1)");

        // Test 8: n == k == 30, target in the middle of the range — the largest counts, where adding
        // k terms without taking the mod would overflow
        System.out.println("Test 8: " + N.numRollsToTarget(30, 30, 465) + " (Expected: 252370474)");
    }
}
