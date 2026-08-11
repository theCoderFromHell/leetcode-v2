package medium;

import java.util.*;

// https://leetcode.com/problems/reachable-nodes-with-restrictions/
public class ReachableNodesWithRestrictions {
    public int reachableNodes(int n, int[][] edges, int[] restricted) {
        int size = edges.length;
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        for (int i = 0; i < n; i++)
            adjList.put(i, new ArrayList<>());
        for (int i = 0; i < size; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            adjList.get(a).add(b);
            adjList.get(b).add(a);
        }
        HashSet<Integer> avoid = new HashSet<>();
        boolean[] visited = new boolean[n];
        int length = restricted.length;
        for (int i = 0; i < length; i++)
            avoid.add(restricted[i]);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        visited[0] = true;
        int result = 1;
        while (!queue.isEmpty()) {
            int top = queue.poll();
            List<Integer> children = adjList.get(top);
            for (int child : children) {
                if (!avoid.contains(child) && !visited[child]) {
                    visited[child] = true;
                    result++;
                    queue.add(child);
                }
            }
        }
        return result;
    }

    /*
     * Revision Note — Reachable Nodes With Restrictions (Medium)
     * Pattern: BFS/DFS on undirected tree with a blocked-node set
     * Key Insight: Build adjacency list, mark restricted nodes in an avoid set, then BFS
     *              from node 0 — skip any neighbor that is restricted or already visited.
     * Gotchas:
     *   - Undirected tree means every edge is stored twice; without a visited array,
     *     the parent is re-enqueued immediately causing an infinite loop.
     *   - boolean[] visited is more efficient than HashSet<Integer> for dense index ranges.
     *   - For trees specifically, parent tracking (nb != parent) can replace the visited
     *     array entirely, but visited array is simpler and works for general graphs too.
     *   - Start result=1 to count node 0 itself (guaranteed not restricted).
     */
    public static void main(String[] args) {
        ReachableNodesWithRestrictions R = new ReachableNodesWithRestrictions();

        // Test 1: LeetCode example 1 — nodes 4 and 5 blocked, can reach {0,1,2,3}
        System.out.println("Test 1: " + R.reachableNodes(7, new int[][]{{0,1},{1,2},{3,1},{4,0},{0,5},{5,6}}, new int[]{4,5}) + " (Expected: 4)");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + R.reachableNodes(7, new int[][]{{0,1},{0,2},{0,5},{0,4},{3,2},{6,5}}, new int[]{4,2,1}) + " (Expected: 3)");

        // Test 3: no restrictions — entire tree reachable
        System.out.println("Test 3: " + R.reachableNodes(4, new int[][]{{0,1},{1,2},{2,3}}, new int[]{}) + " (Expected: 4)");

        // Test 4: all children of root restricted — only node 0 reachable
        System.out.println("Test 4: " + R.reachableNodes(4, new int[][]{{0,1},{0,2},{0,3}}, new int[]{1,2,3}) + " (Expected: 1)");

        // Test 5: restriction mid-chain blocks rest of path
        System.out.println("Test 5: " + R.reachableNodes(5, new int[][]{{0,1},{1,2},{2,3},{3,4}}, new int[]{2}) + " (Expected: 2)");
    }
}
