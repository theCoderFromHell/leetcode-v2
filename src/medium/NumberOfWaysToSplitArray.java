package medium;

import java.util.Arrays;
import java.util.Random;

// https://leetcode.com/problems/number-of-ways-to-split-array/
public class NumberOfWaysToSplitArray {
    public int waysToSplitArray(int[] nums) {
        int size = nums.length;
        long total = 0;
        for (int i = 0; i < size; i++) {
            total += nums[i];
        }
        int result = 0;
        long currSum = 0;
        for (int i = 0; i < size-1; i++) {
            currSum += nums[i];
            if (currSum >= total - currSum)
                result++;
        }
        return result;
    }

    /*
     * Revision Note — Number of Ways to Split Array (Medium)
     *
     * Pattern: Running prefix sum vs derived suffix sum, O(1) space
     *
     * Key Insight: Compute the total once, then sweep. At index i the suffix sum is just
     * total - currSum, so the right-hand accumulation never needs materialising — two long
     * scalars replace two prefix arrays.
     *
     * Gotchas:
     * - >= not >. The statement says "greater than or equal"; equality IS a valid split.
     *   Both LeetCode examples are tie-free, so a strict > passes them and still fails.
     *   Cheapest discriminator: a uniform array [k,k,...,k] has exactly one exact-balance
     *   split, so the wrong operator is off by precisely 1
     * - MUST use long. 1e5 elements x 1e5 magnitude = 1e10, five times past Integer.MAX_VALUE
     * - Loop bound is i < n-1: the split needs at least one element on the right
     * - This total-minus-prefix trick works because sum is INVERTIBLE. For a non-invertible
     *   combine (prefix max, as in 42 Trapping Rain Water) you genuinely need both arrays
     *
     * Template:
     *   long total = sum(nums)
     *   long currSum = 0; int count = 0
     *   for i in 0..n-2:
     *     currSum += nums[i]
     *     if currSum >= total - currSum: count++
     *   return count
     */
    // O(n^2) reference, used only to cross-check
    private static int bruteForce(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            long left = 0, right = 0;
            for (int j = 0; j <= i; j++) left += nums[j];
            for (int j = i + 1; j < nums.length; j++) right += nums[j];
            if (left >= right) count++;
        }
        return count;
    }

    public static void main(String[] args) {
        NumberOfWaysToSplitArray N = new NumberOfWaysToSplitArray();

        System.out.println("Test 1: " + N.waysToSplitArray(new int[]{10, 4, -8, 7}) + " (Expected: 2)");
        System.out.println("Test 2: " + N.waysToSplitArray(new int[]{2, 3, 1, 0})   + " (Expected: 2)");
        System.out.println("Test 3: " + N.waysToSplitArray(new int[]{1, 1})         + " (Expected: 1)"); // left == right, must count
        System.out.println("Test 4: " + N.waysToSplitArray(new int[]{2, 2, 2, 2})   + " (Expected: 2)"); // equality at i=1
        System.out.println("Test 5: " + N.waysToSplitArray(new int[]{-1, -1})       + " (Expected: 1)"); // negatives, still equal
        System.out.println("Test 6: " + N.waysToSplitArray(new int[]{5, 1})         + " (Expected: 1)"); // minimum length

        // overflow: 1e5 elements of 1e5 -> total 1e10, far past Integer.MAX_VALUE
        int[] big = new int[100000];
        Arrays.fill(big, 100000);
        System.out.println("Test 7: " + N.waysToSplitArray(big) + " (Expected: 50000)");

        // cross-check against brute force on random input, including equalities
        boolean agree = true;
        Random rnd = new Random(13);
        for (int t = 0; t < 400; t++) {
            int[] r = new int[2 + rnd.nextInt(20)];
            for (int i = 0; i < r.length; i++) r[i] = rnd.nextInt(7) - 3;   // small range -> frequent ties
            if (N.waysToSplitArray(r) != bruteForce(r)) agree = false;
        }
        System.out.println("Test 8: " + agree + " (Expected: true)  — matches brute force, 400 random");
    }
}
