package medium;

import java.util.Arrays;

// https://leetcode.com/problems/last-visited-cell-in-a-directional-grid/
public class LastVisitedCellInADirectionalGrid {
    public int[] lastVisitedCell(char[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        boolean[][] visited = new boolean[rows][columns];
        int row = 0, column = 0;
        int prevRow, prevColumn;
        do {
            visited[row][column] = true;
            prevRow = row;
            prevColumn = column;
            switch (grid[row][column]) {
                case 'L' :
                    column--;
                    break;
                case 'R' :
                    column++;
                    break;
                case 'U' :
                    row--;
                    break;
                case 'D' :
                    row++;
                    break;
            }
        } while (valid(rows, columns, row, column, visited));
        return new int[]{prevRow, prevColumn};
    }

    private boolean valid(int rows, int columns, int row, int column, boolean[][] visited) {
        return (row >= 0 && row < rows && column >= 0 && column < columns && !visited[row][column]);
    }

    /*
     * Revision Note — Last Visited Cell in a Directional Grid (Medium)
     *
     * Pattern: Direct simulation with a visited grid. Each cell points to exactly one next cell,
     * so there is never a choice to make: follow the arrows and stop on the first illegal move.
     *
     * Key insight: a cell can be entered at most once, so the walk takes at most m · n steps and
     * always ends. That's O(m · n) time without any cycle detection beyond the visited check.
     *
     * Gotchas:
     *  - Two different stop conditions (off the grid, or a visited cell), and in both cases the
     *    move is NOT made: return the cell you're standing on, not the target.
     *  - Mark (0, 0) visited before the first move, or a path that returns to the start
     *    (Test 1: the final 'U' points back at (0, 0)) walks one cell too far.
     *  - m · n ≤ 1e5 cells, so a recursive walk can go 1e5 deep and overflow Java's default stack
     *    (Test 7). Keep it iterative.
     *
     * Complexity: O(m · n) time (each cell entered at most once), O(m · n) space for visited.
     *
     * Template:
     *   r = c = 0; visited[0][0] = true
     *   loop: (nr, nc) = (r, c) + dir[grid[r][c]]
     *         if out of bounds or visited[nr][nc] → return [r, c]
     *         visited[nr][nc] = true; (r, c) = (nr, nc)
     *
     * Alternative, O(1) extra space: overwrite each cell with a sentinel (e.g. '#') once you've
     * read its direction, so "visited" is just grid[nr][nc] == '#'. This modifies the input.
     */
    public static void main(String[] args) {
        LastVisitedCellInADirectionalGrid L = new LastVisitedCellInADirectionalGrid();

        // Test 1: LeetCode example 1 — the last move points back at the start cell (0, 0)
        System.out.println("Test 1: " + Arrays.toString(L.lastVisitedCell(new char[][]{{'R', 'D'}, {'U', 'L'}})) + " (Expected: [1, 0])");

        // Test 2: LeetCode example 2 — stops at the grid's right edge
        System.out.println("Test 2: " + Arrays.toString(L.lastVisitedCell(new char[][]{{'R', 'R', 'R'}})) + " (Expected: [0, 2])");

        // Test 3: single cell — the first move already leaves the grid, so (0, 0) is the answer
        System.out.println("Test 3: " + Arrays.toString(L.lastVisitedCell(new char[][]{{'U'}})) + " (Expected: [0, 0])");

        // Test 4: single column — walks down, then 'U' points at a visited cell
        System.out.println("Test 4: " + Arrays.toString(L.lastVisitedCell(new char[][]{{'D'}, {'D'}, {'U'}})) + " (Expected: [2, 0])");

        // Test 5: two cells pointing at each other — the shortest possible revisit
        System.out.println("Test 5: " + Arrays.toString(L.lastVisitedCell(new char[][]{{'R', 'L'}})) + " (Expected: [0, 1])");

        // Test 6: 3 x 3 snake — visits all 9 cells, then the final 'U' hits a visited one
        System.out.println("Test 6: " + Arrays.toString(L.lastVisitedCell(new char[][]{{'R', 'R', 'D'}, {'D', 'L', 'L'}, {'R', 'R', 'U'}})) + " (Expected: [2, 2])");

        // Test 7: 1 x 100000, all 'R' — a 1e5-step walk (a recursive version overflows the stack)
        char[][] big = new char[1][100000];
        Arrays.fill(big[0], 'R');
        System.out.println("Test 7: " + Arrays.toString(L.lastVisitedCell(big)) + " (Expected: [0, 99999])");
    }
}
