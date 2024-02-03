package medium;

import common.TreeNode;

public class InsertIntoABinarySearchTree {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);
        return insert(root, val, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private TreeNode insert(TreeNode root, int val, int minimum, int maximum) {
        if (root == null)
            return new TreeNode(val);
        if (val <= minimum || val >= maximum)
            return null;
        if (val < root.val) {
            root.left = insert(root.left, val, minimum, root.val);
            return root;
        }
        else {
            root.right = insert(root.right, val, root.val, maximum);
            return root;
        }
    }
}
