package medium;

public class MyCircularDeque {
    DoubleLinkedListNode front;
    DoubleLinkedListNode rear;
    int size;
    int currSize;
    public MyCircularDeque(int k) {
        this.front = null;
        this.rear = null;
        this.size = k;
        this.currSize = 0;
    }

    public boolean insertFront(int value) {
        if (currSize == size)
            return false;
        DoubleLinkedListNode node = new DoubleLinkedListNode(value);
        node.next = front;
        if (front != null)
            front.previous = node;
        front = node;
        if (currSize == 0)
            rear = node;
        currSize++;
        return true;
    }

    public boolean insertLast(int value) {
        if (currSize == size)
            return false;
        DoubleLinkedListNode node = new DoubleLinkedListNode(value);
        node.previous = rear;
        if (rear != null)
            rear.next = node;
        rear = node;
        if (currSize == 0)
            front = node;
        currSize++;
        return true;
    }

    public boolean deleteFront() {
        if (currSize == 0 || front == null)
            return false;
        front = front.next;
        if (front != null)
            front.previous = null;
        else
            rear = null;
        currSize--;
        return true;
    }

    public boolean deleteLast() {
        if (currSize == 0 || rear == null)
            return false;
        rear = rear.previous;
        if (rear != null)
            rear.next = null;
        else
            front = null;
        currSize--;
        return true;
    }

    public int getFront() {
        if (currSize == 0 || front == null)
            return -1;
        return front.val;
    }

    public int getRear() {
        if (currSize == 0 || rear == null)
            return -1;
        return rear.val;
    }

    public boolean isEmpty() {
        return (currSize == 0);
    }

    public boolean isFull() {
        return (currSize == size);
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

    public static void main(String[] args) {
        MyCircularDeque M = new MyCircularDeque(3);
        System.out.println(M.insertLast(1));
        System.out.println(M.insertLast(2));
        System.out.println(M.insertFront(3));
        System.out.println(M.insertFront(4));
        System.out.println(M.getRear());
        System.out.println(M.isFull());
        System.out.println(M.deleteLast());
        System.out.println(M.insertFront(4));
        System.out.println(M.getFront());
    }
}
