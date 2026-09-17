package medium;

import common.TreeNode;

// https://leetcode.com/problems/largest-bst-subtree/
public class LargestBSTSubtree {
    public int largestBSTSubtree(TreeNode root) {
        return find(root)[2];
    }

    private int[] find(TreeNode root) {
        if (root == null)
            return new int[]{
                    Integer.MAX_VALUE,
                    Integer.MIN_VALUE,
                    0 };
        int[] left = find(root.left);
        int[] right = find(root.right);
        if (left[1] < root.val && root.val < right[0]) {
            return new int[]{
                    Math.min(left[0], root.val),
                    Math.max(root.val, right[1]),
                    left[2] + right[2] + 1 };
        }
        return new int[]{
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                Math.max(left[2], right[2]) };

    }

    /*
     * Revision Note — Largest BST Subtree (Medium)
     * Pattern: Bottom-up post-order returning {min, max, bestSize} per subtree
     * Key Insight: A parent cannot judge itself without knowing its children's VALUE RANGE, not
     *              just whether they are BSTs. Node 15 with a valid single-node child 7 still
     *              fails, because 7 < 15 sits on the right. So each call reports back the min and
     *              max of its subtree; the parent is a BST iff left.max < val < right.min.
     *              Post-order (children before parent) makes this O(n) — one visit per node.
     * Gotchas:
     *   - Slot [2] means "largest BST ANYWHERE in this subtree", not "size of the BST rooted
     *     here". That distinction is the whole problem. Returning 0 from the invalid branch
     *     throws away every valid result underneath: for [10,5,15,1,8,null,7], find(5) correctly
     *     computes 3, then find(10) discards it and the answer comes back 0. The invalid branch
     *     must return Math.max(left[2], right[2]).
     *   - Two DIFFERENT sentinels, doing two different jobs:
     *       null    -> {MAX_VALUE, MIN_VALUE, 0}  makes both comparisons vacuously true, so
     *                                             leaves need no special case.
     *       invalid -> {MIN_VALUE, MAX_VALUE, max} poisons every ancestor (left[1] = MAX is never
     *                                             < root.val) WHILE still carrying the running
     *                                             maximum. Poisoning the bounds and preserving
     *                                             the count are independent concerns.
     *   - Strict < on both sides, so duplicates are rejected: [5,5,5] answers 1, not 3.
     *   - A subtree must include ALL descendants — you cannot keep part of what hangs below.
     *   - Recursion depth is O(h); verified safe at 10^4 nodes left-skewed on the default stack.
     *   - Tests that pass by luck: if the whole tree happens to be a BST, root's own size IS the
     *     answer, so the missing max-tracking stays invisible. Always include a case where a
     *     valid BST sits UNDER an invalid ancestor.
     * Alternative: keep a `best` field and let [2] always mean "size rooted here". Same O(n)/O(h);
     *              trades re-entrancy for each slot having a single meaning. A record
     *              BstInfo(min, max, size) would beat both — left[1] and right[0] carry no meaning
     *              at the call site, and that opacity is what hid the bug.
     * Baseline worth naming in an interview: run isValidBST at every node, take the max size.
     *              O(n log n) balanced, O(n^2) skewed, since each node is re-validated once per
     *              ancestor. Mention it, then improve to this.
     */

    // level-order builder; null marks an absent child
    private static TreeNode build(Integer... values) {
        if (values.length == 0 || values[0] == null)
            return null;
        TreeNode root = new TreeNode(values[0]);
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.add(root);
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            if (i < values.length) {
                if (values[i] != null) {
                    node.left = new TreeNode(values[i]);
                    queue.add(node.left);
                }
                i++;
            }
            if (i < values.length) {
                if (values[i] != null) {
                    node.right = new TreeNode(values[i]);
                    queue.add(node.right);
                }
                i++;
            }
        }
        return root;
    }

    public static void main(String[] args) {
        LargestBSTSubtree L = new LargestBSTSubtree();

        // Test 1: LeetCode example — 15 -> 7 is invalid, so the left subtree 5 -> [1, 8] wins
        System.out.println("Test 1: " + L.largestBSTSubtree(build(10, 5, 15, 1, 8, null, 7)) + " (Expected: 3)");

        // Test 2: the whole tree is already a BST
        System.out.println("Test 2: " + L.largestBSTSubtree(build(2, 1, 3)) + " (Expected: 3)");

        // Test 3: single node is trivially a BST
        System.out.println("Test 3: " + L.largestBSTSubtree(build(7)) + " (Expected: 1)");

        // Test 4: empty tree
        System.out.println("Test 4: " + L.largestBSTSubtree(null) + " (Expected: 0)");

        // Test 5: root breaks it — the answer lives entirely in the LEFT subtree
        System.out.println("Test 5: " + L.largestBSTSubtree(build(1, 10, null, 5, 15)) + " (Expected: 3)");

        // Test 6: root breaks it — the answer lives entirely in the RIGHT subtree
        System.out.println("Test 6: " + L.largestBSTSubtree(build(100, null, 10, 5, 15)) + " (Expected: 3)");

        // Test 7: duplicates everywhere — strict comparison means only a single node qualifies
        System.out.println("Test 7: " + L.largestBSTSubtree(build(5, 5, 5)) + " (Expected: 1)");

        // Test 8: left-skewed but a perfectly valid BST end to end
        System.out.println("Test 8: " + L.largestBSTSubtree(build(5, 4, null, 3, null, 2, null, 1)) + " (Expected: 5)");

        // Test 9: a large valid BST buried under a bad root
        System.out.println("Test 9: " + L.largestBSTSubtree(build(50, 20, 60, 10, 30, null, null, 5, 15, 25, 35)) + " (Expected: 9)");

        // Test 10: two competing valid BSTs — the larger one must win
        System.out.println("Test 10: " + L.largestBSTSubtree(build(0, 10, 20, 5, 15, 18, 25)) + " (Expected: 3)");
    }
}
