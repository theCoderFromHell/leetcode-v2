package medium;

import common.TreeNode;

public class LongestZigZagPathInABinaryTree {
    int result;
    public int longestZigZag(TreeNode root) {
        if (root == null)
            return 0;
        result = 0;
        findLongestPath(root, -1);
        findLongestPath(root, 1);
        return result - 1;
    }

    private int findLongestPath(TreeNode root, int direction) {
        if (root == null)
            return 0;
        int left = findLongestPath(root.left, -1);
        int right = findLongestPath(root.right, 1);
        result = Math.max(result, 1 + Math.max(left, right));
        return 1 + (direction == -1 ? right : left);
    }
}
