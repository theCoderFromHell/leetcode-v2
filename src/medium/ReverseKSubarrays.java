package medium;

import common.Util;

// https://leetcode.com/problems/reverse-k-subarrays/
public class ReverseKSubarrays {
    public int[] reverseSubarrays(int[] nums, int k) {
        int size = nums.length;
        int gap = size / k;
        int index = 0;
        while (index < size) {
            int start = index;
            int end = index + gap - 1;
            int temp;
            while (start < end) {
                temp = nums[start];
                nums[start] = nums[end];
                nums[end] = temp;
                start++;
                end--;
            }
            index += gap;
        }
        return nums;
    }

    /*
     * Revision Note — Reverse K Subarrays (Easy)
     *
     * Pattern: Two-pointer in-place reversal in a fixed-step sliding window
     *
     * Key Insight: Subarray size = n/k (not k itself); outer loop advances by gap,
     * inner two-pointer loop reverses [index, index+gap-1] in O(gap) — total work is O(n).
     *
     * Gotchas:
     * - k is the NUMBER of subarrays, not the size — subarray size = n/k
     * - k==n means gap=1, single-element subarrays, nothing changes
     * - k==1 means gap=n, entire array reversed
     *
     * Template:
     *   int gap = nums.length / k, index = 0;
     *   while (index < nums.length) {
     *       int start = index, end = index + gap - 1;
     *       while (start < end) { swap(nums, start++, end--); }
     *       index += gap;
     *   }
     */
    public static void main(String[] args) {
        ReverseKSubarrays R = new ReverseKSubarrays();
        Util.printArray(R.reverseSubarrays(new int[]{1, 2, 4, 3, 5, 6}, 3)); // [2,1,3,4,6,5]
        Util.printArray(R.reverseSubarrays(new int[]{5, 4, 4, 2}, 1));        // [2,4,4,5]
        Util.printArray(R.reverseSubarrays(new int[]{1, 2, 3, 4}, 4));        // [1,2,3,4] (k==n, no change)
        Util.printArray(R.reverseSubarrays(new int[]{1, 2, 3, 4}, 2));        // [2,1,4,3]
        Util.printArray(R.reverseSubarrays(new int[]{7}, 1));                  // [7] (single element)
    }
}
