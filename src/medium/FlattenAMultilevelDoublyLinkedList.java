package medium;

// https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/
public class FlattenAMultilevelDoublyLinkedList {
    public Node flatten(Node head) {
        if (head == null)
            return null;
        return flattenNodes(head)[0];
    }

    private Node[] flattenNodes(Node head) {
        Node tail = null;
        Node curr = head;
        Node next;
        while (curr != null) {
            next = curr.next;
            if (next == null)
                tail = curr;
            if (curr.child != null) {
                Node[] result = flattenNodes(curr.child);
                curr.next = result[0];
                result[0].prev = curr;
                result[1].next = next;
                if (next != null)
                    next.prev = result[1];
                else
                    tail = result[1];
                curr.child = null;
            }
            curr = next;
        }
        return new Node[]{head, tail};
    }
    /*
     * Revision Note — Flatten a Multilevel Doubly Linked List (Medium)
     * Pattern: Recursive [head, tail] return, splice child subtree inline
     * Key Insight: Capture curr.next before any mutation, then advance curr to that
     *              original next — the child subtree is already handled by the recursive call.
     * Gotchas:
     *   - Five pointer updates at every splice: curr.next, child_head.prev,
     *     child_tail.next, next.prev (if next exists), and curr.child = null.
     *     Missing any one corrupts the doubly linked structure or leaves stale child pointers.
     *   - Tail must be updated in two branches: curr when next==null (no child), and
     *     result[1] when next==null after a child splice (child's tail becomes the new tail).
     *   - Recursion depth equals max nesting level D; could StackOverflow for very deep inputs.
     *     Iterative approach with a Deque avoids this entirely.
     */
    public static void main(String[] args) {
        FlattenAMultilevelDoublyLinkedList F = new FlattenAMultilevelDoublyLinkedList();

        // Test 1: null input
        System.out.println("Test 1: " + F.flatten(null) + " (Expected: null)");

        // Test 2: single node, no child
        Node a = node(1);
        System.out.println("Test 2: " + list(F.flatten(a)) + " (Expected: [1])");

        // Test 3: 1->2->3, no children
        Node b1 = node(1), b2 = node(2), b3 = node(3);
        link(b1, b2); link(b2, b3);
        System.out.println("Test 3: " + list(F.flatten(b1)) + " (Expected: [1,2,3])");

        // Test 4: 1->2, 1.child=3->4  =>  1-3-4-2
        Node c1 = node(1), c2 = node(2), c3 = node(3), c4 = node(4);
        link(c1, c2); link(c3, c4); c1.child = c3;
        System.out.println("Test 4: " + list(F.flatten(c1)) + " (Expected: [1,3,4,2])");

        // Test 5: LeetCode example 1
        // 1->2->3->4->5->6, 3.child=7->8->9->10, 8.child=11->12
        // Expected: 1-2-3-7-8-11-12-9-10-4-5-6
        Node[] d = new Node[13];
        for (int i = 1; i <= 12; i++) d[i] = node(i);
        link(d[1],d[2]); link(d[2],d[3]); link(d[3],d[4]); link(d[4],d[5]); link(d[5],d[6]);
        link(d[7],d[8]); link(d[8],d[9]); link(d[9],d[10]);
        link(d[11],d[12]);
        d[3].child = d[7]; d[8].child = d[11];
        System.out.println("Test 5: " + list(F.flatten(d[1])) + " (Expected: [1,2,3,7,8,11,12,9,10,4,5,6])");

        // Test 6: verify prev pointers — traverse backward from tail of Test 3 result
        Node e1 = node(1), e2 = node(2), e3 = node(3);
        link(e1, e2); link(e2, e3);
        Node eHead = F.flatten(e1);
        Node eTail = eHead;
        while (eTail.next != null) eTail = eTail.next;
        System.out.println("Test 6 (prev backwards): " + listBack(eTail) + " (Expected: [3,2,1])");
    }

    private static Node node(int val) { Node n = new Node(); n.val = val; return n; }
    private static void link(Node a, Node b) { a.next = b; b.prev = a; }
    private static String list(Node head) {
        if (head == null) return "null";
        StringBuilder sb = new StringBuilder("[");
        for (Node c = head; c != null; c = c.next) {
            sb.append(c.val);
            if (c.next != null) sb.append(",");
        }
        return sb.append("]").toString();
    }
    private static String listBack(Node tail) {
        StringBuilder sb = new StringBuilder("[");
        for (Node c = tail; c != null; c = c.prev) {
            sb.append(c.val);
            if (c.prev != null) sb.append(",");
        }
        return sb.append("]").toString();
    }
}

class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
};
