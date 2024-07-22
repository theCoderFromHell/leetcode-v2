package hard;

import common.ListNode;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class MergeKSortedLists {
    public ListNode mergeKLists(ListNode[] lists) {
        int N = lists.length;
        if (N == 0)
            return null;
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.val));
        for (int i = 0; i < N; i++) {
            if (lists[i] != null)
                pq.add(lists[i]);
        }
        ListNode head  = null;
        ListNode curr = null;
        while (!pq.isEmpty()) {
            ListNode least = pq.poll();
            if (head == null) {
                head = new ListNode(least.val);
                curr = head;
            } else {
                curr.next = new ListNode(least.val);
                curr = curr.next;
            }
            if (least.next != null) {
                pq.add(least.next);
            }
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
        a.next = new ListNode(4);
        a.next.next = new ListNode(5);

        ListNode b = new ListNode(1);
        b.next = new ListNode(3);
        b.next.next = new ListNode(4);

        ListNode c = new ListNode(2);
        c.next = new ListNode(6);

        MergeKSortedLists M = new MergeKSortedLists();
        ListNode.printList(M.mergeKLists(new ListNode[] {a, b, c}));

    }
}
