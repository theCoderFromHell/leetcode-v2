package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

// https://leetcode.com/problems/find-root-of-n-ary-tree/
public class FindRootOfNAryTree {
    public Node findRoot(List<Node> tree) {
        int size = tree.size();
        long sum = 0;
        for (int i = 0; i < size; i++) {
            Node curr = tree.get(i);
            sum += curr.val;
            List<Node> children = curr.children;
            for (Node child : children)
                sum -= child.val;
        }
        for (int i = 0; i < size; i++) {
            if (tree.get(i).val == sum)
                return tree.get(i);
        }
        return null;
    }

    public Node findRootV2(List<Node> tree) {
        int size = tree.size();
        int XOR = 0;
        for (int i = 0; i < size; i++) {
            Node curr = tree.get(i);
            XOR ^= curr.val;
            List<Node> children = curr.children;
            for (Node child : children)
                XOR ^= child.val;
        }
        for (int i = 0; i < size; i++) {
            if (tree.get(i).val == XOR)
                return tree.get(i);
        }
        return null;
    }

    public Node findRootV3(List<Node> tree) {
        HashSet<Integer> hasParent = new HashSet<>();
        int size = tree.size();
        for (int i = 0; i < size; i++) {
            Node curr = tree.get(i);
            List<Node> children = curr.children;
            for (Node child : children)
                hasParent.add(child.val);
        }
        for (int i = 0; i < size; i++) {
            if (!hasParent.contains(tree.get(i).val))
                return tree.get(i);
        }
        return null;
    }

    static class Node {
        public int val;
        public List<Node> children;

        public Node() {
            this.children = new ArrayList<>();
        }

        public Node(int _val) {
            val = _val;
            this.children = new ArrayList<>();
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    /*
     * Revision Note — Find Root of N-Ary Tree (Medium)
     * Pattern: pairing cancellation — sum (findRoot), XOR (findRootV2), set-difference (findRootV3)
     * Key Insight: The root is the single node that never appears in anyone's children
     *              list. The input already contains EVERY node, so no traversal is needed —
     *              one flat pass over the list sees every parent-child edge. Every non-root
     *              is therefore seen exactly twice (as itself, and as some parent's child)
     *              while the root is seen once; any operation that cancels pairs isolates it.
     * Gotchas:
     *   - Do NOT reach for DFS here. Traversal only pays off when nodes must be DISCOVERED,
     *     and they were all handed to us. It also risks a stack overflow: 50,000 nodes in a
     *     chain blows the default stack, while the flat loop is immune.
     *   - findRoot (sum) MUST accumulate in long, not int: 50,000 values can overflow before
     *     the subtractions bring the running total back down. findRootV2 (XOR) has no such
     *     failure mode at any magnitude, which is the argument for preferring it.
     *   - All three key on val, which is safe only because values are guaranteed unique.
     *     HashSet<Node> (identity) would survive that guarantee being dropped.
     *   - Follow-up demands O(1) space: findRoot and findRootV2 qualify; findRootV3 (O(n)
     *     set) does not, but is the easiest of the three to read.
     * Template (XOR, O(n) time / O(1) space — the one to remember):
     *   x = 0; for (Node n : tree) { x ^= n.val; for (Node c : n.children) x ^= c.val; }
     *   every non-root cancels (appears once as itself, once as a child); root survives
     *   for (Node n : tree) if (n.val == x) return n;
     * Template (sum, O(n) time / O(1) space — same idea, needs long):
     *   long s = 0; for (Node n : tree) { s += n.val; for (Node c : n.children) s -= c.val; }
     * Template (set, O(n) time / O(n) space — easier to read):
     *   for (Node n : tree) for (Node c : n.children) hasParent.add(c.val);
     *   for (Node n : tree) if (!hasParent.contains(n.val)) return n;
     */
    public static void main(String[] args) {
        FindRootOfNAryTree F = new FindRootOfNAryTree();

        // Test 1: LeetCode example — 1 -> [3,2,4], 3 -> [5,6]; root placed LAST in the list
        Node a1 = new Node(1), a2 = new Node(2), a3 = new Node(3),
             a4 = new Node(4), a5 = new Node(5), a6 = new Node(6);
        a1.children = new ArrayList<>(Arrays.asList(a3, a2, a4));
        a3.children = new ArrayList<>(Arrays.asList(a5, a6));
        System.out.println("Test 1: " + val(F.findRoot(new ArrayList<>(Arrays.asList(a5, a2, a6, a4, a3, a1)))) + " (Expected: 1)");

        // Test 2: single node, no children
        Node solo = new Node(7);
        System.out.println("Test 2: " + val(F.findRoot(new ArrayList<>(Arrays.asList(solo)))) + " (Expected: 7)");

        // Test 3: degenerate chain 1 -> 2 -> 3 -> 4; root placed in the MIDDLE
        Node c1 = new Node(1), c2 = new Node(2), c3 = new Node(3), c4 = new Node(4);
        c1.children = new ArrayList<>(Arrays.asList(c2));
        c2.children = new ArrayList<>(Arrays.asList(c3));
        c3.children = new ArrayList<>(Arrays.asList(c4));
        System.out.println("Test 3: " + val(F.findRoot(new ArrayList<>(Arrays.asList(c3, c1, c4, c2)))) + " (Expected: 1)");

        // Test 4: star — root 10 with five leaf children; root placed FIRST
        Node s0 = new Node(10);
        Node s1 = new Node(11), s2 = new Node(12), s3 = new Node(13), s4 = new Node(14), s5 = new Node(15);
        s0.children = new ArrayList<>(Arrays.asList(s1, s2, s3, s4, s5));
        System.out.println("Test 4: " + val(F.findRoot(new ArrayList<>(Arrays.asList(s0, s1, s2, s3, s4, s5)))) + " (Expected: 10)");

        // Test 5: three levels — 1 -> [2,3], 2 -> [4], 3 -> [5,6], 5 -> [7]
        Node d1 = new Node(1), d2 = new Node(2), d3 = new Node(3), d4 = new Node(4),
             d5 = new Node(5), d6 = new Node(6), d7 = new Node(7);
        d1.children = new ArrayList<>(Arrays.asList(d2, d3));
        d2.children = new ArrayList<>(Arrays.asList(d4));
        d3.children = new ArrayList<>(Arrays.asList(d5, d6));
        d5.children = new ArrayList<>(Arrays.asList(d7));
        System.out.println("Test 5: " + val(F.findRoot(new ArrayList<>(Arrays.asList(d7, d4, d6, d2, d5, d3, d1)))) + " (Expected: 1)");

        // Test 6: root has a large value while children are small — guards against
        // any accidental min/max assumption instead of a true parent check
        Node b9 = new Node(99), b1 = new Node(1), b2 = new Node(2);
        b9.children = new ArrayList<>(Arrays.asList(b1, b2));
        System.out.println("Test 6: " + val(F.findRoot(new ArrayList<>(Arrays.asList(b1, b2, b9)))) + " (Expected: 99)");
    }

    private static String val(Node n) {
        return n == null ? "null" : String.valueOf(n.val);
    }
}
