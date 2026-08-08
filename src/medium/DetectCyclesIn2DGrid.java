package medium;

// https://leetcode.com/problems/detect-cycles-in-2d-grid/
public class DetectCyclesIn2DGrid {
    int[] dx = {0,0,1,-1};
    int[] dy = {1,-1,0,0};
    public boolean containsCycle(char[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        boolean[][] visited = new boolean[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (!visited[i][j]) {
                    if (isCycle(i, j, -1, -1, grid, rows, columns, visited, 1, grid[i][j]))
                        return true;
                }
            }
        }
        return false;
    }

    private boolean isCycle(int i, int j, int pi, int pj, char[][] grid, int rows, int columns, boolean[][] visited, int length, char c) {
        visited[i][j] = true;
        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x == pi && y == pj)
                continue;
            if (x >= 0 && x < rows && y >= 0 && y < columns && grid[x][y] == c) {
                if (visited[x][y] && length >= 4)
                    return true;
                if (!visited[x][y]) {
                    if (isCycle(x, y, i, j, grid, rows, columns, visited, length + 1, c))
                        return true;
                }
            }
        }
        return false;
    }

    /*
     * Revision Note — Detect Cycles in 2D Grid (Medium)
     * Pattern: DFS with parent tracking on an implicit grid graph
     * Key Insight: Model same-character adjacent cells as an undirected graph; a cycle
     *              exists iff DFS reaches an already-visited non-parent neighbor.
     * Gotchas:
     *   - Bounds-check before any grid[x][y] access — the check must gate the access,
     *     not come after it.
     *   - Track parent cell (pi, pj) and skip it — without this, a straight 4-cell row
     *     falsely triggers because the grandparent is "visited" and non-parent.
     *   - Grid girth is 4 (minimum cycle = 2×2 square), so the length >= 4 guard is
     *     technically redundant once parent tracking is in place, but harmless.
     *   - Union-Find is a cleaner alternative: union same-char neighbors; if already
     *     in the same component before union, a cycle is detected.
     */
    public static void main(String[] args) {
        DetectCyclesIn2DGrid D = new DetectCyclesIn2DGrid();

        // Test 1: LeetCode example 1 — outer ring of 'a' forms a cycle
        System.out.println("Test 1: " + D.containsCycle(new char[][]{{'a','a','a','a'},{'a','b','b','a'},{'a','b','b','a'},{'a','a','a','a'}}) + " (Expected: true)");

        // Test 2: LeetCode example 2 — 'c' forms a cycle
        System.out.println("Test 2: " + D.containsCycle(new char[][]{{'c','c','c','a'},{'c','d','c','c'},{'c','c','e','c'},{'f','c','c','c'}}) + " (Expected: true)");

        // Test 3: LeetCode example 3 — no 4-cell cycle
        System.out.println("Test 3: " + D.containsCycle(new char[][]{{'a','b','b'},{'b','z','b'},{'b','b','a'}}) + " (Expected: false)");

        // Test 4: 2x2 all same char — smallest possible cycle
        System.out.println("Test 4: " + D.containsCycle(new char[][]{{'a','a'},{'a','a'}}) + " (Expected: true)");

        // Test 5: single cell — no cycle possible
        System.out.println("Test 5: " + D.containsCycle(new char[][]{{'a'}}) + " (Expected: false)");

        // Test 6: 1xN row — no cycle (linear, can't loop back)
        System.out.println("Test 6: " + D.containsCycle(new char[][]{{'a','a','a','a'}}) + " (Expected: false)");
    }
}
