package medium;

import common.ListNode;

public class MergeInBetweenLinkedLists {
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {
        int count = 0;
        ListNode curr = list1;
        ListNode lastNodeList2 = list2;
        while (lastNodeList2 != null && lastNodeList2.next != null)
            lastNodeList2 = lastNodeList2.next;
        while (curr != null) {
            if (a-1 == count) {
                ListNode temp = curr;
                curr = curr.next;
                temp.next = list2;
            } else if (b == count) {
                lastNodeList2.next = curr.next;
                break;
            } else
                curr = curr.next;
            count++;
        }
        return list1;
    }
}
