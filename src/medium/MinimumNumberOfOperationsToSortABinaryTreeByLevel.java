package medium;

import common.TreeNode;

import java.util.*;

// https://leetcode.com/problems/minimum-number-of-operations-to-sort-a-binary-tree-by-level/
public class MinimumNumberOfOperationsToSortABinaryTreeByLevel {
    public int minimumOperations(TreeNode root) {
        int result = 0;
        Queue<TreeNode> Q = new ArrayDeque<>();
        if (root.left != null)
            Q.add(root.left);
        if (root.right != null)
            Q.add(root.right);
        while (!Q.isEmpty()) {
            int size = Q.size();
            int[][] current = new int[size][2];
            int index = 0;
            while (size-- > 0) {
                TreeNode node = Q.poll();
                current[index][0] = node.val;
                current[index][1] = index;
                index++;
                if (node.left != null)
                    Q.add(node.left);
                if (node.right != null)
                    Q.add(node.right);
            }
            int length = current.length;
            Arrays.sort(current, (a, b) -> a[0] - b[0]);
            boolean[] visited = new boolean[length];
            for (int i = 0; i < length; i++) {
                if (visited[i] || current[i][1] == i)
                    continue;
                int currIndex = i;
                int count = 0;
                while (!visited[currIndex]) {
                    visited[currIndex] = true;
                    currIndex = current[currIndex][1];
                    count++;
                }
                result += count - 1;
            }
        }
        return result;
    }

    /*
     * Revision Note — Minimum Number of Operations to Sort a Binary Tree by Level (Medium)
     * Pattern: BFS level collection + minimum swaps via cycle detection on a permutation
     * Key Insight: Build (value, originalIndex) pairs, sort by value — pairs[i][1] defines
     *              a permutation where each cycle of length k needs exactly k-1 swaps.
     * Gotchas:
     *   - Must update HashMap after every swap if using the swap approach (stale map = wrong cycles)
     *   - Fixed points (pairs[i][1] == i) are safe to skip without marking visited
     *   - ArrayDeque >> LinkedList for BFS queue performance
     *   - (a,b) -> a[0]-b[0] is faster than Comparator.comparingInt for int[][] sorting
     */
    public static void main(String[] args) {
        MinimumNumberOfOperationsToSortABinaryTreeByLevel M = new MinimumNumberOfOperationsToSortABinaryTreeByLevel();

        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(4);
        root1.right = new TreeNode(3);
        root1.left.left = new TreeNode(7);
        root1.left.right = new TreeNode(6);
        root1.right.left = new TreeNode(8);
        root1.right.right = new TreeNode(5);
        root1.right.left.left = new TreeNode(9);
        root1.right.right.left = new TreeNode(10);
        System.out.println("Test 1: " + M.minimumOperations(root1) + " (Expected: 3)");

        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(3);
        root2.right = new TreeNode(2);
        root2.left.left = new TreeNode(7);
        root2.left.right = new TreeNode(6);
        root2.right.left = new TreeNode(5);
        root2.right.right = new TreeNode(4);
        System.out.println("Test 2: " + M.minimumOperations(root2) + " (Expected: 3)");

        TreeNode root3 = new TreeNode(1);
        root3.left = new TreeNode(2);
        root3.right = new TreeNode(3);
        root3.left.left = new TreeNode(4);
        root3.left.right = new TreeNode(5);
        root3.right.left = new TreeNode(6);
        System.out.println("Test 3: " + M.minimumOperations(root3) + " (Expected: 0)");

        System.out.println("Test 4: " + M.minimumOperations(new TreeNode(1)) + " (Expected: 0)");
    }
}
