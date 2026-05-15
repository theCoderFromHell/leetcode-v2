package medium;

import common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

// https://leetcode.com/problems/even-odd-tree/
public class EvenOddTree {
    public boolean isEvenOddTree(TreeNode root) {
        if (root == null)
            return true;
        if (root.left == null && root.right == null)
            return root.val % 2 == 1;
        Queue<TreeNode> level = new LinkedList<>();
        level.add(root);
        boolean evenLevel = true;

        while (!level.isEmpty()) {
            int size = level.size();
            TreeNode previous = null;
            while (size > 0) {
                TreeNode curr = level.poll();
                if (evenLevel && (curr.val % 2 == 0 || (previous != null && curr.val <= previous.val)))
                    return false;
                if (!evenLevel && (curr.val % 2 == 1 || (previous != null && curr.val >= previous.val)))
                    return false;
                if (curr.left != null)
                    level.add(curr.left);
                if (curr.right != null)
                    level.add(curr.right);
                previous = curr;
                size--;
            }
            evenLevel = !evenLevel;
        }
        return true;
    }

    /*
     * Revision Note — Even Odd Tree (Medium)
     *
     * Pattern: BFS level-order traversal with per-level constraints
     *
     * Key Insight: Snapshot queue size at the start of each level to process
     * all nodes of that level atomically, then flip the even/odd flag.
     *
     * Gotchas:
     * - Even levels: strictly increasing ODD values
     * - Odd levels: strictly decreasing EVEN values
     * - Strictly means equal values also fail (use < and > not <= and >=)
     *
     * Template:
     *   while (!queue.isEmpty()) {
     *       int size = queue.size();   // snapshot before inner loop
     *       TreeNode prev = null;
     *       while (size-- > 0) {
     *           curr = queue.poll();
     *           // check parity and ordering vs prev
     *           prev = curr;
     *       }
     *       flipLevel();
     *   }
     */
    public static void main(String[] args) {
        EvenOddTree E = new EvenOddTree();
        // true — valid even-odd tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(10);
        root.right = new TreeNode(4);
        root.left.left = new TreeNode(3);
        root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(9);
        root.left.left.left = new TreeNode(12);
        root.left.left.right = new TreeNode(8);
        root.right.left.left = new TreeNode(6);
        root.right.right.right = new TreeNode(2);
        System.out.println(E.isEvenOddTree(root));  // true

        // false — even value at even level
        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(4);
        root2.right = new TreeNode(2);
        root2.left.left = new TreeNode(3);
        root2.left.right = new TreeNode(3);
        root2.right.right = new TreeNode(7);
        System.out.println(E.isEvenOddTree(root2)); // false

        // true — single node
        System.out.println(E.isEvenOddTree(new TreeNode(1))); // true

        // false — single node with even value
        System.out.println(E.isEvenOddTree(new TreeNode(2))); // false
    }
}
