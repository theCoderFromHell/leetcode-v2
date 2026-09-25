package medium;

// https://leetcode.com/problems/inorder-successor-in-bst-ii/
public class InorderSuccessorInBSTII {
    public Node inorderSuccessor(Node node) {
        if (node == null)
            return null;
        if (node.right != null) {
            Node curr = node.right;
            while (curr.left != null)
                curr = curr.left;
            return curr;
        }
        Node parent = node.parent;
        while (parent != null && parent.val < node.val)
            parent = parent.parent;
        return parent;
    }

    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }

    /*
     * Revision Note — Inorder Successor in BST II (Medium)
     * Pattern: Parent-pointer BST navigation, no root access
     * Key Insight: Two disjoint cases based on whether node has a right child.
     *   - Has right child: successor is the MINIMUM of that subtree, i.e. descend
     *     left from node.right as far as possible — NOT node.right itself.
     *   - No right child: climb parent pointers while node is a RIGHT child of its
     *     parent; the first ancestor where node is a LEFT child is the successor
     *     (or null if the climb reaches the root without ever turning left).
     * Gotchas:
     *   - `if (node.right != null) return node.right;` is a classic near-miss bug —
     *     it's only correct when node.right has no left subtree of its own. First
     *     caught here via a tree (7,3,15,9,20) where node=7's successor is 9, not 15.
     *   - The climb-up condition `parent.val < node.val` is value-based but IS
     *     equivalent to the pointer check `parent.right == node`, because parent is
     *     the DIRECT parent at each step — BST invariant guarantees the two agree.
     *   - Follow-up (no value comparisons): replace that condition with tracking a
     *     `cur` pointer and checking `parent.right == cur` instead.
     * Template:
     *   if (node.right != null) {
     *       cur = node.right; while (cur.left != null) cur = cur.left; return cur;
     *   }
     *   parent = node.parent;
     *   while (parent != null && parent.val < node.val) parent = parent.parent;
     *   return parent;
     */
    public static void main(String[] args) {
        InorderSuccessorInBSTII I = new InorderSuccessorInBSTII();

        // LeetCode Example 1: [2,1,3], node = 1 -> 2
        Node a2 = new Node(); a2.val = 2;
        Node a1 = new Node(); a1.val = 1;
        Node a3 = new Node(); a3.val = 3;
        connect(a2, a1, a3);
        System.out.println("Test 1: " + val(I.inorderSuccessor(a1)) + " (Expected: 2)");

        // LeetCode Example 2: [5,3,6,2,4,null,null,1], node = 6 -> null (6 is the max)
        Node b5 = new Node(); b5.val = 5;
        Node b3 = new Node(); b3.val = 3;
        Node b6 = new Node(); b6.val = 6;
        Node b2 = new Node(); b2.val = 2;
        Node b4 = new Node(); b4.val = 4;
        Node b1 = new Node(); b1.val = 1;
        connect(b5, b3, b6);
        connect(b3, b2, b4);
        connect(b2, b1, null);
        System.out.println("Test 2: " + val(I.inorderSuccessor(b6)) + " (Expected: null)");

        // Same tree: node = 3 has a right child (4) with NO left subtree of its own,
        // so the successor genuinely IS node.right here.
        System.out.println("Test 3: " + val(I.inorderSuccessor(b3)) + " (Expected: 4)");

        // Tree from the BSTIteratorII session: 7,3,15,9,20 (in-order: 3,7,9,15,20)
        //         7
        //        / \
        //       3   15
        //          /  \
        //         9   20
        // node = 7 has a right child (15) that DOES have a left subtree (9) -
        // the successor must descend to the leftmost node of that subtree (9),
        // not stop at node.right (15) itself.
        Node c7 = new Node(); c7.val = 7;
        Node c3 = new Node(); c3.val = 3;
        Node c15 = new Node(); c15.val = 15;
        Node c9 = new Node(); c9.val = 9;
        Node c20 = new Node(); c20.val = 20;
        connect(c7, c3, c15);
        connect(c15, c9, c20);
        System.out.println("Test 4: " + val(I.inorderSuccessor(c7)) + " (Expected: 9)");

        // node = 3, a leaf with no right child -> climb one level to parent 7
        System.out.println("Test 5: " + val(I.inorderSuccessor(c3)) + " (Expected: 7)");

        // node = 20, the maximum value -> climb through two ancestors, hit the root, null
        System.out.println("Test 6: " + val(I.inorderSuccessor(c20)) + " (Expected: null)");

        // single node, no parent, no children
        Node solo = new Node(); solo.val = 42;
        System.out.println("Test 7: " + val(I.inorderSuccessor(solo)) + " (Expected: null)");
    }

    private static Node connect(Node parent, Node left, Node right) {
        parent.left = left;
        parent.right = right;
        if (left != null) left.parent = parent;
        if (right != null) right.parent = parent;
        return parent;
    }

    private static String val(Node n) {
        return n == null ? "null" : String.valueOf(n.val);
    }
}
