package medium;

import common.ListNode;

public class OddEvenLinkedList {
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null)
            return head;
        ListNode even = head.next;
        ListNode oddCurr = head;
        ListNode evenCurr = head.next;
        ListNode curr = head.next.next;
        boolean isOdd = true;
        while (curr != null) {
            if (isOdd) {
                oddCurr.next = curr;
                oddCurr = oddCurr.next;
            } else {
                evenCurr.next = curr;
                evenCurr = evenCurr.next;
            }
            isOdd = !isOdd;
            curr = curr.next;
        }
        evenCurr.next = null;
        oddCurr.next = even;
        return head;
    }
}
