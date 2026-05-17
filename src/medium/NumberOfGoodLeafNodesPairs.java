package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/
public class NumberOfGoodLeafNodesPairs {
    int result;
    public int countPairs(TreeNode root, int distance) {
        result = 0;
        dfs(root, distance);
        return result;
    }

    private List<Integer> dfs(TreeNode root, int distance) {
        if (root == null)
            return new ArrayList<>();
        if (root.left == null && root.right == null)
            return List.of(1);
        List<Integer> leftNodes = dfs(root.left, distance);
        List<Integer> rightNodes = dfs(root.right, distance);
        List<Integer> mergedNodes = new ArrayList<>();
        for (int leftNode : leftNodes)
            if (leftNode < distance)
                mergedNodes.add(leftNode + 1);
        for (int rightNode : rightNodes)
            if (rightNode < distance)
                mergedNodes.add(rightNode + 1);
        for (int leftNode : leftNodes) {
            for (int rightNode : rightNodes) {
                if (leftNode + rightNode <= distance)
                    result++;
            }
        }
        return mergedNodes;
    }

    /*
     * Revision Note — Number of Good Leaf Nodes Pairs (Medium)
     * Pattern: Bottom-up DFS returning list of leaf depths from the subtree
     * Key Insight: At each node (potential LCA), pair every left-subtree leaf depth with every
     *              right-subtree leaf depth — if dl + dr <= distance, it's a good pair.
     *              Push depths up incremented by 1; prune values >= distance (can never pair).
     * Gotchas:
     *   - Return empty list (not null) for null children to avoid null guards
     *   - Count BEFORE incrementing (current-level pairing uses raw child depths)
     *   - Increment by 1 when adding to merged list (depth from current node, not child)
     *   - Prune depths >= distance from merged list — they can never form a good pair
     */
    public static void main(String[] args) {
        NumberOfGoodLeafNodesPairs N = new NumberOfGoodLeafNodesPairs();

        // Example 1: root=[1,2,3,null,4], distance=3 → leaves 3 and 4, path=3
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.right = new TreeNode(3);
        root1.left.right = new TreeNode(4);
        System.out.println("Test 1: " + N.countPairs(root1, 3) + " (Expected: 1)");

        // Example 2: perfect tree [1,2,3,4,5,6,7], distance=3 → (4,5) and (6,7)
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.right = new TreeNode(3);
        root2.left.left = new TreeNode(4);
        root2.left.right = new TreeNode(5);
        root2.right.left = new TreeNode(6);
        root2.right.right = new TreeNode(7);
        System.out.println("Test 2: " + N.countPairs(root2, 3) + " (Expected: 2)");

        // Example 3: [7,1,4,6,null,5,3,null,null,null,null,null,2], distance=3 → only (5,2)
        TreeNode root3 = new TreeNode(7);
        root3.left = new TreeNode(1);
        root3.right = new TreeNode(4);
        root3.left.left = new TreeNode(6);
        root3.right.left = new TreeNode(5);
        root3.right.right = new TreeNode(3);
        root3.right.right.right = new TreeNode(2);
        System.out.println("Test 3: " + N.countPairs(root3, 3) + " (Expected: 1)");

        // Single leaf: no pairs possible
        System.out.println("Test 4: " + N.countPairs(new TreeNode(1), 5) + " (Expected: 0)");

        // distance too small: same Example 2 tree but distance=1 → no pairs (siblings are at path 2)
        TreeNode root5 = new TreeNode(1);
        root5.left = new TreeNode(2);
        root5.right = new TreeNode(3);
        root5.left.left = new TreeNode(4);
        root5.left.right = new TreeNode(5);
        System.out.println("Test 5: " + N.countPairs(root5, 1) + " (Expected: 0)");

        // Linear tree (right-skewed): only one leaf → no pairs
        TreeNode root6 = new TreeNode(1);
        root6.right = new TreeNode(2);
        root6.right.right = new TreeNode(3);
        root6.right.right.right = new TreeNode(4);
        System.out.println("Test 6: " + N.countPairs(root6, 10) + " (Expected: 0)");
    }
}
