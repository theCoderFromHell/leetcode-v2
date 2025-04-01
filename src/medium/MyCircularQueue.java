package medium;

import java.util.Arrays;

public class MyCircularQueue {
    int[] queue;
    int front;
    int rear;
    int size;
    int capacity;
    public MyCircularQueue(int k) {
        this.capacity = k;
        this.queue = new int[capacity];
        Arrays.fill(queue, -1);
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public boolean enQueue(int value) {
        if (isFull())
            return false;
        if (queue[rear % capacity] != -1)
            return false;
        queue[rear % capacity] = value;
        rear = (rear + 1) % capacity;
        size++;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty())
            return false;
        queue[front] = -1;
        front = (front + 1) % capacity;
        size--;
        return true;
    }

    public int Front() {
        return queue[front];
    }

    public int Rear() {
        return queue[(rear + capacity - 1) % capacity];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public static void main(String[] args) {
        MyCircularQueue M = new MyCircularQueue(3);
        System.out.println(M.enQueue(1));
        System.out.println(M.enQueue(2));
        System.out.println(M.enQueue(3));
        System.out.println(M.enQueue(4));
        System.out.println(M.Rear());
        System.out.println(M.isFull());
        System.out.println(M.deQueue());
        System.out.println(M.enQueue(4));
        System.out.println(M.Rear());
    }
}
