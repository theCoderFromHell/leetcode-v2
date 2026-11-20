package medium;

import java.util.*;

// https://leetcode.com/problems/remove-methods-from-project/
public class RemoveMethodsFromProject {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        int size = invocations.length;
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        for (int i = 0; i < size; i++) {
            int source = invocations[i][0];
            int destination = invocations[i][1];
            adjList.computeIfAbsent(source, o -> new ArrayList<>()).add(destination);
        }
        boolean[] suspicious = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(k);
        suspicious[k] = true;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            List<Integer> neighbours = adjList.getOrDefault(current, List.of());
            for (int neighbour : neighbours)
                if (!suspicious[neighbour]) {
                    suspicious[neighbour] = true;
                    queue.add(neighbour);
                }
        }
        boolean removeGroup = true;
        for (int i = 0; i < size; i++) {
            int source = invocations[i][0];
            int destination = invocations[i][1];
            if (suspicious[destination] && !suspicious[source]) {
                removeGroup = false;
                break;
            }
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (removeGroup && suspicious[i])
                continue;
            result.add(i);
        }
        return result;
    }

    /*
     * Revision Note — Remove Methods From Project (Medium)
     *
     * Pattern: mark, then check every edge once. One BFS from k over the call graph marks the
     * suspicious set (everything k reaches). A single pass over the invocations then decides
     * whether that set can be removed.
     *
     * Key insight: "no method outside the group invokes one inside it" is a property of EDGES,
     * not of methods. A call [a, b] breaks it exactly when b is suspicious and a isn't. So there
     * is no need to find every caller of every suspicious method (a reverse graph): one O(m)
     * scan over the calls already looks at each one.
     *
     * Gotchas (from this session):
     *  - The first draft's DFS added a method to the set but recursed into its callees without
     *    checking whether they were already in it. Any cycle (Tests 3 and 7) then recurses
     *    forever. Mark on enqueue and skip marked methods.
     *  - Mark on ENQUEUE, not on poll. The second version set suspicious[top] when it was polled.
     *    A method can then sit in the queue once per caller before its first poll, and every copy
     *    re-queues its callees, so the copies double at each layer of a "ladder" (two methods
     *    per layer, each calling both of the next): 2^L queue entries. 26 layers (53 methods) took
     *    1.4 s; 28 layers (57 methods, 110 calls) ran out of a 2 GB heap. LeetCode's tests have no
     *    such graph, so it was still accepted (Test 9).
     *  - Recursion depth. A chain of 10^5 calls (Test 8) overflows a recursive DFS on the
     *    default stack, and still does at -Xss4m; it passes at -Xss8m. The iterative BFS here
     *    has no depth at all.
     *  - All or nothing: if even one call comes in from outside, NOTHING is removed and all n
     *    methods come back (Tests 1 and 6), not "everything except the blocked ones".
     *  - Calls from suspicious methods back into the set, including back into k, are fine
     *    (Test 7). Only calls that cross from outside to inside count.
     *  - The output is all n methods, not the methods that appear in invocations. A method
     *    with no calls at all is still a method (Test 4).
     *
     * Complexity: O(n + m) time (BFS + one scan of the m calls + one pass over the n methods),
     * O(n + m) space for the adjacency map, queue and marks.
     *
     * Template:
     *   build adj from invocations
     *   BFS from k: mark everything reached as suspicious
     *   for each call [a, b]: if suspicious[b] && !suspicious[a] → return all 0..n-1
     *   return every method that isn't suspicious
     *
     * Alternative: flood from the outside, as in 130 Surrounded Regions. Run one multi-source
     * BFS from every non-suspicious method. If it reaches a suspicious one, some call crosses
     * from outside to inside, so nothing can be removed. Same O(n + m), but it is a second
     * search where a scan of the calls is enough.
     */
    public static void main(String[] args) {
        RemoveMethodsFromProject R = new RemoveMethodsFromProject();

        // Test 1: LeetCode example 1 — k's callee 2 is also called by 3, and k itself by 0, so
        // nothing can be removed
        System.out.println("Test 1: " + R.remainingMethods(4, 1, new int[][]{{1, 2}, {0, 1}, {3, 2}}).stream().sorted().toList() + " (Expected: [0, 1, 2, 3])");

        // Test 2: LeetCode example 2 — {0, 1, 2} is only ever called from inside itself; 3 -> 4
        // is a separate part of the graph and stays
        System.out.println("Test 2: " + R.remainingMethods(5, 0, new int[][]{{1, 2}, {0, 2}, {0, 1}, {3, 4}}).stream().sorted().toList() + " (Expected: [3, 4])");

        // Test 3: LeetCode example 3 — a cycle through every method, so all of them are
        // suspicious and removable. A DFS with no visited check never terminates here
        System.out.println("Test 3: " + R.remainingMethods(3, 2, new int[][]{{1, 2}, {0, 1}, {2, 0}}).stream().sorted().toList() + " (Expected: [])");

        // Test 4: no invocations at all — k alone is suspicious and nobody calls it; methods 0
        // and 2 still count even though they appear in no call
        System.out.println("Test 4: " + R.remainingMethods(3, 1, new int[][]{}).stream().sorted().toList() + " (Expected: [0, 2])");

        // Test 5: a single method, which is k — removing it leaves nothing
        System.out.println("Test 5: " + R.remainingMethods(1, 0, new int[][]{}).stream().sorted().toList() + " (Expected: [])");

        // Test 6: 4 is not suspicious but calls 2, the END of k's chain rather than k itself.
        // One crossing call anywhere blocks the whole removal, so all 5 come back
        System.out.println("Test 6: " + R.remainingMethods(5, 0, new int[][]{{0, 1}, {1, 2}, {3, 4}, {4, 2}}).stream().sorted().toList() + " (Expected: [0, 1, 2, 3, 4])");

        // Test 7: a cycle through k (2 calls back into 1). Calls from inside the set don't block
        // removal; 3 -> 0 doesn't touch the set at all
        System.out.println("Test 7: " + R.remainingMethods(4, 1, new int[][]{{1, 2}, {2, 1}, {3, 0}}).stream().sorted().toList() + " (Expected: [0, 3])");

        // Test 8: a chain of 100,000 methods, 0 -> 1 -> ... -> 99999, with k at the head.
        // A recursive DFS goes 10^5 deep: StackOverflowError on the default stack, and at
        // -Xss4m. The iterative BFS has no such limit
        int[][] chain = new int[99999][];
        for (int i = 0; i < 99999; i++)
            chain[i] = new int[]{i, i + 1};
        System.out.println("Test 8: " + R.remainingMethods(100000, 0, chain).stream().sorted().toList() + " (Expected: [])");

        // Test 9: a ladder of 30 layers, 0 -> {1, 2} -> {3, 4} -> ... -> {59, 60}, where both methods
        // of each layer call both methods of the next: 61 methods, 118 calls. Marking on poll instead
        // of on enqueue doubles the queue at every layer (2^30 entries) and runs out of memory
        List<int[]> ladder = new ArrayList<>(List.of(new int[]{0, 1}, new int[]{0, 2}));
        for (int layer = 1; layer < 30; layer++)
            for (int from = 2 * layer - 1; from <= 2 * layer; from++)
                for (int to = 2 * layer + 1; to <= 2 * layer + 2; to++)
                    ladder.add(new int[]{from, to});
        System.out.println("Test 9: " + R.remainingMethods(61, 0, ladder.toArray(new int[0][])).stream().sorted().toList() + " (Expected: [])");
    }
}
