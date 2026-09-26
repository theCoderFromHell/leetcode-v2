package medium;

import common.ListNode;

// https://leetcode.com/problems/remove-duplicates-from-an-unsorted-linked-list/
public class RemoveDuplicatesFromAnUnsortedLinkedList {
    public ListNode deleteDuplicatesUnsorted(ListNode head) {
        int[] duplicates = new int[100001];
        countVisited(head, duplicates);
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode curr = dummy;
        while (curr.next != null) {
            if (duplicates[curr.next.val] > 1) {
                curr.next = curr.next.next;
            } else
                curr = curr.next;
        }
        return dummy.next;
    }

    private void countVisited(ListNode head, int[] duplicates) {
        while (head != null) {
            duplicates[head.val]++;
            head = head.next;
        }
    }

    /*
     * Revision Note — Remove Duplicates From an Unsorted Linked List (Medium)
     *
     * Pattern: Two-pass — frequency detection, then dummy-node removal
     *
     * Key Insight: Pass 1 records how often each VALUE occurs. Pass 2 walks with a dummy
     * node and deletes every node whose value occurred more than once. Unlike the sorted
     * variant, duplicates are scattered and you keep ZERO copies — so nothing can be
     * decided in a single pass.
     *
     * Gotchas:
     * - val is capped at 1e5, so int[100001] beats HashSet<Integer>: no boxing, no hashing.
     *   Falls apart if values are unbounded — use HashMap + merge(val, 1, Integer::sum) then
     * - ONE counter array is enough (check > 1). A second "seen" array is only needed with
     *   Sets, because HashSet.add cannot count — a counter already remembers the count
     * - A HashSet alone cannot detect duplicates: adding every value marks everything as
     *   duplicated and wipes the list. set.add() returning false is the only signal, and
     *   it is transient — capture it at insert time or use a counter
     * - Dummy node is essential: the head itself may be deleted
     * - Do NOT advance curr after a deletion — the new curr.next may also need deleting
     *   (consecutive duplicates like [2,2,2] break otherwise)
     *
     * Template:
     *   pass 1: int[] freq = new int[100001]
     *           for each node: freq[node.val]++
     *   pass 2: dummy.next = head; curr = dummy
     *           while curr.next != null:
     *             if freq[curr.next.val] > 1: curr.next = curr.next.next   // no advance
     *             else: curr = curr.next
     *           return dummy.next
     */
    private static String format(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append(", ");
            head = head.next;
        }
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        RemoveDuplicatesFromAnUnsortedLinkedList R = new RemoveDuplicatesFromAnUnsortedLinkedList();

        System.out.println("Test 1: " + format(R.deleteDuplicatesUnsorted(ListNode.createList(new int[]{1, 2, 3, 2})))       + " (Expected: [1, 3])");
        System.out.println("Test 2: " + format(R.deleteDuplicatesUnsorted(ListNode.createList(new int[]{2, 1, 1, 2})))       + " (Expected: [])");
        System.out.println("Test 3: " + format(R.deleteDuplicatesUnsorted(ListNode.createList(new int[]{3, 2, 2, 1, 3, 2, 4}))) + " (Expected: [1, 4])");
        System.out.println("Test 4: " + format(R.deleteDuplicatesUnsorted(ListNode.createList(new int[]{1})))                + " (Expected: [1])");
        System.out.println("Test 5: " + format(R.deleteDuplicatesUnsorted(ListNode.createList(new int[]{1, 1})))             + " (Expected: [])");
        System.out.println("Test 6: " + format(R.deleteDuplicatesUnsorted(ListNode.createList(new int[]{1, 2, 3})))          + " (Expected: [1, 2, 3])");
    }
}
