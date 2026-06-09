package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// https://leetcode.com/problems/print-binary-tree/
public class PrintBinaryTree {
    public List<List<String>> printTree(TreeNode root) {
        int height = getHeight(root);
        int[] powers = new int[height + 1];
        powers[0] = 1;
        for (int i = 1; i <= height; i++)
            powers[i] = 2 * powers[i - 1];
        int rows = height;
        int columns = powers[height] - 1;
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < rows; i++)
            result.add(new ArrayList<>(Collections.nCopies(columns, "")));
        fillGrid(root, result, 0, (columns-1)/2, powers, height);
        return result;
    }

    private void fillGrid(TreeNode root, List<List<String>> result, int row, int column, int[] powers, int height) {
        if (root == null)
            return;
        result.get(row).set(column, String.valueOf(root.val));
        if (row < height - 1) {
            fillGrid(root.left, result, row + 1, column - powers[height - row - 2], powers, height);
            fillGrid(root.right, result, row + 1, column + powers[height - row - 2], powers, height);
        }
    }

    private int getHeight(TreeNode root) {
        if (root == null)
            return 0;
        int left = getHeight(root.left);
        int right = getHeight(root.right);
        return 1 + Math.max(left, right);
    }

    /*
     * Revision Note — Print Binary Tree (Medium)
     * Pattern: Pre-order DFS with precomputed powers-of-2 offset table
     * Key Insight: At row r in a tree of height h, the column offset from parent to child
     *              is 2^(h - r - 2) — halves at every level. Root sits at the true midpoint
     *              and each subtree splits its half-range symmetrically.
     * Gotchas:
     *   - powers[height - row - 2] is evaluated as an argument BEFORE the null base-case fires.
     *     At the deepest row (row = height-1), the index goes to -1 → AIOOB.
     *     Guard with `if (row < height - 1)` at the caller before any recursive call.
     *   - columns = 2^height - 1, NOT 2^(height-1) - 1
     *   - Root starts at column (columns - 1) / 2 = 2^(height-1) - 1
     */
    public static void main(String[] args) {
        PrintBinaryTree P = new PrintBinaryTree();

        // Example 1: [1,2,3,null,4] height=3, 7 cols → 4 at col 2
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.right = new TreeNode(4);
        System.out.println("Test 1: " + P.printTree(root1) + " (Expected: [[, , , 1, , , ], [, 2, , , , 3, ], [, , 4, , , , ]])");

        // Example 2: [1,2,5,3,null,null,null,4] height=4, 15 cols
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(5);
        root2.left.left = new TreeNode(3);
        root2.left.left.left = new TreeNode(4);
        System.out.println("Test 2: " + P.printTree(root2) + " (Expected: 4 at col 0, 3 at col 1, 2 at col 3, 1 at col 7, 5 at col 11)");

        // Single node: height=1 → 1×1 grid
        System.out.println("Test 3: " + P.printTree(new TreeNode(1)) + " (Expected: [[1]])");

        // Left-skewed chain height=3: 1→2→3, nodes at cols 3,1,0
        TreeNode root4 = new TreeNode(1);
        root4.left = new TreeNode(2);
        root4.left.left = new TreeNode(3);
        System.out.println("Test 4: " + P.printTree(root4) + " (Expected: [[, , , 1, , , ], [, 2, , , , , ], [3, , , , , , ]])");

        // Right-skewed chain height=3: 1→2→3, nodes at cols 3,5,6
        TreeNode root5 = new TreeNode(1);
        root5.right = new TreeNode(2);
        root5.right.right = new TreeNode(3);
        System.out.println("Test 5: " + P.printTree(root5) + " (Expected: [[, , , 1, , , ], [, , , , , 2, ], [, , , , , , 3]])");
    }
}
