package medium;

import java.util.Arrays;

// https://leetcode.com/problems/minimize-the-difference-between-target-and-chosen-elements/
public class MinimizeTheDifferenceBetweenTargetAndChosenElements {
    public int minimizeTheDifference(int[][] mat, int target) {
        int rows = mat.length;
        int columns = mat[0].length;
        int result = Integer.MAX_VALUE;
        boolean[][] dp = new boolean[rows+1][4901];
        for (int i = 0; i < columns; i++)
            dp[0][mat[0][i]] = true;
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int value = mat[i][j];
                for (int k = 0; k <= 4900; k++) {
                    if (dp[i-1][k])
                        dp[i][k + value] = true;
                }
            }
        }
        for (int k = 0; k <= 4900; k++)
            if (dp[rows - 1][k])
                result = Math.min(result, Math.abs(target - k));
        return result;
    }

    /*
     * Revision Note — Minimize the Difference Between Target and Chosen Elements (Medium)
     *
     * Pattern: reachable-sum DP, a subset-sum table built row by row. dp[row][sum] is true when
     * picking one element from each of rows 0..row can add up to exactly sum.
     *
     * Key insight: there are up to 70^70 ways to choose, but at most 70 × 70 = 4,900 different sums.
     * Track which sums are reachable, not how you reached them. The column picked last doesn't
     * matter to later rows; only the running sum carries forward.
     *
     * Gotchas (from this session):
     *  - Keeping only the "closest so far" per state is wrong. With target 10 and rows [7, 12], [3],
     *    12 looks better after row 0 (2 away vs 3), but 7 + 3 = 10 wins. Every reachable sum has to
     *    survive, not just the best-looking one.
     *  - Read the answer from the LAST row only. The first version updated the result while filling
     *    every row, so sums that skipped later rows counted. Test 5 returned 5 instead of 15 (10
     *    alone, never picking from row 1). Test 7 returned 30 instead of 4,100 (a partial sum near
     *    800 after about a dozen rows). That version disagreed with brute force about 25% of the
     *    time.
     *  - The final scan needs k <= 4900: in Test 7 the only reachable sum is exactly 4,900.
     *  - k + value can't overflow the 4,901-wide table. Before the last row, sums are at most
     *    70 × 69 = 4,830.
     *  - Duplicate values in a row just set the same cell twice (Test 6).
     *
     * Complexity: O(rows × columns × 4,900), at most about 24M steps (8 ms at full size).
     * O(rows × 4,900) booleans; two rows would be enough, since each row only reads the one above.
     *
     * Template:
     *   dp[0][x] = true for x in row 0
     *   for each later row i, value v in it, sum k with dp[i-1][k]: dp[i][k + v] = true
     *   answer = min |target - k| over k with dp[last][k]
     *
     * Alternative: prune. Values are positive, so a sum above the target only moves further away as
     * rows are added. Keep every reachable sum <= target plus only the smallest one above it. That
     * caps the work at about 800 sums per row. A BitSet per row, built with shift-and-OR, makes each
     * row a handful of word operations.
     */
    public static void main(String[] args) {
        MinimizeTheDifferenceBetweenTargetAndChosenElements M = new MinimizeTheDifferenceBetweenTargetAndChosenElements();

        // Test 1: LeetCode example 1 — 1 + 5 + 7 hits 13 exactly
        System.out.println("Test 1: " + M.minimizeTheDifference(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}, 13) + " (Expected: 0)");

        // Test 2: LeetCode example 2 — one choice per row, so the only sum is 6
        System.out.println("Test 2: " + M.minimizeTheDifference(new int[][]{{1}, {2}, {3}}, 100) + " (Expected: 94)");

        // Test 3: LeetCode example 3 — a single row: just the element closest to the target
        System.out.println("Test 3: " + M.minimizeTheDifference(new int[][]{{1, 2, 9, 8, 7}}, 6) + " (Expected: 1)");

        // Test 4: single cell — the only sum is 70
        System.out.println("Test 4: " + M.minimizeTheDifference(new int[][]{{70}}, 1) + " (Expected: 69)");

        // Test 5: the smallest full sum (20) is already above the target. Reading the result from
        // row 0 as well counted 10 on its own and returned 5
        System.out.println("Test 5: " + M.minimizeTheDifference(new int[][]{{10, 20}, {10, 30}}, 5) + " (Expected: 15)");

        // Test 6: duplicate values in every row — the only sum is 6
        System.out.println("Test 6: " + M.minimizeTheDifference(new int[][]{{3, 3, 3}, {3, 3, 3}}, 7) + " (Expected: 1)");

        // Test 7: 70 x 70, every value 70, target 800 — the only full sum is 4,900, the top of the
        // table. Counting partial sums returned 30
        int[][] big = new int[70][70];
        for (int[] row : big)
            Arrays.fill(row, 70);
        System.out.println("Test 7: " + M.minimizeTheDifference(big, 800) + " (Expected: 4100)");
    }
}
