package medium;

import common.ListNode;

public class MergeNodesInBetweenZeros {
    public ListNode mergeNodes(ListNode head) {
        if (head == null || head.next == null)
            return null;
        ListNode root = null, previous = null;
        ListNode curr = head;
        while (curr != null) {
            if (curr.val == 0) {
                ListNode[] node = getNewNode(curr);
                if (root == null)
                    root = node[0];
                else
                    previous.next = node[0];
                previous = node[0];
                curr = node[1];
            }
        }
        return root;
    }

    private ListNode[] getNewNode(ListNode curr) {
        if (curr.next == null)
            return new ListNode[]{null, null};
        int sum = 0;
        while (curr.next != null && curr.next.val != 0) {
            sum += curr.next.val;
            curr = curr.next;
        }
        return new ListNode[]{new ListNode(sum), curr.next};
    }

    public static void main(String[] args) {
        MergeNodesInBetweenZeros M = new MergeNodesInBetweenZeros();
        ListNode head = new ListNode(0);
        head.next = new ListNode(3);
        head.next.next = new ListNode(1);
        head.next.next.next = new ListNode(0);
        head.next.next.next.next = new ListNode(4);
        head.next.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next.next = new ListNode(2);
        head.next.next.next.next.next.next.next = new ListNode(0);
        System.out.println(M.mergeNodes(head));
    }
}
