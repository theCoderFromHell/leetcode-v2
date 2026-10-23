package medium;

import java.util.Arrays;

// https://leetcode.com/problems/minimum-operations-to-make-a-uni-value-grid/
public class MinimumOperationsToMakeAUniValueGrid {
    public int minOperations(int[][] grid, int x) {
        int rows = grid.length;
        int columns = grid[0].length;
        int[] flattened = flatten(grid, rows, columns);
        Arrays.sort(flattened);
        int size = flattened.length;
        int median = flattened[size/2];
        int result = 0;
        for (int i = 0; i < size; i++) {
            if (flattened[i] % x != median % x)
                return -1;
            result += (Math.abs(flattened[i] - median) / x);
        }
        return result;
    }

    private int[] flatten(int[][] grid, int rows, int columns) {
        int[] flattened = new int[rows * columns];
        int index = 0;
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                flattened[index++] = grid[i][j];
        return flattened;
    }

    /*
     * Revision Note — Minimum Operations to Make a Uni-Value Grid (Medium)
     * Pattern: median minimises the sum of absolute deviations — flatten, select the median,
     *          then sum the distances to it in units of x.
     * Key Insight: adding or subtracting x never changes v % x, so two values can only meet if they
     *              share a remainder. Once every value does, the grid is just a 1-D array and each
     *              step costs |v - t| / x, which is minimised by the median, as in 462.
     * Gotchas:
     *   - Feasibility first: one value off the common remainder makes the whole grid -1. Comparing
     *     every value against median % x is enough, since any reference value works.
     *   - |v - median| / x is exact division only because the remainders match.
     *   - Even count: any target between the two middle values is optimal. Both middles are grid
     *     values with the common remainder, so size / 2 (the upper one) is as good as the lower.
     *   - The target must be reachable, i.e. ≡ r (mod x). The median is a grid value, so it is.
     *   - int is safe: at most 10^5 cells, each at most 10^4 / x steps away, so under 10^9.
     * Complexity: O(mn log mn) time for the sort; O(mn) space for the flattened copy.
     * Alternatives:
     *   - Quickselect the median (as in 215): O(mn) average time, no full sort.
     *   - Counting sort: values lie in [1, 10^4], so bucket the counts and walk to the
     *     (mn / 2)-th value — O(mn + V) time, O(V) space.
     * Template:
     *   flat = flatten(grid); sort(flat); med = flat[n / 2]
     *   for v in flat:
     *       if v % x != med % x: return -1
     *       ops += |v - med| / x
     *   return ops
     */
    public static void main(String[] args) {
        MinimumOperationsToMakeAUniValueGrid M = new MinimumOperationsToMakeAUniValueGrid();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + M.minOperations(new int[][]{{2, 4}, {6, 8}}, 2) + " (Expected: 4)");

        // Test 2: LeetCode example 2 — x = 1, so every grid is feasible and only the median matters
        System.out.println("Test 2: " + M.minOperations(new int[][]{{1, 5}, {2, 3}}, 1) + " (Expected: 5)");

        // Test 3: LeetCode example 3 — mixed remainders mod 2, impossible
        System.out.println("Test 3: " + M.minOperations(new int[][]{{1, 2}, {3, 4}}, 2) + " (Expected: -1)");

        // Test 4: single cell — already uni-value
        System.out.println("Test 4: " + M.minOperations(new int[][]{{7}}, 3) + " (Expected: 0)");

        // Test 5: single row, even count — either middle value (3 or 5) gives the same 4 operations
        System.out.println("Test 5: " + M.minOperations(new int[][]{{1, 3, 5, 7}}, 2) + " (Expected: 4)");

        // Test 6: single column — the flatten must walk m x 1 as well as 1 x n
        System.out.println("Test 6: " + M.minOperations(new int[][]{{3}, {9}, {6}}, 3) + " (Expected: 2)");

        // Test 7: only one value breaks the common remainder — the whole grid becomes -1
        System.out.println("Test 7: " + M.minOperations(new int[][]{{2, 4}, {6, 9}}, 2) + " (Expected: -1)");

        // Test 8: values at both ends of [1, 10^4], exactly one large step of x = 9999 apart
        System.out.println("Test 8: " + M.minOperations(new int[][]{{1, 10000}, {1, 10000}}, 9999) + " (Expected: 2)");
    }
}
