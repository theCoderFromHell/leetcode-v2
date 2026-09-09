package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://leetcode.com/problems/numbers-with-same-consecutive-differences/
public class NumbersWithSameConsecutiveDifferences {
    public int[] numsSameConsecDiff(int n, int k) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            TreeNode root = new TreeNode(i);
            buildTree(root, n, k, 1);
            addNumbers(root, numbers, n, 0, 1);
        }
        int size = numbers.size();
        int[] result = new int[size];
        for (int i = 0; i < size; i++)
            result[i] = numbers.get(i);
        return result;

    }

    private void addNumbers(TreeNode root, List<Integer> numbers, int n, int current, int level) {
        if (root == null)
            return;
        if (root.left == null && root.right == null && level == n) {
            numbers.add(10 * current + root.val);
            return;
        }
        addNumbers(root.left, numbers, n,  10 * current + root.val, level + 1);
        addNumbers(root.right, numbers, n,  10 * current + root.val, level + 1);
    }

    private void buildTree(TreeNode root, int n, int k, int level) {
        if (root == null || level >= n)
            return;
        if (isValid(root.val - k))
            root.left = new TreeNode(root.val - k);
        if (k > 0) {
            if (isValid(root.val + k))
                root.right = new TreeNode(root.val + k);
        }
        buildTree(root.left, n, k, level + 1);
        buildTree(root.right, n, k, level + 1);
    }

    private boolean isValid(int value) {
        return (0 <= value && value <= 9);
    }

    /*
     * Revision Note — Numbers With Same Consecutive Differences (Medium)
     * Pattern: DFS/backtracking on digit tree — build each n-digit number one digit at a time
     * Key Insight: For each starting digit 1-9, branch left (digit-k) and right (digit+k)
     *              at each level; collect the number only when the full n-digit depth is reached.
     * Gotchas:
     *   - Tree depth stop condition must be level >= n, not level > k or level > n
     *     (both produce wrong depth — one too few, one too many).
     *   - Leaf collection: use 10 * current + root.val, not current + root.val
     *     (plain addition gives garbage — number must be constructed digit by digit).
     *   - k=0: val-k and val+k are the same digit, so only build one child (left).
     *     Guard: if (k > 0) build right child. Without this, each number appears twice.
     *   - Mid-path dead ends (both val±k invalid): node becomes a leaf before depth n;
     *     the level == n guard in addNumbers silently skips it. No special handling needed.
     */
    public static void main(String[] args) {
        NumbersWithSameConsecutiveDifferences N = new NumbersWithSameConsecutiveDifferences();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + Arrays.toString(N.numsSameConsecDiff(2, 1)) + " (Expected: [10, 12, 21, 23, 32, 34, 43, 45, 54, 56, 65, 67, 76, 78, 87, 89, 98])");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + Arrays.toString(N.numsSameConsecDiff(3, 7)) + " (Expected: [181, 292, 707, 818, 929])");

        // Test 3: k=0 — each digit repeated n times, no duplicates
        System.out.println("Test 3: " + Arrays.toString(N.numsSameConsecDiff(2, 0)) + " (Expected: [11, 22, 33, 44, 55, 66, 77, 88, 99])");

        // Test 4: k=9 — only pair is (9,0), so only 90 is valid
        System.out.println("Test 4: " + Arrays.toString(N.numsSameConsecDiff(2, 9)) + " (Expected: [90])");

        // Test 5: n=3, k=9 — only 909 possible
        System.out.println("Test 5: " + Arrays.toString(N.numsSameConsecDiff(3, 9)) + " (Expected: [909])");
    }

    class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode() {}

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}


