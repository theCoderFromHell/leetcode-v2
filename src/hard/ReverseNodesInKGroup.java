package hard;

import common.ListNode;

public class ReverseNodesInKGroup {
    public ListNode reverseKGroup(ListNode head, int k) {
        int count = countNodes(head);
        if (count < k)
            return head;

        ListNode reversed = reverseNodes(head, k);
        head.next = reverseKGroup(head.next, k);
        return reversed;
    }

    private ListNode reverseNodes(ListNode head, int k) {
        ListNode prev = head;
        int i = 0;
        while (i++ < k && prev != null)
            prev = prev.next;
        ListNode curr = head;
        ListNode next;

        while (curr != null && k-- > 0) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    private int countNodes(ListNode head) {
        int count = 0;
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }

    public static void main(String[] args) {
        ListNode head = ListNode.createList(new int[]{1,2,3,4,5,6,7});
        ReverseNodesInKGroup R = new ReverseNodesInKGroup();
        ListNode reversed = R.reverseKGroup(head, 3);
        ListNode.printList(reversed);
    }
}
