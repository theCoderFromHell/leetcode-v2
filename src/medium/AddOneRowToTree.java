package medium;

import common.TreeNode;

public class AddOneRowToTree {
    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        if (depth == 1) {
            TreeNode newRoot = new TreeNode(val);
            newRoot.left = root;
            return newRoot;
        }
        add(root, val, depth, 1);
        return root;
    }

    private void add(TreeNode root, int val, int depth, int currDepth) {
        if (root == null)
            return;
        if (currDepth == depth-1) {
            TreeNode originalLeft = root.left == null ? null : root.left;
            TreeNode originalRight = root.right == null ? null : root.right;
            root.left = new TreeNode(val);
            root.left.left = originalLeft;

            root.right = new TreeNode(val);
            root.right.right = originalRight;
            return;
        }
        add(root.left, val, depth, currDepth+1);
        add(root.right, val, depth, currDepth+1);
    }
}
