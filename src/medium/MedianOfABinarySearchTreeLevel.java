package medium;

import java.util.ArrayList;
import java.util.List;
import common.TreeNode;

public class MedianOfABinarySearchTreeLevel {
    public int levelMedian(TreeNode root, int level) {
            List<Integer> nodes = new ArrayList<>();
            collectNodes(root, 0, level, nodes);
            int size = nodes.size();
            if (size == 0)
                return -1;
            return nodes.get(size/2);
    }

    private void collectNodes(TreeNode root, int currLevel, int level, List<Integer> nodes) {
        if (root == null)
            return;
        if (currLevel == level) {
            nodes.add(root.val);
            return;
        }
        collectNodes(root.left, currLevel + 1, level, nodes);
        collectNodes(root.right, currLevel + 1, level, nodes);
    }

    /*
     * Revision Note — Median of a Binary Search Tree Level (Medium)
     *
     * Pattern: DFS to collect target level + BST sorted-order property
     *
     * Key Insight: In a BST, collecting nodes left-to-right at any level yields them
     * in sorted order (BST invariant propagates down), so no explicit sort is needed.
     * Median = nodes.get(size/2) — correct for both odd (exact middle) and even
     * (upper median = larger of two middle elements) per problem definition.
     *
     * Gotchas:
     * - size/2 is the UPPER median for even counts — matches this problem's definition
     * - BST sorted-order property at each level only holds for BSTs, not general binary trees
     * - Return -1 when target level doesn't exist (list stays empty)
     *
     * Template:
     *   collectNodes(root, currLevel, targetLevel, list):
     *     if null: return
     *     if currLevel == targetLevel: list.add(root.val); return
     *     recurse left and right with currLevel+1
     *   return list.get(list.size() / 2)
     */
    public static void main(String[] args) {
        MedianOfABinarySearchTreeLevel M = new MedianOfABinarySearchTreeLevel();

        //       4
        //      / \
        //     2   6
        //    / \ / \
        //   1  3 5  7
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);  root.right = new TreeNode(6);
        root.left.left = new TreeNode(1);  root.left.right = new TreeNode(3);
        root.right.left = new TreeNode(5); root.right.right = new TreeNode(7);

        System.out.println("Test 1: " + M.levelMedian(root, 0) + " (Expected: 4)");  // root level
        System.out.println("Test 2: " + M.levelMedian(root, 1) + " (Expected: 6)");  // [2,6] even → upper median
        System.out.println("Test 3: " + M.levelMedian(root, 2) + " (Expected: 5)");  // [1,3,5,7] even → upper median
        System.out.println("Test 4: " + M.levelMedian(root, 3) + " (Expected: -1)"); // level doesn't exist

        // single node
        TreeNode single = new TreeNode(9);
        System.out.println("Test 5: " + M.levelMedian(single, 0) + " (Expected: 9)");
    }
}
