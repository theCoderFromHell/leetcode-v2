package medium;

import common.TreeNode;
import java.util.HashMap;
import java.util.Map;

// https://leetcode.com/problems/pseudo-palindromic-paths-in-a-binary-tree/
public class PseudoPalindromicPathsInABinaryTree {
    int count = 0;
    public int pseudoPalindromicPaths (TreeNode root) {
        if (root == null)
            return 0;
        if (root.left == null && root.right == null)
            return 1;
        count = 0;
        Map<Integer, Integer> frequency = new HashMap<>();
        countPseudoPalindromicPaths(root, frequency);
        return count;
    }

    private void countPseudoPalindromicPaths(TreeNode root, Map<Integer, Integer> frequency) {
        if (root == null)
            return;
        frequency.put(root.val, 1 - frequency.getOrDefault(root.val, 0));
        if (frequency.get(root.val) == 0)
            frequency.remove(root.val);
        if (root.left == null && root.right == null) {
            if (frequency.isEmpty() || frequency.size() == 1)
                count++;
        }
        countPseudoPalindromicPaths(root.left, frequency);
        countPseudoPalindromicPaths(root.right, frequency);
        frequency.put(root.val, 1 - frequency.getOrDefault(root.val, 0));
        if (frequency.get(root.val) == 0)
            frequency.remove(root.val);
    }

    /*
     * Revision Note — Pseudo-Palindromic Paths in a Binary Tree (Medium)
     * Pattern: Pre-order DFS with parity-map backtracking
     * Key Insight: A path is pseudo-palindromic iff at most one digit has an odd
     *              frequency. Track parity in a map (1 = odd, remove on even);
     *              at each leaf, map.size() <= 1 means the path qualifies.
     * Gotchas:
     *   - Backtrack must FULLY mirror entry — toggle AND remove if zero.
     *     Omitting the removal leaves stale 0-valued entries that inflate
     *     map.size() for sibling subtrees, silently dropping valid paths.
     *   - The bitmask XOR alternative (mask ^= 1 << val, passed by value)
     *     avoids this class of bug entirely — no mutation, no backtracking needed.
     */
    public static void main(String[] args) {
        PseudoPalindromicPathsInABinaryTree P = new PseudoPalindromicPathsInABinaryTree();

        // Test 1: LeetCode example 1 — [2,3,1,3,1,null,1], paths 2→3→3 and 2→1→1 qualify
        TreeNode root1 = new TreeNode(2);
        root1.left = new TreeNode(3);
        root1.right = new TreeNode(1);
        root1.left.left = new TreeNode(3);
        root1.left.right = new TreeNode(1);
        root1.right.right = new TreeNode(1);
        System.out.println("Test 1: " + P.pseudoPalindromicPaths(root1) + " (Expected: 2)");

        // Test 2: null root
        System.out.println("Test 2: " + P.pseudoPalindromicPaths(null) + " (Expected: 0)");

        // Test 3: single node — always palindromic
        System.out.println("Test 3: " + P.pseudoPalindromicPaths(new TreeNode(1)) + " (Expected: 1)");

        // Test 4: bug regression — stale 0-entries would cause right leaf to be missed
        //   1→2→2 qualifies, 1→2→3 does not, 1→1 qualifies
        TreeNode root4 = new TreeNode(1);
        root4.left = new TreeNode(2);
        root4.right = new TreeNode(1);
        root4.left.left = new TreeNode(2);
        root4.left.right = new TreeNode(3);
        System.out.println("Test 4: " + P.pseudoPalindromicPaths(root4) + " (Expected: 2)");

        // Test 5: all same digit — both leaves get empty map, both qualify
        TreeNode root5 = new TreeNode(1);
        root5.left = new TreeNode(1);
        root5.right = new TreeNode(1);
        System.out.println("Test 5: " + P.pseudoPalindromicPaths(root5) + " (Expected: 2)");

        // Test 6: no qualifying paths — all leaves have 2+ odd-freq digits
        TreeNode root6 = new TreeNode(1);
        root6.left = new TreeNode(2);
        root6.right = new TreeNode(3);
        System.out.println("Test 6: " + P.pseudoPalindromicPaths(root6) + " (Expected: 0)");
    }
}
