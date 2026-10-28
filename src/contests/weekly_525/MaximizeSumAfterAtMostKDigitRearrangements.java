package contests.weekly_525;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// https://leetcode.com/problems/maximize-sum-after-at-most-k-digit-rearrangements/
public class MaximizeSumAfterAtMostKDigitRearrangements {
    public long maximizeSum(int[] nums, int k) {
        int size = nums.length;
        List<long[]> copy = new ArrayList<>();
        for (int i = 0; i < size; i++)
            copy.add(new long[]{nums[i], highestValue(nums[i]), highestValue(nums[i]) - nums[i]});
        copy.sort(Comparator.comparingLong(o -> o[2]));
        long result = 0;
        for (int i = size-1; i >= 0; i--) {
            if (k > 0 && copy.get(i)[1] > copy.get(i)[0]) {
                result += copy.get(i)[1];
                k--;
            } else
                result += copy.get(i)[0];
        }
        return result;
    }

    public long highestValue(int num) {
        int[] digitCount = new int[10];
        int temp = num;
        while (temp > 0) {
            int digit = temp % 10;
            digitCount[digit]++;
            temp /= 10;
        }
        long result = 0;
        for (int i = 9; i >= 0; i--) {
            while (digitCount[i] > 0) {
                result = (result * 10) + i;
                digitCount[i]--;
            }
        }
        return result;
    }

    /*
     * Revision Note — Maximize Sum After At Most K Digit Rearrangements (Medium, Weekly 525 Q2)
     *
     * Pattern: Greedy by marginal gain — operations are independent (each touches one element),
     * so take the k largest gains.
     *
     * Key insight: an element's best rearrangement is its digits in descending order, so one
     * operation is worth gain = maxDigits(x) - x, regardless of what else you pick. Rank by that
     * GAIN — not by the rearranged value, not by length or leading digit.
     *
     * Gotchas:
     *  - Sorting by the rearranged value picks 29→92 and 37→73 in [26,6,37,41,27,29], k = 2
     *    (265), but 27→72 gains 45 vs 37→73's 36 — the answer is 274.
     *  - Zeros sort last in descending order, so there is no leading-zero case: 100 stays 100.
     *  - A gain is never negative, so "at most k" just means skipping zero-gain elements.
     *  - Sum reaches ~1e5 × 1e9 → long. Three 987654321s already overflow an int.
     *  - nums[i] <= 1e9 means up to 10 digits — 1e9 itself can't be improved.
     *
     * Complexity: O(n log n + n·d) time (d <= 10 digits per number), O(n) space.
     *
     * Template:
     *   result = sum(nums)
     *   gains  = [maxDigits(x) - x for x in nums], sorted descending
     *   result += sum of the first k gains
     *
     * Alternatives: a min-heap of size k gives O(n log k); sorting a primitive long[] of gains
     * skips the List<long[]> boxing and comparator; quickselect is O(n) average.
     */
    public static void main(String[] args) {
        MaximizeSumAfterAtMostKDigitRearrangements M = new MaximizeSumAfterAtMostKDigitRearrangements();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + M.maximizeSum(new int[]{123, 45}, 1) + " (Expected: 366)");

        // Test 2: LeetCode example 2 — 90 is already maximal, so both operations go elsewhere
        System.out.println("Test 2: " + M.maximizeSum(new int[]{102, 90, 14}, 2) + " (Expected: 341)");

        // Test 3: biggest rearranged value != biggest gain — ranking by value gave 265
        System.out.println("Test 3: " + M.maximizeSum(new int[]{26, 6, 37, 41, 27, 29}, 2) + " (Expected: 274)");

        // Test 4: single element, single digit — nothing to gain
        System.out.println("Test 4: " + M.maximizeSum(new int[]{5}, 1) + " (Expected: 5)");

        // Test 5: zeros — 100 can't improve, 205 becomes 520
        System.out.println("Test 5: " + M.maximizeSum(new int[]{100, 205}, 1) + " (Expected: 620)");

        // Test 6: every element already maximal — operations go unused
        System.out.println("Test 6: " + M.maximizeSum(new int[]{987, 54}, 2) + " (Expected: 1041)");

        // Test 7: same digits — only 12 gains
        System.out.println("Test 7: " + M.maximizeSum(new int[]{12, 21}, 1) + " (Expected: 42)");

        // Test 8: mixed lengths — the longest number gains the most (3087 vs 9)
        System.out.println("Test 8: " + M.maximizeSum(new int[]{9, 1234, 56}, 1) + " (Expected: 4386)");

        // Test 9: sum passes Integer.MAX_VALUE — needs long
        System.out.println("Test 9: " + M.maximizeSum(new int[]{123456789, 123456789, 123456789}, 3) + " (Expected: 2962962963)");

        // Test 10: max value 1e9 — ten digits, can't be improved
        System.out.println("Test 10: " + M.maximizeSum(new int[]{1000000000, 1}, 2) + " (Expected: 1000000001)");
    }
}
