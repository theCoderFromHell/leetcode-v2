package contests.weekly_525;

import java.util.LinkedHashMap;

// https://leetcode.com/problems/count-frequency-staircase-subarrays-i/
public class CountFrequencyStaircaseSubarraysI {
    public int countStaircase(int[] nums) {
        int size = nums.length;
        int result = size;
        for (int start = 0; start < size; start++) {
            LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>();
            int filled = start;
            int count = 3;
            int current = 3;
            while (start + count <= size) {
                int end = start + count;
                for (int i = filled; i < end; i++)
                    map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
                filled = end;
                int order = 1;
                boolean staircase = true;
                for (int key : map.keySet()) {
                    if (map.get(key) != order) {
                        staircase = false;
                        break;
                    }
                    order++;
                }
                if (staircase)
                    result++;
                count += current;
                current++;
            }
        }
        return result;
    }

    /*
     * Revision Note — Count Frequency Staircase Subarrays I (Medium, Weekly 525 Q3)
     *
     * Pattern: Enumerate subarrays by start, but only at lengths that CAN work, and check the
     * frequencies in first-appearance order with a LinkedHashMap.
     *
     * Key insight: frequencies 1, 2, …, k sum to k(k + 1) / 2, so a staircase's length must be a
     * triangular number (1, 3, 6, 10, …) — only O(√n) lengths per start. LinkedHashMap keeps
     * first-appearance order for free: put() on an existing key does not move it.
     *
     * Gotchas:
     *  - The length and its step must reset for EVERY start. Declared outside the loop they kept
     *    growing, so every start after the first checked nothing ([6,6,9,9] gave 4).
     *  - end is exclusive, so a window ending at the last index has start + length == size —
     *    `<` skipped the whole array in example 1.
     *  - Order matters, not just the multiset: [2,2,1] has frequencies 2, 1 — sorted they are
     *    1, 2, but in first-appearance order it is not a staircase.
     *  - Length 1 always qualifies (result starts at size); length 2 never does.
     *  - Result <= n(n + 1) / 2 ≈ 1.1M → int.
     *
     * Complexity: O(n^2.5) time — per start O(√n) lengths, each map rebuilt in O(length);
     * ≈ 1.6e7 map puts at n = 1500 (~250 ms with all-distinct values). O(n) space for the map.
     *
     * Template:
     *   result = n
     *   for start: for (len = 3, step = 3; start + len <= n; len += step, step++):
     *       freq = first-appearance-ordered counts of nums[start .. start + len)
     *       if freq values == 1, 2, …, k → result++
     *
     * Alternatives (both O(n^2)):
     *  - Smallest change: keep ONE LinkedHashMap per start and add only nums[prevEnd .. end)
     *    before each check instead of rebuilding it.
     *  - O(1) check per step: extend end one element at a time; a value gets rank = ++distinct on
     *    first appearance, and `good` counts values with freq == rank (update it before and after
     *    each freq++). Staircase iff good == distinct — the "formed" counter of 76 Minimum Window
     *    Substring. int[] freq / rank (values <= 1e5), cleared after each start: ~1 ms at n = 1500.
     */
    public static void main(String[] args) {
        CountFrequencyStaircaseSubarraysI C = new CountFrequencyStaircaseSubarraysI();

        // Test 1: LeetCode example 1 — the whole array qualifies, which `start + count < size` skipped
        System.out.println("Test 1: " + C.countStaircase(new int[]{5, 8, 8, 2, 2, 2}) + " (Expected: 9)");

        // Test 2: LeetCode example 2 — [6,9,9] starts at index 1, missed when the length never reset
        System.out.println("Test 2: " + C.countStaircase(new int[]{6, 6, 9, 9}) + " (Expected: 5)");

        // Test 3: LeetCode example 3 — values interleaved, not grouped
        System.out.println("Test 3: " + C.countStaircase(new int[]{2, 3, 4, 4, 3, 4}) + " (Expected: 8)");

        // Test 4: single element
        System.out.println("Test 4: " + C.countStaircase(new int[]{7}) + " (Expected: 1)");

        // Test 5: n = 2 — length 2 is never a staircase, [4,4] has frequency 2 for the first value
        System.out.println("Test 5: " + C.countStaircase(new int[]{4, 4}) + " (Expected: 2)");

        // Test 6: right frequencies in the wrong order — 2, 1 is not 1, 2
        System.out.println("Test 6: " + C.countStaircase(new int[]{2, 2, 1}) + " (Expected: 3)");

        // Test 7: four-step staircase, length 10 — the length must step 3 → 6 → 10
        System.out.println("Test 7: " + C.countStaircase(new int[]{1, 2, 2, 3, 3, 3, 4, 4, 4, 4}) + " (Expected: 15)");

        // Test 8: max n, all distinct — the biggest maps; rebuilding every subarray (O(n^3)) would time out
        int[] distinct = new int[1500];
        for (int i = 0; i < 1500; i++)
            distinct[i] = i + 1;
        System.out.println("Test 8: " + C.countStaircase(distinct) + " (Expected: 1500)");
    }
}
