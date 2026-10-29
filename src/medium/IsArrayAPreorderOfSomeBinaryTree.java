package medium;

import common.TreeNode;

import java.util.List;

// https://leetcode.com/problems/is-array-a-preorder-of-some-binary-tree/
public class IsArrayAPreorderOfSomeBinaryTree {
    int index;
    public boolean isPreorder(List<List<Integer>> nodes) {
        int size = nodes.size();
        if (nodes.getFirst().get(1) != -1)
            return false;
        index = 0;
        build(nodes, size, null);
        return index == size;
    }

    private TreeNode build(List<List<Integer>> nodes, int size, Integer parent) {
        if (index == size)
            return null;
        TreeNode root = null;
        if (parent == null || (int) parent == nodes.get(index).get(1)) {
            root = new TreeNode(nodes.get(index++).get(0));
            root.left = build(nodes, size, root.val);
            root.right = build(nodes, size, root.val);
        }
        return root;
    }

    /*
     * Revision Note — Is Array a Preorder of Some Binary Tree (Medium)
     * Pattern: preorder replay against the current root-to-node path — the recursion stack (or an
     *          explicit one) holds that path, and every entry must attach to a node on it.
     * Key Insight: in a preorder, the entry right after node u is u's next child if u has one left;
     *              otherwise it belongs to some ancestor of u. So each entry's parent must be on the
     *              current path, and a node that falls off the path is finished for good. Left/right
     *              is free ("some" binary tree), so "the next entry whose parent is u becomes u's
     *              child" is forced, not a choice.
     * Gotchas:
     *   - Integer == Integer compares REFERENCES. Java caches only -128..127, so the first version
     *     returned false as soon as an id passed 127 (Test 6; a 10^5-node chain also stopped at id 128). Unbox
     *     with (int) or use .equals — or pass -1 (the root's parentId) as a plain int instead of null.
     *   - Recursion depth = tree height, up to 10^5 for a chain. LeetCode accepted it, but the
     *     default local stack overflows near 10^4 frames, so main() leaves the 10^5-chain test out.
     *     The explicit-stack version has no depth limit.
     *   - The first entry must be the root (parentId == -1).
     *   - The TreeNodes are never read: only index matters, so the tree itself can go.
     * Complexity: O(n) time — at most ~3n build calls, each O(1). O(n) space for the TreeNodes plus
     *             O(h) recursion, h <= n.
     * Alternative — an explicit path stack, no recursion and no tree, O(n) time, O(h) space:
     *   for each [id, parent]:
     *       while path not empty and path.peek() != parent: path.pop()   // close finished subtrees
     *       if path is empty: return false (unless this is the root, first entry)
     *       path.push(id)
     *   return true
     * Template (recursive, as solved):
     *   index = 0; build(parent = none)
     *   build(parent): if index == n or (parent set and nodes[index].parent != parent): return
     *                  u = nodes[index++].id; build(u); build(u)     // left child, then right
     *   return index == n
     */
    public static void main(String[] args) {
        IsArrayAPreorderOfSomeBinaryTree I = new IsArrayAPreorderOfSomeBinaryTree();

        // Test 1: LeetCode example 1 — 1 is a leaf, so 2 (the root's other child) and its subtree [3, 4] follow it
        System.out.println("Test 1: " + I.isPreorder(List.of(List.of(0, -1), List.of(1, 0), List.of(2, 0), List.of(3, 2), List.of(4, 2))) + " (Expected: true)");

        // Test 2: LeetCode example 2 — 3 and 4 are children of 1, but 2 arrives first and closes 1's subtree
        System.out.println("Test 2: " + I.isPreorder(List.of(List.of(0, -1), List.of(1, 0), List.of(2, 0), List.of(3, 1), List.of(4, 1))) + " (Expected: false)");

        // Test 3: single node — just the root
        System.out.println("Test 3: " + I.isPreorder(List.of(List.of(0, -1))) + " (Expected: true)");

        // Test 4: ids are labels, not positions — 7 attaches to the root once 3's subtree [3, 9] is done
        System.out.println("Test 4: " + I.isPreorder(List.of(List.of(5, -1), List.of(3, 5), List.of(9, 3), List.of(7, 5))) + " (Expected: true)");

        // Test 5: 4's parent 2 is two levels down in 1's subtree, which closed when 3 (a root child) arrived
        System.out.println("Test 5: " + I.isPreorder(List.of(List.of(0, -1), List.of(1, 0), List.of(2, 1), List.of(3, 0), List.of(4, 2))) + " (Expected: false)");

        // Test 6: ids above 127 — outside Java's cached Integer range, so two equal ids are separate objects;
        // the first version's Integer == Integer returned false here
        System.out.println("Test 6: " + I.isPreorder(List.of(List.of(200, -1), List.of(300, 200), List.of(400, 200))) + " (Expected: true)");
    }
}
