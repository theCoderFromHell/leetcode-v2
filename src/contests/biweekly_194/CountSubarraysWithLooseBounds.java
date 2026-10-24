package contests.biweekly_194;

import java.util.Arrays;
import java.util.TreeSet;

// https://leetcode.com/problems/count-subarrays-with-loose-bounds/
public class CountSubarraysWithLooseBounds {
    public long countSubarrays(int[] nums, int minK, int maxK) {
        int size = nums.length;
        TreeSet<Integer> current = new TreeSet<>();
        int start = 0, end = 0;
        long result = 0;
        while (start <= end && end < size) {
            current.add(nums[end]);
            int currMin = current.getFirst();
            int currMax = current.getLast();
            end++;
            while (start < end && start< size && !current.isEmpty() && (currMin < minK || currMax > maxK)) {
                current.remove(nums[start]);
                if (!current.isEmpty()) {
                    currMin = current.getFirst();
                    currMax = current.getLast();
                }
                start++;
            }
            result += (end - start);
        }
        return result;
    }

    /*
     * Revision Note — Count Subarrays With Loose Bounds (Medium, Biweekly 194 Q2)
     *
     * Pattern: Sliding window over a per-element predicate — every element must lie in
     * [minK, maxK], so each valid window ending at `end` contributes (end - start) subarrays.
     *
     * Key insight: the predicate is about EACH element, not the window as a whole. The window is
     * valid before every add, so the only element that can break it is the one just added — and
     * then nothing before it can survive, so the window drains completely. That means the
     * TreeSet min/max is more machinery than needed: the answer is just the sum of the in-range
     * run length ending at each index.
     *
     * Gotchas:
     *  - Result must be long: one run of n = 1e5 valid elements is n(n + 1) / 2 = 5,000,050,000.
     *  - The constraints never promise minK <= maxK. If minK > maxK no element fits → 0.
     *  - A TreeSet collapses duplicates, so remove(nums[start]) can drop a value still in the
     *    window. Harmless here only because a bad element always drains the whole window — in a
     *    window that can stop shrinking midway, keep counts in a TreeMap instead.
     *  - Bounds are inclusive: elements equal to minK or maxK count.
     *
     * Complexity: O(n log n) time (TreeSet add / remove / first / last), O(n) space.
     *
     * Template (O(n) time, O(1) space):
     *   run = 0
     *   for x in nums:
     *       run = (minK <= x <= maxK) ? run + 1 : 0
     *       result += run                     ← subarrays ending here
     *
     * Related: 795 Number of Subarrays with Bounded Maximum (one bound on the max), and
     * 2444 Count Subarrays With Fixed Bounds (Hard) — min must EQUAL minK and max EQUAL maxK,
     * so you also track the last index of each bound inside the run.
     */
    public static void main(String[] args) {
        CountSubarraysWithLooseBounds C = new CountSubarraysWithLooseBounds();

        // Test 1: LeetCode example 1 — 7 splits the array into two runs
        System.out.println("Test 1: " + C.countSubarrays(new int[]{1, 4, 7, 3}, 1, 5) + " (Expected: 4)");

        // Test 2: LeetCode example 2 — minK == maxK, duplicates
        System.out.println("Test 2: " + C.countSubarrays(new int[]{1, 1}, 1, 1) + " (Expected: 3)");

        // Test 3: minK > maxK — allowed by the constraints, nothing fits
        System.out.println("Test 3: " + C.countSubarrays(new int[]{3, 4, 5}, 5, 3) + " (Expected: 0)");

        // Test 4: duplicate values on both sides of an out-of-range element — TreeSet collapses them
        System.out.println("Test 4: " + C.countSubarrays(new int[]{2, 3, 2, 9, 2}, 1, 5) + " (Expected: 7)");

        // Test 5: every element out of range
        System.out.println("Test 5: " + C.countSubarrays(new int[]{10, 20}, 1, 5) + " (Expected: 0)");

        // Test 6: elements equal to the bounds — inclusive, the whole array is one run
        System.out.println("Test 6: " + C.countSubarrays(new int[]{1, 5, 1, 5}, 1, 5) + " (Expected: 10)");

        // Test 7: max n, all valid — n(n + 1) / 2 overflows an int
        int[] big = new int[100000];
        Arrays.fill(big, 1);
        System.out.println("Test 7: " + C.countSubarrays(big, 1, 1) + " (Expected: 5000050000)");
    }
}
