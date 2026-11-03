package medium;

import java.util.Arrays;

// https://leetcode.com/problems/count-islands-with-total-value-divisible-by-k/
public class CountIslandsWithTotalValueDivisibleByK {
    int[][] directions = new int[][]{{0,-1}, {0,1}, {-1,0}, {1,0}};
    public int countIslands(int[][] grid, int k) {
        int rows = grid.length;
        int columns = grid[0].length;
        boolean[][] visited = new boolean[rows][columns];
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!visited[i][j] && grid[i][j] != 0) {
                    long sum = dfs(grid, rows, columns, i, j, visited);
                    if (sum % k == 0)
                        count++;
                }
            }
        }
        return count;
    }

    private long dfs(int[][] grid, int rows, int columns, int i, int j, boolean[][] visited) {
        visited[i][j] = true;
        long sum = grid[i][j];
        for (int[] direction : directions) {
            int x = i + direction[0];
            int y = j + direction[1];
            if (valid(x, y, grid, rows, columns, visited)) {
                sum += dfs(grid, rows, columns, x, y, visited);
            }
        }
        return sum;
    }

    private boolean valid(int i, int j, int[][] grid, int rows, int columns, boolean[][] visited) {
        return (i >= 0 && i < rows &&
                j >= 0 && j < columns &&
                grid[i][j] != 0 && !visited[i][j]);
    }

    /*
     * Revision Note — Count Islands With Total Value Divisible by K (Medium)
     * Pattern: grid flood fill (200) where the DFS RETURNS an aggregate: each call gives back its
     *          own value plus the totals of the cells it reaches, so the top-level call holds the
     *          island's sum.
     * Key Insight: one DFS per unvisited land cell is one island; test its total mod k once the
     *              fill returns, never per cell.
     * Gotchas:
     *   - Overflow: an island holds up to m·n = 10^5 cells of up to 10^6, so its total reaches
     *     10^11. The first version summed in int; Test 6's 3 * 10^9 wrapped negative, gave
     *     -967296 mod 10^6, and the island wasn't counted. With k chosen to divide the true sum,
     *     170 of 200 large islands came out wrong. Fix: long (or sum = (sum + v) % k, since every
     *     partial sum then stays below 2 * 10^6).
     *   - A random k almost never divides the true sum, so random tests miss the overflow: wrapped
     *     and correct sums both say "not divisible". Build tests where k divides the real total.
     *   - Mark visited on ENTRY, before recursing, or two neighbours can both enter one cell.
     *   - Recursion depth: one island can be a 10^5-cell chain (a 1 x 100000 row). That throws
     *     StackOverflowError on a default local JVM stack. An explicit stack (ArrayDeque) avoids it.
     *   - Diagonal neighbours don't connect (Example 2: six one-cell islands).
     * Complexity: O(m·n) time — every cell is entered once and checks 4 neighbours. O(m·n) space
     *             for visited plus a recursion stack as deep as the largest island.
     * Alternatives:
     *   - Iterative DFS that zeroes each cell when it's PUSHED: no recursion, no visited array.
     *   - Union-find with a long sum per root, then count roots with sum % k == 0. O(m·n·α).
     * Template:
     *   for each cell with grid > 0 and not visited: if dfs(cell) % k == 0: count++
     *   long dfs(c): visited[c] = true; s = grid[c]
     *                for each in-bounds, land, unvisited neighbour nb: s += dfs(nb)
     *                return s
     */
    public static void main(String[] args) {
        CountIslandsWithTotalValueDivisibleByK C = new CountIslandsWithTotalValueDivisibleByK();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + C.countIslands(new int[][]{{0, 2, 1, 0, 0}, {0, 5, 0, 0, 5}, {0, 0, 1, 0, 0}, {0, 1, 4, 7, 0}, {0, 2, 0, 0, 8}}, 5) + " (Expected: 2)");

        // Test 2: LeetCode example 2 — cells touching only diagonally are six separate islands
        System.out.println("Test 2: " + C.countIslands(new int[][]{{3, 0, 3, 0}, {0, 3, 0, 3}, {3, 0, 3, 0}}, 3) + " (Expected: 6)");

        // Test 3: single cell, water — no island at all, even though k = 1 divides everything
        System.out.println("Test 3: " + C.countIslands(new int[][]{{0}}, 1) + " (Expected: 0)");

        // Test 4: k larger than every island's total — two islands of 1, neither divisible by 3
        System.out.println("Test 4: " + C.countIslands(new int[][]{{1, 0}, {0, 1}}, 3) + " (Expected: 0)");

        // Test 5: one island that turns corners — the fill has to go right, down, then back left to collect all 7
        System.out.println("Test 5: " + C.countIslands(new int[][]{{1, 1, 1}, {0, 0, 1}, {1, 1, 1}}, 7) + " (Expected: 1)");

        // Test 6: one row of 3000 cells, each 10^6 — the total is 3 * 10^9, past Integer.MAX_VALUE. Summed in int
        // it wraps to -1294967296, which isn't divisible by 10^6, and the first version returned 0
        int[][] row = new int[1][3000];
        Arrays.fill(row[0], 1000000);
        System.out.println("Test 6: " + C.countIslands(row, 1000000) + " (Expected: 1)");
    }
}
