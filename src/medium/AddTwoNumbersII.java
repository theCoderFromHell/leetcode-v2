package medium;

import common.ListNode;

public class AddTwoNumbersII {
    public ListNode addTwoNumbers(ListNode a, ListNode b) {
        int aLength = countNodes(a);
        int bLength = countNodes(b);
        int diff = Math.abs(aLength - bLength);
        ListNode aCurrent = a;
        ListNode bCurrent = b;

        if (diff != 0) {
            ListNode dummyHead = new ListNode(0);
            ListNode dummyCurrent = dummyHead;
            diff--;
            while (diff > 0) {
                dummyCurrent.next = new ListNode(0);
                dummyCurrent = dummyCurrent.next;
                diff--;
            }
            if (aLength > bLength) {
                dummyCurrent.next = b;
                bCurrent = dummyHead;
            } else if (aLength < bLength) {
                dummyCurrent.next = a;
                aCurrent = dummyHead;
            }
        }

        ListNode sumNode = sumEqual(aCurrent, bCurrent);
        int carry = 0;
        if (sumNode.val >= 10) {
            carry = sumNode.val / 10;
            sumNode.val = sumNode.val % 10;
        }
        if (carry > 0) {
            ListNode carryNode = new ListNode(carry);
            carryNode.next = sumNode;
            return carryNode;
        }
        return sumNode;
    }

    private ListNode sumEqual(ListNode aCurrent, ListNode bCurrent) {
        if (aCurrent == null || bCurrent == null)
            return null;
        ListNode sumNode = sumEqual(aCurrent.next, bCurrent.next);
        int carry = 0;
        if (sumNode != null && sumNode.val >= 10) {
            carry = sumNode.val / 10;
            sumNode.val = sumNode.val % 10;
        }
        ListNode current = new ListNode(aCurrent.val + bCurrent.val + carry);
        current.next = sumNode;
        return current;
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
        AddTwoNumbersII A = new AddTwoNumbersII();
        ListNode a = new ListNode(3);
        a.next = new ListNode(8);
        a.next.next = new ListNode(7);

        ListNode b = new ListNode(8);
        b.next = new ListNode(9);
        b.next.next = new ListNode(8);
        ListNode.printList(A.addTwoNumbers(a, b));
    }
}
