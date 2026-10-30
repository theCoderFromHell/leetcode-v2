package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

// https://leetcode.com/problems/closest-nodes-queries-in-a-binary-search-tree/
public class ClosestNodesQueriesInABinarySearchTree {
    public List<List<Integer>> closestNodes(TreeNode root, List<Integer> queries) {
        List<Integer> values = new ArrayList<>();
        storeNodes(root, values);
        int size = values.size();
        List<List<Integer>> result = new ArrayList<>();
        for (int query : queries) {
            int index = Collections.binarySearch(values, query);
            if (index >= 0) {
                result.add(List.of(query, query));
                continue;
            }
            int insertAt = -index - 1;
            int min = insertAt > 0 ? values.get(insertAt - 1) : -1;
            int max = insertAt < size ? values.get(insertAt) : -1;
            result.add(List.of(min, max));
        }
        return result;
    }

    private void storeNodes(TreeNode root, List<Integer> values) {
        if (root == null)
            return;
        storeNodes(root.left, values);
        values.add(root.val);
        storeNodes(root.right, values);
    }

    public List<List<Integer>> closestNodesV2(TreeNode root, List<Integer> queries) {
        TreeSet<Integer> values = new TreeSet<>();
        storeNodes(root, values);
        int size = queries.size();
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Integer min = values.floor(queries.get(i));
            if (min == null)
                min = -1;
            Integer max = values.ceiling(queries.get(i));
            if (max == null)
                max = -1;
            result.add(List.of(min, max));
        }
        return result;
    }

    private void storeNodes(TreeNode root, TreeSet<Integer> values) {
        if (root == null)
            return;
        storeNodes(root.left, values);
        values.add(root.val);
        storeNodes(root.right, values);
    }

    /*
     * Revision Note - Closest Nodes Queries in a Binary Search Tree (Medium)
     *
     * Pattern: flatten once, query many. An inorder walk of a BST yields its values already sorted,
     * so collect them into an ArrayList and answer each query by binary search.
     *
     * Key Insight: the tree is NOT guaranteed balanced, so walking it per query is O(height) = O(n)
     * on a chain, and 1e5 queries x 1e5 depth = 1e10. Flattening costs O(n) once, and every query
     * after that is O(log n) whatever the tree's shape.
     *
     * Gotchas:
     * - Collections.binarySearch returns the index on a hit, so an exact match is [q, q] (Tests 3, 5).
     *   On a miss it returns -(insertion point) - 1: decode with insertAt = -index - 1. Then
     *   values[insertAt - 1] is the floor and values[insertAt] is the ceiling.
     * - Either neighbour can fall off the end: insertAt == 0 means below the minimum, [-1, x];
     *   insertAt == size means above the maximum, [x, -1] (Tests 2, 4, 6).
     * - The list is sorted ONLY because the walk is inorder. A preorder or BFS fill would need a sort.
     * - The recursion goes as deep as the tree is tall: a 1e5-node chain overflows the default local
     *   JVM stack. LeetCode runs it with a bigger stack; if it ever fails there, use an iterative
     *   inorder with an explicit stack.
     *
     * Complexity: O(n + q log n) time (no sorting; the inorder walk is linear), O(n) space plus
     * O(height) recursion.
     *
     * Alternative (closestNodesV2): fill a TreeSet and use floor / ceiling, which are inclusive
     * (<= / >=; lower / higher would miss exact hits) and return null where -1 is needed. They hide
     * the insertion-point arithmetic, but the build is O(n log n), each value costs a tree node, and
     * the inorder walk is wasted since the set re-sorts anyway.
     *
     * Template:
     *   sorted = inorder(root)
     *   for q: i = binarySearch(sorted, q); if i >= 0 -> [q, q]
     *          else ins = -i - 1 -> [ins > 0 ? sorted[ins - 1] : -1, ins < n ? sorted[ins] : -1]
     */

    public static void main(String[] args) {
        ClosestNodesQueriesInABinarySearchTree C = new ClosestNodesQueriesInABinarySearchTree();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + C.closestNodes(TreeNode.createTree(6, 2, 13, 1, 4, 9, 15, null, null, null, null, null, null, 14), Arrays.asList(2, 5, 16)) + " (Expected: [[2, 2], [4, 6], [15, -1]])");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + C.closestNodes(TreeNode.createTree(4, null, 9), Arrays.asList(3)) + " (Expected: [[-1, 4]])");

        // Test 3: every query is an exact hit (min, max, root): binarySearch finds it, so each answer is [q, q]
        System.out.println("Test 3: " + C.closestNodes(TreeNode.createTree(2, 1, 3), Arrays.asList(1, 3, 2)) + " (Expected: [[1, 1], [3, 3], [2, 2]])");

        // Test 4: right-skewed chain (height = n, the case that makes per-query tree walks O(n)); insertion point 0 and size both appear
        System.out.println("Test 4: " + C.closestNodes(TreeNode.createTree(2, null, 4, null, 6, null, 8), Arrays.asList(1, 3, 8, 9)) + " (Expected: [[-1, 2], [2, 4], [8, 8], [8, -1]])");

        // Test 5: repeated query; 11 falls between the root (10) and the right subtree's minimum (12), across subtrees; max query value 1e6
        System.out.println("Test 5: " + C.closestNodes(TreeNode.createTree(10, 5, 15, 3, 7, 12, 20), Arrays.asList(7, 7, 11, 1000000)) + " (Expected: [[7, 7], [7, 7], [10, 12], [20, -1]])");

        // Test 6: smallest legal tree (2 nodes) at the value bounds 1 and 1e6, with a query in the wide gap between them
        System.out.println("Test 6: " + C.closestNodes(TreeNode.createTree(1, null, 1000000), Arrays.asList(1, 500000, 1000000)) + " (Expected: [[1, 1], [1, 1000000], [1000000, 1000000]])");

        // V2 (TreeSet floor / ceiling) on the same inputs
        System.out.println("Test 1 (V2): " + C.closestNodesV2(TreeNode.createTree(6, 2, 13, 1, 4, 9, 15, null, null, null, null, null, null, 14), Arrays.asList(2, 5, 16)) + " (Expected: [[2, 2], [4, 6], [15, -1]])");
        System.out.println("Test 2 (V2): " + C.closestNodesV2(TreeNode.createTree(4, null, 9), Arrays.asList(3)) + " (Expected: [[-1, 4]])");
        System.out.println("Test 3 (V2): " + C.closestNodesV2(TreeNode.createTree(2, 1, 3), Arrays.asList(1, 3, 2)) + " (Expected: [[1, 1], [3, 3], [2, 2]])");
        System.out.println("Test 4 (V2): " + C.closestNodesV2(TreeNode.createTree(2, null, 4, null, 6, null, 8), Arrays.asList(1, 3, 8, 9)) + " (Expected: [[-1, 2], [2, 4], [8, 8], [8, -1]])");
        System.out.println("Test 5 (V2): " + C.closestNodesV2(TreeNode.createTree(10, 5, 15, 3, 7, 12, 20), Arrays.asList(7, 7, 11, 1000000)) + " (Expected: [[7, 7], [7, 7], [10, 12], [20, -1]])");
        System.out.println("Test 6 (V2): " + C.closestNodesV2(TreeNode.createTree(1, null, 1000000), Arrays.asList(1, 500000, 1000000)) + " (Expected: [[1, 1], [1, 1000000], [1000000, 1000000]])");
    }
}
