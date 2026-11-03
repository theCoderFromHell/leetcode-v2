package medium;

import java.util.HashMap;

// https://leetcode.com/problems/sum-of-remoteness-of-all-cells/
public class SumOfRemotenessOfAllCells {
    int[][] directions = new int[][]{{0,-1}, {0,1}, {-1,0}, {1,0}};
    public long sumRemoteness(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        boolean[][] visited = new boolean[rows][columns];
        HashMap<Integer,Long> colours = new HashMap<>();
        int colour = -2;
        long totalSum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] != -1)
                    totalSum += grid[i][j];
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!visited[i][j] && grid[i][j] != -1) {
                    long sum = dfs(grid, rows, columns, i, j, visited, colour);
                    colours.put(colour, sum);
                    colour--;
                }
            }
        }
        long result = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] != -1)
                    result += (totalSum - colours.get(grid[i][j]));
            }
        }
        return result;
    }

    private long dfs(int[][] grid, int rows, int columns, int i, int j, boolean[][] visited, int colour) {
        visited[i][j] = true;
        long sum = grid[i][j];
        grid[i][j] = colour;
        for (int[] direction : directions) {
            int x = i + direction[0];
            int y = j + direction[1];
            if (valid(x, y, grid, rows, columns, visited)) {
                sum += dfs(grid, rows, columns, x, y, visited, colour);
            }
        }
        return sum;
    }

    private boolean valid(int i, int j, int[][] grid, int rows, int columns, boolean[][] visited) {
        return (i >= 0 && i < rows &&
                j >= 0 && j < columns &&
                grid[i][j] != -1 && !visited[i][j]);
    }

    /*
     * Revision Note — Sum of Remoteness of All Cells (Medium, premium)
     * Pattern: grid components with per-component aggregates (200 → 3619 → this). Flood-fill each
     *          component once, store its value sum, then answer every cell from that table.
     * Key Insight: the cells that can't reach (i, j) are exactly the cells outside its component, so
     *              R[i][j] = total − componentSum. Every cell in a component gets the same R, so a
     *              component contributes size · (total − sum) and the answer is Σ size · (total − sum).
     * Gotchas:
     *   - Component ids written into the grid start at −2, so they never collide with −1 (blocked) or
     *     with a real value (≥ 1). Key the sums by id, never by cell value: equal values in different
     *     components would merge.
     *   - Overflow: total ≤ 9·10^4 cells · 10^6 = 9·10^10, and the answer ≤ 9·10^4 · 9·10^10 = 8.1·10^15.
     *     Both need long (Test 7 is 2.02·10^15).
     *   - Recursion depth: a fully open 300 x 300 grid is one 90,000-cell component, and the DFS
     *     snakes through all of it. That throws StackOverflowError on a default local JVM (it passes
     *     with -Xss64m, and LeetCode's judge has a big enough stack). An explicit ArrayDeque avoids it.
     *   - The grid is overwritten with ids. Fine on LeetCode, but say so in an interview. Once cells
     *     are recoloured, grid > 0 already means "open and unvisited", so visited is redundant.
     * Complexity: O(n²) time — three passes, and the fill enters each open cell once and checks 4
     *             neighbours. O(n²) space for visited, the recursion stack and up to n²/2 map entries
     *             (the checkerboard has 45,000 one-cell components).
     * Alternatives:
     *   - Fill returns (size, sum): answer = Σ size · (total − sum). No id table, no third pass, no
     *     mutation.
     *   - Union-find with size and sum per root, unioning each open cell with its right and down
     *     neighbours, then the same Σ over roots. O(n² α), and no recursion at all.
     * Template:
     *   total = Σ open values
     *   for each open, unseen cell: (size, sum) = fill(cell); ans += size * (total - sum)
     *   return ans
     */
    public static void main(String[] args) {
        SumOfRemotenessOfAllCells S = new SumOfRemotenessOfAllCells();

        // Test 1: LeetCode example 1 — four one-cell components, total 13: R = 12 + 8 + 9 + 10
        System.out.println("Test 1: " + S.sumRemoteness(new int[][]{{-1, 1, -1}, {5, -1, 4}, {-1, 3, -1}}) + " (Expected: 39)");

        // Test 2: LeetCode example 2 — {3, 4} (sum 7) and {3} (sum 3): 2 · 3 + 1 · 7
        System.out.println("Test 2: " + S.sumRemoteness(new int[][]{{-1, 3, 4}, {-1, -1, -1}, {3, -1, -1}}) + " (Expected: 13)");

        // Test 3: LeetCode example 3 — a single open cell reaches everything
        System.out.println("Test 3: " + S.sumRemoteness(new int[][]{{1}}) + " (Expected: 0)");

        // Test 4: every cell blocked — no components at all, and blocked cells score 0
        System.out.println("Test 4: " + S.sumRemoteness(new int[][]{{-1, -1}, {-1, -1}}) + " (Expected: 0)");

        // Test 5: no blocked cells — one component, so total − componentSum is 0 for every cell
        System.out.println("Test 5: " + S.sumRemoteness(new int[][]{{1, 2}, {3, 4}}) + " (Expected: 0)");

        // Test 6: two components of three cells each, sums 6 and 19 out of 25: 3 · 19 + 3 · 6
        System.out.println("Test 6: " + S.sumRemoteness(new int[][]{{2, 2, -1}, {2, -1, 5}, {-1, 7, 7}}) + " (Expected: 75)");

        // Test 7: 300 x 300 checkerboard of 10^6 and -1 — 45,000 one-cell components. The total is 4.5 · 10^10
        // and the answer 2.02 · 10^15, both past Integer.MAX_VALUE; it also puts 45,000 entries in the id map
        int[][] board = new int[300][300];
        for (int i = 0; i < 300; i++)
            for (int j = 0; j < 300; j++)
                board[i][j] = (i + j) % 2 == 0 ? 1000000 : -1;
        System.out.println("Test 7: " + S.sumRemoteness(board) + " (Expected: 2024955000000000)");

        // Test 8: a ring of sixteen 1s around a walled-in 9 — the fill has to turn every corner to keep the ring
        // as one component (sum 16). Ring cells each get 9, the centre gets 16: 16 · 9 + 16
        System.out.println("Test 8: " + S.sumRemoteness(new int[][]{{1, 1, 1, 1, 1}, {1, -1, -1, -1, 1}, {1, -1, 9, -1, 1}, {1, -1, -1, -1, 1}, {1, 1, 1, 1, 1}}) + " (Expected: 160)");
    }
}
