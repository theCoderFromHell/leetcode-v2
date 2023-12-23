package medium;

import common.TreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeZigzagLevelOrderTraversal {
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if(root == null)
            return result;
        Queue<TreeNode> primary = new LinkedList<>();
        Queue<TreeNode> secondary = new LinkedList<>();
        primary.add(root);
        List<Integer> values = new ArrayList<>();
        boolean flip = true;
        while (!primary.isEmpty()) {
            TreeNode front = primary.poll();
            values.add(front.val);
            if (front.left != null)
                secondary.add(front.left);
            if (front.right != null)
                secondary.add(front.right);
            if (primary.isEmpty()) {
                if (!flip)
                    values = values.reversed();
                result.add(values);
                flip = !flip;
                values = new ArrayList<>();
                Queue<TreeNode> temp = primary;
                primary = secondary;
                secondary = temp;
            }
        }
        return result;
    }
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.right.right = new TreeNode(5);
        System.out.println(zigzagLevelOrder(root));
    }
}
