package medium;

import java.util.*;

// https://leetcode.com/problems/all-paths-from-source-lead-to-destination/
public class AllPathsFromSourceLeadToDestination {
    private static final int ON_PATH = 1;
    private static final int DONE = 2;
    public boolean leadsToDestination(int n, int[][] edges, int source, int destination) {
        int size = edges.length;
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < n; i++)
            adjList.add(new ArrayList<>());
        for (int i = 0; i < size; i++) {
            int src = edges[i][0];
            int dest = edges[i][1];
            adjList.get(src).add(dest);
        }
        int[] status = new int[n];
        return dfs(source, destination, adjList, status);
    }

    private boolean dfs(int node, int destination, List<List<Integer>> adjList, int[] status) {
        if (status[node] == DONE)
            return true;
        status[node] = ON_PATH;
        List<Integer> neighbours = adjList.get(node);
        if (node == destination && !neighbours.isEmpty())
            return false;
        if (node != destination && neighbours.isEmpty())
            return false;
        for (int neighbour : neighbours) {
            if (status[neighbour] == ON_PATH)
                return false;
            if (!dfs(neighbour, destination, adjList, status))
                return false;
        }
        status[node] = DONE;
        return true;
    }

    /*
     * Revision Note — All Paths from Source Lead to Destination (Medium)
     *
     * Pattern: DFS with three node states (0 = unseen, ON_PATH, DONE = finished). It is
     * 207's three-colour cycle check, run from one source, plus a check at every dead end.
     *
     * Key insight: "every path from source ends at destination" says two things about the part of
     * the graph the source can reach: it has no cycle, and its only node with no outgoing edges is
     * the destination. A plain visited flag can't tell a cycle (an edge back to a node still on the
     * current path, Example 2) from a diamond (an edge into a node already finished, Example 3). The
     * third state can. A finished node is known to be fine, because the first failure anywhere
     * returns false and ends the whole search.
     *
     * Gotchas (from this session):
     *  - BFS can't do it. Its visited array says "already visited" in both Example 2 and Example 3,
     *    and only one of them is a cycle. Only DFS has a current path to ask about.
     *  - Skip finished nodes (return true); don't explore them again. Re-exploring walks every
     *    distinct path, and a ladder doubles the paths at each layer (Test 8). 28 layers took 805M
     *    calls and 3.4 s; Test 8's 30 layers would take about four times that.
     *  - The destination must have no outgoing edges at all. A self-loop on it (Test 4), or an edge
     *    out of a source that is the destination (Test 10), makes the answer false. The
     *    `node == destination && !neighbours.isEmpty()` check returns early; without it the DFS
     *    would still find the resulting cycle or dead end, one step later.
     *  - A cycle that can still exit to the destination is false: it gives infinitely many paths
     *    (Test 9).
     *  - A self-loop is a cycle, because the node is already marked on-path when it sees itself. A
     *    parallel edge is harmless: the second copy finds the neighbour finished (Test 6).
     *  - Only what the source reaches matters. A cycle elsewhere is irrelevant (Test 7).
     *  - Recursion depth goes up to n = 10^4. A recursive DFS of this shape handles a 2×10^4 chain
     *    on the default stack and overflows by 5×10^4, so it fits here, unlike 3310's 10^5.
     *
     * Complexity: O(n + m) time: each node is entered once and each edge looked at once. O(n + m)
     * space for the adjacency list, plus O(n) for the recursion stack.
     *
     * Template:
     *   dfs(u):
     *       if state[u] == DONE: return true
     *       state[u] = ON_PATH
     *       if adj[u] is empty: return u == destination
     *       if u == destination: return false            // the destination has an edge out
     *       for v in adj[u]:
     *           if state[v] == ON_PATH or !dfs(v): return false
     *       state[u] = DONE; return true
     *
     * Alternative: reverse-graph Kahn's peeling from the destination, as in 802 Find Eventual Safe
     * States. Count each node's out-degree and start from the destination (only if its out-degree
     * is 0). Walk the reversed edges, decrementing each predecessor's count, and peel a node once
     * its count reaches 0. The source is good exactly when it gets peeled. It is iterative, so there
     * is no recursion depth at all; same O(n + m).
     */
    public static void main(String[] args) {
        AllPathsFromSourceLeadToDestination A = new AllPathsFromSourceLeadToDestination();

        // Test 1: LeetCode example 1 — node 1 is a dead end that isn't the destination
        System.out.println("Test 1: " + A.leadsToDestination(3, new int[][]{{0, 1}, {0, 2}}, 0, 2) + " (Expected: false)");

        // Test 2: LeetCode example 2 — 0 -> 3 is fine, but the source also reaches the cycle
        // 1 -> 2 -> 1, so there are infinitely many paths
        System.out.println("Test 2: " + A.leadsToDestination(4, new int[][]{{0, 1}, {0, 3}, {1, 2}, {2, 1}}, 0, 3) + " (Expected: false)");

        // Test 3: LeetCode example 3 — a diamond. Node 3 is reached a second time after it has
        // finished, which is not a cycle
        System.out.println("Test 3: " + A.leadsToDestination(4, new int[][]{{0, 1}, {0, 2}, {1, 3}, {2, 3}}, 0, 3) + " (Expected: true)");

        // Test 4: self-loop on the destination — the destination must have no outgoing edges at
        // all; the loop gives infinitely many paths
        System.out.println("Test 4: " + A.leadsToDestination(2, new int[][]{{0, 1}, {1, 1}}, 0, 1) + " (Expected: false)");

        // Test 5: single node, source == destination, no edges — the path that never moves already
        // ends at the destination
        System.out.println("Test 5: " + A.leadsToDestination(1, new int[][]{}, 0, 0) + " (Expected: true)");

        // Test 6: parallel edges — the second copy of 0 -> 1 finds node 1 finished, not on the path
        System.out.println("Test 6: " + A.leadsToDestination(3, new int[][]{{0, 1}, {0, 1}, {1, 2}}, 0, 2) + " (Expected: true)");

        // Test 7: a cycle among nodes the source has no path to — only what the source reaches counts
        System.out.println("Test 7: " + A.leadsToDestination(4, new int[][]{{0, 1}, {2, 3}, {3, 2}}, 0, 1) + " (Expected: true)");

        // Test 8: a ladder of 30 layers, 0 -> {1, 2} -> {3, 4} -> ... -> {59, 60} -> 61, where both
        // nodes of each layer have an edge to both nodes of the next: 62 nodes, 120 edges. Every
        // path is fine, but there are 2^30 of them. Re-exploring finished nodes walks each one
        // (28 layers: 805M calls, 3.4 s); skipping them keeps it linear
        List<int[]> ladder = new ArrayList<>(List.of(new int[]{0, 1}, new int[]{0, 2}));
        for (int layer = 1; layer < 30; layer++)
            for (int from = 2 * layer - 1; from <= 2 * layer; from++)
                for (int to = 2 * layer + 1; to <= 2 * layer + 2; to++)
                    ladder.add(new int[]{from, to});
        ladder.add(new int[]{59, 61});
        ladder.add(new int[]{60, 61});
        System.out.println("Test 8: " + A.leadsToDestination(62, ladder.toArray(new int[0][]), 0, 61) + " (Expected: true)");

        // Test 9: a cycle that can exit to the destination, 0 -> 1 -> 0 plus 1 -> 2 — every path
        // can still reach 2, but 0 -> 1 -> 0 -> 1 -> ... never has to, so the paths are infinite
        System.out.println("Test 9: " + A.leadsToDestination(3, new int[][]{{0, 1}, {1, 0}, {1, 2}}, 0, 2) + " (Expected: false)");

        // Test 10: the source is the destination but has an edge out — node 1 is a dead end that
        // isn't the destination
        System.out.println("Test 10: " + A.leadsToDestination(2, new int[][]{{0, 1}}, 0, 0) + " (Expected: false)");
    }
}
