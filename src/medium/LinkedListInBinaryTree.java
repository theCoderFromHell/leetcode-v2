package medium;

import common.ListNode;
import common.TreeNode;

public class LinkedListInBinaryTree {
    public boolean isSubPath(ListNode head, TreeNode root) {
        if (head == null)
            return true;
        if (root == null)
            return false;
        if (head.val == root.val) {
            if (check(head.next, root.left) || check(head.next, root.right))
                return true;
        }
        return isSubPath(head, root.left) || isSubPath(head, root.right);
    }

    private boolean check(ListNode head, TreeNode root) {
        if (head == null)
            return true;
        if (root == null)
            return false;
        if (head.val != root.val)
            return false;
        return (check(head.next, root.left) || check(head.next, root.right));
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(10);

        TreeNode root = new TreeNode(1);
        root.right = new TreeNode(1);
        root.right.left = new TreeNode(10);
        root.right.right = new TreeNode(1);
        root.right.left.left = new TreeNode(9);

        LinkedListInBinaryTree L = new LinkedListInBinaryTree();

        System.out.println(L.isSubPath(head, root));

    }
}
