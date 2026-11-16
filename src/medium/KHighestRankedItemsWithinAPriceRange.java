package medium;

import java.util.*;

// https://leetcode.com/problems/k-highest-ranked-items-within-a-price-range/
public class KHighestRankedItemsWithinAPriceRange {
    int[][] DIRECTIONS = new int[][]{{0,-1}, {0,1}, {-1,0}, {1,0}};
    public List<List<Integer>> highestRankedKItems(int[][] grid, int[] pricing, int[] start, int k) {
        int rows = grid.length;
        int columns = grid[0].length;
        List<int[]> topItems = new ArrayList<>();
        if (pricing[0] <= grid[start[0]][start[1]] && grid[start[0]][start[1]] <= pricing[1])
            topItems.add(new int[]{start[0], start[1], 0, grid[start[0]][start[1]]});
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{start[0], start[1]});
        boolean[][] visited = new boolean[rows][columns];
        visited[start[0]][start[1]] = true;
        int distance = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] current = queue.poll();
                int x = current[0];
                int y = current[1];
                for (int[] direction : DIRECTIONS) {
                    int i = x + direction[0];
                    int j = y + direction[1];
                    if (valid(i, j, rows, columns, visited, grid)) {
                        visited[i][j] = true;
                        queue.add(new int[]{i, j});
                        if (pricing[0] <= grid[i][j] && grid[i][j] <= pricing[1])
                            topItems.add(new int[]{i, j, distance, grid[i][j]});
                    }
                }
            }
            if (topItems.size() >= k)
                break;
            distance++;
        }
        Collections.sort(topItems, (a, b) -> {
            if (a[2] != b[2])
                return a[2] - b[2];
            if (a[3] != b[3])
                return a[3] - b[3];
            if (a[0] != b[0])
                return a[0] - b[0];
            return a[1] - b[1];
        });
        int size = topItems.size();
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < k && i < size; i++)
            result.add(List.of(topItems.get(i)[0], topItems.get(i)[1]));
        return result;
    }

    private boolean valid(int i, int j, int rows, int columns, boolean[][] visited, int[][] grid) {
        return (i >= 0 && i < rows &&
                j >= 0 && j < columns
                && grid[i][j] != 0 && !visited[i][j]);
    }

    /*
     * Revision Note — K Highest Ranked Items Within a Price Range (Medium)
     *
     * Pattern: level-by-level BFS from one start, collecting matching items as it goes, then a
     * 4-key sort of what was collected.
     *
     * Key insight: the first ranking key is the BFS distance, and BFS already discovers cells in
     * distance order. Once a whole layer is finished and at least k items have been collected,
     * nothing still in the queue can outrank them, because every remaining cell is farther away.
     * Stop there and sort only what was collected.
     *
     * Gotchas:
     *  - Stop BETWEEN layers, never inside one. Items in the same layer are ranked by price, row and
     *    column, not by the order BFS happened to find them (Test 8: the price-3 item is found
     *    first, but the price-2 item wins).
     *  - The start cell is distance 0 and can itself be an item (Test 4). Its value is never 0, but
     *    it can be 1, and 1 is never in range because low >= 2.
     *  - Walls (0) block movement; cells with value 1 are walkable but are not items.
     *  - Fewer than k reachable items means returning all of them (Tests 3 and 6), so the output loop
     *    is bounded by both k and the list size.
     *  - The comparator subtracts values of at most 1e5, so it can't overflow here. Integer.compare
     *    is the safe habit when values can be large.
     *
     * Complexity: O(m·n + T log T) time, where T <= m·n is the number of items collected before the
     * stop, so O(m·n log(m·n)) worst case. O(m·n) space (visited + queue + collected items).
     *
     * Template:
     *   collect start if in range; queue = [start]; visited[start] = true; dist = 1
     *   while queue:
     *       repeat queue.size() times: for each open, unvisited neighbour:
     *           visited; enqueue; if in range → collect (row, col, dist, price)
     *       if collected >= k: break          // only after the whole layer
     *       dist++
     *   sort by (dist, price, row, col); return the first min(k, size)
     *
     * Alternatives: sort each layer on its own as it finishes (BFS already orders the layers) and
     * take items until k are in hand. Or keep a max-heap of size k under the reversed ranking,
     * O(T log k).
     */
    public static void main(String[] args) {
        KHighestRankedItemsWithinAPriceRange K = new KHighestRankedItemsWithinAPriceRange();

        // Test 1: LeetCode example 1 — every in-range item is at a different distance, so distance alone decides
        System.out.println("Test 1: " + K.highestRankedKItems(new int[][]{{1, 2, 0, 1}, {1, 3, 0, 1}, {0, 2, 5, 1}}, new int[]{2, 5}, new int[]{0, 0}, 3) + " (Expected: [[0, 1], [1, 1], [2, 1]])");

        // Test 2: LeetCode example 2 — (2,1) and (1,2) tie at distance 2, and the lower price wins
        System.out.println("Test 2: " + K.highestRankedKItems(new int[][]{{1, 2, 0, 1}, {1, 3, 3, 1}, {0, 2, 5, 1}}, new int[]{2, 3}, new int[]{2, 3}, 2) + " (Expected: [[2, 1], [1, 2]])");

        // Test 3: LeetCode example 3 — only 2 reachable items for k = 3, so both are returned
        System.out.println("Test 3: " + K.highestRankedKItems(new int[][]{{1, 1, 1}, {0, 0, 1}, {2, 3, 4}}, new int[]{2, 3}, new int[]{0, 0}, 3) + " (Expected: [[2, 1], [2, 0]])");

        // Test 4: the start cell holds an item in the price range — it is distance 0 and ranks first
        System.out.println("Test 4: " + K.highestRankedKItems(new int[][]{{3, 1, 2}, {1, 1, 1}}, new int[]{2, 3}, new int[]{0, 0}, 2) + " (Expected: [[0, 0], [0, 2]])");

        // Test 5: every item has the same price, start in the centre — ties on distance AND price fall
        // through to row, then column; the 5th item comes from the second layer
        System.out.println("Test 5: " + K.highestRankedKItems(new int[][]{{2, 2, 2}, {2, 1, 2}, {2, 2, 2}}, new int[]{2, 2}, new int[]{1, 1}, 5) + " (Expected: [[0, 1], [1, 0], [1, 2], [2, 1], [0, 0]])");

        // Test 6: some in-range items walled off from the start — unreachable items are never ranked,
        // so k = 3 returns just one
        System.out.println("Test 6: " + K.highestRankedKItems(new int[][]{{1, 2, 0, 3}, {1, 0, 0, 4}, {1, 1, 1, 0}}, new int[]{2, 4}, new int[]{0, 0}, 3) + " (Expected: [[0, 1]])");

        // Test 7: single row, prices on both ends of the range and just outside it — the range is
        // inclusive at both ends
        System.out.println("Test 7: " + K.highestRankedKItems(new int[][]{{1, 1, 2, 3, 4, 5, 6}}, new int[]{3, 5}, new int[]{0, 0}, 7) + " (Expected: [[0, 3], [0, 4], [0, 5]])");

        // Test 8: k is reached partway through a layer — BFS finds the price-3 item at (1,0) before the
        // price-2 item at (0,1). Stopping the moment k items are collected would return (1,0)
        System.out.println("Test 8: " + K.highestRankedKItems(new int[][]{{1, 2, 1}, {3, 1, 1}, {1, 1, 1}}, new int[]{2, 3}, new int[]{1, 1}, 1) + " (Expected: [[0, 1]])");
    }
}
