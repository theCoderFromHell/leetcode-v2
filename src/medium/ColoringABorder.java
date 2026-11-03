package medium;

import java.util.Arrays;

// https://leetcode.com/problems/coloring-a-border/
public class ColoringABorder {
    int[][] directions = new int[][]{{0,-1}, {0,1}, {-1,0}, {1,0}};
    public int[][] colorBorder(int[][] grid, int row, int col, int color) {
        int rows = grid.length;
        int columns = grid[0].length;
        boolean[][] visited = new boolean[rows][columns];
        int targetColour = grid[row][col];
        dfs(grid, rows, columns, row, col, visited, targetColour);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] == 0)
                    grid[i][j] = color;
            }
        }
        return grid;
    }

    private void dfs(int[][] grid, int rows, int columns, int i, int j, boolean[][] visited, int targetColour) {
        visited[i][j] = true;
        for (int[] direction : directions) {
            int x = i + direction[0];
            int y = j + direction[1];
            if (x >= 0 && x < rows && y >= 0 && y < columns) {
                if (!visited[x][y] && grid[x][y] == targetColour)
                    dfs(grid, rows, columns, x, y, visited, targetColour);
                if (!visited[x][y])
                    grid[i][j] = 0;
            }
        }
        if (i == 0 || i == rows-1 || j == 0 || j == columns-1)
            grid[i][j] = 0;
    }

    /*
     * Revision Note — Coloring A Border (Medium)
     *
     * Pattern: DFS flood fill + classify each component cell by its 4 neighbours. Mark border cells
     * with a sentinel (0, safe because colours are ≥ 1) during the DFS, then paint every 0 at the end.
     *
     * Key insight: a component cell is on the border iff one of its neighbours is off the grid or
     * OUTSIDE the component. Decide "outside" with state that never changes mid-traversal:
     * visited[][] is only ever set for component cells, so after the recursive call,
     * !visited[x][y] ⇔ the neighbour is not in the component.
     *
     * Gotchas (both failed versions in this session):
     *  - Checking grid[x][y] != targetColour after marking border cells 0 IN PLACE: a component
     *    neighbour already marked 0 looks like "a different colour", so interior cells get painted
     *    (Tests 3 and 6 came back all-border).
     *  - Moving that same colour check after the recursion doesn't fix it. The recursion into (x, y)
     *    can itself mark (x, y) as 0 before the check runs. A component cell can read as targetColour
     *    or 0 depending on timing, so colour can't decide membership.
     *  - Same colour but not connected is NOT in the component and stays untouched (Test 8).
     *  - Grid-edge cells are border even when every in-grid neighbour matches.
     *
     * Complexity: O(m · n) time (each component cell visited once, 4 checks each, plus the final
     * scan), O(m · n) space for visited, plus recursion up to m · n = 2500 frames on a snake shape.
     *
     * Template:
     *   dfs(i, j): visited = true
     *       for each neighbour (x, y) in the grid:
     *           if !visited && grid == target: dfs(x, y)
     *           if !visited: mark (i, j) border          // visited ⇔ in component
     *       if (i, j) on the grid edge: mark border
     *   paint every marked cell
     *
     * Alternative: collect the border cells in a list during a BFS that only reads colours, and paint
     * them after the traversal. Nothing is mutated mid-traversal, so the plain colour check is safe.
     */
    public static void main(String[] args) {
        ColoringABorder C = new ColoringABorder();

        // Test 1: LeetCode example 1 — (0,0) is border only because it's on the grid edge
        System.out.println("Test 1: " + Arrays.deepToString(C.colorBorder(new int[][]{{1, 1}, {1, 2}}, 0, 0, 3)) + " (Expected: [[3, 3], [3, 2]])");

        // Test 2: LeetCode example 2 — the start's component is the 2s, not (0,0)'s 1
        System.out.println("Test 2: " + Arrays.deepToString(C.colorBorder(new int[][]{{1, 2, 2}, {2, 3, 2}}, 0, 1, 3)) + " (Expected: [[1, 3, 3], [2, 3, 3]])");

        // Test 3: LeetCode example 3 — the centre is interior and must stay 1.
        // Both buggy versions painted it, by reading already-marked neighbours as "different colour"
        System.out.println("Test 3: " + Arrays.deepToString(C.colorBorder(new int[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}}, 1, 1, 2)) + " (Expected: [[2, 2, 2], [2, 1, 2], [2, 2, 2]])");

        // Test 4: single cell — on every grid edge at once
        System.out.println("Test 4: " + Arrays.deepToString(C.colorBorder(new int[][]{{5}}, 0, 0, 7)) + " (Expected: [[7]])");

        // Test 5: new color equals the component's color — the output equals the input
        System.out.println("Test 5: " + Arrays.deepToString(C.colorBorder(new int[][]{{1, 1}, {1, 1}}, 0, 0, 1)) + " (Expected: [[1, 1], [1, 1]])");

        // Test 6: 4 x 4, all one color — a 2 x 2 interior block, so interior cells sit next to each other
        // and next to border cells (the in-place-marking bug painted all 16)
        System.out.println("Test 6: " + Arrays.deepToString(C.colorBorder(new int[][]{{1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}, {1, 1, 1, 1}}, 1, 1, 2)) + " (Expected: [[2, 2, 2, 2], [2, 1, 1, 2], [2, 1, 1, 2], [2, 2, 2, 2]])");

        // Test 7: a "hole" of another colour — every cell touching the hole is border
        System.out.println("Test 7: " + Arrays.deepToString(C.colorBorder(new int[][]{{1, 1, 1}, {1, 2, 1}, {1, 1, 1}}, 0, 0, 3)) + " (Expected: [[3, 3, 3], [3, 2, 3], [3, 3, 3]])");

        // Test 8: same colour but not connected — the 1 at (0,2) is a different component and stays 1
        System.out.println("Test 8: " + Arrays.deepToString(C.colorBorder(new int[][]{{1, 2, 1}}, 0, 0, 5)) + " (Expected: [[5, 2, 1]])");

        // Test 9: the 7 x 9 walkthrough grid — a hole, grid-edge cells, interior cells and a
        // disconnected group of 1s (bottom-right) all at once
        int[][] walkthrough = {
                {2, 2, 1, 1, 1, 2, 2, 3, 3},
                {2, 1, 1, 1, 1, 1, 2, 3, 3},
                {2, 1, 1, 4, 1, 1, 1, 2, 3},
                {2, 1, 1, 1, 1, 1, 1, 2, 3},
                {2, 2, 1, 1, 1, 1, 2, 2, 3},
                {3, 2, 2, 1, 1, 2, 2, 1, 1},
                {3, 3, 2, 2, 1, 2, 2, 1, 1}};
        System.out.println("Test 9: " + Arrays.deepToString(C.colorBorder(walkthrough, 3, 4, 9))
                + " (Expected: [[2, 2, 9, 9, 9, 2, 2, 3, 3], [2, 9, 1, 9, 1, 9, 2, 3, 3], [2, 9, 9, 4, 9, 1, 9, 2, 3], [2, 9, 1, 9, 1, 1, 9, 2, 3], [2, 2, 9, 1, 1, 9, 2, 2, 3], [3, 2, 2, 9, 9, 2, 2, 1, 1], [3, 3, 2, 2, 9, 2, 2, 1, 1]])");
    }
}
