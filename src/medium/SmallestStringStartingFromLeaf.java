package medium;

import common.TreeNode;

// https://leetcode.com/problems/smallest-string-starting-from-leaf/
public class SmallestStringStartingFromLeaf {
    String result;
    public String smallestFromLeaf(TreeNode root) {
        result = null;
        StringBuilder sb = new StringBuilder();
        find(root, sb);
        return result;
    }

    private void find(TreeNode root, StringBuilder sb) {
        if (root == null)
            return;
        sb.append((char) (root.val + 'a'));
        if (root.left == null && root.right == null) {
            String current = new StringBuilder(sb).reverse().toString();
            result = (result == null || current.compareTo(result) < 0) ? current : result;
        }
        find(root.left, sb);
        find(root.right, sb);
        sb.deleteCharAt(sb.length() - 1);
    }

    /*
     * Revision Note — Smallest String Starting From Leaf (Medium)
     * Pattern: DFS with backtracking path (StringBuilder) + lex-min comparison at each leaf
     * Key Insight: Build path root→leaf in shared StringBuilder, then at each leaf produce the
     *              leaf→root candidate via a COPY (new StringBuilder(sb).reverse()) — avoids
     *              mutating the shared buffer. Compare with current best.
     * Gotchas:
     *   - sb.reverse() mutates in-place; always copy or reverse twice if reading mid-traversal
     *   - First-leaf NPE: must guard `result == null` before calling compareTo
     *   - Paths of different lengths: "a" < "ab", standard compareTo handles correctly
     *   - Don't forget the deleteCharAt at the end (backtracking)
     */
    public static void main(String[] args) {
        SmallestStringStartingFromLeaf S = new SmallestStringStartingFromLeaf();

        // Example 1: [0,1,2,3,4,3,4] → leaves give "dba","eba","dca","eca" → "dba"
        TreeNode root1 = new TreeNode(0);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(2);
        root1.left.left = new TreeNode(3);
        root1.left.right = new TreeNode(4);
        root1.right.left = new TreeNode(3);
        root1.right.right = new TreeNode(4);
        System.out.println("Test 1: " + S.smallestFromLeaf(root1) + " (Expected: dba)");

        // Example 2: [25,1,3,1,3,0,2] → "adz" beats "bbz","dbz","cdz"
        TreeNode root2 = new TreeNode(25);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(3);
        root2.left.left = new TreeNode(1);
        root2.left.right = new TreeNode(3);
        root2.right.left = new TreeNode(0);
        root2.right.right = new TreeNode(2);
        System.out.println("Test 2: " + S.smallestFromLeaf(root2) + " (Expected: adz)");

        // Example 3: [2,2,1,null,1,0,null,0] → "abc"
        TreeNode root3 = new TreeNode(2);
        root3.left = new TreeNode(2);
        root3.right = new TreeNode(1);
        root3.left.right = new TreeNode(1);
        root3.right.left = new TreeNode(0);
        root3.left.right.left = new TreeNode(0);
        System.out.println("Test 3: " + S.smallestFromLeaf(root3) + " (Expected: abc)");

        // Single node: only one path of length 1
        System.out.println("Test 4: " + S.smallestFromLeaf(new TreeNode(0)) + " (Expected: a)");

        // Path-length tiebreak: "a" beats "ab"
        // Tree:    a(0)
        //         /
        //        b(1)
        TreeNode root5 = new TreeNode(0);
        root5.left = new TreeNode(1);
        System.out.println("Test 5: " + S.smallestFromLeaf(root5) + " (Expected: ba)");

        // Right-skewed: 0 -> 1 -> 2  (only leaf path "cba")
        TreeNode root6 = new TreeNode(0);
        root6.right = new TreeNode(1);
        root6.right.right = new TreeNode(2);
        System.out.println("Test 6: " + S.smallestFromLeaf(root6) + " (Expected: cba)");
    }
}
