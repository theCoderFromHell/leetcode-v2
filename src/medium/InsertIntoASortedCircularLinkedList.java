package medium;

// https://leetcode.com/problems/insert-into-a-sorted-circular-linked-list/
public class InsertIntoASortedCircularLinkedList {
    public Node insert(Node head, int insertVal) {
        Node newNode = new Node(insertVal);
        if (head == null) {
            newNode.next = newNode;
            return newNode;
        }
        Node curr = head.next;
        Node previous = head;
        do {
            if (previous.val <= insertVal && insertVal <= curr.val)
                break;
            else if (previous.val > curr.val && (previous.val <= insertVal || insertVal <= curr.val))
                break;
            previous = curr;
            curr = curr.next;
        } while (previous != head);
        previous.next = newNode;
        newNode.next = curr;
        return head;
    }

    static class Node {
        public int val;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
    }

    /*
     * Revision Note - Insert into a Sorted Circular Linked List (Medium)
     *
     * Pattern: one lap over (previous, curr) pairs of a circular list, stopping at the first pair
     * the value fits into. do { ... } while (previous != head) visits every pair exactly once,
     * including the closing (last, head) pair that a plain while (curr != head) skips.
     *
     * Key Insight: a sorted circular list has exactly ONE drop pair (max -> min). A value fits
     * either an ordinary pair (prev <= x <= curr) or the drop pair, as a new max (x >= prev) OR a
     * new min (x <= curr). Checking the drop pair in the same pass means you never have to find
     * the minimum first.
     *
     * Gotchas:
     * - Break after inserting. Without it, a value equal to a node matches a second pair and gets
     *   linked twice, cutting out a node: [1,2,3] + 2 lost the original 2.
     * - The drop pair takes BOTH out-of-range sides. The first fix only had x <= curr, so a new
     *   max fell through to the fallback and landed after head: [3,4,1] + 5 -> 3,5,4,1.
     * - head is any node, not the minimum: a fallback of "insert before head" is wrong whenever
     *   head isn't the start of the sorted order.
     * - Finding the start by a running minimum fails on duplicate minimums (1a -> 1b -> 2 -> 3):
     *   whether you update on < or <=, some choice of head picks the wrong 1. The start is the
     *   node right after the drop, not "a smallest node".
     * - All values equal: no pair matches, so insert anywhere after the full lap.
     * - null head -> new self-loop, returned. Otherwise return the ORIGINAL head.
     * - The single-node special case isn't needed: the general loop sees the pair (head, head)
     *   once and falls back to inserting after head.
     * - Do ONE unconditional splice after the loop: after a break, (previous, curr) is the fit;
     *   after a full lap it's (head, head.next), the fallback. Guarding the splice with
     *   if (!inserted) during the refactor skipped every real fit.
     *
     * Complexity: O(n) time (one lap), O(1) space.
     *
     * Alternative (two passes): one lap to find the drop pair -> start = node after max (any node
     * if all equal); then walk from start and insert at the first ordinary fit, else before start.
     * Same O(n)/O(1), more code.
     *
     * Template:
     *   if head == null: x.next = x; return x
     *   prev = head; curr = head.next
     *   do:
     *       if prev <= v <= curr: break
     *       if prev > curr and (v >= prev or v <= curr): break
     *       prev = curr; curr = curr.next
     *   while prev != head
     *   prev.next = x; x.next = curr; return head      // after a full lap: prev = head
     */

    // Builds a circular list from values; the first value is the head.
    private static Node build(int... values) {
        if (values.length == 0)
            return null;
        Node head = new Node(values[0]);
        Node tail = head;
        for (int i = 1; i < values.length; i++) {
            tail.next = new Node(values[i]);
            tail = tail.next;
        }
        tail.next = head;
        return head;
    }

    // Reads one lap from head as [a,b,c].
    private static String show(Node head) {
        StringBuilder sb = new StringBuilder("[");
        Node curr = head;
        do {
            sb.append(curr.val).append(curr.next == head ? "" : ",");
            curr = curr.next;
        } while (curr != head);
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        InsertIntoASortedCircularLinkedList I = new InsertIntoASortedCircularLinkedList();

        System.out.println("Test 1: " + show(I.insert(build(3, 4, 1), 2)) + " (Expected: [3,4,1,2])");          // fits the closing (last, head) pair
        System.out.println("Test 2: " + show(I.insert(build(), 1)) + " (Expected: [1])");                       // empty: new self-loop
        System.out.println("Test 3: " + show(I.insert(build(1), 0)) + " (Expected: [1,0])");                    // single node
        System.out.println("Test 4: " + show(I.insert(build(3, 4, 1), 5)) + " (Expected: [3,4,5,1])");          // new max goes into the drop pair, not after head
        System.out.println("Test 5: " + show(I.insert(build(3, 4, 1), 0)) + " (Expected: [3,4,0,1])");          // new min goes into the drop pair too
        System.out.println("Test 6: " + show(I.insert(build(1, 2, 3), 2)) + " (Expected: [1,2,2,3])");          // equal to a node: two pairs match, insert once
        System.out.println("Test 7: " + show(I.insert(build(3, 1, 2), 1)) + " (Expected: [3,1,1,2])");          // equal to min, drop pair met first: no second insert
        System.out.println("Test 8: " + show(I.insert(build(4, 4, 0), 5)) + " (Expected: [4,4,5,0])");          // duplicate max before the drop
        System.out.println("Test 9: " + show(I.insert(build(1, 2, 3, 1), 0)) + " (Expected: [1,2,3,0,1])");     // duplicate min, head is the wrong 1
        System.out.println("Test 10: " + show(I.insert(build(3, 3, 3), 5)) + " (Expected: [3,5,3,3])");         // all equal: no pair fits, any spot is valid
    }
}
