package hard;

import common.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;


// https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/
public class VerticalOrderTraversalOfABinaryTree {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        TreeMap<Integer, List<int[]>> matrix = new TreeMap<>();
        fill(root, 0, 0, matrix);
        List<List<Integer>> result = new ArrayList<>();
        for (int column : matrix.keySet()) {
            List<int[]> nodes = matrix.get(column);
            Collections.sort(nodes, (a, b) -> {
                if (a[1] == b[1])
                    return a[0] - b[0];
                return a[1] - b[1];
            });
            List<Integer> columnNodes = new ArrayList<>();
            int size = nodes.size();
            for (int i = 0; i < size; i++)
                columnNodes.add(nodes.get(i)[0]);
            result.add(columnNodes);
        }
        return result;
    }

    private void fill(TreeNode root, int column, int level, TreeMap<Integer, List<int[]>> matrix) {
        if (root == null)
            return;
        matrix.computeIfAbsent(column, k -> new ArrayList<>()).add(new int[]{root.val, level});
        fill(root.left, column - 1, level + 1,  matrix);
        fill(root.right, column + 1, level + 1, matrix);
    }

    /*
     * Revision Note - Vertical Order Traversal of a Binary Tree (Hard)
     *
     * Pattern: coordinate tagging + multi-key sort. DFS tags every node with (col, row); a TreeMap
     * keyed by column gives left-to-right order for free, then each column sorts by (row, value).
     *
     * Key Insight: the output order is fully defined by three keys, (col, row, val), so it does NOT
     * depend on the traversal order. Record the coordinates, sort by them, and any DFS or BFS works.
     *
     * Gotchas:
     * - Traversal order is not row order: a preorder DFS reaches a deep node in the left subtree
     *   before a shallow node in the right subtree of the same column (Test 6: 1 is visited before
     *   15). The row key is what fixes it.
     * - Value is only a TIE-BREAK for the same (row, col): sorting a column by value alone breaks
     *   Test 6, where the smallest value is deepest.
     * - Duplicate values at the same position are both kept (Test 7).
     * - Columns can be negative: a TreeMap (or an offset of n into an array) handles that; a plain
     *   HashMap loses the order.
     * - Comparator subtraction (a[1] - b[1]) is safe here, since rows <= 1000 and values <= 1000;
     *   use Integer.compare when the keys can be large.
     *
     * Complexity: O(n log n) time (TreeMap inserts + per-column sorts), O(n) space, O(h) stack.
     *
     * Alternative (one sort): collect {col, row, val} for every node into one list, sort once by
     * (col, row, val), then group consecutive equal cols. Same O(n log n), no TreeMap. Or a BFS
     * gives row order for free, so each column only needs sorting within a row.
     *
     * Template:
     *   dfs(node, col, row): map[col].add({val, row}); dfs(left, col - 1, row + 1); dfs(right, col + 1, row + 1)
     *   for col in sorted(map): sort map[col] by (row, val); emit the vals
     */

    public static void main(String[] args) {
        VerticalOrderTraversalOfABinaryTree V = new VerticalOrderTraversalOfABinaryTree();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + V.verticalTraversal(TreeNode.createTree(3, 9, 20, null, null, 15, 7)) + " (Expected: [[9], [3, 15], [20], [7]])");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + V.verticalTraversal(TreeNode.createTree(1, 2, 3, 4, 5, 6, 7)) + " (Expected: [[4], [2], [1, 5, 6], [3], [7]])");

        // Test 3: LeetCode example 3
        System.out.println("Test 3: " + V.verticalTraversal(TreeNode.createTree(1, 2, 3, 4, 6, 5, 7)) + " (Expected: [[4], [2], [1, 5, 6], [3], [7]])");

        // Test 4: single node: one column, one value
        System.out.println("Test 4: " + V.verticalTraversal(TreeNode.createTree(5)) + " (Expected: [[5]])");

        // Test 5: inner grandchildren meet at (2, 0): the larger value 7 is visited first, so the same-position tie must sort by value
        System.out.println("Test 5: " + V.verticalTraversal(TreeNode.createTree(1, 2, 3, null, 7, 4)) + " (Expected: [[2], [1, 4, 7], [3]])");

        // Test 6: left zigzag back into column 0: DFS visits 1 (row 4) before 15 (row 2), and sorting by value alone
        // would also put 1 first, so only the row key gives [10, 15, 1]
        System.out.println("Test 6: " + V.verticalTraversal(TreeNode.createTree(10, 8, 20, 6, null, 15, null, null, 7, null, null, null, 1)) + " (Expected: [[6], [8, 7], [10, 15, 1], [20]])");

        // Test 7: duplicate values at the same position: both are kept
        System.out.println("Test 7: " + V.verticalTraversal(TreeNode.createTree(1, 2, 3, null, 4, 4)) + " (Expected: [[2], [1, 4, 4], [3]])");
    }
}
