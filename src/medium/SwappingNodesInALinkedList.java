package medium;

import common.ListNode;

public class SwappingNodesInALinkedList {
    public ListNode swapNodes(ListNode head, int k) {
        if (head == null || head.next == null)
            return head;
        ListNode kFromFront = null, kFromBack = null;
        int N = countNodes(head);
        ListNode curr = head;
        int count = 0;
        while (curr != null) {
            count++;
            if (count == k)
                kFromFront = curr;
            if (count == N-k+1)
                kFromBack = curr;
            curr = curr.next;
        }
        int temp = kFromFront.val;
        kFromFront.val = kFromBack.val;
        kFromBack.val = temp;
        return head;
    }
    private int countNodes(ListNode head) {
        int count = 0;
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }
}
