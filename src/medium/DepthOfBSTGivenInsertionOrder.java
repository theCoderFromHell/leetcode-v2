package medium;

import java.util.Map;
import java.util.TreeMap;

// https://leetcode.com/problems/depth-of-bst-given-insertion-order/
public class DepthOfBSTGivenInsertionOrder {
    public int maxDepthBST(int[] order) {
        int size = order.length;
        TreeMap<Integer,Integer> depths = new TreeMap<>();
        depths.put(order[0], 1);
        int result = 1;
        for (int i = 1; i < size; i++) {
            int value = order[i];
            int depth;
            Map.Entry<Integer,Integer> predecessor = depths.lowerEntry(value);
            Map.Entry<Integer,Integer> successor = depths.higherEntry(value);
            if (predecessor == null || successor == null)
                depth = predecessor == null ? successor.getValue() + 1 : predecessor.getValue() + 1;
            else
                depth = Math.max(predecessor.getValue(), successor.getValue()) + 1;
            depths.put(value, depth);
            result = Math.max(result, depth);
        }
        return result;
    }

    /*
     * Revision Note - Depth of BST Given Insertion Order (Medium, Premium)
     *
     * Pattern: ordered map of inserted values -> depth. A BST insert never needs the tree, only the
     * new value's nearest smaller and nearest larger neighbours among the values inserted so far.
     *
     * Key Insight: the new value's parent is its predecessor or its successor, whichever is DEEPER.
     * The two are adjacent in sorted order, so one is an ancestor of the other, and the ancestor's
     * slot toward v is already used by the path to the descendant. So
     * depth(v) = max(depth(pred), depth(succ)) + 1, and you never need to know which one it was.
     *
     * Gotchas:
     * - "Deeper", not "larger value": in [3, 1, 2], 2's neighbours are 1 (depth 2) and 3 (depth 1);
     *   max by value picks 3, whose left slot is taken by 1 (Test 8).
     * - A missing side (lowerEntry / higherEntry return null) means v hangs off the other one.
     *   Sentinels 0 and n + 1 at depth 0 remove the null branch: a depth-0 sentinel never beats a
     *   real neighbour, and the first value gets max(0, 0) + 1 = 1.
     * - lower / higher are STRICT (< and >); floor / ceiling include the key itself.
     * - Building the tree is O(n) per insert on sorted input: O(n^2) = 5e9 at n = 1e5 (Test 7).
     *
     * Complexity: O(n log n) time (two TreeMap lookups and one put per value), O(n) space.
     *
     * Alternative (no TreeMap, O(n)): process values in REVERSE insertion order and delete from a
     * doubly linked list over 1..n (prev[] / next[] arrays). When v is removed, its list neighbours
     * are exactly its predecessor and successor at the time it was inserted. Record them, then run
     * forward computing depths. Same idea as "nearest neighbours at insertion time", answered offline.
     *
     * Template:
     *   map = {0: 0, n + 1: 0}; best = 0
     *   for v in order: d = max(map.lowerEntry(v).value, map.higherEntry(v).value) + 1
     *                   map[v] = d; best = max(best, d)
     */

    public static void main(String[] args) {
        DepthOfBSTGivenInsertionOrder D = new DepthOfBSTGivenInsertionOrder();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + D.maxDepthBST(new int[]{2, 1, 4, 3}) + " (Expected: 3)");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + D.maxDepthBST(new int[]{2, 1, 3, 4}) + " (Expected: 3)");

        // Test 3: LeetCode example 3
        System.out.println("Test 3: " + D.maxDepthBST(new int[]{1, 2, 3, 4}) + " (Expected: 4)");

        // Test 4: single value: the root alone, both neighbours missing
        System.out.println("Test 4: " + D.maxDepthBST(new int[]{1}) + " (Expected: 1)");

        // Test 5: values alternate between the low and high ends: a zigzag, every insert one level deeper
        System.out.println("Test 5: " + D.maxDepthBST(new int[]{1, 5, 2, 4, 3}) + " (Expected: 5)");

        // Test 6: medians first: a perfectly balanced tree, depth log2(8) = 3
        System.out.println("Test 6: " + D.maxDepthBST(new int[]{4, 2, 6, 1, 3, 5, 7}) + " (Expected: 3)");

        // Test 7: n = 1e5, strictly decreasing: a left chain, so building the tree node by node is O(n^2)
        int[] decreasing = new int[100000];
        for (int i = 0; i < decreasing.length; i++)
            decreasing[i] = decreasing.length - i;
        System.out.println("Test 7: " + D.maxDepthBST(decreasing) + " (Expected: 100000)");

        // Test 8: 2's neighbours are 1 (depth 2) and 3 (depth 1): the parent is the DEEPER one, 1, not the larger value 3
        System.out.println("Test 8: " + D.maxDepthBST(new int[]{3, 1, 2}) + " (Expected: 3)");
    }
}
