package medium;

import java.util.Arrays;

// https://leetcode.com/problems/verify-preorder-sequence-in-binary-search-tree/
public class VerifyPreorderSequenceInBinarySearchTree {
    int index = 0;
    public boolean verifyPreorder(int[] preorder) {
        int size = preorder.length;
        int[] inorder = Arrays.copyOf(preorder, size);
        Arrays.sort(inorder);
        index = 0;
        return buildTree(preorder, inorder, 0, size-1);
    }

    private boolean buildTree(int[] preorder, int[] inorder, int start, int end) {
        if (start == end)
            return inorder[start] == preorder[index++];
        int node = preorder[index++];
        int inorderIndex = find(inorder, node, start, end);
        if (inorderIndex == -1)
            return false;
        if (start <= inorderIndex-1 && !buildTree(preorder, inorder, start, inorderIndex-1))
            return false;
        if (inorderIndex+1 <= end && !buildTree(preorder, inorder, inorderIndex+1, end))
            return false;
        return true;
    }

    private int find(int[] inorder, int node, int start, int end) {
        int i = Arrays.binarySearch(inorder, start, end + 1, node);
        return i >= 0 ? i : -1;
    }

    /*
     * Revision Note - Verify Preorder Sequence in Binary Search Tree (Medium, Premium)
     *
     * Pattern: rebuild from preorder + inorder (105), where a BST's inorder is free: it's the sorted
     * array. Preorder gives the root, its position in the sorted range splits left from right, and
     * the sequence is valid iff every root taken from preorder falls inside its current range.
     *
     * Key Insight: values are unique, so sorted(preorder) IS the inorder of any BST holding them.
     * A preorder is valid exactly when 105's reconstruction never asks for a value outside the
     * range [start, end] it is building.
     *
     * Gotchas:
     * - The shared preorder cursor (index) must advance in the base case too, and be reset per call:
     *   it lives on the instance.
     * - Two elements, one subtree empty ([1, 2], Test 7): guard each side with start <= end before
     *   recursing (this was the case being debugged before the accept).
     * - inorder is sorted, so find() is a binary search over [start, end]. A linear scan worked too,
     *   but was O(n^2) on reverse-sorted input (every root at the END of its range).
     * - Arrays.binarySearch takes an EXCLUSIVE toIndex (end + 1) and returns a negative insertion
     *   point when the value is missing, not -1: map any negative to -1 for the caller's check.
     * - Recursion depth is n on a chain (1e4): fine on the default stack.
     *
     * Complexity: O(n log n) for the sort plus n binary searches, O(n) space for the sorted copy
     * plus O(n) recursion stack.
     *
     * Alternative (98's lower bound, O(n), stack): keep a decreasing stack of ancestors whose right
     * subtree hasn't started. A bigger value pops every smaller ancestor; the last one popped becomes
     * the floor, and every later value must beat it. Follow-up O(1) space: reuse preorder[0..top]
     * as the stack.
     *   low = MIN; for v in preorder: if v < low return false
     *                                  while stack nonempty and stack.top < v: low = pop()
     *                                  push(v)
     *
     * Template (this solution):
     *   inorder = sorted(preorder); i = 0
     *   build(lo, hi): root = preorder[i++]; k = indexOf(root in inorder[lo..hi]) or fail
     *                  build(lo, k - 1) if nonempty; build(k + 1, hi) if nonempty
     */

    public static void main(String[] args) {
        VerifyPreorderSequenceInBinarySearchTree V = new VerifyPreorderSequenceInBinarySearchTree();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + V.verifyPreorder(new int[]{5, 2, 1, 3, 6}) + " (Expected: true)");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + V.verifyPreorder(new int[]{5, 2, 6, 1, 3}) + " (Expected: false)");

        // Test 3: single element: the base case alone, start == end
        System.out.println("Test 3: " + V.verifyPreorder(new int[]{1}) + " (Expected: true)");

        // Test 4: strictly decreasing: a left chain, every root sits at the END of its range
        System.out.println("Test 4: " + V.verifyPreorder(new int[]{5, 4, 3, 2, 1}) + " (Expected: true)");

        // Test 5: 3 is right of 2, so 2 becomes a floor; 1 then falls below it. The violated bound comes from 2, not from 1's neighbour 3
        System.out.println("Test 5: " + V.verifyPreorder(new int[]{4, 2, 3, 1}) + " (Expected: false)");

        // Test 6: valid with both sides split at two levels: rejects over-strict checks that compare only neighbours
        System.out.println("Test 6: " + V.verifyPreorder(new int[]{8, 5, 1, 7, 10, 12}) + " (Expected: true)");

        // Test 7: two elements, empty left subtree: each side's recursion must be skipped when its range is empty
        System.out.println("Test 7: " + V.verifyPreorder(new int[]{1, 2}) + " (Expected: true)");
    }
}
