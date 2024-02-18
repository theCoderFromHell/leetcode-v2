package medium;

import common.ListNode;

public class SwapNodesInPairs {
    public static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode curr = head;
        ListNode next = head.next;
        ListNode newHead = next;
        ListNode prev = null;
        while (curr != null && next != null) {
            if (prev != null)
                prev.next = next;
            prev = curr;
            curr.next = next.next;
            next.next = curr;
            curr = curr.next;
            next = (curr == null) ? null : curr.next;
        }
        return newHead;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        System.out.println(swapPairs(head));
    }
}
