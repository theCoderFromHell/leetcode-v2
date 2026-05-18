package medium;

import common.TreeNode;

import java.util.*;

// https://leetcode.com/problems/binary-tree-level-order-traversal-ii/
public class BinaryTreeLevelOrderTraversalII {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<List<Integer>> result = new LinkedList<>();
        if(root == null)
            return result;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> values = new ArrayList<>();
            while (size-- > 0) {
                TreeNode front = queue.poll();
                values.add(front.val);
                if (front.left != null)
                    queue.add(front.left);
                if (front.right != null)
                    queue.add(front.right);
            }
            result.addFirst(values);
        }
        return result;
    }

    /*
     * Revision Note — Binary Tree Level Order Traversal II (Medium)
     * Pattern: BFS level-by-level + prepend each level to result
     * Key Insight: If output is needed in reverse order, build it in reverse via addFirst on a
     *              LinkedList — avoids a final reverse pass and works on all Java versions.
     * Gotchas:
     *   - ArrayDeque >> LinkedList for the BFS queue (better cache locality)
     *   - LinkedList must be declared concrete (not List interface) to call addFirst directly
     *   - Don't forget the null root early return — otherwise the queue would NPE on poll
     */
    public static void main(String[] args) {
        BinaryTreeLevelOrderTraversalII B = new BinaryTreeLevelOrderTraversalII();

        // Example 1: [3,9,20,null,null,15,7] → [[15,7],[9,20],[3]]
        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(9);
        root1.right = new TreeNode(20);
        root1.right.left = new TreeNode(15);
        root1.right.right = new TreeNode(7);
        System.out.println("Test 1: " + B.levelOrderBottom(root1) + " (Expected: [[15, 7], [9, 20], [3]])");

        // Example 2: single node [1] → [[1]]
        System.out.println("Test 2: " + B.levelOrderBottom(new TreeNode(1)) + " (Expected: [[1]])");

        // Empty tree: null → []
        System.out.println("Test 3: " + B.levelOrderBottom(null) + " (Expected: [])");

        // Left-skewed: 1 -> 2 -> 3 → [[3],[2],[1]]
        TreeNode root4 = new TreeNode(1);
        root4.left = new TreeNode(2);
        root4.left.left = new TreeNode(3);
        System.out.println("Test 4: " + B.levelOrderBottom(root4) + " (Expected: [[3], [2], [1]])");

        // Right-skewed: 1 -> 2 -> 3 → [[3],[2],[1]]
        TreeNode root5 = new TreeNode(1);
        root5.right = new TreeNode(2);
        root5.right.right = new TreeNode(3);
        System.out.println("Test 5: " + B.levelOrderBottom(root5) + " (Expected: [[3], [2], [1]])");

        // Perfect tree of depth 3: [1,2,3,4,5,6,7] → [[4,5,6,7],[2,3],[1]]
        TreeNode root6 = new TreeNode(1);
        root6.left = new TreeNode(2);
        root6.right = new TreeNode(3);
        root6.left.left = new TreeNode(4);
        root6.left.right = new TreeNode(5);
        root6.right.left = new TreeNode(6);
        root6.right.right = new TreeNode(7);
        System.out.println("Test 6: " + B.levelOrderBottom(root6) + " (Expected: [[4, 5, 6, 7], [2, 3], [1]])");
    }
}
