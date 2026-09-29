package medium;

// https://leetcode.com/problems/change-the-root-of-a-binary-tree/
public class ChangeTheRootOfABinaryTree {

    // this problem needs a parent pointer, which common.Node does not have
    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int val) {
            this.val = val;
        }
    }

    public Node flipBinaryTree(Node root, Node leaf) {
        Node curr = leaf;
        Node prev = null;
        boolean rightSide = false;
        while (curr != root) {
            if (rightSide)
                curr.right = curr.left;
            Node parent = curr.parent;
            rightSide = (parent.right == curr);
            curr.left = parent;
            curr.parent = prev;
            prev = curr;
            curr = parent;
        }
        if (rightSide)
            root.right = null;
        else
            root.left  = null;
        root.parent = prev;
        return leaf;
    }

    /*
     * Revision Note — Change the Root of a Binary Tree (Medium)
     *
     * Pattern: Iterative pointer rewiring up the leaf-to-root path
     *
     * Key Insight: Walk leaf -> root. At each node the parent becomes the new left child,
     * and the node's own parent pointer becomes the node BELOW it on the path (prev).
     * Track whether the path arrived via the right child: only then does step 1 fire,
     * because only then is the right slot free.
     *
     * Gotchas:
     * - Write curr.parent = prev, NOT parent.parent = curr. The latter clobbers the very
     *   pointer you climb with, and the walk oscillates between two nodes forever.
     *   Rule: never overwrite a pointer you still need to traverse
     * - Step 1 (left becomes right) must be GUARDED. Unconditional curr.right = curr.left
     *   nulls the surviving original right child when the path came up through the left
     * - The right slot is always free when step 1 fires: either the path child was the
     *   right one (so it got overwritten), or step 1 does not run at all
     * - The loop exits AT the root, so the root is never processed as curr — its stale
     *   child pointer must be cleared after the loop, using the final rightSide value,
     *   and its new parent set to prev. Miss this and you get a two-node cycle
     * - New root's parent must be null or the judge fails you
     *
     * Template:
     *   curr = leaf; prev = null; rightSide = false
     *   while curr != root:
     *     if rightSide: curr.right = curr.left
     *     parent = curr.parent
     *     rightSide = (parent.right == curr)
     *     curr.left = parent; curr.parent = prev
     *     prev = curr; curr = parent
     *   if rightSide: root.right = null else root.left = null
     *   root.parent = prev
     *   return leaf
     */
    private static String v(Node n) {
        return n == null ? "null" : String.valueOf(n.val);
    }

    public static void main(String[] args) {
        ChangeTheRootOfABinaryTree C = new ChangeTheRootOfABinaryTree();

        // Test 1 — minimal tree:  1
        //                        /
        //                       2          flip at leaf 2
        Node a1 = new Node(1), a2 = new Node(2);
        a1.left = a2; a2.parent = a1;
        Node r1 = C.flipBinaryTree(a1, a2);
        System.out.println("Test 1 new root     : " + v(r1)        + " (Expected: 2)");
        System.out.println("Test 1 root.parent  : " + v(r1.parent) + " (Expected: null)");
        System.out.println("Test 1 root.left    : " + v(r1.left)   + " (Expected: 1)");
        System.out.println("Test 1 node1.left   : " + v(a1.left)   + " (Expected: null)");
        System.out.println("Test 1 node1.parent : " + v(a1.parent) + " (Expected: 2)");

        // Test 2 — LeetCode example:  root = [3,5,1,6,2,0,8,null,null,7,4], leaf = 7
        //         3
        //      /     \
        //     5       1
        //   /   \    /  \
        //  6     2  0    8
        //       / \
        //      7   4
        Node n3 = new Node(3), n5 = new Node(5), n1 = new Node(1);
        Node n6 = new Node(6), n2 = new Node(2), n0 = new Node(0), n8 = new Node(8);
        Node n7 = new Node(7), n4 = new Node(4);
        n3.left = n5;  n3.right = n1;  n5.parent = n3;  n1.parent = n3;
        n5.left = n6;  n5.right = n2;  n6.parent = n5;  n2.parent = n5;
        n1.left = n0;  n1.right = n8;  n0.parent = n1;  n8.parent = n1;
        n2.left = n7;  n2.right = n4;  n7.parent = n2;  n4.parent = n2;

        Node r2 = C.flipBinaryTree(n3, n7);
        System.out.println("Test 2 new root     : " + v(r2)        + " (Expected: 7)");
        System.out.println("Test 2 7.parent     : " + v(n7.parent) + " (Expected: null)");
        System.out.println("Test 2 7.left/right : " + v(n7.left) + "/" + v(n7.right) + " (Expected: 2/null)");
        System.out.println("Test 2 2.left/right : " + v(n2.left) + "/" + v(n2.right) + " (Expected: 5/4)");
        System.out.println("Test 2 5.left/right : " + v(n5.left) + "/" + v(n5.right) + " (Expected: 3/6)");
        System.out.println("Test 2 3.left/right : " + v(n3.left) + "/" + v(n3.right) + " (Expected: null/1)");
        System.out.println("Test 2 3.parent     : " + v(n3.parent) + " (Expected: 5)");
    }
}
