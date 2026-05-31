package medium;

// https://leetcode.com/problems/make-costs-of-paths-equal-in-a-binary-tree/
public class MakeCostsOfPathsEqualInABinaryTree {
    int result;
    public int minIncrements(int n, int[] cost) {
        result = 0;
        solve(1, n, cost);
        return result;
    }

    private int solve(int root, int n, int[] cost) {
        if (root > n)
            return 0;
        if (isLeaf(root, n))
            return cost[root - 1];
        int left = solve(2 * root, n, cost);
        int right = solve(2 * root + 1, n, cost);
        int diff = Math.abs(left - right);
        result += diff;
        return cost[root - 1] + Math.max(left, right);
    }

    private boolean isLeaf(int node, int n) {
        return (node > n/2 && node <= n);
    }

    /*
     * Revision Note — Make Costs of Paths Equal in a Binary Tree (Medium)
     * Pattern: Post-order DFS on a perfect binary tree (1-based level-order indexing)
     * Key Insight: Since we can only INCREMENT, the cheapest target for every root-to-leaf
     *              path is the current maximum path sum. At every internal node, the diff
     *              between its two children's heaviest subtree paths must be paid — and the
     *              cheapest place to pay it is once, at the root of the lighter subtree,
     *              which lifts every path beneath it by that amount in one shot.
     * Gotchas:
     *   - Increments must be applied on the lighter subtree, never at the common ancestor
     *     (a bump at the ancestor raises both sides equally, leaving the gap unchanged)
     *   - Children of node i live at 2i and 2i+1 (1-based); array index is i-1
     *   - In a perfect tree of size n, leaves are exactly indices (n/2, n]
     *   - Bottom-up iterative variant: for i = n/2..1, accumulate |cost[2i-1]-cost[2i]| and
     *     push max into cost[i-1] — O(n) time, O(1) extra space
     */
    public static void main(String[] args) {
        MakeCostsOfPathsEqualInABinaryTree M = new MakeCostsOfPathsEqualInABinaryTree();

        // Example 1: n=7, perfect tree of depth 3
        System.out.println("Test 1: " + M.minIncrements(7, new int[]{1, 5, 2, 2, 3, 3, 1}) + " (Expected: 6)");

        // Example 2: n=3, already balanced
        System.out.println("Test 2: " + M.minIncrements(3, new int[]{5, 3, 3}) + " (Expected: 0)");

        // All costs equal — no increments needed
        System.out.println("Test 3: " + M.minIncrements(7, new int[]{1, 1, 1, 1, 1, 1, 1}) + " (Expected: 0)");

        // Only root + two leaves, big imbalance
        System.out.println("Test 4: " + M.minIncrements(3, new int[]{1, 10, 2}) + " (Expected: 8)");

        // n=7, lighter side concentrated in a single leaf
        // paths: 1+2+4=7, 1+2+4=7, 1+2+4=7, 1+2+100=103
        // Fix: +96 at node 6 (right subtree gap), then +96 at node 2 (whole left side gap) = 192
        System.out.println("Test 5: " + M.minIncrements(7, new int[]{1, 2, 2, 4, 4, 4, 100}) + " (Expected: 192)");

        // Larger perfect tree, n=15 (depth 4), all same cost → 0
        int[] flat15 = new int[15];
        java.util.Arrays.fill(flat15, 5);
        System.out.println("Test 6: " + M.minIncrements(15, flat15) + " (Expected: 0)");
    }
}

