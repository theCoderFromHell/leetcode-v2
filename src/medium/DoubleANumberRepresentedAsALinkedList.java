package medium;

import common.ListNode;

// https://leetcode.com/problems/double-a-number-represented-as-a-linked-list/
public class DoubleANumberRepresentedAsALinkedList {
    int carry;
    public ListNode doubleIt(ListNode head) {
        carry = 0;
        multiplyWith2(head);
        if (carry == 0)
            return head;
        else {
            ListNode newHead = new ListNode(carry);
            newHead.next = head;
            return newHead;
        }
    }

    private void multiplyWith2(ListNode head) {
        if (head == null)
            return;
        multiplyWith2(head.next);
        int newValue = carry + head.val * 2;
        head.val = newValue % 10;
        carry = newValue / 10;
    }

    /*
     * Revision Note - Double a Number Represented as a Linked List (Medium)
     *
     * Pattern: post-order recursion to process a singly linked list from the tail - recurse to the
     * end first, do the work on the way back. The call stack plays the role of a reversed list.
     *
     * Key Insight: the carry starts at the least significant digit, which is the LAST node. Going
     * down the recursion reaches it; unwinding then visits digits right-to-left, exactly the order
     * schoolbook doubling needs. Whatever carry is left after the head means one new node in front.
     *
     * Gotchas:
     * - Don't convert to a number: up to 1e4 digits, a long holds 19.
     * - The final carry is at most 1 (9 * 2 + 1 = 19), so at most ONE node is prepended.
     * - "0" -> "0": carry stays 0, so no leading zero is added.
     * - carry is an instance field, so reset it at the start of doubleIt; returning it from the
     *   helper instead (int helper(node)) removes the shared state altogether.
     * - Recursion depth is n (1e4): fine here, but it's O(n) stack.
     *
     * Complexity: O(n) time, O(n) space (recursion stack).
     *
     * O(1)-space alternative (look ahead, no reversal): doubling a digit carries at most 1, and the
     * carry INTO node i is 1 exactly when the next digit is >= 5. So:
     *   if head.val >= 5: head = new ListNode(0, head)
     *   for node in list: node.val = node.val * 2 % 10 + (node.next != null && node.next.val >= 5 ? 1 : 0)
     *
     * Template:
     *   carry = helper(head)            // helper: recurse(next) first, then digit = 2*val + carry
     *   return carry > 0 ? new ListNode(carry) -> head : head
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
        DoubleANumberRepresentedAsALinkedList D = new DoubleANumberRepresentedAsALinkedList();

        System.out.println("Test 1: " + show(D.doubleIt(list("189"))) + " (Expected: 378)");
        System.out.println("Test 2: " + show(D.doubleIt(list("999"))) + " (Expected: 1998)");          // carry out of the first digit: one extra node at the front
        System.out.println("Test 3: " + show(D.doubleIt(list("0"))) + " (Expected: 0)");               // zero stays a single node, no leading zero added
        System.out.println("Test 4: " + show(D.doubleIt(list("4"))) + " (Expected: 8)");               // single digit, no carry
        System.out.println("Test 5: " + show(D.doubleIt(list("5"))) + " (Expected: 10)");              // single digit that grows the list
        System.out.println("Test 6: " + show(D.doubleIt(list("199"))) + " (Expected: 398)");           // carries chain through, but the head absorbs the last one
        System.out.println("Test 7: " + show(D.doubleIt(list("500"))) + " (Expected: 1000)");         // only the head carries; zeros after it stay zero
        System.out.println("Test 8: " + show(D.doubleIt(list("909"))) + " (Expected: 1818)");          // a 0 between two carries: carry in, nothing out
        System.out.println("Test 9: " + show(D.doubleIt(list("1234567890123456789012345"))) + " (Expected: 2469135780246913578024690)");   // 25 digits: too big for a long
    }
}
