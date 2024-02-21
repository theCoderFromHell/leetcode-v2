package medium;

import common.TreeNode;

public class DeleteNodeInABST {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null)
            return root;
        if (key == root.val) {
            if (root.left == null && root.right == null)
                return null;
            if (root.left == null)
                return root.right;
            if (root.right == null)
                return root.left;
            fitTheLeftTreeInTheRightTree(root.left, root.right);
            return root.right;
        }
        root.left = deleteNode(root.left, key);
        root.right = deleteNode(root.right, key);
        return root;
    }

    private void fitTheLeftTreeInTheRightTree(TreeNode left, TreeNode right) {
        while (right != null && right.left != null)
            right = right.left;
        right.left = left;
    }
}
