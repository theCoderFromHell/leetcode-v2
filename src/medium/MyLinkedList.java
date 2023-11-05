package medium;

public class MyLinkedList {

    int size;
    DoubleLinkedListNode head;
    DoubleLinkedListNode tail;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public int get(int index) {
        if (size == 0 || index < 0 || index  >= size)
            return -1;
        if (index == 0)
            return head.val;
        if (index == size-1)
            return tail.val;
        DoubleLinkedListNode curr = index < size - 1 - index ? head : tail;
        int idx;
        if (curr == head) {
            idx = 0;
            while (curr != null && idx < index) {
                curr = curr.next;
                idx++;
            }
        } else {
            idx = size - 1;
            while (curr != null && idx > index) {
                curr = curr.previous;
                idx--;
            }
        }
        return (curr != null) ? curr.val : -1;
    }

    public void addAtHead(int val) {
        DoubleLinkedListNode node = new DoubleLinkedListNode(val);
        if (head != null)
            head.previous = node;
        node.next = head;
        head = node;
        size++;
        if (size == 1)
            tail = node;
    }

    public void addAtTail(int val) {
        DoubleLinkedListNode node = new DoubleLinkedListNode(val);
        if (tail != null)
            tail.next = node;
        node.previous = tail;
        tail = node;
        size++;
        if (size == 1)
            head = node;
    }

    public void addAtIndex(int index, int val) {
        if (index < 0 || index  > size)
            return;
        DoubleLinkedListNode node = new DoubleLinkedListNode(val);
        if (index == 0) {
            node.next = head;
            head = node;
            size++;
            if (size == 1)
                tail = node;
        } else if (index == size) {
            node.previous = tail;
            tail = node;
            size++;
            if (size == 1)
                head = node;
        } else {
            DoubleLinkedListNode curr = index < size - 1 - index ? head : tail;
            int idx;
            if (curr == head) {
                idx = 0;
                while (curr != null && idx < index) {
                    curr = curr.next;
                    idx++;
                }
            } else {
                idx = size - 1;
                while (curr != null && idx > index) {
                    curr = curr.previous;
                    idx--;
                }
            }
            if (curr == null)
                return;
            DoubleLinkedListNode prev = curr.previous;
            if (prev != null) {
                prev.next = node;
                node.previous = prev;
            }
            curr.previous = node;
            node.next = curr;
            size++;
        }
    }

    public void deleteAtIndex(int index) {
        if ( size == 0 || index < 0 || index  >= size)
            return;
        if (index == 0) {
            head = head.next;
            if (head != null)
                head.previous = null;
            size--;
            if (size == 0)
                tail = null;
        } else if (index == size-1) {
            tail = tail.previous;
            if (tail != null)
                tail.next = null;
            size--;
            if (size == 0)
                head = null;
        } else {
            DoubleLinkedListNode curr = index < size - 1 - index ? head : tail;
            int idx;
            if (curr == head) {
                idx = 0;
                while (curr != null && idx < index) {
                    curr = curr.next;
                    idx++;
                }
            } else {
                idx = size - 1;
                while (curr != null && idx > index) {
                    curr = curr.previous;
                    idx--;
                }
            }
            if (curr == null)
                return;
            if (curr.previous != null) {
                curr.previous.next = curr.next;
            }
            if (curr.next != null) {
                curr.next.previous = curr.previous;
            }
            curr.previous = curr.next = null;
            size--;
        }
    }
}

class DoubleLinkedListNode {
    public int val;
    public DoubleLinkedListNode next;
    public DoubleLinkedListNode previous;

    public DoubleLinkedListNode(int val) {
        this.val = val;
        this.next = null;
        this.previous = null;
    }


    @Override
    public String toString() {
        return "DoubleListNode{" +
                "val=" + val +
                ", left=" + (next == null ? "null" : next.val) +
                ", right=" + (previous == null ? "null" : previous.val) +
                '}';
    }
}

/*
public static void main(String[] args) {
        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.addAtHead(1);
        myLinkedList.addAtTail(3);
        myLinkedList.addAtIndex(1, 2);
        System.out.println(myLinkedList.get(1));
        myLinkedList.deleteAtIndex(1);
        System.out.println(myLinkedList.get(1));

    }
 */