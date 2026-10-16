package medium;

import java.util.Arrays;

// https://leetcode.com/problems/count-the-number-of-fair-pairs/
public class CountTheNumberOfFairPairs {
    public long countFairPairs(int[] nums, int lower, int upper) {
        int size = nums.length;
        Arrays.sort(nums);
        long result = 0;
        for (int i = 0; i < size-1; i++) {
            if (nums[i] > upper/2)
                break;
            int low = lowerIndex(nums, i+1, size-1, lower - nums[i]);
            int up = higherIndex(nums, i+1, size-1, upper - nums[i]);
            result += (up - low + 1);
        }
        return result;
    }

    private int higherIndex(int[] nums, int start, int end, int target) {
        if (nums[end] <= target)
            return end;
        int mid;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (nums[mid] <= target) {
                if (mid == end || nums[mid+1] > target)
                    return mid;
                else
                    start = mid+1;
            } else
                end = mid-1;
        }
        return end;
    }

    private int lowerIndex(int[] nums, int start, int end, int target) {
        if (nums[start] >= target)
            return start;
        int mid;
        while (start <= end) {
            mid = start + (end - start)/2;
            if (nums[mid] >= target) {
                if (mid == start || nums[mid-1] < target)
                    return mid;
                else
                    end = mid-1;
            } else
                start = mid+1;
        }
        return start;
    }

    /*
     * Revision Note — Count the Number of Fair Pairs (Medium)
     *
     * Pattern: Sort + binary search per element (pair order doesn't matter, so sorting is free).
     *
     * Key insight: after sorting, the partners j > i of nums[i] that make a fair pair form ONE
     * contiguous window [first >= lower - nums[i], last <= upper - nums[i]] in nums[i+1..n-1].
     * Count = up - low + 1.
     *
     * Gotchas:
     *  - result must be long: up to n(n-1)/2 ≈ 5e9 pairs (all-zero test).
     *  - "Not found" conventions make the empty window count 0 for free:
     *    lowerIndex → end + 1, higherIndex → start - 1. Returning -1 instead silently adds up + 2.
     *  - Empty range at i = n-1: the nums[start] / nums[end] pre-checks read out of bounds there,
     *    so either loop i < n-1 or drop the pre-checks (the loop already covers them).
     *  - Early break nums[i] > upper/2: '/' truncates toward zero, which is a weaker condition
     *    than 2*nums[i] > upper for negative odd upper — still safe, it only breaks later.
     *  - lower - nums[i], upper - nums[i] stay within ±2e9 → fit in int.
     *
     * Complexity: O(n log n) time (sort + 2 binary searches per i), O(log n) space (sort).
     *
     * Template:
     *   sort(nums)
     *   for i in 0..n-2:
     *       low = firstIndex(>= lower - nums[i]) in [i+1, n-1]      // returns n if none
     *       up  = lastIndex (<= upper - nums[i]) in [i+1, n-1]      // returns i if none
     *       result += up - low + 1
     *
     * Alternative: count(sum <= upper) - count(sum <= lower - 1), each via two pointers O(n).
     */
    public static void main(String[] args) {
        CountTheNumberOfFairPairs C = new CountTheNumberOfFairPairs();

        System.out.println("Test 1: " + C.countFairPairs(new int[]{0, 1, 7, 4, 4, 5}, 3, 6) + " (Expected: 6)");
        System.out.println("Test 2: " + C.countFairPairs(new int[]{1, 7, 9, 2, 5}, 11, 11) + " (Expected: 1)");
        // single element — no pairs
        System.out.println("Test 3: " + C.countFairPairs(new int[]{5}, 0, 10) + " (Expected: 0)");
        // last index reaches an empty search range (former out-of-bounds)
        System.out.println("Test 4: " + C.countFairPairs(new int[]{0, 0}, 0, 0) + " (Expected: 1)");
        // no partner in range — guards the not-found return (former -1 bug)
        System.out.println("Test 5: " + C.countFairPairs(new int[]{0, 1}, 5, 10) + " (Expected: 0)");
        // negative odd upper — upper/2 truncates toward zero
        System.out.println("Test 6: " + C.countFairPairs(new int[]{-1, -2, -3, -2}, -5, -5) + " (Expected: 2)");
        // extremes: sums reach ±2e9
        System.out.println("Test 7: " + C.countFairPairs(new int[]{1000000000, -1000000000, 1000000000}, 0, 0) + " (Expected: 2)");
        // all duplicates, lower == upper
        System.out.println("Test 8: " + C.countFairPairs(new int[]{2, 2, 2, 2}, 4, 4) + " (Expected: 6)");
        // 1e5 zeros — answer exceeds int range
        System.out.println("Test 9: " + C.countFairPairs(new int[100000], 0, 0) + " (Expected: 4999950000)");
    }

}
