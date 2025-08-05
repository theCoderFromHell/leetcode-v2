package medium;

import common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class MaximumLevelSumOfABinaryTree {
    public int maxLevelSum(TreeNode root) {
        if (root == null)
            return 0;
        int maxSum = Integer.MIN_VALUE;
        int maxSumLevel = -1;
        int level = 1;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            int sum = 0;
            while (size-- > 0) {
                TreeNode node = queue.poll();
                sum += node.val;
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            if (sum > maxSum) {
                maxSum = sum;
                maxSumLevel = level;
            }
            level++;
        }
        return maxSumLevel;
    }
}
