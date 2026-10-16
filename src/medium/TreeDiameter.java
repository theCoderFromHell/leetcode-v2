package medium;

import java.util.*;

// https://leetcode.com/problems/tree-diameter/
public class TreeDiameter {
    int result;
    public int treeDiameter(int[][] edges) {
        result = 0;
        HashMap<Integer, List<Integer>> adjList = new HashMap();
        int size = edges.length;
        for (int i = 0; i < size; i++) {
            int src = edges[i][0];
            int dest = edges[i][1];
            adjList.computeIfAbsent(src, k -> new ArrayList<>()).add(dest);
            adjList.computeIfAbsent(dest, k -> new ArrayList<>()).add(src);
        }
        dfs(0, adjList, -1);
        return result;
    }

    private int dfs(int node, HashMap<Integer, List<Integer>> adjList, int parent) {
        List<Integer> children = adjList.getOrDefault(node, List.of());
        List<Integer> paths = new ArrayList<>();
        for (int child : children) {
            if (child != parent)
                paths.add(1 + dfs(child, adjList, node));
        }
        paths.sort(Comparator.reverseOrder());
        if (paths.size() == 1) {
            result = Math.max(result, paths.getFirst());
            return paths.getFirst();
        } else if (paths.size() >= 2) {
            result = Math.max(result, paths.get(0) + paths.get(1));
            return paths.get(0);
        }
        return 0;
    }

    /*
     * Revision Note — Tree Diameter (Medium)
     * Pattern: DFS tree DP on an undirected adjacency list — return the longest branch
     *          going down, and update a global max with the best path that bends at each node.
     * Key Insight: the longest path through a node uses only its TWO longest branches,
     *              however many children it has; the parent only ever receives the longest one.
     * Gotchas:
     *   - `child != parent` with `Integer parent = null` unboxes and throws NPE at the root.
     *     Use `int parent` with -1 as "no parent".
     *   - Branching on paths.size() == 1 / == 2 silently drops nodes with 3+ children.
     *     Sort descending (or track the top two) so any number of children works.
     *   - Count the connecting edge exactly ONCE. Add it when collecting (1 + dfs(child)),
     *     then the formulas are plain sums. This went wrong in both directions here: first
     *     one short (h1 + 1 + h2), then double-counted after moving the +1 only halfway.
     *   - n = 1 means no edges, so node 0 has no map entry: getOrDefault(node, List.of()).
     *   - Recursion depth: a 10^4-node path rooted at one end is 10^4 frames deep and
     *     overflowed the default local stack. Iterative fix: BFS order from 0, then walk it
     *     backwards, pushing (best1[u] + 1) into the parent's top two.
     *   - Alternatives: BFS twice (the node farthest from ANY node is a diameter endpoint),
     *     or peel leaves layer by layer: diameter = 2 * layers + (two nodes left ? 1 : 0).
     * Complexity: O(n log n) time worst case from the per-node sort (a star sorts n - 1
     *             entries); O(n) with top-two tracking. O(n) space incl. recursion.
     * Template:
     *   dfs(node, parent):
     *       best1 = best2 = 0
     *       for child in adj[node] where child != parent:
     *           len = 1 + dfs(child, node)          // +1 for the edge to the child, once
     *           if len > best1: best2 = best1; best1 = len  elif len > best2: best2 = len
     *       result = max(result, best1 + best2)
     *       return best1
     */
    public static void main(String[] args) {
        TreeDiameter T = new TreeDiameter();

        // Test 1: LeetCode example 1 — two leaf branches meet at the root. Caught both +1 bugs:
        // one short (0 + 1 + 0 = 1), then double-counted (1 + 1 + 1 = 3)
        System.out.println("Test 1: " + T.treeDiameter(new int[][]{{0,1},{0,2}}) + " (Expected: 2)");

        // Test 2: LeetCode example 2 — diameter 3-2-1-4-5 bends at node 1, not the root
        System.out.println("Test 2: " + T.treeDiameter(new int[][]{{0,1},{1,2},{2,3},{1,4},{4,5}}) + " (Expected: 4)");

        // Test 3: n = 1, no edges — node 0 has no map entry, so adjList.get(0) is null
        System.out.println("Test 3: " + T.treeDiameter(new int[][]{}) + " (Expected: 0)");

        // Test 4: single edge — the one-branch case alone; the double +1 returned 2 here
        System.out.println("Test 4: " + T.treeDiameter(new int[][]{{0,1}}) + " (Expected: 1)");

        // Test 5: star — root has four children; branching on size() == 2 returned 0
        System.out.println("Test 5: " + T.treeDiameter(new int[][]{{0,1},{0,2},{0,3},{0,4}}) + " (Expected: 2)");

        // Test 6: 0 sits mid-path 2-1-0-3-4, so the diameter passes through the root
        System.out.println("Test 6: " + T.treeDiameter(new int[][]{{0,1},{1,2},{0,3},{3,4}}) + " (Expected: 4)");

        // Test 7: diameter 5-2-1-3-6 avoids the root, and node 1 has three children
        //       0
        //       |
        //       1
        //     / | \
        //    2  3  4
        //    |  |
        //    5  6
        System.out.println("Test 7: " + T.treeDiameter(new int[][]{{0,1},{1,2},{1,3},{1,4},{2,5},{3,6}}) + " (Expected: 4)");

        // Test 8: the two longest branches come LAST in adjacency order — root's branches are
        // [1, 2, 2], so taking the first two without sorting gives 3, not 5-2-0-3-4 = 4
        System.out.println("Test 8: " + T.treeDiameter(new int[][]{{0,1},{0,2},{0,3},{3,4},{2,5}}) + " (Expected: 4)");
    }
}
