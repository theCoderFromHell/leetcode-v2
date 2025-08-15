package medium;

import common.TreeNode;

public class DistributeCoinsInBinaryTree {
    int result = 0;
    public int distributeCoins(TreeNode root) {
        if (root == null)
            return 0;
        result = 0;
        distribute(root);
        return result;
    }
    public int distribute(TreeNode root) {
        if (root == null)
            return 0;
        int left = distribute(root.left);
        int right = distribute(root.right);
        int total = (1 - root.val) + left + right;
        result += Math.abs(total);
        return total;
    }

    public static void main(String[] args) {
        DistributeCoinsInBinaryTree D = new DistributeCoinsInBinaryTree();

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(0);
        root.right = new TreeNode(0);
        System.out.println(D.distributeCoins(root));

        root = new TreeNode(0);
        root.left = new TreeNode(3);
        root.right = new TreeNode(0);
        System.out.println(D.distributeCoins(root));
    }
}
