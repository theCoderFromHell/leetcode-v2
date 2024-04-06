package medium;

import common.TreeNode;

public class InorderSuccessorInBST {
    TreeNode last = null;
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null)
            return null;
        return inorder(root, p.val);
    }

    private TreeNode inorder(TreeNode root, int value) {
        TreeNode curr = null;
        while (root != null) {
            if (value < root.val) {
                curr = root;
                root = root.left;
            } else
                root = root.right;
        }
        return curr;
    }

    private TreeNode inorderV2(TreeNode root, int value) {
        if (root == null)
            return null;
        TreeNode left = inorder(root.left, value);
        if (last != null && last.val == value) {
            last = root;
            return root;
        }
        last = root;
        TreeNode right = inorder(root.right, value);
        if (left == null && right == null)
            return null;
        if (left != null)
            return left;
        else
            return right;
    }

    public static void main(String[] args) {
        InorderSuccessorInBST i = new InorderSuccessorInBST();
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.left.left.left = new TreeNode(1);
        System.out.println(i.inorderSuccessor(root, root.left.left.left));
    }
}
