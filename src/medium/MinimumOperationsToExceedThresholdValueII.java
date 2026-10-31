package medium;

import java.util.PriorityQueue;

// https://leetcode.com/problems/minimum-operations-to-exceed-threshold-value-ii/
public class MinimumOperationsToExceedThresholdValueII {
    public int minOperations(int[] nums, int k) {
        int size = nums.length;
        PriorityQueue<Long> pq = new PriorityQueue<>();
        for (int i = 0; i < size; i++)
            pq.add((long) nums[i]);
        int steps = 0;
        while (!pq.isEmpty()) {
            if (pq.peek() >= k)
                return steps;
            long lowest = pq.poll();
            if (pq.isEmpty())
                return -1;
            long secondLowest = pq.poll();
            pq.add(secondLowest + 2 * lowest);
            steps++;
        }
        return steps;
    }

    /*
     * Revision Note — Minimum Operations to Exceed Threshold Value II (Medium)
     * Pattern: min-heap simulation — repeatedly pop the two smallest, push their combination.
     * Key Insight: only the minimum decides when to stop. All elements are >= k exactly when the
     *              heap's top is, so the loop is "while peek() < k: merge the two smallest".
     * Gotchas:
     *   - Overflow: 2x + y reaches ~3 * 10^9 with values near 10^9. In an int heap it wraps
     *     negative, stays below k, and the run ends wrong (Test 6 gives -1). Store longs.
     *   - Since x <= y, min(x, y) * 2 + max(x, y) is just 2 * lowest + secondLowest.
     *   - Elements already >= k still take part: one can be the second-smallest y (Test 7), so
     *     they go into the heap too (or are counted separately, see the variant below).
     *   - The "answer always exists" guarantee means the heap never runs down to one element
     *     below k, so the -1 branch and the return after the loop are unreachable.
     * Complexity: O(n log n) time — n inserts, then at most n - 1 operations, each 2 polls + 1 add.
     *             O(n) space for the heap.
     * Alternative — two queues (Huffman trick): the merged values come out NON-DECREASING (each
     *   step's two smallest are >= the previous step's), so sort nums once and append merged
     *   values to a FIFO. The next smallest is always at the front of one of the two. O(n log n)
     *   for the sort, then O(n), with no boxing.
     * Variant: keep only values < k in the heap. When one is left, it merges with any element
     *   >= k for one more operation; a merged value >= k is never pushed back.
     * Template:
     *   heap = all nums as long; ops = 0
     *   while heap.peek() < k: x = poll(); y = poll(); add(2x + y); ops++
     *   return ops
     */
    public static void main(String[] args) {
        MinimumOperationsToExceedThresholdValueII M = new MinimumOperationsToExceedThresholdValueII();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + M.minOperations(new int[]{2, 11, 10, 1, 3}, 10) + " (Expected: 2)");

        // Test 2: LeetCode example 2 — merges everything down to one element
        System.out.println("Test 2: " + M.minOperations(new int[]{1, 1, 2, 4, 9}, 20) + " (Expected: 4)");

        // Test 3: every element already >= k, including one equal to k — zero operations, stop on the first peek
        System.out.println("Test 3: " + M.minOperations(new int[]{5, 7, 9}, 5) + " (Expected: 0)");

        // Test 4: two elements — the minimum legal input; 1 * 2 + 1 = 3 lands exactly on k
        System.out.println("Test 4: " + M.minOperations(new int[]{1, 1}, 3) + " (Expected: 1)");

        // Test 5: all values equal — ties between the two smallest, and merged values outgrow the originals
        System.out.println("Test 5: " + M.minOperations(new int[]{1, 1, 1, 1, 1, 1}, 10) + " (Expected: 5)");

        // Test 6: values near 10^9 — the second merge is 2 * 999999999 + 1000000001 = 2999999999, past
        // Integer.MAX_VALUE. An int heap wraps it negative and returns -1
        System.out.println("Test 6: " + M.minOperations(new int[]{1, 999999999, 999999999}, 1000000000) + " (Expected: 2)");

        // Test 7: one value below k, one far above — the element >= k is still needed as the second-smallest
        System.out.println("Test 7: " + M.minOperations(new int[]{1, 100}, 10) + " (Expected: 1)");
    }
}
