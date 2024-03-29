package medium;

import java.util.HashMap;

public class LRUCache {

    DoubleListNode head;
    DoubleListNode tail;
    HashMap<Integer, DoubleListNode> location;
    int currSize;
    int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.currSize = 0;
        this.head = null;
        this.tail = null;
        location = new HashMap<>();

    }

    public int get(int key) {
        DoubleListNode node;
        if (location.containsKey(key)) {
            node = location.get(key);
            moveNodeToEnd(node, false);
            return node.value;
        }
        return -1;
    }

    private void moveNodeToEnd(DoubleListNode node, boolean isNewNode) {
        if (node == tail)
            return;
        if (!isNewNode) {
            if (node == head) {
                head = head.right;
                if (head != null)
                    head.left = null;
            }
            else if (node != tail) {
                node.left.right = node.right;
                node.right.left = node.left;
            }
        }
        node.left = tail;
        node.right = null;
        if (tail != null)
            tail.right = node;
        tail = node;
    }

    public void put(int key, int value) {
        DoubleListNode node;
        if (location.containsKey(key)) {
            node = location.get(key);
            node.value = value;
            moveNodeToEnd(node, false);
        } else {
            node = new DoubleListNode(key, value);
            location.put(key, node);
            moveNodeToEnd(node, true);
            if (currSize == 0)
                head = node;
            currSize++;
            if (currSize > capacity)
                removeHead();
        }
    }

    private void removeHead() {
        location.remove(head.key);
        if (head == tail) {
            head = tail = null;
            return;
        }
        head = head.right;
        if (head != null)
            head.left = null;

    }

    class DoubleListNode {
        int key;
        public int value;
        public DoubleListNode left;
        public DoubleListNode right;

        public DoubleListNode(int key, int value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }

        @Override
        public String toString() {
            return "DoubleListNode{" +
                    "key=" + key +
                    ", val=" + value +
                    ", left=" + (left == null ? "null" : left.value) +
                    ", right=" + (right == null ? "null" : right.value) +
                    '}';
        }
    }

    public static void main(String[] args) {
//        LRUCache cache = new LRUCache(2);
//        cache.put(1,1);
//        cache.put(2,2);
//        System.out.println(cache.get(1));
//        cache.put(3,3);
//        System.out.println(cache.get(2));
//        cache.put(4,4);
//        System.out.println(cache.get(1));
//        System.out.println(cache.get(3));
//        System.out.println(cache.get(4));
        LRUCache cache = new LRUCache(1);
        cache.put(2,1);
        System.out.println(cache.get(2));

    }

}
