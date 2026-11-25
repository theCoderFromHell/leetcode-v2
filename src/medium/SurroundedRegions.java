package medium;

// https://leetcode.com/problems/surrounded-regions/
public class SurroundedRegions {
    private int[] dx = {-1, 1, 0, 0};
    private int[] dy = {0, 0, -1, 1};

    public void solve(char[][] board) {
        if(board == null || board.length == 0 || board[0].length == 0)
            return;
        int M = board.length;
        int N = board[0].length;
        for(int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if(board[i][j] == 'O' && (i == 0 || i == M-1 || j == 0 || j == N-1)) {
                    dfs(board, i, j, M, N);
                }
            }
        }
        for(int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 'O')
                    board[i][j] = 'X';
                else if(board[i][j] == 'Z')
                    board[i][j] = 'O';
            }
        }
    }

    private void dfs(char[][] board, int i, int j, int M, int N) {
        board[i][j] = 'Z';
        for(int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if((x >= 0 && y >= 0 && x < M && y < N) && board[x][y] == 'O') {
                dfs(board, x, y, M, N);
            }
        }
    }

    // Test helpers: build a board from row strings, and print one back as "row/row/row"
    private static char[][] board(String... rows) {
        char[][] b = new char[rows.length][];
        for (int i = 0; i < rows.length; i++)
            b[i] = rows[i].toCharArray();
        return b;
    }

    private static String show(char[][] b) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : b)
            sb.append(sb.length() == 0 ? "" : "/").append(row);
        return sb.toString();
    }

    /*
     * Revision Note — Surrounded Regions (Medium)
     *
     * Pattern: flood from the border, then sweep. A region survives exactly when it touches the
     * edge, so instead of asking "does this region reach the border?" once per region, ask "what
     * does the border reach?" once. Every 'O' on the edge starts a DFS that marks its whole region
     * 'Z' (safe). One final pass captures every 'O' that is still 'O' and turns the 'Z's back.
     *
     * Key insight: invert the question. Checking each region separately needs a flag carried
     * through the fill and a second pass to undo or apply it; seeding from the border answers
     * every region at once, in O(M·N).
     *
     * Gotchas:
     *  - The temporary third state ('Z') is what lets one board hold both answers. Without it, a
     *    safe 'O' and an unvisited 'O' look the same during the final sweep.
     *  - The final sweep is one pass with if / else-if, so each cell takes exactly one branch:
     *    'O' → 'X' or 'Z' → 'O'. Split into two passes in the wrong order (first 'Z' → 'O', then
     *    'O' → 'X'), it would capture the safe cells too.
     *  - Connectivity is 4-directional. An 'O' touching a border 'O' only diagonally is still
     *    captured (Test 7).
     *  - The first scan visits all M·N cells but only edge cells start a fill. Walking just the
     *    four edges does the same work in O(M + N) before the fills.
     *  - The DFS recursion can go M·N deep. A 200 × 200 board of all 'O' (the largest input) is
     *    about 40,000 frames. Run cold from main() it throws StackOverflowError on the default
     *    2 MB stack, and even at -Xss4m; it passes at -Xss8m. Once the JIT has compiled dfs the
     *    frames shrink and it fits in 2 MB, which is probably why LeetCode, running many tests in
     *    one JVM, accepted it. That test is left out of main() because it would crash it. An
     *    explicit stack, or a BFS from the border, removes the risk.
     *  - The null/empty guard can't trigger: the constraints give 1 <= m, n.
     *
     * Complexity: O(M·N) time (each cell is marked at most once, plus two scans). O(M·N) space for
     * the recursion stack in the worst case; the board itself holds the marks.
     *
     * Template:
     *   for each edge cell that is 'O': dfs(cell) marks its region 'Z'
     *   for each cell: 'O' → 'X'; 'Z' → 'O'
     *
     * Alternative: union-find with one virtual node for the border. Union every edge 'O' with the
     * virtual node and every 'O' with its 'O' neighbours; afterwards an 'O' is captured unless it
     * shares a root with the virtual node. No recursion, same near-linear time.
     */
    public static void main(String[] args) {
        SurroundedRegions S = new SurroundedRegions();

        // Test 1: LeetCode example 1 — the interior region is captured; the bottom 'O' is on the
        // edge, so it stays
        char[][] b1 = board("XXXX", "XOOX", "XXOX", "XOXX");
        S.solve(b1);
        System.out.println("Test 1: " + show(b1) + " (Expected: XXXX/XXXX/XXXX/XOXX)");

        // Test 2: LeetCode example 2 — a single 'X', nothing to do
        char[][] b2 = board("X");
        S.solve(b2);
        System.out.println("Test 2: " + show(b2) + " (Expected: X)");

        // Test 3: every cell is 'O' — one region touching the edge, so nothing is captured
        char[][] b3 = board("OOO", "OOO", "OOO");
        S.solve(b3);
        System.out.println("Test 3: " + show(b3) + " (Expected: OOO/OOO/OOO)");

        // Test 4: a winding region that reaches the edge only at (4,3) survives, while the lone
        // interior 'O' at (3,1) is captured
        char[][] b4 = board("XXXXX", "XOOOX", "XXXOX", "XOXOX", "XXXOX");
        S.solve(b4);
        System.out.println("Test 4: " + show(b4) + " (Expected: XXXXX/XOOOX/XXXOX/XXXOX/XXXOX)");

        // Test 5: a single row — every cell is on the edge, so nothing can be captured
        char[][] b5 = board("OOXO");
        S.solve(b5);
        System.out.println("Test 5: " + show(b5) + " (Expected: OOXO)");

        // Test 6: a ring of 'O' around an 'X', touching no edge — the whole ring is captured
        char[][] b6 = board("XXXXX", "XOOOX", "XOXOX", "XOOOX", "XXXXX");
        S.solve(b6);
        System.out.println("Test 6: " + show(b6) + " (Expected: XXXXX/XXXXX/XXXXX/XXXXX/XXXXX)");

        // Test 7: an interior 'O' touching an edge 'O' only diagonally — diagonals don't connect,
        // so it is captured
        char[][] b7 = board("OXX", "XOX", "XXX");
        S.solve(b7);
        System.out.println("Test 7: " + show(b7) + " (Expected: OXX/XXX/XXX)");
    }
}
