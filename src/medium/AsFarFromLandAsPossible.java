package medium;

import java.util.Arrays;

// https://leetcode.com/problems/as-far-from-land-as-possible/
public class AsFarFromLandAsPossible {
    public int maxDistance(int[][] grid) {
        int N = grid.length;
        int[][] dist = new int[N][N];
        for (int i = 0; i < N; i++)
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        int maxDistance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 1)
                    dist[i][j] = 0;
                else {
                    if (i > 0 && dist[i-1][j] != Integer.MAX_VALUE)
                        dist[i][j] = Math.min(dist[i][j], 1 + dist[i-1][j]);
                    if (j > 0 && dist[i][j-1] != Integer.MAX_VALUE)
                        dist[i][j] = Math.min(dist[i][j], 1 + dist[i][j-1]);
                }
            }
        }
        for (int i = N-1; i >= 0; i--) {
            for (int j = N-1; j >= 0; j--) {
                if (grid[i][j] == 1)
                    dist[i][j] = 0;
                else {
                    if (i < N-1 && dist[i+1][j] != Integer.MAX_VALUE)
                        dist[i][j] = Math.min(dist[i][j], 1 + dist[i+1][j]);
                    if (j < N-1 && dist[i][j+1] != Integer.MAX_VALUE)
                        dist[i][j] = Math.min(dist[i][j], 1 + dist[i][j+1]);
                }
                if (dist[i][j] != Integer.MAX_VALUE)
                    maxDistance = Math.max(maxDistance, dist[i][j]);
            }
        }
        return (maxDistance == Integer.MAX_VALUE || maxDistance == 0) ? -1 : maxDistance;
    }

    /*
     * Revision Note — As Far from Land as Possible (Medium)
     * Pattern: two-pass DP distance transform (542's trick) instead of 286's multi-source BFS.
     *          Pass 1 runs top-left → bottom-right and pulls from top and left; pass 2 runs
     *          bottom-right → top-left and pulls from bottom and right.
     * Key Insight: with no walls, a shortest path to the nearest land only ever moves in two
     *              directions, and the two sweeps cover every pair. Land up-right of a cell travels
     *              DOWN its column in pass 1, then LEFT along the row in pass 2 (Test 6); land
     *              down-left travels right in pass 1, then up in pass 2 (Test 7).
     * Gotchas:
     *   - Guard Integer.MAX_VALUE before adding 1: 1 + MAX_VALUE wraps to MIN_VALUE and would win
     *     every min.
     *   - −1 has two causes: all land (every dist is 0, so the max stays 0) and no land (every dist
     *     stays MAX_VALUE, is skipped, and the max stays 0). So maxDistance == 0 covers both; the
     *     maxDistance == Integer.MAX_VALUE check can never fire, because MAX_VALUE cells are skipped
     *     before the max.
     *   - Take the max in pass 2 only: a cell's value is final once pass 2 reaches it, since it
     *     reads bottom and right neighbours that pass 2 has already finished.
     *   - Only correct because every cell is passable. With walls (286's rooms), paths have to bend
     *     around obstacles, and two sweeps miss them; that needs BFS.
     * Complexity: O(n²) time — two sweeps, each cell reading two neighbours. O(n²) space for dist
     *             (reusing grid would make it O(1) extra).
     * Alternative — multi-source BFS (286): queue every land cell at distance 0, expand layer by
     *   layer, and the answer is the number of layers after the first. −1 if the queue starts empty
     *   or full. Same O(n²), and it still works when walls are added.
     * Template:
     *   dist = INF everywhere; land = 0
     *   for i ↓, j →: dist = min(dist, top + 1, left + 1)
     *   for i ↑, j ←: dist = min(dist, bottom + 1, right + 1); best = max(best, dist)
     *   return best == 0 ? -1 : best
     */
    public static void main(String[] args) {
        AsFarFromLandAsPossible A = new AsFarFromLandAsPossible();

        // Test 1: LeetCode example 1 — the centre is 2 from every corner
        System.out.println("Test 1: " + A.maxDistance(new int[][]{{1, 0, 1}, {0, 0, 0}, {1, 0, 1}}) + " (Expected: 2)");

        // Test 2: LeetCode example 2 — one land cell, so the far corner is 2 + 2 away
        System.out.println("Test 2: " + A.maxDistance(new int[][]{{1, 0, 0}, {0, 0, 0}, {0, 0, 0}}) + " (Expected: 4)");

        // Test 3: all land — no water cell to measure, so −1 (the max stays 0)
        System.out.println("Test 3: " + A.maxDistance(new int[][]{{1, 1}, {1, 1}}) + " (Expected: -1)");

        // Test 4: all water — no land, every dist stays MAX_VALUE and is skipped, so −1
        System.out.println("Test 4: " + A.maxDistance(new int[][]{{0, 0}, {0, 0}}) + " (Expected: -1)");

        // Test 5: single cell of land — n = 1, no water
        System.out.println("Test 5: " + A.maxDistance(new int[][]{{1}}) + " (Expected: -1)");

        // Test 6: land only in the top-right corner — (2, 0) needs pass 1 to carry it down the column and pass 2
        // to carry it left along the row
        System.out.println("Test 6: " + A.maxDistance(new int[][]{{0, 0, 1}, {0, 0, 0}, {0, 0, 0}}) + " (Expected: 4)");

        // Test 7: land only in the bottom-left corner — the mirror case: right in pass 1, then up in pass 2
        System.out.println("Test 7: " + A.maxDistance(new int[][]{{0, 0, 0}, {0, 0, 0}, {1, 0, 0}}) + " (Expected: 4)");

        // Test 8: land in two opposite corners — each cell takes the NEARER one, so the off-corners are 3, not 6
        System.out.println("Test 8: " + A.maxDistance(new int[][]{{1, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 1}}) + " (Expected: 3)");
    }
}
