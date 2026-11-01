package contests.weekly_526;

import java.util.Arrays;

// https://leetcode.com/problems/minimum-operations-to-sort-with-locked-indices/
public class MinimumOperationsToSortWithLockedIndices {
    public int minOperations(int[] nums, int[] locked, int k) {
        int size = nums.length;
        int swaps = 0;
        int unlocks = 0;
        for (int i = 0; i < size; i++) {
            for (int j = i+1; j < size; j++) {
                if (nums[i] > nums[j]) {
                    if (locked[i] == 1) {
                        if (unlocks >= k)
                            return -1;
                        locked[i] = 0;
                        unlocks++;
                    }
                    if (locked[j] == 1) {
                        if (unlocks >= k)
                            return -1;
                        locked[j] = 0;
                        unlocks++;
                    }
                    swaps++;
                }
            }
        }
        return swaps + unlocks;
    }

    /*
     * Revision Note — Minimum Operations to Sort With Locked Indices (Medium, Weekly 526 Q2)
     *
     * Pattern: Inversion counting. Min adjacent swaps to sort = number of inversions, and an index
     * has to be unlocked exactly when it takes part in at least one inversion.
     *
     * Key insight: if nums[a] > nums[b] with a < b, every index m between them is itself in an
     * inversion (nums[m] < nums[a] gives (a, m); otherwise nums[m] > nums[a] > nums[b] gives (m, b)).
     * So the indices in NO inversion already hold their final value and nothing ever crosses them.
     * The array splits into fully unlocked segments that bubble-sort in exactly #inversions swaps.
     *   answer = inversions + (locked indices that sit in some inversion), or -1 if that count > k
     *
     * Gotchas:
     *  - A locked index whose value is already in place costs nothing, even inside the array (Test 7).
     *  - Unlocks are capped at k, swaps are not.
     *  - n ≤ 2000, so the O(n²) pair scan is ~2e6 steps, and inversions ≤ ~2e6 fit an int.
     *  - The loop flips locked[] in the caller's array (harmless on LeetCode, but it mutates input).
     *
     * Complexity: O(n²) time, O(1) extra space.
     *
     * Alternative, O(n log n): count inversions with a Fenwick tree or merge sort. Index i is in some
     * inversion iff prefixMax(0..i-1) > nums[i] or suffixMin(i+1..) < nums[i], which takes two O(n) passes.
     */
    public static void main(String[] args) {
        MinimumOperationsToSortWithLockedIndices M = new MinimumOperationsToSortWithLockedIndices();

        // Test 1: LeetCode example 1 — one unlock + one swap
        System.out.println("Test 1: " + M.minOperations(new int[]{2, 1, 3, 4}, new int[]{0, 1, 0, 1}, 1) + " (Expected: 2)");

        // Test 2: LeetCode example 2 — two unlocks + two swaps
        System.out.println("Test 2: " + M.minOperations(new int[]{3, 1, 2}, new int[]{1, 0, 1}, 2) + " (Expected: 4)");

        // Test 3: LeetCode example 3 — a locked index must move but k = 0
        System.out.println("Test 3: " + M.minOperations(new int[]{2, 3, 1}, new int[]{1, 0, 0}, 0) + " (Expected: -1)");

        // Test 4: example 2 with one unlock too few
        System.out.println("Test 4: " + M.minOperations(new int[]{3, 1, 2}, new int[]{1, 0, 1}, 1) + " (Expected: -1)");

        // Test 5: already sorted and fully locked — nothing to do, even with k = 0
        System.out.println("Test 5: " + M.minOperations(new int[]{1, 2, 3}, new int[]{1, 1, 1}, 0) + " (Expected: 0)");

        // Test 6: single element
        System.out.println("Test 6: " + M.minOperations(new int[]{1}, new int[]{1}, 0) + " (Expected: 0)");

        // Test 7: a locked index in the middle that is in no inversion — no unlock needed
        System.out.println("Test 7: " + M.minOperations(new int[]{2, 1, 3, 5, 4}, new int[]{0, 0, 1, 0, 0}, 0) + " (Expected: 2)");

        // Test 8: reversed with the middle locked — 6 inversions + 2 unlocks
        System.out.println("Test 8: " + M.minOperations(new int[]{4, 3, 2, 1}, new int[]{0, 1, 1, 0}, 2) + " (Expected: 8)");
    }
}
