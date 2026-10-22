package medium;

import common.TreeNode;

// https://leetcode.com/problems/find-distance-in-a-binary-tree/
public class FindDistanceInABinaryTree {
    public int findDistance(TreeNode root, int p, int q) {
        TreeNode LCA = lca(root, p, q);
        return distance(LCA, p, 0)[0] + distance(LCA, q, 0)[0];
    }

    private int[] distance(TreeNode root, int target, int distance) {
        if (root == null)
            return new int[]{0, 0};
        if (root.val == target)
            return new int[]{distance, 1};
        int[] left = distance(root.left, target, distance + 1);
        if (left[1] == 1)
            return left;
        int[] right = distance(root.right, target, distance + 1);
        if (right[1] == 1)
            return right;
        return new int[]{distance, 0};
    }

    private TreeNode lca(TreeNode root, int p, int q) {
        if (root == null || root.val == p || root.val == q)
            return root;
        TreeNode left = lca(root.left, p, q);
        TreeNode right = lca(root.right, p, q);
        if (left != null && right != null)
            return root;
        return left != null ? left : right;
    }

    /*
     * Revision Note - Find Distance in a Binary Tree (Medium, Premium)
     *
     * Pattern: LCA of a binary tree (236) + depth search from the LCA. The count-only version of
     * 2096: there the path string was 'U' x depth(p) + down path to q, here only its length matters.
     *
     * Key Insight: every path in a tree turns at the LCA, so
     * dist(p, q) = depth of p below the LCA + depth of q below the LCA.
     *
     * Gotchas:
     * - p == q is allowed: lca returns that node, and both depths are 0.
     * - One node can be the other's ancestor: then it IS the LCA and its own depth is 0.
     * - distance() needs a found flag (or a -1 sentinel): the depth alone can't tell "found at
     *   depth d" from "not in this subtree".
     * - Values go up to 1e9, so they are labels only; never index an array by value.
     *
     * Complexity: O(n) time (LCA pass + two depth searches, each O(n) worst case), O(h) stack,
     * h <= n = 1e4 when skewed (fine for the default JVM stack).
     *
     * Alternative (one pass): a DFS returning the distance from this node down to whichever of
     * p/q it contains, or -1. When both children return >= 0, this node is the LCA: the answer is
     * l + r + 2. When the node itself is p or q and a child contains the other one, the answer is
     * that child's value + 1.
     *
     * Template:
     *   lca = LCA(root, p, q)
     *   return depth(lca, p) + depth(lca, q)
     *   depth(node, t, d): null -> not found; node.val == t -> d; else left, then right with d + 1
     */

    public static void main(String[] args) {
        FindDistanceInABinaryTree F = new FindDistanceInABinaryTree();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + F.findDistance(TreeNode.createTree(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4), 5, 0) + " (Expected: 3)");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + F.findDistance(TreeNode.createTree(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4), 5, 7) + " (Expected: 2)");

        // Test 3: LeetCode example 3
        System.out.println("Test 3: " + F.findDistance(TreeNode.createTree(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4), 5, 5) + " (Expected: 0)");

        // Test 4: single-node tree, p == q == root: the LCA is the node itself and both depths are 0
        System.out.println("Test 4: " + F.findDistance(TreeNode.createTree(1), 1, 1) + " (Expected: 0)");

        // Test 5: p is the root, so it is the LCA: the answer is just q's depth
        System.out.println("Test 5: " + F.findDistance(TreeNode.createTree(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4), 3, 4) + " (Expected: 3)");

        // Test 6: leaves on opposite sides of the root: the search for 8 fails down the whole left side first
        System.out.println("Test 6: " + F.findDistance(TreeNode.createTree(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4), 7, 8) + " (Expected: 5)");

        // Test 7: right-skewed chain, values up to 1e9: the values are labels, not indices
        System.out.println("Test 7: " + F.findDistance(TreeNode.createTree(1000000000, null, 7, null, 999999999, null, 0), 0, 1000000000) + " (Expected: 3)");

        // Test 8: q is an ancestor of p, passed in that order: the LCA is q and its depth is 0
        System.out.println("Test 8: " + F.findDistance(TreeNode.createTree(3, 5, 1, 6, 2, 0, 8, null, null, 7, 4), 4, 5) + " (Expected: 2)");
    }
}
