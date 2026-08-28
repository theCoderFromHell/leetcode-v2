package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// https://leetcode.com/problems/recover-binary-search-tree/
public class RecoverBinarySearchTree {
    TreeNode prev, first, second;
    public void recoverTree(TreeNode root) {
        prev = first = second = null;
        solve(root);
        swap(first, second);

    }

    private void solve(TreeNode root) {
        if (root == null)
            return;
        solve(root.left);
        if (prev != null && prev.val > root.val) {
            if (first == null)
                first = prev;
            second = root;
        }
        prev = root;
        solve(root.right);
    }

    private void swap(TreeNode first, TreeNode second) {
        if (first == null || second == null)
            return;
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }


    /*
     * Revision Note — Recover Binary Search Tree (Hard)
     *
     * Pattern: In-order traversal with violation tracking (instance fields)
     *
     * Key Insight: In-order traversal of a valid BST is strictly increasing; a swap
     * creates at most two inversions (prev.val > curr.val). Track first inversion's
     * `prev` as `first` and always update `second` to current — then swap their values.
     *
     * Gotchas:
     * - Java passes references by value — assigning `first = node` inside a recursive
     *   method parameter does NOT update the caller; use instance fields instead
     * - Adjacent swap → one inversion: first=prev, second=curr (still works with always-update-second logic)
     * - Non-adjacent swap → two inversions: first set at inversion 1, second updated at inversion 2
     * - Reset instance fields at the start of recoverTree for safe re-use
     *
     * Template:
     *   TreeNode prev, first, second;  // instance fields
     *   solve(root):
     *     solve(root.left)
     *     if prev != null && prev.val > root.val:
     *       if first == null: first = prev
     *       second = root
     *     prev = root
     *     solve(root.right)
     *   swap(first.val, second.val)
     */
    int index;
    public void recoverTreeV2(TreeNode root) {
        List<Integer> inorder = new ArrayList<>();
        getInorder(root, inorder);
        Collections.sort(inorder);
        index = 0;
        setInorder(root, inorder);
    }

    private void getInorder(TreeNode root, List<Integer> inorder) {
        if (root == null)
            return;
        getInorder(root.left, inorder);
        inorder.add(root.val);
        getInorder(root.right, inorder);
    }

    private void setInorder(TreeNode root, List<Integer> inorder) {
        if (root == null)
            return;
        setInorder(root.left, inorder);
        root.val = inorder.get(index);
        index++;
        setInorder(root.right, inorder);
    }

    public static void main(String[] args) {
        RecoverBinarySearchTree R = new RecoverBinarySearchTree();

        // [1,3,null,null,2] → swap 1 and 3 → [3,1,null,null,2]
        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(3);
        root1.left.right = new TreeNode(2);
        R.recoverTree(root1);
        TreeNode.printTree(root1);  // 3,1,#,#,2

        // [3,1,4,null,null,2] → swap 2 and 3 → [2,1,4,null,null,3]
        TreeNode root2 = new TreeNode(3);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(4);
        root2.right.left = new TreeNode(2);
        R.recoverTree(root2);
        TreeNode.printTree(root2);  // 2,1,4,#,#,3

        // [2,3,1] → swap 1 and 3 → [2,1,3]
        TreeNode root3 = new TreeNode(2);
        root3.left = new TreeNode(3);
        root3.right = new TreeNode(1);
        R.recoverTree(root3);
        TreeNode.printTree(root3);  // 2,1,3

        // [3,5] → root=3, left=5 invalid (5>3 on left) → swap → [5,3]
        TreeNode root4 = new TreeNode(3);
        root4.left = new TreeNode(5);
        R.recoverTree(root4);
        TreeNode.printTree(root4);  // 5,3
    }
}
