package medium;

import common.TreeNode;

public class DeleteLeavesWithAGivenValue {
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if (root == null)
            return root;
        TreeNode left = removeLeafNodes(root.left, target);
        TreeNode right = removeLeafNodes(root.right, target);
        if (left == null && right == null && root.val == target) {
            root = null;
            return null;
        }
        root.left = left;
        root.right = right;
        return root;
    }
}
