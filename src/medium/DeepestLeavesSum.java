package medium;

import common.TreeNode;

public class DeepestLeavesSum {
    public int deepestLeavesSum(TreeNode root) {
        if (root == null)
            return 0;
        int depth = getDepth(root);
        return sum(root, depth, 1);
    }

    private int sum(TreeNode root, int depth, int level) {
        if (root == null)
            return 0;
        if (level == depth && root.left == null && root.right == null)
            return root.val;
        return sum(root.left, depth, level+1) + sum(root.right, depth, level+1);
    }

    private int getDepth(TreeNode root) {
        if (root == null)
            return 0;
        return 1 + Math.max(getDepth(root.left), getDepth(root.right));
    }
}
