package medium;

import common.Node;

public class PopulatingNextRightPointersInEachNode {
    public Node connect(Node root) {
        if (root == null)
            return null;
        if (root.left == null && root.right == null)
            return root;
        root.left.next = root.right;
        root.right.next = root.next == null ? null : root.next.left;
        connect(root.left);
        connect(root.right);
        return root;
    }

    public static void main(String[] args) {

    }
}
