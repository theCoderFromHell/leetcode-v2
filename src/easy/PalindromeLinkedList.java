package easy;

import common.ListNode;

public class PalindromeLinkedList {
    public boolean isPalindrome(ListNode head) {
        int N = size(head);
        int[] table = new int[((N-1)/2)+1];
        int index = 0;
        return find(head, table, index, N);
    }

    private boolean find(ListNode head, int[] table, int index, int N) {
        if (head == null)
            return true;
        if (index <= (N-1)/2)
            table[index] = head.val;
        else if (head.val != table[N-1-index])
            return false;
        return find(head.next, table, index+1, N);
    }

    private int size(ListNode head) {
        int count = 0;
        while (head != null) {
            count++;
            head = head.next;
        }
        return count;
    }
}
