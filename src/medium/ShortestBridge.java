package medium;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

// https://leetcode.com/problems/shortest-bridge/
public class ShortestBridge {
    int[][] DIRECTIONS = new int[][]{{0,-1}, {0,1}, {-1,0}, {1,0}};
    public int shortestBridge(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        boolean[][] visited = new boolean[rows][columns];
        boolean firstIslandFound = false;
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < rows && !firstIslandFound; i++) {
            for (int j = 0; j < columns && !firstIslandFound; j++) {
                if (!visited[i][j] && grid[i][j] != 0) {
                    dfs(grid, rows, columns, i, j, visited, queue);
                    firstIslandFound = true;
                }
            }
        }
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cell = queue.poll();
                int i = cell[0];
                int j = cell[1];
                for (int[] direction : DIRECTIONS) {
                    int x = i + direction[0];
                    int y = j + direction[1];
                    if (valid(x, y, rows, columns) && !visited[x][y]) {
                        if (grid[x][y] == 1)
                            return step;
                        else if (grid[x][y] == 0) {
                            visited[x][y] = true;
                            queue.add(new int[]{x, y});
                        }
                    }
                }
            }
            step++;
        }
        return -1;
    }

    private void dfs(int[][] grid, int rows, int columns, int i, int j, boolean[][] visited, Queue<int[]> queue) {
        visited[i][j] = true;
        queue.add(new int[]{i, j});
        for (int[] direction : DIRECTIONS) {
            int x = i + direction[0];
            int y = j + direction[1];
            if (valid(x, y, rows, columns) && grid[x][y] == 1 && !visited[x][y])
                dfs(grid, rows, columns, x, y, visited, queue);
        }
    }

    private boolean valid(int i, int j, int rows, int columns) {
        return (i >= 0 && i < rows &&
                j >= 0 && j < columns);
    }

    /*
     * Revision Note — Shortest Bridge (Medium)
     *
     * Pattern: flood fill + multi-source BFS. DFS finds one whole island and drops every one of its
     * cells into the queue. BFS then grows outward through water, one layer at a time, until it
     * touches the other island.
     *
     * Key insight: the source of the shortest path is a REGION, not a cell. Seeding the queue with
     * the whole island makes layer k = every water cell exactly k steps from the island. The answer
     * is the layer number of the cell being expanded when a neighbour turns out to be island 2.
     * Island cells are layer 0, so you return `step` before incrementing it.
     *
     * Gotchas (from this session):
     *  - Mark water visited WHEN ENQUEUED. The first version never marked water at all, so every
     *    cell was re-added from every neighbour, including the way back. The queue then held one
     *    entry per WALK, not per cell: OutOfMemoryError at n = 10 with single-cell corner islands
     *    (Test 8 guards it).
     *  - Keep island 2 unvisited. An early version marked both islands visited, and a !visited filter
     *    would then skip the very cell you're looking for.
     *  - A plain `break` leaves only the inner loop. Without the flag, the scan went on and
     *    flood-filled island 2 as well.
     *  - Starting from a single island-1 cell gives the wrong distances when island 1 is large
     *    (Tests 5 and 7). Every island cell has to be a source.
     *  - The recursive DFS goes about 10^4 frames deep on a near-full island (Test 9). That fits the
     *    default 2 MB stack here but overflows at -Xss1m. LeetCode accepted the recursive version,
     *    but an iterative flood fill removes the risk anywhere.
     *
     * Complexity: O(n²) time (each cell enqueued at most once, 4 neighbour checks), O(n²) space
     * (visited + queue + DFS stack).
     *
     * Template:
     *   flood-fill island 1: visited = true, queue.add(cell)
     *   step = 0
     *   while queue: repeat size times:
     *       for each in-bounds, unvisited neighbour:
     *           if land → return step
     *           visited = true; queue.add(neighbour)
     *       step++
     *
     * Alternative: 0-1 BFS (deque) from any one land cell. Stepping onto land costs 0 (addFirst),
     * stepping onto water costs 1 (addLast). All of island 1 settles at distance 0 before any water,
     * so the first land cell popped with distance > 0 is island 2, and its distance is the answer.
     * One pass, no separate flood fill, O(n²).
     */
    public static void main(String[] args) {
        ShortestBridge S = new ShortestBridge();

        // Test 1: LeetCode example 1 — diagonal neighbours, one flip; layer 1 touches island 2
        System.out.println("Test 1: " + S.shortestBridge(new int[][]{{0, 1}, {1, 0}}) + " (Expected: 1)");

        // Test 2: LeetCode example 2 — the trace where unmarked water cells were added twice
        System.out.println("Test 2: " + S.shortestBridge(new int[][]{{0, 1, 0}, {0, 0, 0}, {0, 0, 1}}) + " (Expected: 2)");

        // Test 3: LeetCode example 3 — one island encloses the other; the scan finds the ring first
        System.out.println("Test 3: " + S.shortestBridge(new int[][]{{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}}) + " (Expected: 1)");

        // Test 4: single-cell islands in opposite corners — Manhattan distance 6, so 5 flips
        System.out.println("Test 4: " + S.shortestBridge(new int[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 1}}) + " (Expected: 5)");

        // Test 5: island 1 is a full row — a search seeded from (0, 0) alone would answer 5, not 1
        System.out.println("Test 5: " + S.shortestBridge(new int[][]{{1, 1, 1, 1, 1}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}}) + " (Expected: 1)");

        // Test 6: two 2 x 2 blocks — the bridge runs between the nearest corners, (1,1) and (3,3)
        System.out.println("Test 6: " + S.shortestBridge(new int[][]{{1, 1, 0, 0, 0}, {1, 1, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 1, 1}, {0, 0, 0, 1, 1}}) + " (Expected: 3)");

        // Test 7: island 2 is a single cell — island 1 is the column the scan finds first, and only
        // its bottom cell is close; seeding the whole column is what finds it
        System.out.println("Test 7: " + S.shortestBridge(new int[][]{{0, 0, 0, 0, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 1}, {0, 0, 0, 0, 1}, {1, 0, 0, 0, 1}}) + " (Expected: 3)");

        // Test 8: n = 100, single-cell islands in opposite corners — the longest possible bridge.
        // Without marking water visited on enqueue, this ran out of memory already at n = 10
        int[][] corners = new int[100][100];
        corners[0][0] = 1;
        corners[99][99] = 1;
        System.out.println("Test 8: " + S.shortestBridge(corners) + " (Expected: 197)");

        // Test 9: n = 100, island 1 fills all but 4 cells, island 2 is the corner (99, 99).
        // The DFS recurses ~10^4 deep: fine on the default 2 MB stack, StackOverflowError at -Xss1m
        int[][] nearFull = new int[100][100];
        for (int[] row : nearFull)
            Arrays.fill(row, 1);
        nearFull[98][98] = 0;
        nearFull[98][99] = 0;
        nearFull[99][98] = 0;
        System.out.println("Test 9: " + S.shortestBridge(nearFull) + " (Expected: 1)");
    }
}
