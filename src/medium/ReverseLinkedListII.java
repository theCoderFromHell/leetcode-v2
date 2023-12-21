package medium;

import common.ListNode;

public class ReverseLinkedListII {
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == right)
            return head;
        int count = right-left+1;
        ListNode reverseStart = null, start = null, end = null;
        ListNode curr = head;
        int currIdx = 0;
        while (curr != null) {
            currIdx++;
            if (left == currIdx)
                reverseStart = curr;
            else if (reverseStart == null)
                start = curr;
            if (right == currIdx)
                end = curr.next;
            curr = curr.next;
        }
        Pair pair = reverseList(reverseStart, count);
        if (start != null)
            start.next = pair.head;
        pair.tail.next = end;
        return (left == 1) ? pair.head : head;
    }
    public static Pair reverseList(ListNode head, int count) {
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
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        head.printList(head);
        ListNode reversed = reverseBetween(head, 2, 4);
        System.out.println();
        reversed.printList(reversed);
    }
}
class Pair {
    ListNode head;
    ListNode tail;

    public Pair(ListNode head, ListNode tail) {
        this.head = head;
        this.tail = tail;
    }
    public String toString() {
        return "Pair{" +
                "head=" + head +
                ", tail=" + tail +
                '}';
    }
}
