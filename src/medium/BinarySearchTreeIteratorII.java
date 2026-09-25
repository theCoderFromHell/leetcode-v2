package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/binary-search-tree-iterator-ii/
public class BinarySearchTreeIteratorII {

    static class BSTIterator {
        List<Integer> inorder;
        int curr;
        public BSTIterator(TreeNode root) {
            this.inorder = new ArrayList<>();
            inorder.add(-1);
            this.curr = 0;
            iterate(root, inorder);
        }

        private void iterate(TreeNode root, List<Integer> inorder) {
            if (root == null)
                return;
            iterate(root.left, inorder);
            inorder.add(root.val);
            iterate(root.right, inorder);
        }

        public boolean hasNext() {
            return curr + 1 < inorder.size();
        }

        public int next() {
            curr++;
            return inorder.get(curr);
        }

        public boolean hasPrev() {
            return curr - 1 >= 1;
        }

        public int prev() {
            curr--;
            return inorder.get(curr);
        }
    }

    /*
     * Revision Note — Binary Search Tree Iterator II (Medium)
     * Pattern: Precompute in-order into an array + index pointer (bidirectional walk)
     * Key Insight: A next()/prev() pair is just index++/index-- over the sorted values —
     *              no need to re-derive position from tree structure each call.
     *              A "-1" sentinel at index 0 encodes "pointer before the first element"
     *              as ordinary index arithmetic instead of a separate hasStarted flag.
     * Gotchas:
     *   - hasPrev() must check (curr - 1 >= 1), not >= 0 — index 0 is the sentinel,
     *     not a real value, so "previous exists" requires skipping over it.
     *   - Recursive in-order traversal in the constructor risks StackOverflowError on
     *     a skewed BST (up to 10^5 nodes = 10^5 recursion depth). Prefer an iterative
     *     traversal (explicit Deque<TreeNode>) when n can be large and shape unknown.
     *   - This is O(n) space upfront regardless of how much the caller actually uses
     *     the iterator — the problem's follow-up (O(h) space) needs a lazily-advancing
     *     stack instead, expanding one node at a time as next()/prev() are called.
     * Template:
     *   inorder = [-1]; recursiveInorder(root) appends into inorder; curr = 0
     *   next(): inorder.get(++curr)      hasNext(): curr+1 < inorder.size()
     *   prev(): inorder.get(--curr)      hasPrev(): curr-1 >= 1
     */
    public static void main(String[] args) {
        // LeetCode example:      7
        //                       / \
        //                      3   15
        //                         /  \
        //                        9   20
        // in-order: 3, 7, 9, 15, 20
        TreeNode root = new TreeNode(7);
        root.left = new TreeNode(3);
        root.right = new TreeNode(15);
        root.right.left = new TreeNode(9);
        root.right.right = new TreeNode(20);

        BSTIterator B = new BSTIterator(root);
        System.out.println("Test 1: " + B.next() + " (Expected: 3)");
        System.out.println("Test 2: " + B.next() + " (Expected: 7)");
        System.out.println("Test 3: " + B.prev() + " (Expected: 3)");
        System.out.println("Test 4: " + B.next() + " (Expected: 7)");
        System.out.println("Test 5: " + B.hasNext() + " (Expected: true)");
        System.out.println("Test 6: " + B.next() + " (Expected: 9)");
        System.out.println("Test 7: " + B.next() + " (Expected: 15)");
        System.out.println("Test 8: " + B.next() + " (Expected: 20)");
        System.out.println("Test 9: " + B.hasNext() + " (Expected: false)");
        System.out.println("Test 10: " + B.hasPrev() + " (Expected: true)");
        System.out.println("Test 11: " + B.prev() + " (Expected: 15)");
        System.out.println("Test 12: " + B.prev() + " (Expected: 9)");

        // single-node tree: nothing before or after the only value
        BSTIterator B2 = new BSTIterator(new TreeNode(5));
        System.out.println("Test 13: " + B2.hasPrev() + " (Expected: false)");
        System.out.println("Test 14: " + B2.next() + " (Expected: 5)");
        System.out.println("Test 15: " + B2.hasNext() + " (Expected: false)");
        System.out.println("Test 16: " + B2.hasPrev() + " (Expected: false)");
    }
}
