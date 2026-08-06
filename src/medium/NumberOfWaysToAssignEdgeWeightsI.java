package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// https://leetcode.com/problems/number-of-ways-to-assign-edge-weights-i/
public class NumberOfWaysToAssignEdgeWeightsI {
    public int assignEdgeWeights(int[][] edges) {
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        int size = edges.length;
        int n = 0;
        for (int i = 0; i < size; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            n = Math.max(n, Math.max(u, v));
            adjList.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
            adjList.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
        }
        boolean[] visited = new boolean[n+1];
        int depth = dfs(1, adjList, visited);
        return power(2, depth-2, 1000000007);
    }

    private int dfs(int node, HashMap<Integer, List<Integer>> adjList, boolean[] visited) {
        visited[node] = true;
        List<Integer> neighbours = adjList.get(node);
        int maxDepth = 0;
        for (int neighbour : neighbours) {
            if (!visited[neighbour])
                maxDepth = Math.max(maxDepth, dfs(neighbour, adjList, visited));
        }
        return 1 + maxDepth;
    }

    private int power(int base, int exponent, int MOD) {
        base = base % MOD;
        int result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1)
                result = (int)(1L * result * base % MOD);
            base = (int)(1L * base * base % MOD);
            exponent = exponent/2;
        }
        return result;
    }

    /*
     * Revision Note — Number of Ways to Assign Edge Weights I (Medium)
     * Pattern: DFS for max depth + modular binary exponentiation
     * Key Insight: Among all 2^d assignments of d edges, exactly half produce an odd
     *              sum (flipping any single edge toggles parity) → answer is 2^(d-1).
     *              DFS returns node count so edge count = nodeCount-1, exponent = nodeCount-2.
     * Gotchas:
     *   - Use computeIfAbsent for adjacency list — getOrDefault doesn't write back to map.
     *   - DFS needs !visited[neighbour] guard — undirected tree recurses back to parent otherwise.
     *   - DFS returns node count (1 + maxDepth); edges = nodeCount-1, so exponent = nodeCount-2.
     *   - (int)(1L * a * b) % MOD is WRONG — cast truncates before mod, silently corrupting
     *     base to 0 after 5 squarings. Correct: (int)(1L * a * b % MOD).
     *   - Recursive DFS risks StackOverflow for linear chains near n=10^5; BFS is safer.
     */
    public static void main(String[] args) {
        NumberOfWaysToAssignEdgeWeightsI N = new NumberOfWaysToAssignEdgeWeightsI();

        // Test 1: single edge — 1 edge path, 2^0 = 1
        System.out.println("Test 1: " + N.assignEdgeWeights(new int[][]{{1, 2}}) + " (Expected: 1)");

        // Test 2: LeetCode example 2 — max depth 2 edges, 2^1 = 2
        System.out.println("Test 2: " + N.assignEdgeWeights(new int[][]{{1,2},{1,3},{3,4},{3,5}}) + " (Expected: 2)");

        // Test 3: star graph — all leaves at depth 1 edge, 2^0 = 1
        System.out.println("Test 3: " + N.assignEdgeWeights(new int[][]{{1,2},{1,3},{1,4}}) + " (Expected: 1)");

        // Test 4: linear chain depth 3 edges (4 nodes), 2^2 = 4
        System.out.println("Test 4: " + N.assignEdgeWeights(new int[][]{{1,2},{2,3},{3,4}}) + " (Expected: 4)");

        // Test 5: linear chain depth 4 edges (5 nodes), 2^3 = 8
        System.out.println("Test 5: " + N.assignEdgeWeights(new int[][]{{1,2},{2,3},{3,4},{4,5}}) + " (Expected: 8)");

        // Test 6: mixed tree, deepest path has 3 edges (nodes 1→3→4→5), 2^2 = 4
        System.out.println("Test 6: " + N.assignEdgeWeights(new int[][]{{1,2},{1,3},{3,4},{4,5}}) + " (Expected: 4)");
    }
}
