package common;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    TreeNode() {}
    public TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    // Builds a tree from LeetCode's level-order form: createTree(4, 2, 6, null, 3) is [4,2,6,null,3].
    public static TreeNode createTree(Integer... level) {
        if (level.length == 0 || level[0] == null)
            return null;
        TreeNode root = new TreeNode(level[0]);
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int i = 1;
        while (!queue.isEmpty() && i < level.length) {
            TreeNode node = queue.poll();
            if (i < level.length && level[i++] != null) {
                node.left = new TreeNode(level[i - 1]);
                queue.add(node.left);
            }
            if (i < level.length && level[i++] != null) {
                node.right = new TreeNode(level[i - 1]);
                queue.add(node.right);
            }
        }
        return root;
    }

    // Reads a tree back in LeetCode's level-order form, with nulls for missing children and
    // trailing nulls trimmed, so shape is visible: [2,1] and [2,null,1] print differently.
    public static String serialize(TreeNode root) {
        List<String> out = new ArrayList<>();
        List<TreeNode> order = new ArrayList<>();
        order.add(root);
        for (int i = 0; i < order.size(); i++) {
            TreeNode node = order.get(i);
            out.add(node == null ? "null" : String.valueOf(node.val));
            if (node != null) {
                order.add(node.left);
                order.add(node.right);
            }
        }
        while (!out.isEmpty() && out.get(out.size() - 1).equals("null"))
            out.remove(out.size() - 1);
        return "[" + String.join(",", out) + "]";
    }

    public static List<Integer> levelOrder(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        if (root == null) return out;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode n = q.poll();
            out.add(n.val);
            if (n.left != null) q.add(n.left);
            if (n.right != null) q.add(n.right);
        }
        return out;
    }

    public static void printTree(TreeNode root) {
        if (root == null) {
            System.out.println("null");
            return;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            System.out.print(node.val + " ");
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "val=" + val +
                ", left=" + (left == null ? "null" : left.val) +
                ", right=" + (right == null ? "null" : right.val) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return val == treeNode.val && Objects.equals(left, treeNode.left) && Objects.equals(right, treeNode.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, left, right);
    }
}
