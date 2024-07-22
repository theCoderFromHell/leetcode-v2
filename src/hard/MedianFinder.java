package hard;

import java.util.Collections;
import java.util.PriorityQueue;

public class MedianFinder {
    PriorityQueue<Integer> minHeap;
    PriorityQueue<Integer> maxHeap;
    int total;
    public MedianFinder() {
        this.minHeap = new PriorityQueue<>();
        this.maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        this.total = 0;
    }

    public void addNum(int num) {
        if (maxHeap.isEmpty() || num <= maxHeap.peek())
            maxHeap.add(num);
        else
            minHeap.add(num);
        int minHeapSize = minHeap.size();
        int maxHeapSize = maxHeap.size();

        if (maxHeapSize - minHeapSize >= 2)
            minHeap.add(maxHeap.poll());
        if (minHeapSize - maxHeapSize >= 2)
            maxHeap.add(minHeap.poll());
    }

    public double findMedian() {
        int minHeapSize = minHeap.size();
        int maxHeapSize = maxHeap.size();
        if (minHeapSize == maxHeapSize)
            return (double) (maxHeap.peek() + minHeap.peek()) /2;
        else if (maxHeapSize > minHeapSize)
            return maxHeap.peek();
        else
            return minHeap.peek();
    }

    public static void main(String[] args) {
        MedianFinder M = new MedianFinder();
        M.addNum(1);
        M.addNum(2);
        System.out.println(M.findMedian());
        M.addNum(3);
        System.out.println(M.findMedian());
    }
}
