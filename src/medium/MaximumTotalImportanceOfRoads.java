package medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

// https://leetcode.com/problems/maximum-total-importance-of-roads/
public class MaximumTotalImportanceOfRoads {
    public long maximumImportance(int n, int[][] roads) {
        int[] degree = new int[n];
        int size = roads.length;
        for (int i = 0; i < size; i++) {
            degree[roads[i][0]]++;
            degree[roads[i][1]]++;
        }
        Arrays.sort(degree);
        long result = 0;
        for (int i = 0; i < n; i++)
            result += ((long) (i + 1) * degree[i]);
        return result;
    }
    /*
     * Revision Note — Maximum Total Importance of Roads (Medium)
     * Pattern: Greedy — assign highest values to highest-degree cities
     * Key Insight: A city with degree d contributes value × d to the total; maximized by
     *              sorting degrees ascending and multiplying degree[i] × (i+1).
     * Gotchas:
     *   - PriorityQueue comparator must return a difference (b[1]-a[1]), not just b[1].
     *     Returning b[1] alone gives nonsensical ordering because comparators signal via sign.
     *   - Cast to long before multiplying: (i+1) * degree[i] can exceed int range for large n.
     *   - Zero-degree cities don't need special handling — they naturally sort to the front
     *     and contribute 0 regardless of their assigned value.
     *   - Arrays.sort + multiply is simpler than PriorityQueue for this pattern (V2 kept for reference).
     */
    public static void main(String[] args) {
        MaximumTotalImportanceOfRoads M = new MaximumTotalImportanceOfRoads();

        // Test 1: hub node with degree 3, rest degree 1 — hub gets highest value
        System.out.println("Test 1: " + M.maximumImportance(4, new int[][]{{0,1},{1,2},{1,3}}) + " (Expected: 18)");

        // Test 2: single road — two cities each get value 1 and 2, sum = 3
        System.out.println("Test 2: " + M.maximumImportance(2, new int[][]{{0,1}}) + " (Expected: 3)");

        // Test 3: no roads — all contributions are 0
        System.out.println("Test 3: " + M.maximumImportance(5, new int[][]{}) + " (Expected: 0)");

        // Test 4: complete graph of 3 nodes — all degrees equal, any assignment gives same result
        System.out.println("Test 4: " + M.maximumImportance(3, new int[][]{{0,1},{1,2},{0,2}}) + " (Expected: 12)");

        // Test 5: isolated nodes mixed with connected — zero-degree nodes get lowest values
        System.out.println("Test 5: " + M.maximumImportance(5, new int[][]{{0,1},{1,2},{1,3}}) + " (Expected: 24)");

        // Test 6: V2 matches V1
        System.out.println("Test 6 (V2): " + M.maximumImportanceV2(4, new int[][]{{0,1},{1,2},{1,3}}) + " (Expected: 18)");
    }

    public long maximumImportanceV2(int n, int[][] roads) {
        HashMap<Integer, Integer> count = new HashMap<>();
        int size = roads.length;
        for (int i = 0; i < size; i++) {
            int u = roads[i][0];
            int v = roads[i][1];
            count.put(u, count.getOrDefault(u, 0) + 1);
            count.put(v, count.getOrDefault(v, 0) + 1);
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        for (int node : count.keySet())
            pq.add(new int[]{node, count.get(node)});
        long result = 0;
        int importance = n;
        while (!pq.isEmpty()) {
            int[] value = pq.poll();
            result += ((long) value[1] * importance);
            importance--;
        }
        return result;
    }
}
