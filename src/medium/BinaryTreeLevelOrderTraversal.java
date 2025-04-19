package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeLevelOrderTraversal {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if(root == null)
            return result;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> values = new ArrayList<>();;
            while (size-- > 0) {
                TreeNode front = queue.poll();
                values.add(front.val);
                if (front.left != null)
                    queue.add(front.left);
                if (front.right != null)
                    queue.add(front.right);
            }
            result.add(values);
        }
        return result;
    }
}
