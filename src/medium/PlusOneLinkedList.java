package medium;

import common.ListNode;

// https://leetcode.com/problems/plus-one-linked-list/
public class PlusOneLinkedList {
    int carry = 0;
    public ListNode plusOne(ListNode head) {
        carry = 0;
        addOne(head);
        if (carry > 0) {
            ListNode newHead = new ListNode(carry);
            newHead.next = head;
            return newHead;
        }
        return head;
    }

    private void addOne(ListNode head) {
        if (head == null)
            return;
        addOne(head.next);
        int value = head.val + carry + (head.next == null ? 1 : 0);
        head.val = value % 10;
        carry = value / 10;
    }

    /*
     * Revision Note - Plus One Linked List (Medium)
     *
     * Pattern: post-order recursion to process the list from the tail (same as 2816 Double a
     * Number), carrying into the next-more-significant digit on the way back up.
     *
     * Key Insight: adding one is a carry of 1 into the last digit. The carry only keeps going
     * while digits are 9, and whatever survives past the head becomes one new front node.
     *
     * Gotchas:
     * - carry is an instance field: reset it at the start of every call. LeetCode builds a new
     *   Solution per test case, so a stale carry passed there - but on one reused object,
     *   9 -> 10 left carry = 1 and the next call turned 99 into 101.
     * - The helper must recurse into ITSELF (addOne), not the public method. Calling plusOne
     *   from addOne still visits every node, but builds a throwaway front node at each level
     *   with a carry - right answer by accident.
     * - 0 -> 1, and all 9s -> 1 followed by zeros (one extra node, never more).
     *
     * Complexity: O(n) time, O(n) recursion stack (n <= 100).
     *
     * O(1)-space alternative (19's dummy + trailing pointer): only the LAST non-9 digit goes up
     * by one and every digit after it becomes 0. A dummy 0 in front is that digit when all are 9.
     *   dummy -> head; last = dummy
     *   for n in list: if n.val != 9: last = n
     *   last.val++; zero every node after last
     *   return dummy.val == 1 ? dummy : head
     *
     * Template:
     *   carry = 1; recurse(head)          // recurse(next) first, then v = val + carry
     *   return carry > 0 ? new node(carry) -> head : head
     */

    // Builds a list from a digit string, most significant digit first.
    private static ListNode list(String digits) {
        int[] values = new int[digits.length()];
        for (int i = 0; i < values.length; i++)
            values[i] = digits.charAt(i) - '0';
        return ListNode.createList(values);
    }

    // Reads a list back as a digit string.
    private static String show(ListNode head) {
        StringBuilder sb = new StringBuilder();
        for (ListNode curr = head; curr != null; curr = curr.next)
            sb.append(curr.val);
        return sb.toString();
    }

    public static void main(String[] args) {
        PlusOneLinkedList P = new PlusOneLinkedList();

        System.out.println("Test 1: " + show(P.plusOne(list("123"))) + " (Expected: 124)");
        System.out.println("Test 2: " + show(P.plusOne(list("0"))) + " (Expected: 1)");            // zero
        System.out.println("Test 3: " + show(P.plusOne(list("9"))) + " (Expected: 10)");           // single 9: new front node
        System.out.println("Test 4: " + show(P.plusOne(list("99"))) + " (Expected: 100)");         // same object right after a carry-out: stale carry gave 101
        System.out.println("Test 5: " + show(P.plusOne(list("999"))) + " (Expected: 1000)");       // all 9s
        System.out.println("Test 6: " + show(P.plusOne(list("129"))) + " (Expected: 130)");        // carry stops at the first non-9 from the end
        System.out.println("Test 7: " + show(P.plusOne(list("1099"))) + " (Expected: 1100)");      // a 0 absorbs the carry
        System.out.println("Test 8: " + show(P.plusOne(list("9899"))) + " (Expected: 9900)");      // a 9 BEFORE the last non-9 stays untouched
    }
}
