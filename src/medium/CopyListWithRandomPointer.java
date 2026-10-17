package medium;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

// https://leetcode.com/problems/copy-list-with-random-pointer/
public class CopyListWithRandomPointer {
    public Node copyRandomList(Node head) {
        if(head == null)
            return null;
        Map<Node, Node> map = new HashMap<>();
        Node node = map.getOrDefault(head, new Node(head.val));
        map.put(head, node);
        return copy(head, node, map);

    }

    private Node copy(Node head, Node node, Map<Node, Node> map) {
        if (head.random != null) {
            node.random = map.getOrDefault(head.random, new Node(head.random.val));
            map.put(head.random, node.random);
        }
        if (head.next != null) {
            node.next = map.getOrDefault(head.next, new Node(head.next.val));
            map.put(head.next, node.next);
            copy(head.next, node.next, map);
        }
        return node;
    }

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    /*
     * Revision Note - Copy List with Random Pointer (Medium)
     *
     * Pattern: deep copy of a pointer structure with a HashMap<original, copy>. Same idea as
     * Clone Graph (133) and Clone Binary Tree With Random Pointer (1485).
     *
     * Key Insight: a random pointer can point FORWARD to a node whose copy doesn't exist yet. The
     * map makes "get the copy of X" safe at any moment: getOrDefault + put creates the copy on
     * first sight, and every later reference (via next or via another random) reuses that same
     * object, so each original maps to exactly one copy.
     *
     * Gotchas:
     * - Duplicate values are common ([3,3,3]), so the map key must be the node itself, never val.
     *   Node doesn't override equals/hashCode, so HashMap compares by identity - which is right.
     * - Random can be null, point backward, point forward, or point at the node itself; the
     *   map-lookup-before-create covers all four.
     * - getOrDefault(k, new Node(...)) builds a throwaway Node even when the key exists; harmless,
     *   but computeIfAbsent(k, x -> new Node(x.val)) builds it only when needed.
     * - Recursion along next is n deep; fine for n <= 1000.
     *
     * Complexity: O(n) time, O(n) space for the map (+ O(n) recursion stack).
     *
     * O(1)-space alternative (the Reorder List 143 weave):
     *   1. weave: insert each copy right after its original      A -> A' -> B -> B'
     *   2. randoms: A.next.random = A.random == null ? null : A.random.next
     *   3. unweave: split the alternating nodes back into two lists, restoring the original
     *
     * Template:
     *   map = {}; get(x) = x == null ? null : map.computeIfAbsent(x, k -> new Node(k.val))
     *   for cur in list: get(cur).next = get(cur.next); get(cur).random = get(cur.random)
     *   return get(head)
     */

    // Builds a list from [val, randomIndex] pairs; randomIndex -1 means null.
    private static Node build(int[][] spec) {
        Node[] nodes = new Node[spec.length];
        for (int i = 0; i < spec.length; i++)
            nodes[i] = new Node(spec[i][0]);
        for (int i = 0; i < spec.length; i++) {
            if (i + 1 < spec.length)
                nodes[i].next = nodes[i + 1];
            if (spec[i][1] >= 0)
                nodes[i].random = nodes[spec[i][1]];
        }
        return spec.length == 0 ? null : nodes[0];
    }

    // Prints the list in LeetCode's [[val,randomIndex],...] form.
    private static String show(Node head) {
        Map<Node, Integer> index = new IdentityHashMap<>();
        int i = 0;
        for (Node cur = head; cur != null; cur = cur.next)
            index.put(cur, i++);
        StringBuilder sb = new StringBuilder("[");
        for (Node cur = head; cur != null; cur = cur.next) {
            sb.append("[").append(cur.val).append(",").append(cur.random == null ? "null" : index.get(cur.random)).append("]");
            if (cur.next != null)
                sb.append(",");
        }
        return sb.append("]").toString();
    }

    // True when no node of the copy is a node of the original.
    private static boolean isDeep(Node original, Node copy) {
        Map<Node, Boolean> seen = new IdentityHashMap<>();
        for (Node cur = original; cur != null; cur = cur.next)
            seen.put(cur, true);
        for (Node cur = copy; cur != null; cur = cur.next)
            if (seen.containsKey(cur) || (cur.random != null && seen.containsKey(cur.random)))
                return false;
        return true;
    }

    public static void main(String[] args) {
        CopyListWithRandomPointer C = new CopyListWithRandomPointer();

        System.out.println("Test 1: " + show(C.copyRandomList(build(new int[][]{{7, -1}, {13, 0}, {11, 4}, {10, 2}, {1, 0}}))) + " (Expected: [[7,null],[13,0],[11,4],[10,2],[1,0]])");
        System.out.println("Test 2: " + show(C.copyRandomList(build(new int[][]{{1, 1}, {2, 1}}))) + " (Expected: [[1,1],[2,1]])");
        System.out.println("Test 3: " + show(C.copyRandomList(build(new int[][]{{3, -1}, {3, 0}, {3, -1}}))) + " (Expected: [[3,null],[3,0],[3,null]])");   // duplicate values: map by node, not val
        System.out.println("Test 4: " + C.copyRandomList(null) + " (Expected: null)");                                                                          // empty list
        System.out.println("Test 5: " + show(C.copyRandomList(build(new int[][]{{1, 0}}))) + " (Expected: [[1,0]])");                                          // random points at itself
        System.out.println("Test 6: " + show(C.copyRandomList(build(new int[][]{{1, 3}, {2, 3}, {3, 3}, {4, -1}}))) + " (Expected: [[1,3],[2,3],[3,3],[4,null]])");  // forward randoms, all to the same node
        Node original = build(new int[][]{{7, -1}, {13, 0}, {11, 4}, {10, 2}, {1, 0}});
        System.out.println("Test 7: " + isDeep(original, C.copyRandomList(original)) + " (Expected: true)");                                                   // no pointer back into the original
    }
}
