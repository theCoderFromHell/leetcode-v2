package medium;

import common.TreeNode;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

// https://leetcode.com/problems/complete-binary-tree-inserter/
public class CompleteBinaryTreeInserter {

    /*
     * Revision Note — Complete Binary Tree Inserter (Medium)
     * Pattern: 1-based level-order indexing of a complete binary tree
     * Key Insight: In a complete binary tree numbered 1..n in BFS order, the parent of index i
     *              is i/2; even i is a left child, odd i (i>1) is a right child. Maintain a
     *              HashMap<index, TreeNode> so insert is O(1).
     * Gotchas:
     *   - Must put() the newly inserted node into the map too — otherwise once it must serve
     *     as a parent for a later insert, get(value/2) returns null → NPE
     *   - Alternative (less memory): ArrayDeque of "open parents" — only nodes with a free
     *     child slot live in the deque; constructor seeds it via BFS
     */
    public static void main(String[] args) {
        // Test 1: start from [1], insert 2..7 → perfect tree of depth 3
        CBTInserter c1 = new CBTInserter(new TreeNode(1));
        int p2 = c1.insert(2); // parent should be 1
        int p3 = c1.insert(3); // parent should be 1
        int p4 = c1.insert(4); // parent should be 2
        int p5 = c1.insert(5); // parent should be 2
        int p6 = c1.insert(6); // parent should be 3
        int p7 = c1.insert(7); // parent should be 3
        System.out.println("Test 1 parents: [" + p2 + "," + p3 + "," + p4 + "," + p5 + "," + p6 + "," + p7
                + "] (Expected: [1,1,2,2,3,3])");
        System.out.println("Test 1 tree: " + TreeNode.levelOrder(c1.get_root()) + " (Expected: [1, 2, 3, 4, 5, 6, 7])");

        // Test 2: seed with an already-complete tree [1,2,3,4,5,6], insert 7,8,9,10,11
        TreeNode seed = new TreeNode(1);
        seed.left = new TreeNode(2);
        seed.right = new TreeNode(3);
        seed.left.left = new TreeNode(4);
        seed.left.right = new TreeNode(5);
        seed.right.left = new TreeNode(6);
        CBTInserter c2 = new CBTInserter(seed);
        int q7 = c2.insert(7);   // parent 3
        int q8 = c2.insert(8);   // parent 4
        int q9 = c2.insert(9);   // parent 4
        int q10 = c2.insert(10); // parent 5
        int q11 = c2.insert(11); // parent 5
        System.out.println("Test 2 parents: [" + q7 + "," + q8 + "," + q9 + "," + q10 + "," + q11
                + "] (Expected: [3,4,4,5,5])");
        System.out.println("Test 2 tree: " + TreeNode.levelOrder(c2.get_root())
                + " (Expected: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11])");

        // Test 3: insertions that themselves become parents (the previously-buggy path).
        // Start [1], insert 2 (parent 1), insert 3 (parent 1), insert 4 (parent 2 — NEWLY INSERTED),
        // insert 5 (parent 2), insert 6 (parent 3 — newly inserted), insert 7 (parent 3),
        // insert 8 (parent 4 — newly inserted), insert 9 (parent 4).
        CBTInserter c3 = new CBTInserter(new TreeNode(1));
        c3.insert(2);
        c3.insert(3);
        int r4 = c3.insert(4); // parent 2 (inserted in this same session)
        c3.insert(5);
        int r6 = c3.insert(6); // parent 3 (inserted in this same session)
        c3.insert(7);
        int r8 = c3.insert(8); // parent 4 (inserted in this same session)
        c3.insert(9);
        System.out.println("Test 3 parents (4,6,8): [" + r4 + "," + r6 + "," + r8
                + "] (Expected: [2,3,4])");
        System.out.println("Test 3 tree: " + TreeNode.levelOrder(c3.get_root())
                + " (Expected: [1, 2, 3, 4, 5, 6, 7, 8, 9])");

        // Test 4: get_root never changes
        CBTInserter c4 = new CBTInserter(new TreeNode(42));
        c4.insert(99);
        c4.insert(100);
        System.out.println("Test 4 root: " + c4.get_root().val + " (Expected: 42)");
    }
}

class CBTInserter {
    HashMap<Integer, TreeNode> treeMap = new HashMap<>();
    int count = 0;
    public CBTInserter(TreeNode root) {
        if (root == null)
            return;
        Queue<TreeNode> Q = new ArrayDeque<>();
        Q.add(root);
        while (!Q.isEmpty()) {
            int size = Q.size();
            while (size-- > 0) {
                TreeNode node = Q.poll();
                treeMap.put(++count, node);
                if (node.left != null)
                    Q.add(node.left);
                if (node.right != null)
                    Q.add(node.right);
            }
        }
    }

    public int insert(int val) {
        int value = ++count;
        TreeNode node = new TreeNode(val);
        treeMap.put(value, node);
        TreeNode parent = treeMap.get(value/2);
        if (value % 2 == 0)
            parent.left = node;
        else
            parent.right = node;
        return parent.val;
    }

    public TreeNode get_root() {
        return treeMap.get(1);
    }
}
