package medium;

import common.TreeNode;

// https://leetcode.com/problems/count-univalue-subtrees/
public class CountUnivalueSubtrees {
    int result;
    public int countUnivalSubtrees(TreeNode root) {
        result = 0;
        count(root, Integer.MAX_VALUE);
        return result;
    }

    private boolean count(TreeNode root, Integer parent) {
        if (root == null)
            return true;
        boolean left = count(root.left, root.val);
        boolean right = count(root.right, root.val);
        if (left && right) {
            result++;
            return root.val == parent;
        }
        return false;
    }

    /*
     * Revision Note — Count Univalue Subtrees (Medium)
     *
     * Pattern: Post-order DFS — count while signalling upward
     *
     * Key Insight: count(node, parent) returns true when node's subtree is uni-value
     * AND node.val == parent, letting the caller know whether it can extend its streak.
     * Both children are checked against root.val, so if both return true, every node
     * in both subtrees already equals root.val.
     *
     * Gotchas:
     * - null node returns true (empty subtree is trivially uni-value — essential for leaves)
     * - Integer.MAX_VALUE sentinel for root call is safe: top-level return value is discarded
     * - MUST evaluate both children eagerly before &&-ing — short-circuit `&&` skips the right
     *   subtree entirely when left returns false, causing those nodes to never be counted
     *
     * Template:
     *   count(node, parent):
     *     if null: return true
     *     boolean left = count(node.left, node.val)   // evaluate eagerly
     *     boolean right = count(node.right, node.val) // evaluate eagerly
     *     if left && right: result++; return node.val == parent
     *     return false
     *   call: count(root, Integer.MAX_VALUE)
     */
    public static void main(String[] args) {
        CountUnivalueSubtrees C = new CountUnivalueSubtrees();

        // [5,1,5,5,5,null,5] → 4
        TreeNode t1 = new TreeNode(5);
        t1.left = new TreeNode(1);  t1.right = new TreeNode(5);
        t1.left.left = new TreeNode(5); t1.left.right = new TreeNode(5);
        t1.right.right = new TreeNode(5);
        System.out.println("Test 1: " + C.countUnivalSubtrees(t1) + " (Expected: 4)");

        // [0,1,0,null,null,1,0,null,null,1,0] → 5 (LeetCode example)
        TreeNode t2 = new TreeNode(0);
        t2.left = new TreeNode(1); t2.right = new TreeNode(0);
        t2.right.left = new TreeNode(1); t2.right.right = new TreeNode(0);
        System.out.println("Test 2: " + C.countUnivalSubtrees(t2) + " (Expected: 3)");

        // null → 0
        System.out.println("Test 3: " + C.countUnivalSubtrees(null) + " (Expected: 0)");

        // [1] → 1 (single node)
        System.out.println("Test 4: " + C.countUnivalSubtrees(new TreeNode(1)) + " (Expected: 1)");

        // [5,5,5,5,5] → 5 (all same, every subtree is uni-value)
        TreeNode t5 = new TreeNode(5);
        t5.left = new TreeNode(5); t5.right = new TreeNode(5);
        t5.left.left = new TreeNode(5); t5.left.right = new TreeNode(5);
        System.out.println("Test 5: " + C.countUnivalSubtrees(t5) + " (Expected: 5)");
    }
}
