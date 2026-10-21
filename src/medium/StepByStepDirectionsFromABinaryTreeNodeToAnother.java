package medium;

import common.TreeNode;

// https://leetcode.com/problems/step-by-step-directions-from-a-binary-tree-node-to-another/
public class StepByStepDirectionsFromABinaryTreeNodeToAnother {
    public String getDirections(TreeNode root, int startValue, int destValue) {
        TreeNode lca = LCA(root, startValue, destValue);
        StringBuilder startPath = new StringBuilder();
        findStartPath(lca, startValue, startPath);
        StringBuilder destPath = new StringBuilder();
        findDestPath(lca, destValue, destPath);
        return startPath.append(destPath).toString();
    }

    private boolean findStartPath(TreeNode root, int startValue, StringBuilder startPath) {
        if (root == null)
            return false;
        if (root.val == startValue)
            return true;
        startPath.append("U");
        boolean left = findStartPath(root.left, startValue, startPath);
        if (left)
            return left;
        boolean right = findStartPath(root.right, startValue, startPath);
        if (!right)
            startPath.deleteCharAt(startPath.length() - 1);
        return right;
    }

    private boolean findDestPath(TreeNode root, int destValue, StringBuilder destPath) {
        if (root == null)
            return false;
        if (root.val == destValue)
            return true;
        destPath.append("L");
        boolean left = findDestPath(root.left, destValue, destPath);
        if (left)
            return left;
        destPath.deleteCharAt(destPath.length() - 1);
        destPath.append("R");
        boolean right = findDestPath(root.right, destValue, destPath);
        if (!right)
            destPath.deleteCharAt(destPath.length() - 1);
        return right;
    }

    private TreeNode LCA(TreeNode root, int startValue, int destValue) {
        if (root == null || root.val == startValue || root.val == destValue)
            return root;
        TreeNode left = LCA(root.left, startValue, destValue);
        TreeNode right = LCA(root.right, startValue, destValue);
        if (left != null && right != null)
            return root;
        return  (left != null) ? left : right;
    }

    /*
     * Revision Note - Step-By-Step Directions From a Binary Tree Node to Another (Medium)
     *
     * Pattern: LCA of a binary tree (236) + root-to-node path recording with backtracking.
     *
     * Key Insight: the shortest path in a tree goes UP from start to the LCA, then only DOWN to
     * dest. The up-leg has no letters to choose: it is just 'U' x depth(start below LCA). Only the
     * down-leg needs real L/R directions.
     *
     * Gotchas:
     * - Either node can BE the LCA (one is an ancestor of the other). Both path searches then
     *   return true immediately with an empty path, so "" + "RR" or "UU" + "" falls out.
     * - Backtracking: append before recursing, delete the char when that subtree fails. Every
     *   failing call must undo exactly its own char.
     * - findStartPath keeps searching right even after left found start. It's still correct
     *   (values are unique, so the right side fails and undoes itself) but wastes a traversal:
     *   short-circuit with if (left) return true.
     * - n = 1e5 with a skewed tree means recursion depth 1e5. Passes on LeetCode, but a default
     *   local JVM stack overflows on that chain; iterative DFS or a parent map avoids it.
     *
     * Complexity: O(n) time (LCA pass + two path searches, each O(n) worst case), O(h) space
     * for recursion and the path builders, h up to n.
     *
     * Alternative (no LCA pass): record root->start and root->dest as L/R strings, drop their
     * longest common prefix; the answer is 'U' x (rest of start path) + rest of dest path.
     * Same O(n), two DFS calls instead of three.
     *
     * Template:
     *   lca = LCA(root, s, t)
     *   up   = "U" * depth of s below lca
     *   down = L/R path from lca to t (append, recurse, delete on failure)
     *   return up + down
     */

    public static void main(String[] args) {
        StepByStepDirectionsFromABinaryTreeNodeToAnother S = new StepByStepDirectionsFromABinaryTreeNodeToAnother();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + S.getDirections(TreeNode.createTree(5, 1, 2, 3, null, 6, 4), 3, 6) + " (Expected: UURL)");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + S.getDirections(TreeNode.createTree(2, 1), 2, 1) + " (Expected: L)");

        // Test 3: start is the root, so it is the LCA: no U steps, only the down-leg
        System.out.println("Test 3: " + S.getDirections(TreeNode.createTree(5, 1, 2, 3, null, 6, 4), 5, 4) + " (Expected: RR)");

        // Test 4: destination is the root, so it is the LCA: only U steps, empty down-leg
        System.out.println("Test 4: " + S.getDirections(TreeNode.createTree(5, 1, 2, 3, null, 6, 4), 6, 5) + " (Expected: UU)");

        // Test 5: two nodes, start is the right child: smallest up-only path
        System.out.println("Test 5: " + S.getDirections(TreeNode.createTree(1, null, 2), 2, 1) + " (Expected: U)");

        // Test 6: both nodes deep in the same subtree: the LCA is 2, not the root, so the path never reaches the root
        System.out.println("Test 6: " + S.getDirections(TreeNode.createTree(1, 2, 3, 4, 5, null, null, 6, 7, 8, 9), 6, 9) + " (Expected: UURR)");

        // Test 7: destination is an ancestor below the root: the LCA is an inner node and equals dest
        System.out.println("Test 7: " + S.getDirections(TreeNode.createTree(1, 2, 3, 4, 5, null, null, 6, 7, 8, 9), 8, 2) + " (Expected: UU)");

        // Test 8: start is an ancestor below the root: the LCA is an inner node and equals start
        System.out.println("Test 8: " + S.getDirections(TreeNode.createTree(1, 2, 3, 4, 5, null, null, 6, 7, 8, 9), 2, 9) + " (Expected: RR)");

        // Test 9: start in the LCA's RIGHT subtree, dest in its left: the start search fails left first and must undo its U
        System.out.println("Test 9: " + S.getDirections(TreeNode.createTree(1, 2, 3, 4, 5, null, null, 6, 7, 8, 9), 9, 6) + " (Expected: UULL)");
    }
}
