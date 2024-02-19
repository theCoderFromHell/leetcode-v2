package medium;

import common.ListNode;

public class DeleteTheMiddleNodeOfALinkedList {
    public ListNode deleteMiddle(ListNode head) {
        if (head == null || head.next == null)
            return null;
        if (head.next.next == null) {
            head.next = null;
            return head;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        slow.next = slow.next.next;
        return head;
    }
    public static void main(String[] args) {

    }
}
