package medium;

import common.TreeNode;

// https://leetcode.com/problems/house-robber-iii/
public class HouseRobberIII {
    public int rob(TreeNode root) {
        if(root == null)
            return 0;
        if(root.left == null && root.right == null)
            return root.val;
        WithWithout withWithout = maxRob(root);
        return Math.max(withWithout.with, withWithout.withOut);
    }

    private WithWithout maxRob(TreeNode root) {
        if(root == null)
            return new WithWithout(0,0);
        if(root.left == null && root.right == null)
            return new WithWithout(root.val, 0);
        WithWithout withWithoutLeft = maxRob(root.left);
        WithWithout withWithoutRight = maxRob(root.right);
        int oo = withWithoutLeft.with + withWithoutRight.with;
        int xx = withWithoutLeft.withOut + withWithoutRight.withOut;
        int ox = withWithoutLeft.with + withWithoutRight.withOut;
        int xo = withWithoutLeft.withOut + withWithoutRight.with;
        int withOut = Math.max(xo, Math.max(ox, Math.max(oo, xx)));
        int with = root.val + withWithoutLeft.withOut + withWithoutRight.withOut;
        return new WithWithout(with, withOut);
    }

    /*
     * Revision Note — House Robber III (Medium)
     *
     * Pattern: DP on a tree, post-order. Each node hands its parent a pair of answers for its
     * subtree: `with` (this node is robbed) and `withOut` (this node is skipped). The parent builds
     * its own pair from its children's pairs, so every node is solved exactly once.
     *
     * Key insight: robbing a node forces both children to be skipped, but skipping a node forces
     * nothing. So
     *   with    = val + left.withOut + right.withOut
     *   withOut = best of the four child combinations (oo, xx, ox, xo)
     * The two sides are independent, so the best of the four is just
     * max(left.with, left.withOut) + max(right.with, right.withOut).
     *
     * Gotchas:
     *  - `withOut` must not assume the children are robbed. On the chain 4-1-2-3 the best is 4 + 3,
     *    skipping 1 and 2 back to back (Test 4); that's the xx combination.
     *  - Robbing alternate levels is not the answer. Each subtree chooses on its own, so the best set
     *    can take a child on one side and a grandchild on the other (Test 5: 10 + 20 = 30, while
     *    the best alternate-level split is 21).
     *  - The leaf checks in rob and maxRob are not needed. A null child returns (0, 0), so a leaf
     *    already comes out as (val, 0). They're harmless.
     *  - No overflow: at most 10^4 nodes of value at most 10^4 gives 10^8, well inside int.
     *
     * Complexity: O(n) time (one visit per node, O(1) work each). O(h) space for the recursion
     * stack, up to O(n) on a skewed tree, plus one small pair object per call.
     *
     * Template:
     *   pair solve(node):
     *       if node == null: return (0, 0)
     *       L = solve(node.left); R = solve(node.right)
     *       with    = node.val + L.without + R.without
     *       without = max(L.with, L.without) + max(R.with, R.without)
     *       return (with, without)
     *   answer = max(solve(root))
     *
     * Alternative: rob(node) = max(node.val + rob(the four grandchildren), rob(left) + rob(right)),
     * memoised in a HashMap<TreeNode, Integer>. Without the memo it re-solves every grandchild
     * subtree and goes exponential; with it, it's O(n) but heavier than the pair. An int[2] works
     * in place of the WithWithout class.
     */
    public static void main(String[] args) {
        HouseRobberIII H = new HouseRobberIII();

        // Test 1: LeetCode example 1 — rob the root and both grandchildren: 3 + 3 + 1
        System.out.println("Test 1: " + H.rob(TreeNode.createTree(3, 2, 3, null, 3, null, 1)) + " (Expected: 7)");

        // Test 2: LeetCode example 2 — skip the root and rob both children: 4 + 5
        System.out.println("Test 2: " + H.rob(TreeNode.createTree(3, 4, 5, 1, 3, null, 1)) + " (Expected: 9)");

        // Test 3: a single node — the smallest legal tree
        System.out.println("Test 3: " + H.rob(TreeNode.createTree(5)) + " (Expected: 5)");

        // Test 4: the chain 4-1-2-3 — rob 4 and 3 and skip the two nodes between them, so a skipped
        // node's child can be skipped too
        System.out.println("Test 4: " + H.rob(TreeNode.createTree(4, 1, null, 2, null, 3)) + " (Expected: 7)");

        // Test 5: rob the left child (10) and the right grandchild (20) — the sides choose
        // independently, so alternate levels (1 + 20 or 10 + 1) both fall short
        System.out.println("Test 5: " + H.rob(TreeNode.createTree(1, 10, 1, null, null, null, 20)) + " (Expected: 30)");
    }
}
class WithWithout{
    int with;
    int withOut;

    public WithWithout(int with, int withOut) {
        this.with = with;
        this.withOut = withOut;
    }

    @Override
    public String toString() {
        return "WithWithout{" +
                "with=" + with +
                ", withOut=" + withOut +
                '}';
    }
}
