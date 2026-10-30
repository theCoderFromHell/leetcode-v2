package medium;

// https://leetcode.com/problems/design-linked-list/
public class DesignLinkedList {

static class MyLinkedList {
    int size;
    DoubleLinkedListNode head;
    DoubleLinkedListNode tail;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public int get(int index) {
        if (size == 0 || index < 0 || index  >= size)
            return -1;
        if (index == 0)
            return head.val;
        if (index == size-1)
            return tail.val;
        DoubleLinkedListNode curr = nodeAt(index);
        return (curr != null) ? curr.val : -1;
    }

    public void addAtHead(int val) {
        DoubleLinkedListNode node = new DoubleLinkedListNode(val);
        if (head != null)
            head.previous = node;
        node.next = head;
        head = node;
        size++;
        if (size == 1)
            tail = node;
    }

    public void addAtTail(int val) {
        DoubleLinkedListNode node = new DoubleLinkedListNode(val);
        if (tail != null)
            tail.next = node;
        node.previous = tail;
        tail = node;
        size++;
        if (size == 1)
            head = node;
    }

    public void addAtIndex(int index, int val) {
        if (index < 0 || index  > size)
            return;

        if (index == 0)
            addAtHead(val);
        else if (index == size)
            addAtTail(val);
        else {
            DoubleLinkedListNode node = new DoubleLinkedListNode(val);
            DoubleLinkedListNode curr = nodeAt(index);
            DoubleLinkedListNode prev = curr.previous;
            if (prev != null) {
                prev.next = node;
                node.previous = prev;
            }
            curr.previous = node;
            node.next = curr;
            size++;
        }
    }

    public void deleteAtIndex(int index) {
        if ( size == 0 || index < 0 || index  >= size)
            return;
        if (index == 0) {
            head = head.next;
            if (head != null)
                head.previous = null;
            size--;
            if (size == 0)
                tail = null;
        } else if (index == size-1) {
            tail = tail.previous;
            if (tail != null)
                tail.next = null;
            size--;
            if (size == 0)
                head = null;
        } else {
            DoubleLinkedListNode curr = nodeAt(index);
            if (curr == null)
                return;
            if (curr.previous != null) {
                curr.previous.next = curr.next;
            }
            if (curr.next != null) {
                curr.next.previous = curr.previous;
            }
            curr.previous = curr.next = null;
            size--;
        }
    }

    // Walks from whichever end is nearer; callers guarantee 0 <= index < size
    private DoubleLinkedListNode nodeAt(int index) {
        DoubleLinkedListNode curr = index < size - 1 - index ? head : tail;
        int idx;
        if (curr == head) {
            idx = 0;
            while (curr != null && idx < index) {
                curr = curr.next;
                idx++;
            }
        } else {
            idx = size - 1;
            while (curr != null && idx > index) {
                curr = curr.previous;
                idx--;
            }
        }
        return curr;
    }

    static class DoubleLinkedListNode {
        public int val;
        public DoubleLinkedListNode previous;
        public DoubleLinkedListNode next;

        public DoubleLinkedListNode(int val) {
            this.val = val;
            this.next = null;
            this.previous = null;
        }
    }
}

    /*
     * Revision Note - Design Linked List (Medium)
     *
     * Pattern: doubly linked list with head, tail and size; one nodeAt(index) helper walks from
     * whichever end is nearer, shared by get, addAtIndex and deleteAtIndex.
     *
     * Key Insight: every insert sets FOUR links (node.prev, node.next, prev.next, next.prev). At the
     * ends, two of those are head / tail themselves, so route addAtIndex(0) to addAtHead and
     * addAtIndex(size) to addAtTail instead of re-writing the end cases by hand.
     *
     * Gotchas:
     * - The original hand-written end branches each dropped one link: index == size never set
     *   oldTail.next, so a forward walk fell off after the old tail (head = head.next -> null, then
     *   NPE; Test 3), and index == 0 never set oldHead.previous, so a backward walk returned -1
     *   (Test 4). Delegating fixed both.
     * - With size tracked, a walk inside [0, size) never meets null. A guard like
     *   `if (curr == null) return` doesn't handle a case, it hides a broken link: it turned the
     *   backward-walk bug into a silent -1 instead of a crash.
     * - addAtIndex accepts index == size (append); get and deleteAtIndex reject it (Test 5).
     * - Deleting the only node must clear BOTH head and tail, or the next add links to a ghost (Test 6).
     * - 0 <= val <= 1000, so the -1 sentinel never collides with a stored value.
     *
     * Complexity: addAtHead / addAtTail O(1); get / addAtIndex / deleteAtIndex O(min(i, n - i)).
     * O(n) space.
     *
     * Alternative: two sentinel nodes (head <-> tail, never removed) make every node have a
     * non-null prev and next, so there are no end cases at all: addAtHead / addAtTail become
     * addAtIndex(0) / addAtIndex(size), and nodeAt can take -1 and size to return the sentinels.
     * Same shape as LRU Cache (146).
     *
     * Template (sentinels):
     *   nodeAt(i): i < size/2 ? walk next from head (i + 1 steps) : walk prev from tail (size - i steps)
     *   insert(i, v): next = nodeAt(i); prev = next.prev; link prev <-> n <-> next; size++
     *   delete(i):    n = nodeAt(i); n.prev.next = n.next; n.next.prev = n.prev; size--
     */

    public static void main(String[] args) {
        MyLinkedList M = new MyLinkedList();

        // Test 1: LeetCode example 1
        M.addAtHead(1);
        M.addAtTail(3);
        M.addAtIndex(1, 2);
        System.out.println("Test 1.1: " + M.get(1) + " (Expected: 2)");
        M.deleteAtIndex(1);
        System.out.println("Test 1.2: " + M.get(1) + " (Expected: 3)");

        // Test 2: LeetCode example 2
        M = new MyLinkedList();
        M.addAtHead(1);
        M.deleteAtIndex(0);
        M.addAtTail(2);
        System.out.println("Test 2.1: " + M.get(0) + " (Expected: 2)");

        // Test 3: list built with addAtIndex(size, val), then the head deleted: the append must set
        // oldTail.next, or head.next is null and get(0) throws an NPE (original bug 1)
        M = new MyLinkedList();
        M.addAtHead(1);
        M.addAtIndex(1, 2);
        M.addAtIndex(2, 3);
        System.out.println("Test 3.1: " + M.get(0) + " (Expected: 1)");
        System.out.println("Test 3.2: " + M.get(1) + " (Expected: 2)");
        System.out.println("Test 3.3: " + M.get(2) + " (Expected: 3)");
        M.deleteAtIndex(0);
        System.out.println("Test 3.4: " + M.get(0) + " (Expected: 2)");

        // Test 4: addAtIndex(0, val) on a non-empty list, then get(1) walked backward from the tail:
        // the prepend must set oldHead.previous, or the walk hits null and returns -1 (original bug 2)
        M = new MyLinkedList();
        M.addAtTail(9);
        M.addAtIndex(0, 2);
        M.addAtHead(7);
        System.out.println("Test 4.1: " + M.get(1) + " (Expected: 2)");
        System.out.println("Test 4.2: " + M.get(2) + " (Expected: 9)");
        M.deleteAtIndex(2);
        System.out.println("Test 4.3: " + M.get(1) + " (Expected: 2)");
        System.out.println("Test 4.4: " + M.get(2) + " (Expected: -1)");

        // Test 5: index > size for addAtIndex (ignored), index == size for get and deleteAtIndex
        // (out of range): addAtIndex's bound is > size, the other two use >= size
        M = new MyLinkedList();
        M.addAtHead(5);
        M.addAtIndex(2, 8);
        System.out.println("Test 5.1: " + M.get(1) + " (Expected: -1)");
        M.deleteAtIndex(1);
        System.out.println("Test 5.2: " + M.get(0) + " (Expected: 5)");

        // Test 6: emptied, then rebuilt: deleting the only node must reset both head and tail
        M = new MyLinkedList();
        M.addAtTail(1);
        M.deleteAtIndex(0);
        System.out.println("Test 6.1: " + M.get(0) + " (Expected: -1)");
        M.addAtIndex(0, 4);
        M.addAtTail(6);
        System.out.println("Test 6.2: " + M.get(0) + " (Expected: 4)");
        System.out.println("Test 6.3: " + M.get(1) + " (Expected: 6)");

        // Test 7: insert in the back half (walks from the tail), delete in the front half (walks from
        // the head), then read every index: [0..5] -> [0, 2, 3, 9, 4, 5]
        M = new MyLinkedList();
        for (int v = 0; v < 6; v++)
            M.addAtTail(v);
        M.addAtIndex(4, 9);
        M.deleteAtIndex(1);
        System.out.println("Test 7.1: " + M.get(0) + " (Expected: 0)");
        System.out.println("Test 7.2: " + M.get(1) + " (Expected: 2)");
        System.out.println("Test 7.3: " + M.get(2) + " (Expected: 3)");
        System.out.println("Test 7.4: " + M.get(3) + " (Expected: 9)");
        System.out.println("Test 7.5: " + M.get(4) + " (Expected: 4)");
        System.out.println("Test 7.6: " + M.get(5) + " (Expected: 5)");
        System.out.println("Test 7.7: " + M.get(6) + " (Expected: -1)");
    }
}
