package medium;

import common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class CheckCompletenessOfABinaryTree {
    public boolean isCompleteTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null))
            return true;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean missing = false;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    if (missing)
                        return false;
                    queue.add(node.left);
                } else
                    missing = true;
                if (node.right != null) {
                    if (missing)
                        return false;
                    queue.add(node.right);
                } else
                    missing = true;
            }
        }
        return true;
    }
}
