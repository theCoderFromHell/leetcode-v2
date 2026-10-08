package medium;

import java.util.Arrays;

// https://leetcode.com/problems/find-the-maximum-number-of-marked-indices/
public class FindTheMaximumNumberOfMarkedIndices {
    public int maxNumOfMarkedIndices(int[] nums) {
        int size = nums.length;
        Arrays.sort(nums);
        int start = 0, mid = size/2;
        int result = 0;
        while (start < size/2 && mid < size) {
            if (2 * nums[start] <= nums[mid]) {
                result += 2;
                start++;
                mid++;
            } else
                mid++;
        }
        return result;
    }

    /*
     * Revision Note — Find the Maximum Number of Marked Indices (Medium)
     * Pattern: Sort + two pointers across a fixed split — left half small, right half large
     * Key Insight: At most n/2 pairs can exist, so only the n/2 smallest elements can ever play
     *              the small role and the larger half the big role. Walk i over [0, n/2) and j
     *              from n/2; give each small element the SMALLEST large partner that covers it.
     * Gotchas:
     *   - Pairing extremes (Boats to Save People style) is WRONG here. Boats must rescue everyone;
     *     this maximises pairs, so spending a huge element on a tiny one wastes it.
     *     [2,3,4,5,9,10]: extremes mark 4, half-split marks 6.
     *   - j can start at n/2 even when n is odd: i stays below n/2, so the halves never overlap
     *     and the middle element is free to serve as a large partner.
     *   - No match -> move j. A nums[j] too small for nums[i] is too small for every later i.
     *     Match -> move both. Stop when either side runs out.
     *   - Return 2 * pairs: the answer counts INDICES, not pairs.
     *   - No overflow: nums[i] <= 1e9, so 2 * nums[i] <= 2e9 < Integer.MAX_VALUE.
     * Complexity: O(n log n) for the sort, O(n) for the scan; O(1) extra (sorts in place).
     */
    public static void main(String[] args) {
        FindTheMaximumNumberOfMarkedIndices F = new FindTheMaximumNumberOfMarkedIndices();

        // Test 1: LeetCode example 1 — only (2, 5) works
        System.out.println("Test 1: " + F.maxNumOfMarkedIndices(new int[]{3, 5, 2, 4}) + " (Expected: 2)");

        // Test 2: LeetCode example 2 — (2, 5) and (4, 9)
        System.out.println("Test 2: " + F.maxNumOfMarkedIndices(new int[]{9, 2, 5, 4}) + " (Expected: 4)");

        // Test 3: LeetCode example 3 — no a with 2a <= b
        System.out.println("Test 3: " + F.maxNumOfMarkedIndices(new int[]{7, 6, 8}) + " (Expected: 0)");

        // Test 4: single element — nothing to pair
        System.out.println("Test 4: " + F.maxNumOfMarkedIndices(new int[]{1}) + " (Expected: 0)");

        // Test 5: exact boundary 2a == b counts
        System.out.println("Test 5: " + F.maxNumOfMarkedIndices(new int[]{1, 2}) + " (Expected: 2)");

        // Test 6: THE BOATS TRAP — pairing smallest with largest marks only 4; half-split marks 6
        System.out.println("Test 6: " + F.maxNumOfMarkedIndices(new int[]{2, 3, 4, 5, 9, 10}) + " (Expected: 6)");

        // Test 7: odd length — right half starts at n/2, middle element may serve as a large partner
        System.out.println("Test 7: " + F.maxNumOfMarkedIndices(new int[]{1, 2, 5}) + " (Expected: 2)");

        // Test 8: all equal — 2a <= a is impossible for positive a
        System.out.println("Test 8: " + F.maxNumOfMarkedIndices(new int[]{5, 5, 5, 5}) + " (Expected: 0)");

        // Test 9: value ceiling — 2 * 10^9 = 2,000,000,000 still fits in int (max 2,147,483,647)
        System.out.println("Test 9: " + F.maxNumOfMarkedIndices(new int[]{1000000000, 1000000000}) + " (Expected: 0)");

        // Test 10: value ceiling, exact boundary — 2 * 5*10^8 == 10^9
        System.out.println("Test 10: " + F.maxNumOfMarkedIndices(new int[]{500000000, 1000000000}) + " (Expected: 2)");

    }
}
