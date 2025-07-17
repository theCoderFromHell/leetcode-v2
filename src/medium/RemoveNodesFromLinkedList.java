package medium;

import common.ListNode;

public class RemoveNodesFromLinkedList {
    public ListNode removeNodes(ListNode node) {
        if (node == null)
            return null;
        ListNode rightMax = removeNodes(node.next);
        if (rightMax != null && rightMax.val > node.val)
            return rightMax;
        node.next = rightMax;
        return node;
    }

}
