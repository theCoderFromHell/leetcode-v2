package medium;

import common.TreeNode;

// https://leetcode.com/problems/binary-tree-longest-consecutive-sequence/
public class BinaryTreeLongestConsecutiveSequence {
    int result;
    public int longestConsecutive(TreeNode root) {
        result = 0;
        findLCS(root, 1, null);
        return result;
    }

    private void findLCS(TreeNode root, int count, Integer parent) {
        if (root == null)
            return;
        if (parent != null && root.val == parent + 1)
            count++;
        else
            count = 1;
        result = Math.max(result, count);
        findLCS(root.left, count, root.val);
        findLCS(root.right, count, root.val);
    }

    /*
     * Revision Note — Binary Tree Longest Consecutive Sequence (Medium)
     *
     * Pattern: Top-down DFS passing current streak length and parent value
     *
     * Key Insight: Pass the parent value down; if child.val == parent.val + 1,
     * extend the streak, otherwise reset to 1. Track global max across all nodes.
     *
     * Gotchas:
     * - parent is Integer (boxed) — `parent + 1` NPEs when parent is null (root call).
     *   Always guard: `if (parent != null && root.val == parent + 1)`
     * - Streak is parent→child only (one direction), not any path in the tree
     * - Reset instance field at the start of longestConsecutive for safe re-use
     *
     * Template:
     *   findLCS(node, count, parent):
     *     if node == null: return
     *     count = (parent != null && node.val == parent+1) ? count+1 : 1
     *     result = max(result, count)
     *     findLCS(node.left, count, node.val)
     *     findLCS(node.right, count, node.val)
     */
    public static void main(String[] args) {
        BinaryTreeLongestConsecutiveSequence B = new BinaryTreeLongestConsecutiveSequence();

        // [1,null,3,2,4,null,null,null,5] → longest: 3-4-5 = 3
        TreeNode t1 = new TreeNode(1);
        t1.right = new TreeNode(3);
        t1.right.left = new TreeNode(2);
        t1.right.right = new TreeNode(4);
        t1.right.right.right = new TreeNode(5);
        System.out.println("Test 1: " + B.longestConsecutive(t1) + " (Expected: 3)");

        // [2,3,null,4] → 2-3-4 = 3
        TreeNode t2 = new TreeNode(2);
        t2.left = new TreeNode(3);
        t2.left.left = new TreeNode(4);
        System.out.println("Test 2: " + B.longestConsecutive(t2) + " (Expected: 3)");

        // [1] → single node = 1
        System.out.println("Test 3: " + B.longestConsecutive(new TreeNode(1)) + " (Expected: 1)");

        // [3,2,null,1] → decreasing, no consecutive streak → 1
        TreeNode t4 = new TreeNode(3);
        t4.left = new TreeNode(2);
        t4.left.left = new TreeNode(1);
        System.out.println("Test 4: " + B.longestConsecutive(t4) + " (Expected: 1)");

        // null root → 0
        System.out.println("Test 5: " + B.longestConsecutive(null) + " (Expected: 0)");
    }
}
