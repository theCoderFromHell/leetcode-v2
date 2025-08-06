package medium;

import common.TreeNode;

import java.util.HashSet;

public class SmallestSubtreeWithAllTheDeepestNodes {
    TreeNode theRealLCA;
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        if (root == null || (isLeaf(root)))
            return root;
        int maxDepth = maxDepth(root);
        HashSet<TreeNode> deepest =  new HashSet<>();
        countDeepest(root , maxDepth, 1, deepest);
        if (deepest.size() == 1)
            return deepest.iterator().next();
        theRealLCA = null;
        lca(root, deepest, maxDepth);
        return theRealLCA;
    }

    private int lca(TreeNode root, HashSet<TreeNode> deepest, int maxDepth) {
        if (root == null)
            return 0;
        int left = lca(root.left, deepest, maxDepth);
        int right = lca(root.right, deepest, maxDepth);
        if (theRealLCA == null && left + right == deepest.size())
            theRealLCA = root;
        return left + right + (deepest.contains(root) ? 1 : 0);
    }

    private void countDeepest(TreeNode root, int maxDepth, int currDepth, HashSet<TreeNode> deepest) {
        if (root == null)
            return;
        if (isLeaf(root) && currDepth == maxDepth)
            deepest.add(root);
        countDeepest(root.left, maxDepth, currDepth + 1, deepest);
        countDeepest(root.right, maxDepth, currDepth + 1, deepest);
    }

    private int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    private boolean isLeaf(TreeNode root) {
        return root != null && root.left == null && root.right == null;
    }

    public static void main(String[] args) {
        SmallestSubtreeWithAllTheDeepestNodes S = new SmallestSubtreeWithAllTheDeepestNodes();
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);
        System.out.println(S.subtreeWithAllDeepest(root));
    }
}
