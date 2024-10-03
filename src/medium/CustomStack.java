package medium;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class CustomStack {
    private int maxSize;
    private int[] stack;
    private int top;

    public CustomStack(int maxSize) {
        this.maxSize = maxSize;
        this.stack = new int[maxSize];
        this.top = -1;
    }

    public void push(int x) {
        if (top < maxSize - 1) {
            stack[++top] = x;
        }
    }

    public int pop() {
        if (top >= 0) {
            return stack[top--];
        }
        return -1;
    }

    public void increment(int k, int val) {
        int limit = Math.min(top, k-1);
        for (int i = 0; i <= limit; i++) {
            stack[i] += val;
        }
    }

    class CustomStackV2 {
        int maxSize;
        private Deque<Integer> deque;

        public CustomStackV2(int maxSize) {
            this.maxSize = maxSize;
            this.deque = new LinkedList<>();
        }

        public void push(int x) {
            if (deque.size() < maxSize)
                deque.addLast(x);
        }

        public int pop() {
            if (deque.isEmpty())
                return -1;
            return deque.removeLast();
        }

        public void increment(int k, int val) {
            List<Integer> removed = new ArrayList<>();
            while (!deque.isEmpty() && k-- > 0)
                removed.add(deque.removeFirst() + val);
            removed = removed.reversed();
            int size = removed.size();
            for (int i = 0; i < size; i++)
                deque.addFirst(removed.get(i));
        }
    }
}