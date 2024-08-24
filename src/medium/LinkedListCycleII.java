package medium;

import common.ListNode;

import java.util.HashSet;
import java.util.List;

public class LinkedListCycleII {
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null)
            return null;
        if (head.next == head)
            return head;
        ListNode slow = head;
        ListNode fast = head.next;
        ListNode loopNode = null;
        while (slow != null && fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                loopNode = slow;
                break;
            }
        }
        if (loopNode == null)
            return null;
        HashSet<ListNode> loopNodes = new HashSet<>();
        loopNodes.add(loopNode);
        ListNode curr = loopNode.next;
        while (curr != loopNode) {
            loopNodes.add(curr);
            curr = curr.next;
        }

        curr = head;
        while (curr != null) {
            if (loopNodes.contains(curr))
                return curr;
            curr = curr.next;
        }
        return null;
    }

    public static void main(String[] args) {
        LinkedListCycleII L = new LinkedListCycleII();
        ListNode head = new ListNode(3);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(0);
        ListNode node4 = new ListNode(-4);
        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2;
        System.out.println(L.detectCycle(head));
    }
}
