package medium;

import common.ListNode;


public class InsertGreatestCommonDivisorsInLinkedList {
    public ListNode insertGreatestCommonDivisors(ListNode head) {
        ListNode current = head;
        while (current != null && current.next != null) {
            int gcd = gcd(current.val, current.next.val);
            ListNode next = current.next;
            current.next = new ListNode(gcd);
            current.next.next = next;
            current = next;
        }
        return head;

    }

    int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    public static void main(String[] args) {
        InsertGreatestCommonDivisorsInLinkedList I = new InsertGreatestCommonDivisorsInLinkedList();
        ListNode head = ListNode.createList(new int[]{18,6,10,3});
        ListNode result = I.insertGreatestCommonDivisors(head);
        ListNode.printList(result);
    }
}
