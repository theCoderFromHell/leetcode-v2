package medium;

import common.ListNode;

public class RemoveDuplicatesFromSortedListII {
    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null)
            return head;
        int[] hash = new int[201];
        ListNode curr = head;
        while (curr != null) {
            hash[curr.val + 100]++;
            curr = curr.next;
        }
        curr = head;
        ListNode result = null;
        ListNode currNonRepeated = null;
        while (curr != null) {
            if (hash[curr.val + 100] >= 2) {
                while (curr != null && hash[curr.val + 100] >= 2)
                    curr = curr.next;
            }
            if (result == null)
                result = curr;
            if (currNonRepeated != null)
                currNonRepeated.next = curr;
            currNonRepeated = curr;
            if (curr != null)
                curr = curr.next;
        }
        return result;
    }
    public static void main(String[] args) {

        ListNode head = ListNode.createList(new int[] {1, 1});
        ListNode result = deleteDuplicates(head);
        ListNode.printList(result);

//        ListNode head = ListNode.createList(new int[] {1, 2, 3, 3, 4, 4, 5});
//        ListNode result = deleteDuplicates(head);
//        ListNode.printList(result);
//
//        head = ListNode.createList(new int[] {1, 1, 1, 2, 3});
//        result = deleteDuplicates(head);
//        ListNode.printList(result);
    }
}
