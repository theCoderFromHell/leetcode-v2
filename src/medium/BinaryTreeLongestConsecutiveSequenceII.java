package medium;

import common.TreeNode;

import java.util.ArrayDeque;
import java.util.Queue;

// https://leetcode.com/problems/binary-tree-longest-consecutive-sequence-ii/
public class BinaryTreeLongestConsecutiveSequenceII {
    int result;
    public int longestConsecutive(TreeNode root) {
        result = 0;
        longestFrom(root, null);
        return result;
    }

    // returns {decreasingDownward, increasingDownward}
    private int[] longestFrom(TreeNode node, Integer parent) {
        if (node == null)
            return new int[]{0,0};
        int[] left = longestFrom(node.left, node.val);
        int[] right = longestFrom(node.right, node.val);
        result = Math.max(result, Math.max(left[0], right[0]) + 1);
        result = Math.max(result, Math.max(left[1], right[1]) + 1);
        result = Math.max(result, left[0] + 1 + right[1]);
        result = Math.max(result, left[1] + 1 + right[0]);
        if (parent != null) {
            if (node.val == parent - 1)
                return new int[]{1 + Math.max(left[0], right[0]), 0};
            else if (node.val == parent + 1)
                return new int[]{0, 1 + Math.max(left[1], right[1])};
        }
        return new int[]{0,0};
    }

    /*
     * Revision Note - Binary Tree Longest Consecutive Sequence II (Medium)
     *
     * Pattern: BOTTOM-UP tree DP returning a PAIR per node, with the bend formed locally
     *
     * Key Insight: 298 could be top-down because a path only ran parent->child, so a node
     * already knew its whole path on the way down. Here the path may BEND at one node, and
     * forming that bend needs both children at once - which only a bottom-up return can give.
     * Each node reports two numbers upward:
     *     slot 0 = longest run starting here and DECREASING as it goes down
     *     slot 1 = longest run starting here and INCREASING as it goes down
     * A bend glues one of each from opposite children: left[0] + 1 + right[1] walks UP the
     * left subtree (increasing toward the root) then DOWN the right (still increasing), so the
     * whole path is monotonic. Plus the mirror, left[1] + 1 + right[0].
     *
     * Gotchas:
     * - The root has NO parent. Dereferencing it NPEs on the very first call, before any logic
     *   runs. Identical trap to 298 (there it was a boxed Integer, here a TreeNode) - whenever a
     *   helper takes a parent parameter, settle what the root passes in FIRST
     * - The BEND IS NEVER RETURNED UPWARD. It is folded into the global `result` and nothing
     *   else. Propagating it would let a parent extend a path that already turned a corner, which
     *   is not a simple path. Test 12 ([1,null,2,3,1]) exists only to catch this: the correct 3
     *   becomes 4 if the bend leaks
     * - Equal values are not consecutive - [5,5,5] is 1, not 3
     * - A node that does not continue its parent's direction returns {0,0}, not its own run
     *   length. The "do I connect to my parent" test living in the CHILD is what forces the
     *   parent parameter to exist at all
     * - Answer floor is 1 (a single node is a path), which falls out of max(0,0) + 1 - no
     *   special case needed
     * - Values may be negative and span zero, so never use 0 as a sentinel for "no value"
     *
     * Complexity: O(n) time, one visit per node. O(h) space for the recursion stack, O(n) in the
     * worst case for a degenerate tree - well inside n <= 3e4.
     *
     * Template:
     *   longestFrom(node, parent) -> {decreasingDown, increasingDown}
     *     if node == null: return {0, 0}
     *     L = longestFrom(node.left, node.val);  R = longestFrom(node.right, node.val)
     *     result = max(result, max(L[0], R[0]) + 1)        // straight, decreasing
     *     result = max(result, max(L[1], R[1]) + 1)        // straight, increasing
     *     result = max(result, L[0] + 1 + R[1])            // bend
     *     result = max(result, L[1] + 1 + R[0])            // bend, mirrored
     *     if parent == null:            return {0, 0}      // the guard that was missing
     *     if node.val == parent.val - 1: return {1 + max(L[0], R[0]), 0}
     *     if node.val == parent.val + 1: return {0, 1 + max(L[1], R[1])}
     *     return {0, 0}
     *
     * Alternative shape worth knowing: move the +-1 comparison into the PARENT instead of the
     * child. Then the helper needs no parent parameter at all - each node returns its own two
     * run lengths unconditionally, and the caller checks `child.val == node.val +- 1` before
     * adding 1. Same O(n), and the null-parent trap disappears by construction.
     */

    // ---------- helpers used only by the tests ----------

    // level-order build, null for a missing child (LeetCode's own array format)
    private static TreeNode build(Integer... level) {
        if (level.length == 0 || level[0] == null) return null;
        TreeNode root = new TreeNode(level[0]);
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        int i = 1;
        while (!q.isEmpty() && i < level.length) {
            TreeNode n = q.poll();
            if (i < level.length) {
                if (level[i] != null) { n.left = new TreeNode(level[i]); q.add(n.left); }
                i++;
            }
            if (i < level.length) {
                if (level[i] != null) { n.right = new TreeNode(level[i]); q.add(n.right); }
                i++;
            }
        }
        return root;
    }

    public static void main(String[] args) {
        BinaryTreeLongestConsecutiveSequenceII B = new BinaryTreeLongestConsecutiveSequenceII();

        System.out.println("Test 1: " + B.longestConsecutive(build(1, 2, 3)) + " (Expected: 2)");
        System.out.println("Test 2: " + B.longestConsecutive(build(2, 1, 3)) + " (Expected: 3)");
        System.out.println("Test 3: " + B.longestConsecutive(build(1)) + " (Expected: 1)");               // single node
        System.out.println("Test 4: " + B.longestConsecutive(build(5, 5, 5)) + " (Expected: 1)");          // all equal, never consecutive
        System.out.println("Test 5: " + B.longestConsecutive(build(1, 2, null, 3, null, 4)) + " (Expected: 4)"); // pure downward chain, no bend
        System.out.println("Test 6: " + B.longestConsecutive(build(4, 3, 5, 2, null, null, 6)) + " (Expected: 5)"); // 2-3-4-5-6 bends at root
        System.out.println("Test 7: " + B.longestConsecutive(build(3, 2, 2, 1, null, null, 1)) + " (Expected: 3)"); // bend one level down, duplicate values
        System.out.println("Test 8: " + B.longestConsecutive(build(-1, -2, 0)) + " (Expected: 3)");         // negatives span zero
        System.out.println("Test 9: " + B.longestConsecutive(build(1, 2, 2, 3, 3, 3, 3)) + " (Expected: 3)"); // many equal-length options
        System.out.println("Test 10: " + B.longestConsecutive(build(2, 3, 1, 4, null, null, null, 5)) + " (Expected: 5)"); // 1-2-3-4-5, bend at the root reaching deep on one side
        System.out.println("Test 11: " + B.longestConsecutive(build(3, 4, 2, 5, null, 1)) + " (Expected: 5)");              // 5-4-3-2-1, bend at the root, both sides deep

        // THE 549 trap: a bend formed at a node must not be reusable by that node's parent.
        // Node 2 forms 1-2-3 (length 3). If that got propagated upward as a straight run, the
        // root would extend it to 4. The real answer stays 3.
        System.out.println("Test 12: " + B.longestConsecutive(build(1, null, 2, 3, 1)) + " (Expected: 3)");

        // longest path avoids the root entirely - the bend is one level down
        System.out.println("Test 13: " + B.longestConsecutive(build(10, 1, null, 2, 0)) + " (Expected: 3)");
    }
}
