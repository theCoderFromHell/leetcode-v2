package medium;

import common.TreeNode;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class KthLargestSumInABinaryTree {
    public long kthLargestLevelSum(TreeNode root, int k) {
        if (root == null)
            return -1;
        PriorityQueue<Long> pq = new PriorityQueue<>(k);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        Long levelSum;
        while (!queue.isEmpty()) {
            int size = queue.size();
            levelSum = 0L;
            while (size-- > 0) {
                TreeNode node = queue.poll();
                levelSum += node.val;
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }
            if (pq.size() < k || pq.peek() < levelSum) {
                if (pq.size() == k)
                    pq.poll();
                pq.add(levelSum);
            }
        }
        if (pq.isEmpty() || pq.size() < k)
            return -1;
        return pq.peek();
    }
}
