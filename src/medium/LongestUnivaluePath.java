package medium;

import common.TreeNode;

public class LongestUnivaluePath {
    Integer result = 0;
    public int longestUnivaluePath(TreeNode root) {
        solve(root, null);
        return result;
    }

    private int solve(TreeNode root, Integer parent) {
        if (root == null)
            return 0;
        int left = solve(root.left, root.val);
        int right = solve(root.right, root.val);
        result = Math.max(result, left + right);
        if (parent != null && root.val == parent)
            return 1 + Math.max(left, right);
        return 0;
    }
}
