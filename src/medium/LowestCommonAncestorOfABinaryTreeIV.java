package medium;

import common.TreeNode;

import java.util.Arrays;
import java.util.HashSet;

public class LowestCommonAncestorOfABinaryTreeIV {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode[] nodes) {
        HashSet<TreeNode> nodesSet = new HashSet<>(Arrays.asList(nodes));
        return LCA(root, nodesSet);
    }

    private TreeNode LCA(TreeNode root, HashSet<TreeNode> nodes) {
        if (root == null)
            return null;
        if (nodes.contains(root))
            return root;
        TreeNode left = LCA(root.left, nodes);
        TreeNode right = LCA(root.right, nodes);
        if (left != null && right != null)
            return root;
        return left != null ? left : right;
    }
}
