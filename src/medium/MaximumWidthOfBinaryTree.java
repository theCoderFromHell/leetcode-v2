package medium;

import common.TreeNode;

import java.util.*;

// https://leetcode.com/problems/maximum-width-of-binary-tree/
public class MaximumWidthOfBinaryTree {
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null)
            return 0;
        if (root.left == null && root.right == null)
            return 1;
        List<int[]> nodes = new ArrayList<>();
        fill(root, 1, 0, nodes);
        HashMap<Integer,Integer> levels = new HashMap<>();
        int size = nodes.size();
        int maxNodesInLevel = 1;
        for (int i = 0; i < size; i++) {
            int index = nodes.get(i)[0];
            int level = nodes.get(i)[1];
            if (!levels.containsKey(level))
                levels.put(level, index);
            else {
                maxNodesInLevel = Math.max(maxNodesInLevel, index - levels.get(level) + 1);
            }
        }
        return maxNodesInLevel;
    }

    private void fill(TreeNode root, int index, int level, List<int[]> nodes) {
        if (root == null)
            return;
        nodes.add(new int[]{index, level});
        fill(root.left, 2 * index, level + 1, nodes);
        fill(root.right, 2 * index + 1, level+1, nodes);
    }

    /*
     * Revision Note - Maximum Width of Binary Tree (Medium)
     *
     * Pattern: heap numbering + per-level first index. Number nodes as if the tree were complete
     * (root 1, children 2i and 2i + 1); a level's width is lastIndex - firstIndex + 1, which counts
     * the missing nodes in between without ever storing them.
     *
     * Key Insight: a preorder DFS (left before right) reaches each level's LEFTMOST node first.
     * Store that first index per level, and every later node on the level is a candidate right end.
     *
     * Gotchas:
     * - Never derive the level from the index (log2). The index overflows past depth 30, wraps
     *   negative, and the level falls apart: carry the level down explicitly (first attempt crashed
     *   on Test 8).
     * - Overflow itself is harmless here: both indices in a subtraction are on the SAME level, and
     *   wrapping int subtraction is exact mod 2^32. The problem promises the true width fits in an
     *   int, so the wrapped difference is the true one. What breaks is anything that uses the
     *   index's magnitude or order: sorting by it, comparing with <, log2. Switching to long only
     *   moves the wall to depth 63, and 3000 nodes go much deeper.
     * - Count the first node of a level too: a lone node is width 1. Updating max only on a
     *   level's second node returned 0 for every chain, [1] included (Tests 4, 9).
     * - Off-by-one between where fill() starts the levels and where the loop starts crashed Test 1.
     *
     * Complexity: O(n) time (one DFS, one pass, O(1) map ops), O(n) space for the node list and
     * map, plus O(h) recursion stack, h <= 3000.
     *
     * Alternative (BFS, renormalised): queue of (node, index); per level, subtract the level's
     * first index before computing children (2 * (i - first), 2 * (i - first) + 1). The indices
     * stay below the level's width, so no overflow at all, and no list or map.
     *
     * Template:
     *   dfs(node, idx, lvl): first.putIfAbsent(lvl, idx); best = max(best, idx - first[lvl] + 1)
     *                        dfs(left, 2 * idx, lvl + 1); dfs(right, 2 * idx + 1, lvl + 1)
     */

    public static void main(String[] args) {
        MaximumWidthOfBinaryTree M = new MaximumWidthOfBinaryTree();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + M.widthOfBinaryTree(TreeNode.createTree(1, 3, 2, 5, 3, null, 9)) + " (Expected: 4)");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + M.widthOfBinaryTree(TreeNode.createTree(1, 3, 2, 5, null, null, 9, 6, null, 7)) + " (Expected: 7)");

        // Test 3: LeetCode example 3
        System.out.println("Test 3: " + M.widthOfBinaryTree(TreeNode.createTree(1, 3, 2, 5)) + " (Expected: 2)");

        // Test 4: single node: a lone node is width 1 (the max-only-on-second-node bug returned 0)
        System.out.println("Test 4: " + M.widthOfBinaryTree(TreeNode.createTree(1)) + " (Expected: 1)");

        // Test 5: outermost grandchildren only: the two null slots between them count, so 4 not 2
        System.out.println("Test 5: " + M.widthOfBinaryTree(TreeNode.createTree(1, 2, 3, 4, null, null, 5)) + " (Expected: 4)");

        // Test 6: full tree with one extra node below: the widest level is not the deepest one
        System.out.println("Test 6: " + M.widthOfBinaryTree(TreeNode.createTree(1, 2, 3, 4, 5, 6, 7, 8)) + " (Expected: 4)");

        // Test 7: inner grandchildren only: adjacent heap indices 5 and 6, so 2, not the full level of 4
        System.out.println("Test 7: " + M.widthOfBinaryTree(TreeNode.createTree(1, 2, 3, null, 4, 5)) + " (Expected: 2)");

        // Test 8: right chain 100 levels deep, two children at the bottom: the index wraps past depth 30,
        // so the level must be carried down, not read from log2(index) (first attempt crashed here)
        Integer[] chain = new Integer[201];
        chain[0] = 0;
        for (int i = 1; i < 100; i++)
            chain[2 * i] = i;
        chain[199] = 100;
        chain[200] = 101;
        System.out.println("Test 8: " + M.widthOfBinaryTree(TreeNode.createTree(chain)) + " (Expected: 2)");

        // Test 9: two-node chain: every level has one node, so the else branch never runs and max must start at 1
        System.out.println("Test 9: " + M.widthOfBinaryTree(TreeNode.createTree(1, 2)) + " (Expected: 1)");

        // Test 10: right chain 60 levels deep, then a full 3-level subtree: wrapped indices on a wide level
        // still subtract to the true width 8, since both ends of the subtraction are on the same level
        Integer[] deep = new Integer[2 * 60 + 13];
        deep[0] = 0;
        for (int i = 1; i < 60; i++)
            deep[2 * i] = i;
        for (int i = 2 * 60 - 1, v = 60; i < deep.length; i++)
            deep[i] = v++;
        System.out.println("Test 10: " + M.widthOfBinaryTree(TreeNode.createTree(deep)) + " (Expected: 8)");
    }
}
