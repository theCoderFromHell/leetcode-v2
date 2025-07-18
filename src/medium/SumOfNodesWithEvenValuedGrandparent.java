package medium;

import common.TreeNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class SumOfNodesWithEvenValuedGrandparent {
    public int sumEvenGrandparent(TreeNode root) {
        if (root == null)
            return 0;
        int result = 0;
        HashMap<TreeNode,TreeNode> parents = new HashMap<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        parents.put(root, null);
        while (!queue.isEmpty()) {
            TreeNode peek = queue.poll();
            if (parents.get(peek) != null
                    && parents.get(parents.get(peek)) != null
                    && parents.get(parents.get(peek)).val % 2 == 0) {
                result += peek.val;
            }
            if (peek.left != null) {
                parents.put(peek.left, peek);
                queue.add(peek.left);
            }
            if (peek.right != null) {
                parents.put(peek.right, peek);
                queue.add(peek.right);
            }
        }
        return result;
    }
}
