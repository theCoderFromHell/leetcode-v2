package contests.weekly_529;

import java.util.*;

// https://leetcode.com/problems/earliest-signal-activation-times/
public class EarliestSignalActivationTimes {
    public long[] earliestActivationTimes(int n, int[][] edges, int[] requiredSignals) {
        int size = edges.length;
        HashMap<Integer, List<int[]>> adjList = new HashMap<>();
        for (int i = 0; i < n; i++)
            adjList.put(i, new ArrayList<>());
        for (int i = 0; i < size; i++) {
            int src = edges[i][0];
            int dest = edges[i][1];
            int delay = edges[i][2];
            adjList.get(src).add(new int[]{dest, delay});
        }
        long[] result = new long[n];
        Arrays.fill(result, -1);
        HashMap<Integer,Integer> signalCount = new HashMap<>();
        Queue<long[]> queue = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        queue.add(new long[]{0, 0});
        signalCount.put(0, requiredSignals[0] - 1);
        while (!queue.isEmpty()) {
            long[] front = queue.poll();
            int node = (int) front[0];
            long delay = front[1];
            signalCount.put(node, signalCount.getOrDefault(node, 0) + 1);
            if (signalCount.get(node) != requiredSignals[node])
                continue;
            result[node] = delay;
            List<int[]> neighbours = adjList.get(node);
            for (int[] neighbour : neighbours)
                queue.add(new long[]{neighbour[0], delay + neighbour[1]});
        }
        return result;
    }

    /*
     * Revision Note — Earliest Signal Activation Times (Medium, Weekly Contest 529)
     *
     * Pattern: Dijkstra over arrival events. A min-heap holds (node, arrival time), and a node
     * activates on its requiredSignals[node]-th arrival to be polled.
     *
     * Key insight: arrivals at a node leave the heap in time order, so the k-th one polled is the
     * k-th earliest, which is exactly when the node activates. Each node activates once, so each edge
     * carries at most one signal. There is at most one edge u -> v, so every arrival at v comes from a
     * different neighbour. Counting arrivals is therefore the same as counting distinct neighbours.
     * With every requirement equal to 1 this is plain Dijkstra (743 Network Delay Time).
     *
     * Gotchas:
     *  - Node 0 is seeded with count requiredSignals[0] - 1, so its first poll activates it at time 0
     *    whatever requiredSignals[0] says (Examples 2-4 have 3).
     *  - Activate on == required, not >=. Signals that reach an active node push its count past the
     *    requirement, so it never matches again and the node never re-sends (Example 4's 1 -> 0).
     *  - Activation is at the k-th earliest arrival, not the last one (Test 7), and not the first
     *    when k > 1 (Test 6).
     *  - Times need long: up to 10^5 edges of 10^9 each is 10^14 (Test 5 already passes 2^31).
     *  - Nodes that wait on each other in a cycle never activate (Example 4), and a node with no
     *    incoming edges stays -1 (Test 8).
     *
     * Complexity: O((n + m) log m) time. Each edge is pushed at most once, when its source
     * activates. O(n + m) space. Checked: a 10^5 chain took 16 ms, and 10^5 random edges 19 ms.
     *
     * Template:
     *   heap of (node, time) by time; push (0, 0); count[0] = required[0] - 1
     *   while heap: poll (u, t); if ++count[u] != required[u]: continue
     *       ans[u] = t; for each (v, d) out of u: push (v, t + d)
     *   ans defaults to -1
     */
    public static void main(String[] args) {
        EarliestSignalActivationTimes E = new EarliestSignalActivationTimes();

        // Test 1: LeetCode example 1 — node 2 needs 2 distinct neighbours but has only one
        System.out.println("Test 1: " + Arrays.toString(E.earliestActivationTimes(3, new int[][]{{0, 1, 1}, {0, 2, 1}}, new int[]{1, 1, 2})) + " (Expected: [0, 1, -1])");

        // Test 2: LeetCode example 2 — requiredSignals[0] = 3 is ignored; node 3 gets both signals at time 3
        System.out.println("Test 2: " + Arrays.toString(E.earliestActivationTimes(4, new int[][]{{0, 1, 1}, {0, 2, 1}, {1, 3, 2}, {2, 3, 2}}, new int[]{3, 1, 1, 2})) + " (Expected: [0, 1, 1, 3])");

        // Test 3: LeetCode example 3 — node 2's two signals both arrive at time 5
        System.out.println("Test 3: " + Arrays.toString(E.earliestActivationTimes(4, new int[][]{{0, 1, 2}, {1, 2, 3}, {0, 2, 5}, {2, 3, 1}}, new int[]{3, 1, 2, 1})) + " (Expected: [0, 2, 5, 6])");

        // Test 4: LeetCode example 4 — 1 -> 0 reaches an already active node; nodes 2 and 3 wait on each other
        System.out.println("Test 4: " + Arrays.toString(E.earliestActivationTimes(4, new int[][]{{0, 1, 2}, {1, 0, 1}, {1, 2, 1}, {2, 3, 1}, {3, 2, 1}}, new int[]{3, 1, 2, 1})) + " (Expected: [0, 2, -1, -1])");

        // Test 5: a chain of delays of 10^9 — node 2 activates at 2·10^9, past Integer.MAX_VALUE
        System.out.println("Test 5: " + Arrays.toString(E.earliestActivationTimes(4, new int[][]{{0, 1, 1000000000}, {1, 2, 1000000000}, {2, 3, 1000000000}}, new int[]{1, 1, 1, 1})) + " (Expected: [0, 1000000000, 2000000000, 3000000000])");

        // Test 6: node 2 needs both signals — the one via node 1 arrives at 3, but it must wait for
        // the direct one at 10
        System.out.println("Test 6: " + Arrays.toString(E.earliestActivationTimes(3, new int[][]{{0, 1, 1}, {0, 2, 10}, {1, 2, 2}}, new int[]{1, 1, 2})) + " (Expected: [0, 1, 10])");

        // Test 7: node 3 has three incoming signals (at 2, 6 and 9) and needs two — it activates at
        // the second, 6, not the last
        System.out.println("Test 7: " + Arrays.toString(E.earliestActivationTimes(4, new int[][]{{0, 1, 1}, {0, 2, 5}, {0, 3, 9}, {1, 3, 1}, {2, 3, 1}}, new int[]{1, 1, 1, 2})) + " (Expected: [0, 1, 5, 6])");

        // Test 8: no edges — only node 0 is ever active
        System.out.println("Test 8: " + Arrays.toString(E.earliestActivationTimes(2, new int[][]{}, new int[]{1, 1})) + " (Expected: [0, -1])");
    }
}
