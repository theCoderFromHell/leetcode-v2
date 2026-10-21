package medium;

import common.TreeNode;

// https://leetcode.com/problems/split-bst/
public class SplitBST {
    public TreeNode[] splitBST(TreeNode root, int target) {
        if (root == null)
            return new TreeNode[2];
        if (root.val <= target) {
            TreeNode[] right = splitBST(root.right, target);
            root.right = right[0];
            return new TreeNode[]{root, right[1]};
        } else {
            TreeNode[] left = splitBST(root.left, target);
            root.left = left[1];
            return new TreeNode[]{left[0], root};
        }
    }

    /*
     * Revision Note - Split BST (Medium)
     *
     * Pattern: recursion down ONE root-to-leaf path steered by BST order (same walk as 235 LCA of
     * a BST). Each call returns [small, big] for its subtree, and the caller stitches one half back
     * into the slot it recursed into.
     *
     * Key Insight: if root.val <= target, root and its whole left subtree are already "small";
     * only root.right is mixed. Split it: its small half becomes root.right, and its big half IS
     * the big tree. Mirror for root.val > target. Only nodes on the search path get a new child,
     * so a node changes parent exactly when its parent landed on the other side (3 in example 1).
     *
     * Gotchas:
     * - The half that reattaches is the one on root's OWN side: small root takes right[0], big
     *   root takes left[1]. The other half is passed straight up.
     * - target need not be in the tree, and can be below or above every value: one side is null.
     * - Base case null -> [null, null] (new TreeNode[2] is exactly that).
     * - <= vs >: values equal to target go to the small side.
     * - In place: the input tree is relinked, no new nodes.
     *
     * Complexity: O(h) time (one path), O(h) recursion stack. h <= n = 50 when skewed.
     *
     * Alternative (iterative, O(1) space): two dummy tails. Walk down; a small node hangs on
     * smallTail.right and you go right, a big node hangs on bigTail.left and you go left; at the
     * end cut smallTail.right and bigTail.left.
     *
     * Template:
     *   split(node): if node == null: return [null, null]
     *     if node.val <= t: [s, b] = split(node.right); node.right = s; return [node, b]
     *     else:             [s, b] = split(node.left);  node.left  = b; return [s, node]
     */

    // Reads the pair of roots as [[small],[big]].
    private static String show(TreeNode[] roots) {
        return "[" + TreeNode.serialize(roots[0]) + "," + TreeNode.serialize(roots[1]) + "]";
    }

    public static void main(String[] args) {
        SplitBST S = new SplitBST();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + show(S.splitBST(TreeNode.createTree(4, 2, 6, 1, 3, 5, 7), 2)) + " (Expected: [[2,1],[4,3,6,null,null,5,7]])");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + show(S.splitBST(TreeNode.createTree(1), 1)) + " (Expected: [[1],[]])");

        // Test 3: path alternates left and right: 4 moves from under 6 to under 3, and 6 from under 3 to under 8
        System.out.println("Test 3: " + show(S.splitBST(TreeNode.createTree(8, 3, 10, 1, 6, null, null, null, null, 4, 7), 5)) + " (Expected: [[3,1,4],[8,6,10,null,7]])");

        // Test 4: target below every value: small side is empty
        System.out.println("Test 4: " + show(S.splitBST(TreeNode.createTree(8, 3, 10, 1, 6, null, null, null, null, 4, 7), 0)) + " (Expected: [[],[8,3,10,1,6,null,null,null,null,4,7]])");

        // Test 5: target above every value: big side is empty
        System.out.println("Test 5: " + show(S.splitBST(TreeNode.createTree(8, 3, 10, 1, 6, null, null, null, null, 4, 7), 1000)) + " (Expected: [[8,3,10,1,6,null,null,null,null,4,7],[]])");

        // Test 6: single node, target below it: the null base case fills the small side
        System.out.println("Test 6: " + show(S.splitBST(TreeNode.createTree(5), 0)) + " (Expected: [[],[5]])");

        // Test 7: target equals the root: equal goes small, so the root's right subtree becomes the big tree unchanged
        System.out.println("Test 7: " + show(S.splitBST(TreeNode.createTree(4, 2, 6, 1, 3, 5, 7), 4)) + " (Expected: [[4,2,null,1,3],[6,5,7]])");

        // Test 8: right-skewed tree: recursion depth n, the chain is cut in the middle
        System.out.println("Test 8: " + show(S.splitBST(TreeNode.createTree(1, null, 2, null, 3, null, 4), 2)) + " (Expected: [[1,null,2],[3,null,4]])");
    }
}
