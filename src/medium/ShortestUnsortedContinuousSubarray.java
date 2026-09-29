package medium;

// https://leetcode.com/problems/shortest-unsorted-continuous-subarray/
public class ShortestUnsortedContinuousSubarray {
    public int findUnsortedSubarray(int[] nums) {
        int size = nums.length;
        int minimumSoFar = Integer.MAX_VALUE;
        int leftIndex = -1;
        for (int i = size-1; i >= 0; i--) {
            if (nums[i] > minimumSoFar)
                leftIndex = i;
            minimumSoFar = Math.min(minimumSoFar, nums[i]);
        }
        int maximumSoFar = Integer.MIN_VALUE;
        int rightIndex = -1;
        for (int i = 0; i < size; i++) {
            if (nums[i] < maximumSoFar)
                rightIndex = i;
            maximumSoFar = Math.max(maximumSoFar, nums[i]);
        }
        if (leftIndex == -1 || rightIndex == -1)
            return 0;
        return Math.abs(rightIndex - leftIndex) + 1;
    }

    /*
     * Revision Note — Shortest Unsorted Continuous Subarray (Medium)
     *
     * Pattern: Two opposite scans, each carrying one running extreme
     *
     * Key Insight: An element is out of place iff something smaller sits to its right, or
     * something larger sits to its left. Scan RIGHT-to-LEFT tracking the running minimum —
     * the last index where nums[i] > min is the window's left edge. Scan LEFT-to-RIGHT
     * tracking the running maximum — the last index where nums[i] < max is the right edge.
     *
     * Gotchas:
     * - Update the running extreme AFTER the comparison, so at test time minimumSoFar is
     *   min(nums[i+1..n-1]) and maximumSoFar is max(nums[0..i-1]) — the element itself excluded
     * - Initialise both indices to -1, NOT 0. With 0, an already-sorted array is
     *   indistinguishable from a one-element window at index 0 and the +1 returns 1 instead of 0
     * - Strict > and < are what make duplicates work: ascending means <=, so equal neighbours
     *   are already in order
     * - Reverse loop steps with i--, not i++. `i >= 0` with `i++` runs off the top of the array
     * - Math.abs on the final span is unnecessary once the logic is right: rightIndex >= leftIndex
     *   always holds when both were set
     *
     * Template:
     *   left = right = -1
     *   min = MAX; for i = n-1 down to 0: if nums[i] > min: left = i; min = min(min, nums[i])
     *   max = MIN; for i = 0 to n-1:    if nums[i] < max: right = i; max = max(max, nums[i])
     *   return left == -1 ? 0 : right - left + 1
     */
    public static void main(String[] args) {
        ShortestUnsortedContinuousSubarray S = new ShortestUnsortedContinuousSubarray();

        System.out.println("Test 1: " + S.findUnsortedSubarray(new int[]{2, 6, 4, 8, 10, 9, 15}) + " (Expected: 5)");
        System.out.println("Test 2: " + S.findUnsortedSubarray(new int[]{1, 2, 3, 4})            + " (Expected: 0)"); // already sorted
        System.out.println("Test 3: " + S.findUnsortedSubarray(new int[]{1})                     + " (Expected: 0)"); // single element
        System.out.println("Test 4: " + S.findUnsortedSubarray(new int[]{2, 1})                  + " (Expected: 2)"); // whole array
        System.out.println("Test 5: " + S.findUnsortedSubarray(new int[]{1, 3, 2, 2, 2})         + " (Expected: 4)"); // duplicates inside window
        System.out.println("Test 6: " + S.findUnsortedSubarray(new int[]{1, 1, 1})               + " (Expected: 0)"); // all equal, sorted
    }
}
