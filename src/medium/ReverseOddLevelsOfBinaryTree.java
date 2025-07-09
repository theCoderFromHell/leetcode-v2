package medium;

import common.TreeNode;

import java.util.ArrayDeque;

public class ReverseOddLevelsOfBinaryTree {
    public TreeNode reverseOddLevels(TreeNode root) {
        if (root == null || (root.left == null && root.right == null))
            return root;
        ArrayDeque<TreeNode> deque = new ArrayDeque<>();
        deque.add(root);
        boolean even = true;
        while (!deque.isEmpty()) {
            TreeNode left, right;
            if (even) {
                TreeNode[] nodes = new TreeNode[deque.size()];
                nodes = deque.toArray(nodes);
                int start = 0, end = nodes.length-1;
                while (start <= end) {
                    left = nodes[start];
                    right = nodes[end];
                    if (left.left != null && right.right != null) {
                        int temp = left.left.val;
                        left.left.val = right.right.val;
                        right.right.val = temp;
                    }
                    if (start < end) {
                        if (left.right != null && right.left != null) {
                            int temp = left.right.val;
                            left.right.val = right.left.val;
                            right.left.val = temp;
                        }
                    }
                    start++;
                    end--;
                }
            }
            int size = deque.size();
            while (size-- > 0) {
                TreeNode node = deque.removeFirst();
                if (node.left != null)
                    deque.add(node.left);
                if (node.right != null)
                    deque.add(node.right);
            }
            even = !even;
        }
        return root;
    }

    public static void main(String[] args) {
        ReverseOddLevelsOfBinaryTree R = new ReverseOddLevelsOfBinaryTree();

        System.out.println("************** Test case 1 ************** ");
        TreeNode root1 = new TreeNode(2);
        root1.left = new TreeNode(3);
        root1.right = new TreeNode(5);
        root1.left.left = new TreeNode(8);
        root1.left.right = new TreeNode(13);
        root1.right.left = new TreeNode(21);
        root1.right.right = new TreeNode(34);

        System.out.println(R.reverseOddLevels(root1));

        System.out.println("************** Test case 2 ************** ");
        TreeNode root2 = new TreeNode(0);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(2);

        root2.left.left = new TreeNode(0);
        root2.left.right = new TreeNode(0);
        root2.right.left = new TreeNode(0);
        root2.right.right = new TreeNode(0);

        root2.left.left.left = new TreeNode(1);
        root2.left.left.right = new TreeNode(1);
        root2.left.right.left = new TreeNode(1);
        root2.left.right.right = new TreeNode(1);
        root2.right.left.left = new TreeNode(2);
        root2.right.left.right = new TreeNode(2);
        root2.right.right.left = new TreeNode(2);
        root2.right.right.right = new TreeNode(2);

        System.out.println(R.reverseOddLevels(root2));
    }
}
