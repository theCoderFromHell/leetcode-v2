package medium;

import java.util.Arrays;

// https://leetcode.com/problems/minimize-product-sum-of-two-arrays/
public class MinimizeProductSumOfTwoArrays {
    public int minProductSum(int[] nums1, int[] nums2) {
        int size = nums1.length;
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int result = 0;
        for (int i = 0; i < size; i++)
            result += nums1[i] * nums2[size - 1 - i];
        return result;
    }

    /*
     * Revision Note — Minimize Product Sum of Two Arrays (Medium)
     * Pattern: Greedy — rearrangement inequality
     * Key Insight: Dot product is minimized when one array is sorted ascending and the
     *              other descending; pair nums1[i] with nums2[size-1-i] after sorting both ascending.
     * Gotchas:
     *   - No need to reverse either array — index reversal (size-1-i) achieves the same pairing
     *     without boxing or an extra pass.
     *   - Constraints guarantee result fits in int; no long cast needed.
     */
    public static void main(String[] args) {
        MinimizeProductSumOfTwoArrays M = new MinimizeProductSumOfTwoArrays();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + M.minProductSum(new int[]{5,3,4,2}, new int[]{4,2,2,5}) + " (Expected: 40)");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + M.minProductSum(new int[]{2,1,4,5,7}, new int[]{3,2,4,8,6}) + " (Expected: 65)");

        // Test 3: single element
        System.out.println("Test 3: " + M.minProductSum(new int[]{3}, new int[]{7}) + " (Expected: 21)");

        // Test 4: all same values — any pairing gives same result
        System.out.println("Test 4: " + M.minProductSum(new int[]{2,2,2}, new int[]{3,3,3}) + " (Expected: 18)");

        // Test 5: already optimally ordered — sort should still produce correct pairing
        System.out.println("Test 5: " + M.minProductSum(new int[]{1,2,3}, new int[]{3,2,1}) + " (Expected: 10)");
    }
}
