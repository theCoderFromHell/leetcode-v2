package easy;

import common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class SecondMinimumNodeInABinaryTree {
    public static int findSecondMinimumValue(TreeNode root) {
        if (root == null || (root.left == null && root.right == null))
            return -1;
        Queue<TreeNode> queue = new LinkedList<>();
        if (root.left != null)
            queue.add(root.left);
        if (root.right != null)
            queue.add(root.right);
        int first = root.val;
        int second = Integer.MAX_VALUE;
        boolean found = false;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.val > first) {
                found = true;
                second = Math.min(second, node.val);
            }
            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);
        }
        return (!found) ? -1 : second;
    }



    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.right.left = new TreeNode(5);
        root.right.right = new TreeNode(7);

        System.out.println(findSecondMinimumValue(root));
    }
}
