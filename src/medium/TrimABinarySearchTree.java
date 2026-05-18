package medium;

import common.TreeNode;

// https://leetcode.com/problems/trim-a-binary-search-tree/
public class TrimABinarySearchTree {
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null)
            return null;
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);
        if (root.val < low)
            return root.right;
        if (root.val > high)
            return root.left;
        return root;
    }

    private static String inorder(TreeNode root) {
        if (root == null) return "";
        String left = inorder(root.left);
        String right = inorder(root.right);
        StringBuilder sb = new StringBuilder();
        if (!left.isEmpty()) sb.append(left).append(",");
        sb.append(root.val);
        if (!right.isEmpty()) sb.append(",").append(right);
        return sb.toString();
    }

    /*
     * Revision Note — Trim a Binary Search Tree (Medium)
     * Pattern: Post-order recursion exploiting BST invariant
     * Key Insight: When current node is out of range, the BST property guarantees one whole
     *              subtree is also out of range — return the other subtree directly.
     *              Pre-order variant (range check first) is often faster as it skips entire
     *              eliminated subtrees instead of recursing into them.
     * Gotchas:
     *   - Order matters: recurse on children before checking range so subtrees are pre-trimmed
     *   - When root.val < low, ALL left descendants are also < low (already null after trim)
     *   - Don't mutate values — return new wiring via return value pattern
     */
    public static void main(String[] args) {
        TrimABinarySearchTree T = new TrimABinarySearchTree();

        // Example 1: [1,0,2], low=1, high=2 → [1,null,2]
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(0);
        root1.right = new TreeNode(2);
        System.out.println("Test 1: " + inorder(T.trimBST(root1, 1, 2)) + " (Expected: 1,2)");

        // Example 2: [3,0,4,null,2,null,null,1], low=1, high=3 → [3,2,null,1]
        TreeNode root2 = new TreeNode(3);
        root2.left = new TreeNode(0);
        root2.right = new TreeNode(4);
        root2.left.right = new TreeNode(2);
        root2.left.right.left = new TreeNode(1);
        System.out.println("Test 2: " + inorder(T.trimBST(root2, 1, 3)) + " (Expected: 1,2,3)");

        // All in range: nothing trimmed
        TreeNode root3 = new TreeNode(2);
        root3.left = new TreeNode(1);
        root3.right = new TreeNode(3);
        System.out.println("Test 3: " + inorder(T.trimBST(root3, 1, 3)) + " (Expected: 1,2,3)");

        // All out of range: empty tree
        TreeNode root4 = new TreeNode(5);
        root4.left = new TreeNode(4);
        root4.right = new TreeNode(6);
        System.out.println("Test 4: " + inorder(T.trimBST(root4, 10, 20)) + " (Expected: )");

        // Single node, in range
        System.out.println("Test 5: " + inorder(T.trimBST(new TreeNode(5), 1, 10)) + " (Expected: 5)");

        // Single node, out of range
        TreeNode root6 = T.trimBST(new TreeNode(5), 10, 20);
        System.out.println("Test 6: " + (root6 == null ? "null" : inorder(root6)) + " (Expected: null)");

        // Null root
        TreeNode root7 = T.trimBST(null, 1, 10);
        System.out.println("Test 7: " + (root7 == null ? "null" : inorder(root7)) + " (Expected: null)");

        // low == high: only that exact value survives
        TreeNode root8 = new TreeNode(2);
        root8.left = new TreeNode(1);
        root8.right = new TreeNode(3);
        System.out.println("Test 8: " + inorder(T.trimBST(root8, 2, 2)) + " (Expected: 2)");
    }
}
