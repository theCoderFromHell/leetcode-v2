package medium;

import common.ListNode;

import java.util.AbstractMap;
import java.util.List;

public class ReverseLinkedListII {
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == right)
            return head;
        int count = right-left+1;
        ListNode reverseStart = null, reverseEnd = null, start = null, end = null;
        ListNode curr = head;
        int currIdx = 0;
        while (curr != null) {
            currIdx++;
            if (left == currIdx) {
                reverseStart = curr;
            }

        }
        return reverseStart;
    }
    public Pair reverseList(ListNode head, int count) {
        ListNode prev = null;
        ListNode curr = head;
        ListNode next;
        while(curr != null && count > 0) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            count--;
        }
        return new Pair(prev, head);
    }

    public static void main(String[] args) {

    }
}
class Pair {
    ListNode head;
    ListNode tail;

    public Pair(ListNode head, ListNode tail) {
        this.head = head;
        this.tail = tail;
    }
    @Override
    public String toString() {
        return "Pair{" +
                "head=" + head +
                ", tail=" + tail +
                '}';
    }
}
