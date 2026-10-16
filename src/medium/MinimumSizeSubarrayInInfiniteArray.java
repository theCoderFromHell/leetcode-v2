package medium;

import java.util.HashMap;

// https://leetcode.com/problems/minimum-size-subarray-in-infinite-array/
public class MinimumSizeSubarrayInInfiniteArray {
    public int minSizeSubarray(int[] nums, int target) {
        int size = nums.length;
        long total = 0;
        for (int i = 0; i < size; i++)
            total += nums[i];
        long leftover = target % total;
        int fullCopies = size * (int)(target / total);
        if (leftover == 0)
            return fullCopies;
        int minWindow = minSubArray(nums, size, leftover);
        if (minWindow == -1)
            return -1;
        return fullCopies + minWindow;
    }

    private int minSubArray(int[] nums, int size, long leftover) {
        int[] doubleNums = new int[2 * size];
        for (int i = 0; i < 2* size; i++)
            doubleNums[i] = nums[i % size];
        HashMap<Long, Integer> prefixIndex = new HashMap<>();
        prefixIndex.put(0L, -1);
        long currSum = 0;
        int minWindow = Integer.MAX_VALUE;
        for (int i = 0; i < 2 * size; i++) {
            currSum += doubleNums[i];
            if (prefixIndex.containsKey(currSum - leftover)) {
                int index = prefixIndex.get(currSum - leftover);
                minWindow = Math.min(minWindow, i - index);
            }
            prefixIndex.put(currSum, i);
        }
        return minWindow == Integer.MAX_VALUE ? -1 : minWindow;
    }

    /*
     * Revision Note - Minimum Size Subarray in Infinite Array (Medium)
     *
     * Pattern: split target into full copies + a leftover, then an exact-sum subarray search over
     * nums concatenated once (prefix-sum -> last index HashMap, as in 560 / 325).
     *
     * Key Insight: any n consecutive elements of the infinite array sum to total, whatever the
     * start. So a window summing to leftover + total is just a leftover window plus one full lap,
     * n elements longer. Taking the maximum number of copies, target / total, is always optimal;
     * what remains is leftover = target % total < total. A window that small is shorter than n, so
     * it fits inside nums + nums (it may wrap past the end once, never twice).
     *
     * Gotchas:
     * - total is long: up to 1e5 * 1e5 = 1e10 overflows int. target % total and target / total
     *   follow from it.
     * - (target / total) * n fits int: every nums[i] >= 1, so total >= n and the product <= target.
     * - leftover == 0 -> answer is the copies alone. The map search can't return length 0: positive
     *   numbers make every prefix sum new, so currSum - 0 is never already in the map.
     * - No leftover window -> -1, even when full copies exist ([2,4,6,8], target 23). Translate
     *   the MAX_VALUE sentinel before adding, or size * k + MAX_VALUE wraps negative.
     * - The first version had both of the last two bugs: it returned -2147483646 for [1,1,1], 3.
     *
     * Complexity: O(n) time - one pass for total, one pass over 2n elements. O(n) space for the
     * doubled array and the map. The sliding window (209-style, shrink while sum > leftover,
     * record when sum == leftover, index with i % n) cuts this to O(1), since all values are
     * positive.
     *
     * Template:
     *   total = sum(nums); k = target / total; left = target % total
     *   if left == 0: return k * n
     *   best = shortest window in nums+nums with sum == left   (map or sliding window)
     *   return best not found ? -1 : k * n + best
     */

    public static void main(String[] args) {
        MinimumSizeSubarrayInInfiniteArray M = new MinimumSizeSubarrayInInfiniteArray();

        System.out.println("Test 1: " + M.minSizeSubarray(new int[]{1, 2, 3}, 5) + " (Expected: 2)");
        System.out.println("Test 2: " + M.minSizeSubarray(new int[]{1, 1, 1, 2, 3}, 4) + " (Expected: 2)");
        System.out.println("Test 3: " + M.minSizeSubarray(new int[]{2, 4, 6, 8}, 3) + " (Expected: -1)");          // no window, no copies
        System.out.println("Test 4: " + M.minSizeSubarray(new int[]{1, 1, 1}, 3) + " (Expected: 3)");              // leftover 0: copies only
        System.out.println("Test 5: " + M.minSizeSubarray(new int[]{5}, 10) + " (Expected: 2)");                   // single element, leftover 0
        System.out.println("Test 6: " + M.minSizeSubarray(new int[]{1, 2, 3}, 14) + " (Expected: 7)");             // 2 copies + [2]
        System.out.println("Test 7: " + M.minSizeSubarray(new int[]{3, 1, 1, 3}, 6) + " (Expected: 2)");           // window wraps: last 3 + first 3
        System.out.println("Test 8: " + M.minSizeSubarray(new int[]{2, 4, 6, 8}, 23) + " (Expected: -1)");         // copies exist, leftover 3 doesn't
        int[] big = new int[100000];
        java.util.Arrays.fill(big, 100000);
        System.out.println("Test 9: " + M.minSizeSubarray(big, 1000000000) + " (Expected: 10000)");                // total = 1e10 overflows int
    }
}
