package medium;

import common.ListNode;

public class RotateList {
    public static ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0)
            return head;
        int N = size(head);
        k %= N;
        if (k == 0)
            return head;
        int count = 1;
        ListNode curr = head;
        while (curr.next != null)
            curr = curr.next;
        curr.next = head;
        curr = head;
        while (curr != null && count < N-k) {
            curr = curr.next;
            count++;
        }
        head = curr.next;
        curr.next = null;
        return head;
    }

    private static int size(ListNode head) {
        int size = 0;
        while (head != null) {
            size++;
            head = head.next;
        }
        return size;
    }

    public static void main(String[] args) {
        ListNode head = ListNode.createList(new int[] {1, 2, 3, 4, 5});
        ListNode result = rotateRight(head, 5);
        ListNode.printList(result);
    }
}
