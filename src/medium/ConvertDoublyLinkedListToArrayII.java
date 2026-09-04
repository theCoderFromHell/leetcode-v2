package medium;

import common.Util;

// https://leetcode.com/problems/convert-doubly-linked-list-to-array-ii/
public class ConvertDoublyLinkedListToArrayII {
    public int[] toArray(Node node) {
        if (node == null)
            return new int[]{};
        while (node.prev != null) {
            node = node.prev;
        }
        Node head = node;
        int size = getSize(head);
        return fillArrayWithNodes(head, size);
    }

    private int[] fillArrayWithNodes(Node head, int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = head.val;
            head = head.next;
        }
        return result;
    }

    private int getSize(Node head) {
        int count = 0;
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }

    /*
     * Revision Note — Convert Doubly Linked List to Array II (Medium)
     *
     * Pattern: Walk to head via prev pointers, then linear scan
     *
     * Key Insight: Input can be any node in the list — rewind to head first using
     * prev pointers, then compute size in one pass and fill array in another.
     *
     * Gotchas:
     * - Input is NOT guaranteed to be the head — must walk prev to find it
     * - null input → return empty array (guard before the while loop)
     * - Two-pass (size then fill) is clean; could do one-pass with a List but
     *   that adds boxing overhead
     *
     * Template:
     *   while node.prev != null: node = node.prev   // rewind to head
     *   size = count nodes via next
     *   fill int[] from head via next
     */
    class Node {
        public int val;
        public Node prev;
        public Node next;
    };

    public static void main(String[] args) {
        ConvertDoublyLinkedListToArrayII C = new ConvertDoublyLinkedListToArrayII();

        // Build: 1 <-> 2 <-> 3 <-> 4 <-> 5
        Node n1 = C.new Node(); n1.val = 1;
        Node n2 = C.new Node(); n2.val = 2;
        Node n3 = C.new Node(); n3.val = 3;
        Node n4 = C.new Node(); n4.val = 4;
        Node n5 = C.new Node(); n5.val = 5;
        n1.next = n2; n2.prev = n1;
        n2.next = n3; n3.prev = n2;
        n3.next = n4; n4.prev = n3;
        n4.next = n5; n5.prev = n4;

        Util.printArray(C.toArray(n3)); // from middle → [1,2,3,4,5]
        Util.printArray(C.toArray(n1)); // from head   → [1,2,3,4,5]
        Util.printArray(C.toArray(n5)); // from tail   → [1,2,3,4,5]

        Node single = C.new Node(); single.val = 7;
        Util.printArray(C.toArray(single)); // single node → [7]

        Util.printArray(C.toArray(null));   // null → []
    }
}



