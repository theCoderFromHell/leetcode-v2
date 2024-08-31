package medium;

import com.sun.source.tree.Tree;
import common.ListNode;
import common.TreeNode;

public class FlattenBinaryTreeToLinkedList {
    public static void flatten(TreeNode root) {
        if (root == null)
            return;
        if (root.left == null && root.right == null)
            return;
        TreeNode current = root;
        while (current != null) {
            if (current.left != null) {
                TreeNode rightMost = current.left;
                while (rightMost.right != null)
                    rightMost = rightMost.right;
                rightMost.right = current.right;
                current.right = current.left;
                current.left = null;
            }
            current = current.right;
        }
    }

    public static void flattenV2(TreeNode root) {
        if (root == null)
            return;
        if (root.left == null && root.right == null)
            return;
        flattenTree(root);
    }

    private static TreeNode flattenTree(TreeNode root) {
        if (root == null)
            return null;
        TreeNode left = flattenTree(root.left);
        TreeNode right = flattenTree(root.right);
        if (left != null) {
            left.right = root.right;
            root.right = root.left;
            root.left = null;
        }
        if (right != null)
            return right;
        if (left != null)
            return left;
        return root;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(6);

        flatten(root);
        while (root != null) {
            System.out.print(root.val + " -> ");
            root = root.right;
        }

    }
}
