package medium;

import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/boundary-of-binary-tree/
public class BoundaryOfBinaryTree {
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        if (root == null)
            return new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        result.add(root.val);
        addLeftBoundary(root.left, result);
        addLeaves(root.left, result);
        addLeaves(root.right, result);
        int size = result.size();
        addRightBoundary(root.right, result);
        reverseRightPart(result, size);
        return result;
    }

    private void reverseRightPart(List<Integer> result, int size) {
        if (result.size() == size)
            return;
        int temp, low = size, high = result.size() - 1;
        while (low < high) {
            temp = result.get(low);
            result.set(low, result.get(high));
            result.set(high, temp);
            low++;
            high--;
        }
    }

    private void addRightBoundary(TreeNode root, List<Integer> result) {
        if (root == null)
            return;
        if (root.left == null && root.right == null)
            return;
        result.add(root.val);
        if (root.right != null)
            addRightBoundary(root.right, result);
        else
            addRightBoundary(root.left, result);
    }

    private void addLeaves(TreeNode root, List<Integer> result) {
        if (root == null)
            return;
        if (root.left == null && root.right == null) {
            result.add(root.val);
            return;
        }
        addLeaves(root.left, result);
        addLeaves(root.right, result);
    }

    private void addLeftBoundary(TreeNode root, List<Integer> result) {
        if (root == null)
            return;
        if (root.left == null && root.right == null)
            return;
        result.add(root.val);
        if (root.left != null)
            addLeftBoundary(root.left, result);
        else
            addLeftBoundary(root.right, result);
    }

    /*
     * Revision Note — Boundary of Binary Tree (Medium)
     * Pattern: Three independent traversals — left spine, leaves, right spine (reversed)
     * Key Insight: The boundary is root + left boundary (top-down) + leaves (left-to-right)
     *              + right boundary (bottom-up). Collect the right spine forward, then reverse
     *              just that segment in place using the size captured before the walk.
     * Gotchas:
     *   - Both spine walks must SKIP leaves (guard placed above the add), otherwise every
     *     boundary leaf is emitted twice — once by the spine, once by addLeaves.
     *   - addRightBoundary must recurse into ITSELF, preferring right then falling back to left.
     *     Delegating to addLeftBoundary flips the preference below the first level and the walk
     *     wanders into the left subtree.
     *   - Call addLeaves on root.left and root.right separately, never on root. A single-node
     *     tree is its own leaf, so addLeaves(root) would emit the root a second time.
     *     Narrowing the entry point beats guarding the output.
     *   - Spine fallback matters: a boundary node with only the opposite child still continues
     *     the spine (left boundary falls to root.right, right boundary falls to root.left).
     *   - Recursion depth is O(H) — fine for n <= 10^4, but a degenerate spine is the deep case.
     */
    public static void main(String[] args) {
        BoundaryOfBinaryTree B = new BoundaryOfBinaryTree();

        // Test 1: single node — root is its own leaf, must not be duplicated
        TreeNode t1 = new TreeNode(1);
        System.out.println("Test 1: " + B.boundaryOfBinaryTree(t1) + " (Expected: [1])");

        // Test 2: LeetCode example 1 — root has only a right subtree
        TreeNode t2 = new TreeNode(1);
        t2.right = new TreeNode(2);
        t2.right.left = new TreeNode(3);
        t2.right.right = new TreeNode(4);
        System.out.println("Test 2: " + B.boundaryOfBinaryTree(t2) + " (Expected: [1, 3, 4, 2])");

        // Test 3: LeetCode example 2 — complete tree
        TreeNode t3 = new TreeNode(1);
        t3.left = new TreeNode(2);
        t3.right = new TreeNode(3);
        t3.left.left = new TreeNode(4);
        t3.left.right = new TreeNode(5);
        t3.right.left = new TreeNode(6);
        t3.right.right = new TreeNode(7);
        System.out.println("Test 3: " + B.boundaryOfBinaryTree(t3) + " (Expected: [1, 2, 4, 5, 6, 7, 3])");

        // Test 4: right spine must keep preferring right — node 4 is NOT on the boundary
        TreeNode t4 = new TreeNode(1);
        t4.right = new TreeNode(2);
        t4.right.right = new TreeNode(3);
        t4.right.right.left = new TreeNode(4);
        t4.right.right.right = new TreeNode(5);
        t4.right.right.left.left = new TreeNode(6);
        System.out.println("Test 4: " + B.boundaryOfBinaryTree(t4) + " (Expected: [1, 6, 5, 3, 2])");

        // Test 5: left spine — deepest node is a leaf, belongs to leaves not the spine
        TreeNode t5 = new TreeNode(1);
        t5.left = new TreeNode(2);
        t5.left.left = new TreeNode(3);
        System.out.println("Test 5: " + B.boundaryOfBinaryTree(t5) + " (Expected: [1, 2, 3])");

        // Test 6: left boundary falls through to the right child
        TreeNode t6 = new TreeNode(1);
        t6.left = new TreeNode(2);
        t6.left.right = new TreeNode(3);
        System.out.println("Test 6: " + B.boundaryOfBinaryTree(t6) + " (Expected: [1, 2, 3])");

        // Test 7: right boundary falls through to the left child
        TreeNode t7 = new TreeNode(1);
        t7.right = new TreeNode(2);
        t7.right.left = new TreeNode(3);
        System.out.println("Test 7: " + B.boundaryOfBinaryTree(t7) + " (Expected: [1, 3, 2])");

        // Test 8: null root
        System.out.println("Test 8: " + B.boundaryOfBinaryTree(null) + " (Expected: [])");

        // Test 9: duplicate values — traversal is structural, repeats are expected
        TreeNode t9 = new TreeNode(1);
        t9.left = new TreeNode(1);
        t9.right = new TreeNode(1);
        System.out.println("Test 9: " + B.boundaryOfBinaryTree(t9) + " (Expected: [1, 1, 1])");
    }
}
