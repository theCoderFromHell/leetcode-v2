package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/two-sum-bsts/
public class TwoSumBSTs {
    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        List<Integer> inorder1 = new ArrayList<>();
        List<Integer> inorder2 = new ArrayList<>();
        inorder(root1, inorder1);
        inorder(root2, inorder2);
        int size1 = inorder1.size();
        int size2 = inorder2.size();
        int index1 = 0, index2 = size2 - 1;
        while (index1 < size1 && index2 >= 0) {
            int curr = inorder1.get(index1) + inorder2.get(index2);
            if (curr == target)
                return true;
            else if (curr < target)
                index1++;
            else
                index2--;
        }
        return false;
    }

    private void inorder(TreeNode root, List<Integer> inorder) {
        if (root == null)
            return;
        inorder(root.left, inorder);
        inorder.add(root.val);
        inorder(root.right, inorder);
    }

    /*
     * Revision Note — Two Sum BSTs (Medium)
     * Pattern: In-order traversal of each BST + converging two pointers
     * Key Insight: In-order on a BST yields a SORTED list, which turns this into classic Two Sum
     *              on two sorted arrays: walk the first ascending from the left and the second
     *              descending from the right. Sum too small means the only way up is index1++,
     *              too large means index2--. Each step eliminates a whole row or column of the
     *              implicit m x n pair grid, so one linear pass suffices.
     * Gotchas:
     *   - index1 indexes inorder1, index2 indexes inorder2. Writing inorder1.get(index2) compiles
     *     cleanly and still passes the LeetCode example, so the compiler and the sample test are
     *     both useless here. It fails two different ways: IndexOutOfBounds when size2 > size1,
     *     and a silent wrong answer (summing two values from the same tree) when size2 <= size1.
     *     Always include a test where the two trees have DIFFERENT sizes — that converts the
     *     silent failure into a loud one.
     *   - One node must come from EACH tree; pairs within a single tree do not count.
     *   - No overflow: |val| <= 10^9 so the sum lands in [-2e9, 2e9], inside int (+/-2.147e9).
     *     Worth checking rather than assuming, since 10^9 inputs usually do demand a long.
     *   - Recursion depth is O(h). A degenerate 5000-node BST makes that O(n) stack.
     * Alternative: HashSet of tree 1's values, then DFS tree 2 looking for target - node.val.
     *              O(m + n) time, O(m) space, short-circuits early, and never uses BST ordering
     *              at all — so it works on arbitrary trees. Best on space: walk both trees with
     *              explicit stacks (ascending on one, descending on the other) for O(h1 + h2).
     */

    // BSTs are built by insertion since common.TreeNode has no BST builder
    private static TreeNode insert(TreeNode node, int value) {
        if (node == null)
            return new TreeNode(value);
        if (value < node.val)
            node.left = insert(node.left, value);
        else
            node.right = insert(node.right, value);
        return node;
    }

    private static TreeNode buildBST(int... values) {
        TreeNode root = null;
        for (int value : values)
            root = insert(root, value);
        return root;
    }

    public static void main(String[] args) {
        TwoSumBSTs T = new TwoSumBSTs();

        // Test 1: LeetCode example 1 — 2 from tree 1 and 3 from tree 2 sum to 5
        System.out.println("Test 1: " + T.twoSumBSTs(buildBST(2, 1, 4), buildBST(1, 0, 3), 5) + " (Expected: true)");

        // Test 2: LeetCode example 2 — no cross-tree pair reaches 18
        System.out.println("Test 2: " + T.twoSumBSTs(buildBST(0, -10, 10), buildBST(5, 1, 7, 0, 2), 18) + " (Expected: false)");

        // Test 3: one node in each tree, pair hits the target
        System.out.println("Test 3: " + T.twoSumBSTs(buildBST(3), buildBST(4), 7) + " (Expected: true)");

        // Test 4: one node in each tree, pair misses
        System.out.println("Test 4: " + T.twoSumBSTs(buildBST(3), buildBST(4), 8) + " (Expected: false)");

        // Test 5: second tree much larger — catches indexing inorder1 with index2
        System.out.println("Test 5: " + T.twoSumBSTs(buildBST(1), buildBST(1, 2, 3, 4, 5, 6), 7) + " (Expected: true)");

        // Test 6: first tree much larger — the mirror of Test 5
        System.out.println("Test 6: " + T.twoSumBSTs(buildBST(1, 2, 3, 4, 5, 6), buildBST(1), 7) + " (Expected: true)");

        // Test 7: all negative values and a negative target
        System.out.println("Test 7: " + T.twoSumBSTs(buildBST(-5, -3, -1), buildBST(-2, -4, -6), -7) + " (Expected: true)");

        // Test 8: upper magnitude bound — 10^9 + 10^9 = 2e9 still fits in int
        System.out.println("Test 8: " + T.twoSumBSTs(buildBST(1000000000), buildBST(1000000000), 2000000000) + " (Expected: true)");

        // Test 9: lower magnitude bound — -2e9 also fits
        System.out.println("Test 9: " + T.twoSumBSTs(buildBST(-1000000000), buildBST(-1000000000), -2000000000) + " (Expected: true)");

        // Test 10: a valid pair exists only by pairing the smallest of one with the largest of the other
        System.out.println("Test 10: " + T.twoSumBSTs(buildBST(5, 3, 8), buildBST(20, 15, 25), 28) + " (Expected: true)");

        // Test 11: duplicate values within a tree do not create a valid same-tree pair
        System.out.println("Test 11: " + T.twoSumBSTs(buildBST(4, 4, 4), buildBST(9, 9), 8) + " (Expected: false)");
    }
}
