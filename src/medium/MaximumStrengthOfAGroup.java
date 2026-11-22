package medium;

// https://leetcode.com/problems/maximum-strength-of-a-group/
public class MaximumStrengthOfAGroup {
    public long maxStrength(int[] nums) {
        int size = nums.length;
        long[] minDP = new long[size+1];
        long[] maxDP = new long[size+1];
        minDP[1] = maxDP[1] = nums[0];
        for (int i = 1; i < size; i++) {
            if (nums[i] >= 0) {
                minDP[i+1] = Math.min(nums[i], Math.min(minDP[i], minDP[i] * nums[i]));
                maxDP[i+1] = Math.max(nums[i], Math.max(maxDP[i], maxDP[i] * nums[i]));
            } else {
                minDP[i+1] = Math.min(nums[i], Math.min(minDP[i], maxDP[i] * nums[i]));
                maxDP[i+1] = Math.max(nums[i], Math.max(maxDP[i], minDP[i] * nums[i]));
            }
        }
        return Math.max(minDP[size], maxDP[size]);
    }

    long result;
    public long maxStrengthV2(int[] nums) {
        int size = nums.length;
        result = Integer.MIN_VALUE;
        backtrack(nums, 0, size, 1L, 0);
        return result;
    }

    private void backtrack(int[] nums, int index, int size, long curr, int count) {
        if (index == size) {
            if (count > 0)
                result = Math.max(result, curr);
            return;
        }
        backtrack(nums, index+1, size, curr, count);
        backtrack(nums, index+1, size, curr * nums[index], count+1);
    }

    /*
     * Revision Note — Maximum Strength of a Group (Medium)
     *
     * Pattern: max/min product DP over SUBSETS (not subarrays). At each element, skip it,
     *          start a new group with it, or extend the best or worst group so far.
     *          maxStrengthV2 is the O(2^n) include/exclude backtracking, which is fine at n <= 13.
     *
     * Key insight: a negative swaps the extremes, so track both the max and the min:
     *   x >= 0:  max' = max(x, max, max * x)     min' = min(x, min, min * x)
     *   x <  0:  max' = max(x, max, min * x)     min' = min(x, min, max * x)
     *   Keeping max/min as candidates is the "skip x" option that a subset allows
     *   and a subarray (152) doesn't.
     *
     * Gotchas:
     *   - Seed with nums[0], NOT 1. The value 1 is the empty group's product, and a max carries
     *     it to the end: [-5] -> 1, [-5, 0] -> 1, [0, 0, 0] -> 1 (tests 3-5). The group must be non-empty.
     *   - long, not Integer: 9^10 already exceeds int, and the int multiply wraps silently
     *     (tests 6-7 gave 9^8 and 9^9).
     *   - In the backtracking, count > 0 is the same non-empty guard.
     *   - Greedy alternative: multiply all non-zero values; if negative, divide out the largest negative;
     *     special-case all zeros / one negative plus zeros / a lone negative.
     *
     * Complexity: DP O(n) time, O(n) space as written (O(1) with two variables).
     *             Backtracking O(2^n) time, O(n) recursion depth.
     *
     * Template:
     *   hi = lo = nums[0]                          // non-empty seed
     *   for x in nums[1..]:
     *       h = max(hi, x, hi * x, lo * x); l = min(lo, x, hi * x, lo * x)
     *       hi, lo = h, l
     *   return hi
     */
    public static void main(String[] args) {
        MaximumStrengthOfAGroup M = new MaximumStrengthOfAGroup();

        // Test 1: LeetCode example 1 — even number of negatives once -1 is dropped; everything else is multiplied
        System.out.println("Test 1: " + M.maxStrength(new int[]{3, -1, -5, 2, 5, -9}) + " (Expected: 1350)");

        // Test 2: LeetCode example 2 — three negatives, so drop the one closest to zero (-4) and pair the rest
        System.out.println("Test 2: " + M.maxStrength(new int[]{-4, -5, -4}) + " (Expected: 20)");

        // Test 3: single negative element — the group must be non-empty, so -5, not 1 (seeding the DP with 1 gave 1)
        System.out.println("Test 3: " + M.maxStrength(new int[]{-5}) + " (Expected: -5)");

        // Test 4: one negative and one zero — the lone negative cannot be paired, so {0} beats {-5}
        System.out.println("Test 4: " + M.maxStrength(new int[]{-5, 0}) + " (Expected: 0)");

        // Test 5: all zeros — no non-zero product exists, so the answer is 0
        System.out.println("Test 5: " + M.maxStrength(new int[]{0, 0, 0}) + " (Expected: 0)");

        // Test 6: thirteen -9s — odd count, drop one: 9^12 exceeds int (Integer[] DP gave 43046721)
        System.out.println("Test 6: " + M.maxStrength(new int[]{-9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9, -9}) + " (Expected: 282429536481)");

        // Test 7: thirteen 9s — the largest possible answer, 9^13 = 2.5e12, needs long
        System.out.println("Test 7: " + M.maxStrength(new int[]{9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9}) + " (Expected: 2541865828329)");

        // Test 8: two negatives separated by a zero — skip the zero and pair the negatives
        System.out.println("Test 8: " + M.maxStrength(new int[]{-5, 0, -3}) + " (Expected: 15)");
    }
}
