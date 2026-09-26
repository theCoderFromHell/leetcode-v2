package medium;

import common.TreeNode;

// https://leetcode.com/problems/count-nodes-equal-to-sum-of-descendants/
public class CountNodesEqualToSumOfDescendants {
    int count;
    public int equalToDescendants(TreeNode root) {
        count = 0;
        subtreeSum(root);
        return count;
    }

    private long subtreeSum(TreeNode root) {
        if(root == null)
            return 0;
        long left = subtreeSum(root.left);
        long right = subtreeSum(root.right);
        if (left + right == root.val)
            count++;
        return (left + root.val + right);
    }

    /*
     * Revision Note — Count Nodes Equal to Sum of Descendants (Medium)
     *
     * Pattern: Post-order DFS returning subtree sum
     *
     * Key Insight: subtreeSum() returns the sum of the ENTIRE subtree including the node
     * itself, so at any node left+right is exactly its descendant sum. Compare that to
     * root.val, then return left + root.val + right for the parent to use.
     *
     * Gotchas:
     * - MUST return long, not int: 10^5 nodes x 10^5 max value = 10^10, which overflows
     *   int (max ~2.1x10^9). An int return could wrap negative and accidentally match
     *   some node's val, silently inflating the count
     * - Return value includes the node itself; the DESCENDANT sum is left+right only.
     *   Mixing these up is the classic error here
     * - A leaf has descendant sum 0, so a leaf with val 0 DOES count
     * - Recursion depth is O(n) on a skewed tree — 10^5 frames is near the JVM default limit
     *
     * Template:
     *   long subtreeSum(node):
     *     if node == null: return 0
     *     left = subtreeSum(node.left); right = subtreeSum(node.right)
     *     if left + right == node.val: count++
     *     return left + node.val + right
     */
    public static void main(String[] args) {
        CountNodesEqualToSumOfDescendants C = new CountNodesEqualToSumOfDescendants();

        //       10
        //      /  \
        //     3    4      10 -> 3+4+2+1 = 10 ok,  3 -> 2+1 = 3 ok
        //    / \
        //   2   1
        TreeNode t1 = new TreeNode(10);
        t1.left = new TreeNode(3);
        t1.right = new TreeNode(4);
        t1.left.left = new TreeNode(2);
        t1.left.right = new TreeNode(1);
        System.out.println("Test 1: " + C.equalToDescendants(t1) + " (Expected: 2)");

        //     2
        //    /
        //   3        no node matches its descendant sum
        //  /
        // 2
        TreeNode t2 = new TreeNode(2);
        t2.left = new TreeNode(3);
        t2.left.left = new TreeNode(2);
        System.out.println("Test 2: " + C.equalToDescendants(t2) + " (Expected: 0)");

        // single node 0 -> no descendants, sum 0, 0 == 0
        System.out.println("Test 3: " + C.equalToDescendants(new TreeNode(0)) + " (Expected: 1)");

        // single node 1 -> descendant sum 0 != 1
        System.out.println("Test 4: " + C.equalToDescendants(new TreeNode(1)) + " (Expected: 0)");

        //   0
        //  / \        every node has descendant sum 0 and val 0
        // 0   0
        TreeNode t5 = new TreeNode(0);
        t5.left = new TreeNode(0);
        t5.right = new TreeNode(0);
        System.out.println("Test 5: " + C.equalToDescendants(t5) + " (Expected: 3)");
    }
}
