package medium;

import java.util.ArrayDeque;
import java.util.Arrays;

// https://leetcode.com/problems/maximal-range-that-each-element-is-maximum-in-it/
public class MaximalRangeThatEachElementIsMaximumInIt {
    public int[] maximumLengthOfRanges(int[] nums) {
        int size = nums.length;
        ArrayDeque<int[]> stack = new ArrayDeque<>();
        int[] left = new int[size];
        int[] right = new int[size];
        for (int i = 0; i < size; i++) {
            int currStreak = 1;
            while (!stack.isEmpty() && stack.peek()[0] <= nums[i]) {
                int[] top = stack.pop();
                currStreak += top[1];
            }
            stack.push(new int[]{nums[i], currStreak});
            left[i] = currStreak;
        }
        stack.clear();
        for (int i = size-1; i >= 0; i--) {
            int currStreak = 1;
            while (!stack.isEmpty() && stack.peek()[0] <= nums[i]) {
                int[] top = stack.pop();
                currStreak += top[1];
            }
            stack.push(new int[]{nums[i], currStreak});
            right[i] = currStreak;
        }
        int[] result = new int[size];
        for (int i = 0; i < size; i++)
            result[i] = left[i] + right[i] - 1;
        return result;
    }

    /*
     * Revision Note — Maximal Range That Each Element Is Maximum in It (Medium, Premium)
     *
     * Pattern: 901 Online Stock Span run in BOTH directions. A decreasing stack of (value, span)
     * pairs: each new value pops every entry ≤ it and absorbs their spans.
     *
     * Key insight: nums[i]'s range stops at the nearest GREATER element on each side. The left
     * pass gives "how many elements back, including i, are ≤ nums[i]" (exactly 901), and the
     * right-to-left pass gives the same going forward. Both counts include i itself, so:
     *   ans[i] = left[i] + right[i] - 1
     *
     * Gotchas:
     *  - Expanding outward from each i is O(n²): on an increasing array every element walks back
     *    to index 0, ~5e9 steps at n = 1e5 (Test 6).
     *  - The -1: i is counted in both spans.
     *  - Values are distinct, so <= vs < in the pop doesn't matter here. With duplicates you'd pop
     *    on < in one pass and <= in the other, or equal values would claim each other's ranges
     *    (the 907 Sum of Subarray Minimums trick).
     *
     * Complexity: O(n) time (each index is pushed and popped at most once per pass), O(n) space.
     *
     * Template:
     *   for i in 0..n-1:   span = 1; while top.value <= a[i]: span += pop().span; push(a[i], span); left[i] = span
     *   for i in n-1..0:   same → right[i]
     *   ans[i] = left[i] + right[i] - 1
     *
     * Alternative, one pass with an index stack: when i pops j, i is j's right boundary, and after
     * popping, the new top is i's left boundary (-1 if empty). Indices still on the stack at the end
     * have right boundary n. ans[j] = right[j] - left[j] - 1.
     */
    public static void main(String[] args) {
        MaximalRangeThatEachElementIsMaximumInIt M = new MaximalRangeThatEachElementIsMaximumInIt();

        // Test 1: LeetCode example 1 — 5's range [1, 5, 4, 3] stops at the 6
        System.out.println("Test 1: " + Arrays.toString(M.maximumLengthOfRanges(new int[]{1, 5, 4, 3, 6})) + " (Expected: [1, 4, 2, 1, 5])");

        // Test 2: LeetCode example 2 — increasing: only the left span grows, right span is always 1
        System.out.println("Test 2: " + Arrays.toString(M.maximumLengthOfRanges(new int[]{1, 2, 3, 4, 5})) + " (Expected: [1, 2, 3, 4, 5])");

        // Test 3: single element — left = right = 1, minus the double count
        System.out.println("Test 3: " + Arrays.toString(M.maximumLengthOfRanges(new int[]{7})) + " (Expected: [1])");

        // Test 4: strictly decreasing — the mirror of Test 2: only the right span grows
        System.out.println("Test 4: " + Arrays.toString(M.maximumLengthOfRanges(new int[]{5, 4, 3, 2, 1})) + " (Expected: [5, 4, 3, 2, 1])");

        // Test 5: max in the middle — both spans matter, and 6's pop absorbs the spans of 3, 1, 2
        System.out.println("Test 5: " + Arrays.toString(M.maximumLengthOfRanges(new int[]{3, 1, 2, 6, 5, 4})) + " (Expected: [3, 1, 2, 6, 2, 1])");

        // Test 6: max n, strictly increasing 1..100000 — an expand-outward scan takes ~5e9 steps here
        // (prints the sum of ans)
        int[] big = new int[100000];
        for (int i = 0; i < big.length; i++)
            big[i] = i + 1;
        System.out.println("Test 6: " + Arrays.stream(M.maximumLengthOfRanges(big)).asLongStream().sum() + " (Expected: 5000050000)");
    }
}
