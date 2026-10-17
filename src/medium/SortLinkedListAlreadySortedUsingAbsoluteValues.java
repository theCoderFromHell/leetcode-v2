package medium;

import common.ListNode;

// https://leetcode.com/problems/sort-linked-list-already-sorted-using-absolute-values/
public class SortLinkedListAlreadySortedUsingAbsoluteValues {
    public ListNode sortLinkedList(ListNode head) {
        ListNode curr = head;
        ListNode temp, newHead = head;
        while (curr != null && curr.next != null) {
            if (curr.next.val < 0) {
                temp = curr.next;
                curr.next = curr.next.next;
                temp.next = newHead;
                newHead = temp;
            } else
                curr = curr.next;
        }
        return newHead;
    }

    /*
     * Revision Note - Sort Linked List Already Sorted Using Absolute Values (Medium)
     *
     * Pattern: one-pass pointer rewiring with head insertion - unlink selected nodes and push each
     * to the front of the list. Head insertion is how a list gets reversed, applied only to the
     * nodes that need it.
     *
     * Key Insight: sorted by |x|, the non-negatives are already in increasing order, and the
     * negatives are in DEcreasing order (-1, -3, -5 ...). So every negative met later is smaller
     * than all before it: moving each to the current front lands it in the right place, O(1) each.
     * The head never moves - it has the smallest |x|, so even if negative, every other negative
     * gets pushed in front of it.
     *
     * Gotchas:
     * - After unlinking curr.next, DON'T advance: the new curr.next hasn't been checked. Advancing
     *   anyway skipped the second of two adjacent negatives (1 -> -2 -> -3 gave -2 -> 1 -> -3).
     *   Advance only in the else branch - same trap as deleting from an array while iterating.
     * - Track newHead separately; head stays somewhere in the middle of the result.
     * - Zero counts as non-negative (val < 0 test), so it stays in place.
     *
     * Complexity: O(n) time, O(1) space - pointers only, no new nodes.
     *
     * Alternative: split into two lists (negatives by head insertion, non-negatives by tail
     * append), then join them. Same O(n)/O(1), more code, no cut-and-advance trap.
     *
     * Template:
     *   front = head; curr = head
     *   while curr.next != null:
     *       if curr.next.val < 0: moved = curr.next; curr.next = moved.next; moved.next = front; front = moved
     *       else: curr = curr.next
     *   return front
     */

    // Reads a list back as [a,b,c].
    private static String show(ListNode head) {
        StringBuilder sb = new StringBuilder("[");
        for (ListNode curr = head; curr != null; curr = curr.next)
            sb.append(curr.val).append(curr.next == null ? "" : ",");
        return sb.append("]").toString();
    }

    public static void main(String[] args) {
        SortLinkedListAlreadySortedUsingAbsoluteValues S = new SortLinkedListAlreadySortedUsingAbsoluteValues();

        System.out.println("Test 1: " + show(S.sortLinkedList(ListNode.createList(new int[]{0, 2, -5, 5, 10, -10}))) + " (Expected: [-10,-5,0,2,5,10])");
        System.out.println("Test 2: " + show(S.sortLinkedList(ListNode.createList(new int[]{0, 1, 2}))) + " (Expected: [0,1,2])");               // no negatives
        System.out.println("Test 3: " + show(S.sortLinkedList(ListNode.createList(new int[]{1}))) + " (Expected: [1])");                        // single node
        System.out.println("Test 4: " + show(S.sortLinkedList(ListNode.createList(new int[]{1, -2, -3}))) + " (Expected: [-3,-2,1])");          // adjacent negatives: the advance-after-unlink bug
        System.out.println("Test 5: " + show(S.sortLinkedList(ListNode.createList(new int[]{-1, -2}))) + " (Expected: [-2,-1])");               // negative head stays, others go in front
        System.out.println("Test 6: " + show(S.sortLinkedList(ListNode.createList(new int[]{-1, -2, -3, -4}))) + " (Expected: [-4,-3,-2,-1])"); // all negative: full reversal
        System.out.println("Test 7: " + show(S.sortLinkedList(ListNode.createList(new int[]{-2, 2, -3, -4, 4, 5}))) + " (Expected: [-4,-3,-2,2,4,5])");   // equal |x| both signs
        System.out.println("Test 8: " + show(S.sortLinkedList(ListNode.createList(new int[]{0, -1, 1}))) + " (Expected: [-1,0,1])");            // zero is non-negative
    }
}
