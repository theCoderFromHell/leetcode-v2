package hard;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

// https://leetcode.com/problems/max-stack/
public class MaxStack {
    DoubleListNode head;
    DoubleListNode tail;
    TreeMap<Integer, List<DoubleListNode>> nodes;
    public MaxStack() {
        this.head = null;
        this.tail = null;
        this.nodes = new TreeMap<>();
    }

    public void push(int x) {
        DoubleListNode node = new DoubleListNode(x);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.previous = tail;
            tail = node;
        }
        nodes.computeIfAbsent(x, k -> new ArrayList<>()).add(node);
    }

    public int pop() {
        int value = tail.value;
        if (head == tail) {
            head = tail = null;
        } else {
            DoubleListNode prev = tail.previous;
            prev.next = null;
            tail.previous = null;
            tail = prev;
        }
        List<DoubleListNode> tailNodes = nodes.get(value);
        tailNodes.removeLast();
        if (tailNodes.isEmpty())
            nodes.remove(value);
        return value;
    }

    public int top() {
        return tail.value;
    }

    public int peekMax() {
        return nodes.lastKey();
    }

    public int popMax() {
        int value = nodes.lastKey();
        List<DoubleListNode> maxNodes = nodes.get(value);
        DoubleListNode maxNode = maxNodes.getLast();
        if (maxNode.previous != null)
            maxNode.previous.next = maxNode.next;
        else
            head = maxNode.next;
        if (maxNode.next != null)
            maxNode.next.previous = maxNode.previous;
        else
            tail = maxNode.previous;
        maxNodes.removeLast();
        if (maxNodes.isEmpty())
            nodes.remove(value);
        return value;
    }

    /*
     * Revision Note — Max Stack (Hard)
     *
     * Pattern: Doubly linked list (stack order, O(1) unlink anywhere) + TreeMap<value, List<node>>
     * (max order) — two indexes over the same nodes, kept in sync on every removal.
     *
     * Key insight: Min Stack's "aggregate per entry" trick dies on popMax, because the max can sit
     * in the MIDDLE. So separate the two jobs: the DLL removes by position, the TreeMap finds by
     * value. Appending each node to its value's list means getLast() is always the copy nearest
     * the top — exactly the one popMax must remove, and also the one pop() removes (the tail is
     * the newest push of its value).
     *
     * Gotchas:
     *  - popMax must move head/tail when the max IS the head/tail — the max is usually on top,
     *    so forgetting this breaks LeetCode's own example (top() returns the removed node).
     *  - Remove the TreeMap key once its list empties, in BOTH pop and popMax — else lastKey()
     *    returns a value that is gone, and getLast() throws on the empty list.
     *  - Sentinel head/tail nodes remove every null branch: one unlink(node) serves pop and popMax.
     *  - toString() that prints previous/next recurses forever on a doubly linked list.
     *  - List.getLast()/removeLast() need Java 21 (SequencedCollection).
     *
     * Complexity: top O(1); push / pop / peekMax / popMax O(log n) (TreeMap); O(n) space.
     *
     * Template:
     *   push(x):  node = append to DLL tail;  map[x].add(node)
     *   pop():    node = tail; unlink(node);  map[node.val].removeLast()  (drop key if empty)
     *   popMax(): v = map.lastKey(); node = map[v].removeLast() (drop key if empty); unlink(node)
     *
     * Alternative: lazy deletion — stack + max-heap of {val, id} ordered (val desc, id desc) and a
     * removed-id set; discard dead entries at the top before reading. Amortised O(log n).
     */
    public static void main(String[] args) {
        MaxStack M = new MaxStack();
        M.push(5);
        M.push(1);
        M.push(5);
        System.out.println("Test 1: " + M.top() + " (Expected: 5)");
        System.out.println("Test 2: " + M.popMax() + " (Expected: 5)");
        System.out.println("Test 3: " + M.top() + " (Expected: 1)");
        System.out.println("Test 4: " + M.peekMax() + " (Expected: 5)");
        System.out.println("Test 5: " + M.pop() + " (Expected: 1)");
        System.out.println("Test 6: " + M.top() + " (Expected: 5)");

        // max is the tail — tail pointer must move back
        M = new MaxStack();
        M.push(1);
        M.push(5);
        M.popMax();
        System.out.println("Test 7: " + M.top() + " (Expected: 1)");

        // max is the head — head pointer must move forward (was a NullPointerException on pop)
        M = new MaxStack();
        M.push(5);
        M.push(1);
        M.popMax();
        System.out.println("Test 8: " + M.pop() + " (Expected: 1)");
        M.push(3);
        System.out.println("Test 9: " + M.top() + " (Expected: 3)");

        // last copy of the max removed — its TreeMap key must go
        M = new MaxStack();
        M.push(5);
        M.push(1);
        M.popMax();
        System.out.println("Test 10: " + M.peekMax() + " (Expected: 1)");

        // duplicate max — popMax removes the copy nearest the top
        M = new MaxStack();
        M.push(7);
        M.push(2);
        M.push(7);
        M.push(3);
        System.out.println("Test 11: " + M.popMax() + " (Expected: 7)");
        System.out.println("Test 12: " + M.pop() + " (Expected: 3)");
        System.out.println("Test 13: " + M.top() + " (Expected: 2)");
        System.out.println("Test 14: " + M.peekMax() + " (Expected: 7)");

        // pop() removes the max — map must be updated by pop too
        M = new MaxStack();
        M.push(2);
        M.push(9);
        M.pop();
        System.out.println("Test 15: " + M.peekMax() + " (Expected: 2)");

        // value extremes
        M = new MaxStack();
        M.push(-10000000);
        M.push(10000000);
        System.out.println("Test 16: " + M.popMax() + " (Expected: 10000000)");
        System.out.println("Test 17: " + M.peekMax() + " (Expected: -10000000)");
    }

    class DoubleListNode {
        int value;
        DoubleListNode previous;
        DoubleListNode next;

        public DoubleListNode(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "DoubleListNode{" +
                    "value=" + value +
                    ", previous=" + previous +
                    ", next=" + next +
                    '}';
        }
    }
}
