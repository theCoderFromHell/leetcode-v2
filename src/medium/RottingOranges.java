package medium;

import java.util.LinkedList;
import java.util.Queue;

// https://leetcode.com/problems/rotting-oranges/
public class RottingOranges {
    private int[] dx = {-1, 1, 0, 0};
    private int[] dy = {0, 0, -1, 1};

    public int orangesRotting(int[][] grid) {
        if(null == grid || grid.length == 0 || grid[0].length == 0)
            return Integer.MAX_VALUE;
        int M = grid.length;
        int N = grid[0].length;
        int result = 0;
        Queue<Cell> queue = new LinkedList<>();
        int countOfFresh = 0;
        int countOfRotten = 0;
        int[][] visited = new int[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if(grid[i][j] == 2) {
                    Cell cell = new Cell(i, j, 0);
                    queue.add(cell);
                    visited[cell.x][cell.y] = 1;
                } else if(grid[i][j] == 1) {
                    countOfFresh++;
                }
            }
        }

        while(!queue.isEmpty()) {
            Cell cell = queue.poll();
            for (int i = 0; i < 4; i++) {
                int x = cell.x + dx[i];
                int y = cell.y + dy[i];
                result = Math.max(result, cell.time);
                if((x >= 0 && y >= 0 && x < M && y < N) && visited[x][y] == 0 && grid[x][y] == 1) {
                    Cell newCell = new Cell(x, y, cell.time + 1);
                    queue.add(newCell);
                    visited[x][y] = 1;
                    countOfRotten++;
                }
            }
        }
        if(countOfRotten < countOfFresh)
            return -1;
        return result;
    }

    /*
     * Revision Note — Rotting Oranges (Medium)
     *
     * Pattern: multi-source BFS. Every rotten orange goes into the queue at time 0 before the search
     * starts, so the waves from all of them spread together and each fresh orange is reached by the
     * nearest one first. Its BFS time is the minute it rots; the answer is the largest time seen.
     *
     * Key insight: "impossible" is a counting question, not a search question. Count the fresh
     * oranges up front, count the ones the BFS reaches, and if any are left over the answer is -1.
     *
     * Gotchas:
     *  - All sources go in before the BFS runs. Starting from one rotten orange and adding the others
     *    later gives each cell its distance to the WRONG orange (Test 4: 2, not 4).
     *  - No fresh oranges at all is 0, not -1, even with no rotten one either (Tests 3 and 6).
     *    Fresh oranges with no rotten orange anywhere is -1 (Test 5).
     *  - Mark a cell visited when it is enqueued, as here, so it is never queued twice.
     *  - The time lives on each Cell, so the answer is max(cell.time). Here the max is updated inside
     *    the 4-neighbour loop; it is correct, but it only needs to run once per poll.
     *  - The null/empty guard can't trigger: the constraints give 1 <= m, n.
     *
     * Complexity: O(m·n) time (each cell enqueued at most once, 4 neighbour checks), O(m·n) space
     * (visited + queue).
     *
     * Template:
     *   for each cell: rotten → queue.add(cell, time 0), visited; fresh → fresh++
     *   while queue: cell = poll; ans = max(ans, cell.time)
     *       for each in-bounds, unvisited fresh neighbour: visited; rotted++; queue.add(nb, time + 1)
     *   return rotted < fresh ? -1 : ans
     *
     * Alternative: drop the time field and count minutes by layers, processing queue.size() cells
     * per minute (the same level loop as 934 Shortest Bridge). Setting grid[x][y] = 2 when an orange
     * rots replaces the visited array, and decrementing `fresh` lets the loop stop the moment it
     * reaches 0. Same O(m·n).
     */
    public static void main(String[] args) {
        RottingOranges R = new RottingOranges();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + R.orangesRotting(new int[][]{{2, 1, 1}, {1, 1, 0}, {0, 1, 1}}) + " (Expected: 4)");

        // Test 2: LeetCode example 2 — the bottom-left orange only touches empty cells, so it never rots
        System.out.println("Test 2: " + R.orangesRotting(new int[][]{{2, 1, 1}, {0, 1, 1}, {1, 0, 1}}) + " (Expected: -1)");

        // Test 3: LeetCode example 3 — no fresh oranges at minute 0, so the answer is 0
        System.out.println("Test 3: " + R.orangesRotting(new int[][]{{0, 2}}) + " (Expected: 0)");

        // Test 4: a rotten orange at each end of a row of fresh ones — the waves meet in the middle
        // after 2 minutes; spreading from one end alone would take 4
        System.out.println("Test 4: " + R.orangesRotting(new int[][]{{2, 1, 1, 1, 1, 2}}) + " (Expected: 2)");

        // Test 5: fresh oranges but no rotten one anywhere — nothing can ever start the spread
        System.out.println("Test 5: " + R.orangesRotting(new int[][]{{1, 1}, {0, 1}}) + " (Expected: -1)");

        // Test 6: no oranges at all — nothing fresh is left, so 0, not -1
        System.out.println("Test 6: " + R.orangesRotting(new int[][]{{0}}) + " (Expected: 0)");

        // Test 7: one winding path of fresh oranges — the time is the path length (6), not the
        // straight-line distance to the far corner (4)
        System.out.println("Test 7: " + R.orangesRotting(new int[][]{{2, 1, 1}, {0, 0, 1}, {1, 1, 1}}) + " (Expected: 6)");
    }
}

class Cell {
    int x;
    int y;
    int time;

    public Cell(int x, int y, int time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", time=" + time +
                '}';
    }
}
