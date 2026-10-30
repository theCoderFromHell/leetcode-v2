package medium;

import java.util.*;

// https://leetcode.com/problems/all-ancestors-of-a-node-in-a-directed-acyclic-graph/
public class AllAncestorsOfANodeInADirectedAcyclicGraph {
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        int[] parentsCount = new int[n];
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < n; i++)
            adjList.add(new ArrayList<>());
        List<TreeSet<Integer>> ancestors = new ArrayList<>();
        for (int i = 0; i < n; i++)
            ancestors.add(new TreeSet<>());
        for (int[] edge : edges) {
            int source = edge[0];
            int destination = edge[1];
            parentsCount[destination]++;
            adjList.get(source).add(destination);
            ancestors.get(destination).add(source);
        }
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (parentsCount[i] == 0)
                queue.add(i);
        }
        while (!queue.isEmpty()) {
            int node = queue.poll();
            if (parentsCount[node] != 0)
                continue;
            parentsCount[node]--;
            List<Integer> neighbours = adjList.get(node);
            for (int neighbour : neighbours) {
                ancestors.get(neighbour).addAll(ancestors.get(node));
                if (--parentsCount[neighbour] == 0)
                    queue.add(neighbour);
            }
        }
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n; i++)
            result.add(new ArrayList<>(ancestors.get(i)));
        return result;
    }

    // Test helper: every edge i -> j with i < j, so node 0 -> node n - 1 has 2^(n - 2) distinct paths
    private static int[][] completeDag(int n) {
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                edges.add(new int[]{i, j});
        return edges.toArray(new int[0][]);
    }

    /*
     * Revision Note - All Ancestors of a Node in a Directed Acyclic Graph (Medium)
     *
     * Pattern: Kahn's topological order (in-degree countdown) + push each node's ancestor set to its children.
     *
     * Key Insight: ancestors(child) = union over parents p of ({p} + ancestors(p)), so a node may only pass
     * its set on once it is FINAL, i.e. once every one of its parents has been processed. The in-degree
     * countdown is exactly "how many parents are still unfinished".
     *
     * Gotchas (all hit while solving):
     * - DFS with no visited check is correct but walks every PATH, and a DAG has exponentially many
     *   (complete DAG: 2^(n-2) paths 0 -> n-1). Test 7 never finishes that way.
     * - A visited check on a plain BFS from the roots makes it fast but wrong: a node gets expanded the
     *   first time it's polled, before a later parent has filled in its set (Test 8).
     * - Checking `count != 0` at POLL time while enqueueing once per parent: a node with k parents sits
     *   in the queue k times. Every copy polled after the count hits 0 re-processes it, decrementing its
     *   children twice, so a child's count overshoots to -1 and is skipped forever (Test 10). Fixed here
     *   by driving the node's own count to -1 once processed; the standard form is to enqueue a child
     *   only at the moment its count reaches 0, so each node enters the queue exactly once.
     * - HashSet iterates in bucket order, not ascending: {3, 17} comes out [17, 3] (Test 9). TreeSet,
     *   or sort at the end.
     *
     * Complexity: each node processed once, each edge does one addAll of up to n values into a TreeSet:
     * O(n * m * log n) time (about 2e7 at the limits), O(n^2) space for the sets.
     *
     * Alternative: flip the question. For each u = 0..n-1, DFS forward from u with a fresh visited[] and
     * append u to answer[v] for every v reached. u increases, so every list is born sorted: no sets, no
     * sort, no topological order. O(n * (n + m)) time.
     *
     * Template (Kahn):
     *   indeg[] from edges; queue = nodes with indeg 0
     *   while queue: u = poll()
     *       for v in adj[u]: anc[v].add(u); anc[v].addAll(anc[u])
     *                        if (--indeg[v] == 0) queue.add(v)
     */

    public static void main(String[] args) {
        AllAncestorsOfANodeInADirectedAcyclicGraph A = new AllAncestorsOfANodeInADirectedAcyclicGraph();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + A.getAncestors(8, new int[][]{{0, 3}, {0, 4}, {1, 3}, {2, 4}, {2, 7}, {3, 5}, {3, 6}, {3, 7}, {4, 6}}) + " (Expected: [[], [], [], [0, 1], [0, 2], [0, 1, 3], [0, 1, 2, 3, 4], [0, 1, 2, 3]])");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + A.getAncestors(5, new int[][]{{0, 1}, {0, 2}, {0, 3}, {0, 4}, {1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {3, 4}}) + " (Expected: [[], [0], [0, 1], [0, 1, 2], [0, 1, 2, 3]])");

        // Test 3: n == 1, no edges
        System.out.println("Test 3: " + A.getAncestors(1, new int[][]{}) + " (Expected: [[]])");

        // Test 4: diamond (0 -> 1, 0 -> 2, 1 -> 3, 2 -> 3)
        System.out.println("Test 4: " + A.getAncestors(4, new int[][]{{0, 1}, {0, 2}, {1, 3}, {2, 3}}) + " (Expected: [[], [0], [0], [0, 1, 2]])");

        // Test 5: chain with every edge from a larger label to a smaller one (3 -> 2 -> 1 -> 0)
        System.out.println("Test 5: " + A.getAncestors(4, new int[][]{{3, 2}, {2, 1}, {1, 0}}) + " (Expected: [[1, 2, 3], [2, 3], [3], []])");

        // Test 6: two chains (5 -> 4 -> 3 and 0 -> 1) meeting at node 2; the second chain arrives after
        // node 2 has already been reached once
        System.out.println("Test 6: " + A.getAncestors(6, new int[][]{{5, 4}, {4, 3}, {0, 1}, {1, 2}, {3, 2}}) + " (Expected: [[], [0], [0, 1, 3, 4, 5], [4, 5], [5], []])");

        // Test 7: complete DAG on 63 nodes (1953 edges): 2^61 paths from node 0 to node 62, so walking
        // every path never finishes; answer[62] is every other node
        System.out.println("Test 7: " + A.getAncestors(63, completeDag(63)).get(62).size() + " (Expected: 62)");

        // Test 8: node 4 has parents 0 and 3, and 3's own parent (1) finishes after 4 is first reached;
        // expanding 4 on first sight hands node 2 an incomplete set (missing 1)
        System.out.println("Test 8: " + A.getAncestors(5, new int[][]{{1, 3}, {0, 4}, {3, 4}, {4, 2}}) + " (Expected: [[], [], [0, 1, 3, 4], [1], [0, 1, 3]])");

        // Test 9: ancestors {3, 17}: a HashSet iterates them as [17, 3] (17 lands in bucket 1 of 16)
        System.out.println("Test 9: " + A.getAncestors(18, new int[][]{{17, 0}, {3, 0}}).get(0) + " (Expected: [3, 17])");

        // Test 10: node 3 has two parents (2, 5) AND a child (1 -> 4); processing 3 once per parent
        // decrements node 1 twice, its count goes to -1, and node 4 never receives 1's set
        System.out.println("Test 10: " + A.getAncestors(6, new int[][]{{1, 4}, {5, 3}, {2, 3}, {3, 1}}) + " (Expected: [[], [2, 3, 5], [], [2, 5], [1, 2, 3, 5], []])");
    }
}
