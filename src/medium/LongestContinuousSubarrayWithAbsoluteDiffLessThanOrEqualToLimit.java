package medium;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.TreeMap;

// https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/
public class LongestContinuousSubarrayWithAbsoluteDiffLessThanOrEqualToLimit {
    public int longestSubarray(int[] nums, int limit) {
        int size = nums.length;
        Deque<Integer> maxDeque = new ArrayDeque<>();
        Deque<Integer> minDeque = new ArrayDeque<>();
        int start = 0;
        int maxWindow = 1;
        for (int end = 0; end < size; end++) {
            while (!maxDeque.isEmpty() && maxDeque.peekLast() < nums[end])
                maxDeque.pollLast();
            maxDeque.addLast(nums[end]);
            while (!minDeque.isEmpty() && minDeque.peekLast() > nums[end])
                minDeque.pollLast();
            minDeque.addLast(nums[end]);
            while (maxDeque.peekFirst() - minDeque.peekFirst() > limit) {
                if (maxDeque.peekFirst() == nums[start])
                    maxDeque.pollFirst();
                if (minDeque.peekFirst() == nums[start])
                    minDeque.pollFirst();
                start++;
            }
            maxWindow = Math.max(maxWindow, end - start + 1);
        }
        return maxWindow;
    }
    public int longestSubarrayV2(int[] nums, int limit) {
        int size = nums.length;
        TreeMap<Integer,Integer> window = new TreeMap<>();
        int start = 0, end = 0;
        int maxWindow = 1;
        int currMin, currMax;
        while (end < size) {
            window.put(nums[end], window.getOrDefault(nums[end], 0) + 1);
            currMin = window.firstKey();
            currMax = window.lastKey();
            while (start < end && currMax - currMin > limit) {
                window.put(nums[start], window.get(nums[start]) - 1);
                if (window.get(nums[start]) == 0)
                    window.remove(nums[start]);
                currMin = window.firstKey();
                currMax = window.lastKey();
                start++;
            }
            maxWindow = Math.max(maxWindow, end - start + 1);
            end++;
        }
        return maxWindow;
    }

    /*
     * Revision Note — Longest Continuous Subarray With Absolute Diff <= Limit (Medium)
     *
     * Pattern: Sliding window + two monotonic deques (window max AND min in O(1)) — Min Stack
     * generalised to a queue: elements now also leave from the FRONT.
     *
     * Key insight: "every pair differs by <= limit" == "max - min <= limit", and validity is
     * monotone (shrinking a valid window keeps it valid) → grow end, shrink start while invalid.
     * For the max: an older value <= a newer one can never be the max again (the newer outlives
     * it), so pop it from the BACK on arrival. Survivors are decreasing → max at the FRONT.
     *
     * Gotchas:
     *  - Loop order: add nums[end] → shrink while invalid → record. Recording first misses the
     *    final window; incrementing end before reading it runs past the array.
     *  - Storing VALUES (not indices): pop only STRICTLY smaller/larger so duplicates survive —
     *    otherwise retiring nums[start] by value equality would drop its twin too.
     *    Storing indices avoids the issue: retire when front index < start.
     *  - Stack fails (removes the newest); PriorityQueue.remove(obj) is O(n); TreeMap needs
     *    value → count because keys are unique.
     *  - Integer == int unboxes (safe); Integer == Integer would compare references.
     *
     * Complexity: deques O(n) time (each element pushed/popped at most once per deque), O(n) space.
     *             TreeMap (V2) O(n log n) — correct but slow (~5th percentile).
     *
     * Template:
     *   for end in 0..n-1:
     *       while maxQ.back < x: maxQ.popBack();  maxQ.pushBack(x)
     *       while minQ.back > x: minQ.popBack();  minQ.pushBack(x)
     *       while maxQ.front - minQ.front > limit:
     *           if maxQ.front == nums[start]: maxQ.popFront()
     *           if minQ.front == nums[start]: minQ.popFront()
     *           start++
     *       best = max(best, end - start + 1)
     */
    public static void main(String[] args) {
        LongestContinuousSubarrayWithAbsoluteDiffLessThanOrEqualToLimit L = new LongestContinuousSubarrayWithAbsoluteDiffLessThanOrEqualToLimit();

        System.out.println("Test 1: " + L.longestSubarray(new int[]{8, 2, 4, 7}, 4) + " (Expected: 2)");
        System.out.println("Test 2: " + L.longestSubarray(new int[]{10, 1, 2, 4, 7, 2}, 5) + " (Expected: 4)");
        System.out.println("Test 3: " + L.longestSubarray(new int[]{4, 2, 2, 2, 4, 4, 2, 2}, 0) + " (Expected: 3)");
        // single element
        System.out.println("Test 4: " + L.longestSubarray(new int[]{5}, 0) + " (Expected: 1)");
        // all duplicates, limit 0 — value-equality retirement must not drop twins
        System.out.println("Test 5: " + L.longestSubarray(new int[]{3, 3, 3, 3}, 0) + " (Expected: 4)");
        // best window ends at the last index — guards the record-before-shrink ordering bug
        System.out.println("Test 6: " + L.longestSubarray(new int[]{1, 5, 6, 7}, 2) + " (Expected: 3)");
        // diff just over the limit at the value extremes
        System.out.println("Test 7: " + L.longestSubarray(new int[]{1, 1000000000, 1}, 999999998) + " (Expected: 1)");
        System.out.println("Test 8: " + L.longestSubarray(new int[]{1, 1000000000, 1}, 999999999) + " (Expected: 3)");
        // TreeMap version agrees
        System.out.println("Test 9: " + L.longestSubarrayV2(new int[]{10, 1, 2, 4, 7, 2}, 5) + " (Expected: 4)");
        System.out.println("Test 10: " + L.longestSubarrayV2(new int[]{4, 2, 2, 2, 4, 4, 2, 2}, 0) + " (Expected: 3)");
    }
}
