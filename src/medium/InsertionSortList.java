package medium;

import common.ListNode;

public class InsertionSortList {
    public static ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        int size = countNodes(head);
        ListNode result = head;
        for (int i = 2; i <= size; i++) {
            result = insert(result, 1, i);
        }
        return result;
    }

    private static ListNode insert(ListNode head, int count, int target) {
        if (head == null)
            return null;
        if (count == target)
            return head;
        ListNode node = insert(head.next, count+1, target);
        if (node != null && node.val < head.val) {
            head.next = node.next;
            node.next = head;
            return node;
        }
        head.next = node;
        return head;
    }

    private static int countNodes(ListNode head) {
        int count = 0;
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }

    public static void main(String[] args) {
        ListNode head = ListNode.createList(new int[]{-1,5,3,4,0});
        ListNode result = insertionSortList(head);
        ListNode.printList(result);
    }
}
