package medium;

// https://leetcode.com/problems/predict-the-winner/
public class PredictTheWinner {
    public boolean predictTheWinner(int[] nums) {
        int size = nums.length;
        Integer[][] dp = new Integer[size][size];
        int diff = solve(nums, 0, size-1, dp);
        return diff >= 0;
    }

    private int solve(int[] nums, int start, int end, Integer[][] dp) {
        if (start > end)
            return 0;
        if (dp[start][end] != null)
            return dp[start][end];
        int maxScore = Math.max(
                nums[start] - solve(nums, start+1, end, dp),
                nums[end] - solve(nums, start, end-1, dp));
        dp[start][end] = maxScore;
        return dp[start][end];
    }

    /*
     * Revision Note — Predict the Winner (Medium)
     *
     * Pattern: interval DP on a two-player zero-sum game, scored as a DIFFERENCE
     *          (minimax collapsed into one max via negation).
     *
     * Key insight: let solve(l, r) = the best (my score - opponent's score) the player
     *   TO MOVE can force on nums[l..r]. After I take an end, the opponent faces the
     *   rest and gets their own best difference, which counts AGAINST me:
     *     solve(l, r) = max(nums[l] - solve(l+1, r), nums[r] - solve(l, r-1))
     *   Player 1 wins (ties included) iff solve(0, n-1) >= 0.
     *
     * Gotchas:
     *   - Greedy "take the bigger end" fails (test 2: taking 7 exposes 233 to Player 2).
     *   - Tie counts as a Player 1 win: >= 0, not > 0 (tests 4 and 5 end level).
     *   - Without the memo it's 2^n calls; with it O(n^2) distinct (l, r) states.
     *   - No overflow: |diff| <= 20 * 1e7 = 2e8 < Integer.MAX_VALUE.
     *   - Even length is always a Player 1 win (they can take all even- or all odd-index
     *     cells), but the DP handles every length uniformly.
     *
     * Complexity: O(n^2) time and space (memo), O(n) recursion depth.
     *
     * Template:
     *   solve(l, r): if l > r return 0
     *                return max(a[l] - solve(l+1, r), a[r] - solve(l, r-1))   // memoised
     *   bottom-up:   dp[i][i] = a[i]; for len 2..n: dp[l][r] = max(a[l]-dp[l+1][r], a[r]-dp[l][r-1])
     */
    public static void main(String[] args) {
        PredictTheWinner P = new PredictTheWinner();

        // Test 1: LeetCode example 1 — Player 2 always gets the 5 in the middle
        System.out.println("Test 1: " + P.predictTheWinner(new int[]{1, 5, 2}) + " (Expected: false)");

        // Test 2: LeetCode example 2 — take the SMALLER end (1) so 233 comes back to Player 1
        System.out.println("Test 2: " + P.predictTheWinner(new int[]{1, 5, 233, 7}) + " (Expected: true)");

        // Test 3: single element — base case l == r, Player 1 takes it
        System.out.println("Test 3: " + P.predictTheWinner(new int[]{7}) + " (Expected: true)");

        // Test 4: symmetric array — 4 vs 4, a tie counts as a win (>= 0)
        System.out.println("Test 4: " + P.predictTheWinner(new int[]{2, 4, 2}) + " (Expected: true)");

        // Test 5: odd length, five elements — ends level at 9 vs 9, so the tie rule decides it
        System.out.println("Test 5: " + P.predictTheWinner(new int[]{1, 5, 2, 4, 6}) + " (Expected: true)");

        // Test 6: large value in the middle — every first move loses; Player 2 can always reach 55
        System.out.println("Test 6: " + P.predictTheWinner(new int[]{2, 4, 55, 6, 8}) + " (Expected: false)");
    }
}
