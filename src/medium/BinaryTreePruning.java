package medium;

import common.TreeNode;

public class BinaryTreePruning {
    public TreeNode pruneTree(TreeNode root) {
        if (prune(root))
            return root;
        return null;
    }

    private boolean prune(TreeNode root) {
        if (root == null)
            return false;
        boolean isOne = root.val == 1;
        boolean left = prune(root.left);
        if (!left)
            root.left = null;
        boolean right = prune(root.right);
        if (!right)
            root.right = null;
        return (left || right || isOne);
    }

    public static void main(String[] args) {
        BinaryTreePruning B = new BinaryTreePruning();

        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(0);
        root1.right = new TreeNode(1);
        root1.left.left = new TreeNode(0);
        root1.left.right = new TreeNode(0);
        root1.right.left = new TreeNode(0);
        root1.right.right = new TreeNode(1);
        System.out.print("Test 1: ");
        TreeNode.printTree(B.pruneTree(root1));

        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(0);
        root2.left.left = new TreeNode(0);
        root2.left.right = new TreeNode(0);
        System.out.print("Test 2: ");
        TreeNode.printTree(B.pruneTree(root2));

        TreeNode root3 = new TreeNode(1);
        root3.right = new TreeNode(1);
        root3.right.left = new TreeNode(1);
        root3.right.right = new TreeNode(1);
        System.out.print("Test 3: ");
        TreeNode.printTree(B.pruneTree(root3));

        TreeNode root4 = new TreeNode(0);
        root4.left = new TreeNode(0);
        root4.right = new TreeNode(0);
        System.out.print("Test 4: ");
        TreeNode.printTree(B.pruneTree(root4));

        TreeNode root5 = null;
        System.out.print("Test 5: ");
        TreeNode.printTree(B.pruneTree(root5));

        TreeNode root6 = new TreeNode(1);
        System.out.print("Test 6: ");
        TreeNode.printTree(B.pruneTree(root6));
    }
}
