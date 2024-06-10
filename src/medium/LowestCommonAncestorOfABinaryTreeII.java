package medium;

import common.TreeNode;

public class LowestCommonAncestorOfABinaryTreeII {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null)
            return null;
        if (!check(p, root) || !check(q, root))
            return null;
        return LCS(root, p, q);
    }

    private boolean check(TreeNode node, TreeNode root) {
        if (node == null)
            return true;
        if (root == null)
            return false;
        if (node.val == root.val)
            return true;
        return check(node, root.left) || check(node, root.right);
    }

    public TreeNode LCS(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root.val == p.val || root.val == q.val)
            return root;
        TreeNode left = LCS(root.left, p, q);
        TreeNode right = LCS(root.right, p, q);
        if (left != null && right != null)
            return root;
        return (left != null) ? left : right;
    }
}
