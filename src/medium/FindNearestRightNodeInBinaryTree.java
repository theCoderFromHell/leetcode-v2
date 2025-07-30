package medium;

import common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class FindNearestRightNodeInBinaryTree {
    public TreeNode findNearestRightNode(TreeNode root, TreeNode u) {
        if (root == null || (root.left == null && root.right == null))
            return null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                if (node == u) {
                    if (size == 0)
                        return null;
                    else
                        return queue.peek();
                }
                if (node.left != null)
                    queue.add(node.left);
                if (node.right != null)
                    queue.add(node.right);
            }

        }
        return null;
    }
}
