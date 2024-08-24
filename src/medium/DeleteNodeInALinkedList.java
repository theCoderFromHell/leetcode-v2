package medium;

import common.ListNode;

public class DeleteNodeInALinkedList {
    public void deleteNode(ListNode node) {
        ListNode prev = null;
        while (node != null && node.next != null) {
            prev = node;
            node.val = node.next.val;
            node = node.next;
        }
        prev.next = null;
    }
}
