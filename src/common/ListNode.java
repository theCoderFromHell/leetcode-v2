package common;
public class ListNode {
    public int val;
    public ListNode next;
    ListNode() {}
    public ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + ((head.next == null) ? " " : " -> "));
            head = head.next;
        }
    }
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

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + (next == null ? "null" : next.val) +
                '}';
    }
}
