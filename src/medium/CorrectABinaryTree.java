package medium;

import common.TreeNode;

import java.util.HashSet;

// https://leetcode.com/problems/correct-a-binary-tree/
public class CorrectABinaryTree {
    public TreeNode correctBinaryTree(TreeNode root) {
        HashSet<Integer> visited = new HashSet<>();
        correct(root, visited, null, null);
        return root;
    }

    private void correct(TreeNode root, HashSet<Integer> visited, TreeNode parent, Boolean leftChild) {
        if (root == null)
            return;
        visited.add(root.val);
        if (root.right != null && visited.contains(root.right.val)) {
            if (leftChild)
                parent.left = null;
            else
                parent.right = null;
            return;
        }
        correct(root.right, visited, root, false);
        correct(root.left, visited, root, true);
    }

    /*
     * Revision Note — Correct a Binary Tree (Medium)
     *
     * Pattern: DFS visiting RIGHT before LEFT, with a visited set
     *
     * Key Insight: The bad pointer always goes rightward within the same depth. Recursing
     * right-before-left means that for any two nodes at equal depth, the right one is visited
     * first — so when you reach the invalid node, its wrongly-targeted node is already in
     * `visited`. Detection becomes a single set lookup on root.right.
     *
     * Gotchas:
     * - MUST return immediately after detaching. root.right still points at toNode, whose
     *   whole subtree is already visited, so continuing the recursion fires a spurious
     *   detach at every level. The first one writes to the already-removed node (harmless,
     *   which is why both LeetCode examples still pass), but one level deeper it nulls a
     *   LIVE node's child. Needs toNode's subtree to be 2+ deep to show up
     * - Thread parent AND which-side down the recursion: you detect at the invalid node but
     *   must unlink from its parent, so the node alone is not enough
     * - visited keyed on val is safe only because values are guaranteed unique; keying on
     *   node identity is the more robust choice
     * - Boolean leftChild unboxes — safe here only because the root can never be the invalid
     *   node (nothing sits right of it at depth 0), so it is never null when read
     *
     * Template:
     *   correct(node, visited, parent, isLeftChild):
     *     if node == null: return
     *     visited.add(node.val)
     *     if node.right != null and node.right.val in visited:
     *       if isLeftChild: parent.left = null else parent.right = null
     *       return                                   // <- essential
     *     correct(node.right, visited, node, false)   // RIGHT first
     *     correct(node.left,  visited, node, true)
     */
    private static String preorder(TreeNode n) {
        if (n == null) return "#";
        return n.val + " " + preorder(n.left) + " " + preorder(n.right);
    }

    public static void main(String[] args) {
        CorrectABinaryTree C = new CorrectABinaryTree();

        //      1
        //     / \
        //    2   3        2.right wrongly -> 3
        TreeNode a = new TreeNode(1);
        a.left = new TreeNode(2);
        a.right = new TreeNode(3);
        a.left.right = a.right;
        System.out.println("Test 1: " + preorder(C.correctBinaryTree(a)) + "  (Expected: 1 # 3 # #)");

        //        8
        //      /   \
        //     3     1
        //    /     / \
        //   7     9   4      7.right wrongly -> 4
        //  /         / \
        // 2         5   6
        TreeNode b = new TreeNode(8);
        b.left = new TreeNode(3);   b.right = new TreeNode(1);
        b.left.left = new TreeNode(7);
        b.right.left = new TreeNode(9); b.right.right = new TreeNode(4);
        b.left.left.left = new TreeNode(2);
        b.right.right.left = new TreeNode(5); b.right.right.right = new TreeNode(6);
        b.left.left.right = b.right.right;
        System.out.println("Test 2: " + preorder(C.correctBinaryTree(b)) + "  (Expected: 8 3 # # 1 9 # # 4 5 # # 6 # #)");

        //        1
        //      /   \
        //     2     3
        //    /       \
        //   4         5      4.right wrongly -> 5
        //              \
        //               6
        //                \
        //                 7
        TreeNode c = new TreeNode(1);
        c.left = new TreeNode(2);  c.right = new TreeNode(3);
        c.left.left = new TreeNode(4);
        c.right.right = new TreeNode(5);
        c.right.right.right = new TreeNode(6);
        c.right.right.right.right = new TreeNode(7);
        c.left.left.right = c.right.right;
        System.out.println("Test 3: " + preorder(C.correctBinaryTree(c)) + "  (Expected: 1 2 # # 3 # 5 # 6 # 7 # #)");
    }
}
