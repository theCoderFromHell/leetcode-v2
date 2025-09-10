package medium;

import java.util.Arrays;

public class QueriesOnAPermutationWithKey {
    public int[] processQueries(int[] queries, int m) {
        int size = queries.length;
        int[] values = new int[m];
        for (int i = 0; i < m; i++)
            values[i] = i + 1;
        ListNode head = ListNode.createList(values);
        int[] result = new int[size];
        for (int i = 0; i < size; i++)
            head = find(head, queries[i], result, i);
        return result;
    }

    private ListNode find(ListNode head, int query, int[] result, int index) {
        if (head != null && head.val == query) {
            result[index] = 0;
            return head;
        }
        int count = 0;
        ListNode curr = head;
        while (curr != null && curr.next != null) {
            if (curr.next.val == query)
                break;
            count++;
            curr = curr.next;
        }
        result[index] = count + 1;
        ListNode temp = curr.next;
        curr.next = curr.next.next;
        temp.next = head;
        head = temp;
        return head;
    }

    static class ListNode {
        public int val;
        public ListNode next;
        public ListNode(int val) { this.val = val; }

        public static ListNode createList(int[] list) {
            if (list.length == 0)
                return null;
            int N = list.length;
            ListNode head = new ListNode(list[0]);
            ListNode curr = head;
            for (int i = 1; i < N; i++) {
                curr.next = new ListNode(list[i]);
                curr = curr.next;
            }
            return head;
        }
    }

    public static void main(String[] args) {
        QueriesOnAPermutationWithKey Q = new QueriesOnAPermutationWithKey();
        System.out.println(Arrays.toString(Q.processQueries(new int[]{3,1,2,1}, 5)));
        System.out.println(Arrays.toString(Q.processQueries(new int[]{4,1,2,2}, 4)));
        System.out.println(Arrays.toString(Q.processQueries(new int[]{7,5,5,8,3}, 8)));
    }
}
