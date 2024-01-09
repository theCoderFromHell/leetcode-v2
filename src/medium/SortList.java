package medium;

import common.ListNode;

import java.util.Arrays;
import java.util.List;

public class SortList {
    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        return sort(head);
    }
    private static ListNode sort(ListNode head) {
        if (head == null || head.next == null)
            return head;
        List<ListNode> nodes = partition(head);
        ListNode left = sort(nodes.get(0));
        ListNode right = sort(nodes.get(1));
        return merge(left, right);
    }

    private static ListNode merge(ListNode left, ListNode right) {
        if (left == null && right == null)
            return null;
        if (left == null)
            return right;
        if(right == null)
            return left;
        ListNode resultHead = null;
        ListNode prev = null;
        while (left != null && right != null) {
            ListNode curr;
            if (left.val < right.val) {
                curr = left;
                left = left.next;
            }
            else {
                curr = right;
                right = right.next;
            }
            if (resultHead == null)
                resultHead = curr;
            if (prev != null)
                prev.next = curr;
            prev = curr;
        }
        prev.next = left != null ? left : right;
        return resultHead;

    }

    private static List<ListNode> partition(ListNode head) {
        if (head == null)
            return Arrays.asList(null, null);
        if (head.next == null)
            return Arrays.asList(head, null);
        ListNode left = head;
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode right = slow.next;
        slow.next = null;
        return Arrays.asList(left, right);
    }

    public static void main(String[] args) {
        ListNode head = ListNode.createList(new int[]{-1,5,3,4,0});
        ListNode result = sortList(head);
        ListNode.printList(result);
    }
}
