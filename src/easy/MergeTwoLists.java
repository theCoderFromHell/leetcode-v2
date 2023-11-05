package easy;

import common.ListNode;

public class MergeTwoLists {
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if(list1 == null) return list2;
        if(list2 == null) return list1;
        ListNode a = list1;
        ListNode b = list2;
        ListNode result = null;
        ListNode current = null;
        ListNode smaller;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                smaller = a;
                a = a.next;
            } else {
                smaller = b;
                b = b.next;
            }
            if(result == null) {
                result = new ListNode(smaller.val);
                current = result;
            } else {
                current.next = new ListNode(smaller.val);
                current = current.next;
            }
        }
        if(current != null) {
            current.next = a == null ? b : a;
        }
        return result;
    }

    public static void main(String[] args) {

    }
}

