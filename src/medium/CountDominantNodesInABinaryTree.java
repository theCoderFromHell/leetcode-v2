package medium;

import common.TreeNode;

// https://leetcode.com/problems/count-dominant-nodes-in-a-binary-tree/
public class CountDominantNodesInABinaryTree {
    int count;
    public int countDominantNodes(TreeNode root) {
        count = 0;
        countNodes(root);
        return count;
    }

    private int countNodes(TreeNode root) {
        if (root == null)
            return Integer.MIN_VALUE;
        if (root.left == null && root.right == null) {
            count++;
            return root.val;
        }
        int left = countNodes(root.left);
        int right = countNodes(root.right);
        int currMax = Math.max(root.val, Math.max(left, right));
        if (root.val == currMax)
            count++;
        return currMax;
    }

    /*
     * Revision Note — Count Dominant Nodes in a Binary Tree (Medium)
     *
     * Pattern: Post-order DFS returning subtree maximum
     *
     * Key Insight: A post-order traversal returns the max value in each subtree;
     * a node is dominant when its own value equals max(node.val, leftMax, rightMax).
     *
     * Gotchas:
     * - Null sentinel must be Integer.MIN_VALUE, not -1 — using -1 silently corrupts
     *   results when any node value is < -1 (null would inflate the perceived subtree max)
     * - Leaves are always dominant (no subtree values to exceed them) — handle before recursion
     * - Reset count instance field in the public method for safe re-use across calls
     *
     * Template:
     *   int countNodes(node):
     *     if null: return Integer.MIN_VALUE
     *     if leaf: count++; return node.val
     *     left = countNodes(node.left); right = countNodes(node.right)
     *     currMax = max(node.val, left, right)
     *     if node.val == currMax: count++
     *     return currMax
     */
    public static void main(String[] args) {
        CountDominantNodesInABinaryTree C = new CountDominantNodesInABinaryTree();

        // [5] → single node is always dominant
        TreeNode t1 = new TreeNode(5);
        System.out.println("Test 1: " + C.countDominantNodes(t1) + " (Expected: 1)");

        // [3,2,1] → root 3 >= all, leaves 2 and 1 are dominant → 3
        TreeNode t2 = new TreeNode(3);
        t2.left = new TreeNode(2); t2.right = new TreeNode(1);
        System.out.println("Test 2: " + C.countDominantNodes(t2) + " (Expected: 3)");

        // [1,2,3] → root 1 < 3, not dominant; leaves 2 and 3 are dominant → 2
        TreeNode t3 = new TreeNode(1);
        t3.left = new TreeNode(2); t3.right = new TreeNode(3);
        System.out.println("Test 3: " + C.countDominantNodes(t3) + " (Expected: 2)");

        // [5,3,7,1,4] → 3 is not dominant (child 4>3), 5 is not dominant (child 7>5); leaves 1,4,7 → 3
        TreeNode t4 = new TreeNode(5);
        t4.left = new TreeNode(3); t4.right = new TreeNode(7);
        t4.left.left = new TreeNode(1); t4.left.right = new TreeNode(4);
        System.out.println("Test 4: " + C.countDominantNodes(t4) + " (Expected: 3)");

        // [2,2,2] → all equal, all dominant → 3
        TreeNode t5 = new TreeNode(2);
        t5.left = new TreeNode(2); t5.right = new TreeNode(2);
        System.out.println("Test 5: " + C.countDominantNodes(t5) + " (Expected: 3)");
    }
}
