package medium;

import common.ListNode;

public class RemoveNthNodeFromEndOfList {
    int k;
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null)
            return null;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        k = 0;
        remove(dummy, n);
        return dummy.next;
    }

    private void remove(ListNode head, int n) {
        if (head == null)
            return;
        remove(head.next, n);
        if (n == k) {
            head.next = head.next.next;
        }
        k++;
    }

    public static void main(String[] args) {
        RemoveNthNodeFromEndOfList R = new RemoveNthNodeFromEndOfList();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode.printList(R.removeNthFromEnd(head, 5));

    }
}
