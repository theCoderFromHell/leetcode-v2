package medium;

import common.ListNode;

public class PartitionList {
    public static ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null)
            return head;
        ListNode left = null, right = null;
        ListNode leftPrev = null, rightPrev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode node = new ListNode(curr.val);
            if (curr.val < x) {
                if (left == null)
                    left = node;
                else
                    leftPrev.next = node;
                leftPrev = node;
            } else {
                if (right == null)
                    right = node;
                else
                    rightPrev.next = node;
                rightPrev = node;
            }
            curr = curr.next;
        }
        if (left == null)
            return right;
        leftPrev.next = right;
        return left;
    }
    public static void main(String[] args) {
        ListNode head = ListNode.createList(new int[]{1, 4, 3, 2, 5, 2});
        ListNode result = partition(head, 3);
        ListNode.printList(result);

    }
}
