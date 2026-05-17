package medium;

import java.util.HashMap;
import java.util.PriorityQueue;

// https://leetcode.com/problems/reduce-array-size-to-the-half/
public class ReduceArraySizeToTheHalf {
    public int minSetSize(int[] arr) {
        int size = arr.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        HashMap<Integer,Integer> frequency = new HashMap<>();
        for (int i = 0; i < size; i++)
            frequency.put(arr[i], frequency.getOrDefault(arr[i], 0) + 1);
        for (int value : frequency.keySet())
            pq.add(frequency.get(value));
        int count = 0;
        int currSize = size;
        while (!pq.isEmpty()) {
            int top = pq.poll();
            count++;
            if (currSize - top <= size/2)
                return count;
            currSize -= top;
        }
        return count;
    }

    /*
     * Revision Note — Reduce Array Size to The Half (Medium)
     *
     * Pattern: Greedy by frequency — max-heap
     *
     * Key Insight: Always remove the most frequent element first to minimise
     * the number of distinct elements removed while maximising size reduction.
     *
     * Gotchas:
     * - CRITICAL: Keep originalSize fixed — use a separate currSize for tracking.
     *   Mutating size means size/2 (the target) shrinks too, causing wrong early returns.
     * - Heap stores frequencies only (Integer), not int[] pairs — the original value is irrelevant
     * - Target is arr.length/2 (integer division = floor), not ceil
     * - Heap comparator: descending frequency (b - a)
     *
     * Template:
     *   build frequency map → max-heap of frequencies only
     *   int currSize = originalSize
     *   while heap not empty:
     *       poll top, count++
     *       if currSize - top <= originalSize/2: return count
     *       currSize -= top
     */
    public static void main(String[] args) {
        ReduceArraySizeToTheHalf R = new ReduceArraySizeToTheHalf();
        System.out.println(R.minSetSize(new int[]{3, 3, 3, 3, 5, 5, 5, 2, 2, 7})); // 2
        System.out.println(R.minSetSize(new int[]{7, 7, 7, 7, 7, 7}));              // 1 — all same
        System.out.println(R.minSetSize(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10})); // 5 — all distinct
        System.out.println(R.minSetSize(new int[]{1, 9}));                           // 1
    }
}
