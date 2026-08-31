package medium;

// https://leetcode.com/problems/maximum-number-of-matching-indices-after-right-shifts/
public class MaximumNumberOfMatchingIndicesAfterRightShifts {
    public int maximumMatchingIndices(int[] nums1, int[] nums2) {
        int maxMatch = 0;
        int size = nums1.length;
        for (int i = 0; i < size; i++) {
            int currMatch = match(nums1, nums2, size, i);
            maxMatch = Math.max(maxMatch, currMatch);
        }
        return maxMatch;
    }

    private int match(int[] nums1, int[] nums2, int size, int shift) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (nums2[(i + shift) % size] == nums1[i])
                count++;
        }
        return count;
    }

    /*
     * Revision Note — Maximum Number of Matching Indices After Right Shifts (Medium)
     *
     * Pattern: Brute-force over all rotations — O(n²)
     *
     * Key Insight: A right shift by k and a left shift by (n-k) are the same rotation;
     * trying all shift values 0..n-1 covers every possible rotation regardless of direction.
     * Use (i + shift) % size to access nums2 elements under each rotation.
     *
     * Gotchas:
     * - Direction (left vs right) doesn't matter when you try all n shifts — always covers all rotations
     * - No edge case for empty array since constraints guarantee n >= 1
     * - O(n) alternative exists via Z-algorithm on nums1 + sentinel + nums2 + nums2 (not needed for n ≤ 1000)
     *
     * Template:
     *   for shift in 0..n-1:
     *       count = 0
     *       for i in 0..n-1:
     *           if nums2[(i + shift) % n] == nums1[i]: count++
     *       maxMatch = max(maxMatch, count)
     */
    public static void main(String[] args) {
        MaximumNumberOfMatchingIndicesAfterRightShifts M = new MaximumNumberOfMatchingIndicesAfterRightShifts();
        System.out.println("Test 1: " + M.maximumMatchingIndices(new int[]{1, 2, 3}, new int[]{3, 1, 2})           + " (Expected: 3)"); // shift=1 aligns all
        System.out.println("Test 2: " + M.maximumMatchingIndices(new int[]{2, 1, 3, 4}, new int[]{1, 2, 2, 3})     + " (Expected: 1)"); // at most 1 match
        System.out.println("Test 3: " + M.maximumMatchingIndices(new int[]{1, 1, 1}, new int[]{1, 1, 1})           + " (Expected: 3)"); // all equal, every shift matches
        System.out.println("Test 4: " + M.maximumMatchingIndices(new int[]{1, 2, 3}, new int[]{4, 5, 6})           + " (Expected: 0)"); // no values in common
        System.out.println("Test 5: " + M.maximumMatchingIndices(new int[]{5}, new int[]{5})                       + " (Expected: 1)"); // single element
    }
}
