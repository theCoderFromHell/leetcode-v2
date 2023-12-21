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

    public void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + ((head.next == null) ? " " : " -> "));
            head = head.next;
        }
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + (next == null ? "null" : next.val) +
                '}';
    }
}
