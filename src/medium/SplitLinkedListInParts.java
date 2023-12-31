package medium;

import common.ListNode;
import java.util.Arrays;

public class SplitLinkedListInParts {
    public ListNode[] splitListToParts(ListNode head, int k) {
        ListNode[] result = new ListNode[k];
        if (head == null) {
            Arrays.fill(result, null);
            return result;
        }
        int N = size(head);
        int[] p = new int[k];
        Arrays.fill(p, N / k);
        int remaining = N % k;
        int idx = 0;
        while (remaining-- > 0)
            p[idx++]++;
        idx = 0;
        ListNode curr = head;
        ListNode prev = null;
        while (idx < k) {
            int count = p[idx];
            result[idx] = curr;
            while (count > 0) {
                count--;
                prev = curr;
                curr = curr.next;
            }
            prev.next = null;
            idx++;
        }
        return result;
    }

    private static int size(ListNode head) {
        int size = 0;
        while (head != null) {
            size++;
            head = head.next;
        }
        return size;
    }
    public static void main(String[] args) {

    }
}
