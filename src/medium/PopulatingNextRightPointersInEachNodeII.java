package medium;

import common.Node;

import java.util.LinkedList;
import java.util.Queue;

public class PopulatingNextRightPointersInEachNodeII {

    public static Node connect(Node root) {
        if (root == null)
            return null;
        Queue<Node> primary = new LinkedList<>();
        Queue<Node> secondary = new LinkedList<>();
        primary.add(root);
        Node prev = null;
        while (!primary.isEmpty()) {
            Node front = primary.poll();
            if (prev != null)
                prev.next = front;
            prev = front;
            if (front.left != null)
                secondary.add(front.left);
            if (front.right != null)
                secondary.add(front.right);
            if (primary.isEmpty()) {
                prev = null;
                Queue<Node> temp = primary;
                primary = secondary;
                secondary = temp;
            }
        }
        return root;
    }

    public static Node connectV2(Node root) {
        if (root == null)
            return null;
        connectV2(root.next);
        if (root.left == null && root.right == null) {
            connectV2(findNext(root.next));
            return root;
        }
        if (root.left != null) {
            if (root.right != null) {
                root.left.next = root.right;
                root.right.next = findNext(root.next);
            }
            else
                root.left.next = findNext(root.next);
            connectV2(root.left);
        }
        if (root.right != null) {
            root.right.next = findNext(root.next);
            connectV2(root.right);
        }
        return root;
    }

    private static Node findNext(Node root) {
        if (root == null)
            return null;
        if (root.left != null)
            return root.left;
        if (root.right != null)
            return root.right;
        return findNext(root.next);
    }

    public static void main(String[] args) {
        Node root = new Node(2);
        root.left = new Node(1);
        root.right = new Node(3);
        root.left.left = new Node(0);
        root.left.right = new Node(7);
        root.right.left = new Node(9);
        root.right.right = new Node(1);
        root.left.left.left = new Node(2);
        root.left.right.left = new Node(1);
        root.left.right.right = new Node(0);
        root.right.right.left = new Node(8);
        root.right.right.right = new Node(8);
        root.left.right.right.left = new Node(7);
        Node result = connect(root);
        System.out.println(result);





    }
}
