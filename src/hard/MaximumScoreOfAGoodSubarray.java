package hard;

import java.util.Arrays;

// https://leetcode.com/problems/maximum-score-of-a-good-subarray/
public class MaximumScoreOfAGoodSubarray {
    public int maximumScore(int[] nums, int k) {
        int size = nums.length;
        int result = nums[k];
        int left = k;
        int right = k;
        int currMin = nums[k];
        while (left >= 1 || right < size-1) {
            int leftValue, rightValue;
            leftValue = left < 1 ? 0 : nums[left - 1];
            rightValue = right >= size - 1 ? 0 : nums[right + 1];
            if (leftValue > rightValue) {
                currMin = Math.min(currMin, leftValue);
                left--;
            } else {
                currMin = Math.min(currMin, rightValue);
                right++;
            }
            result = Math.max(result, currMin * (right - left + 1));
        }
        return result;
    }

    /*
     * Revision Note - Maximum Score of a Good Subarray (Hard)
     *
     * Pattern: Two pointers EXPANDING outward from a fixed pivot, always taking the larger neighbour
     *
     * Key Insight: Start the window as [k, k] and grow it one cell at a time. Every extension adds
     * exactly 1 to the width, so the ONLY thing separating the two choices is what happens to the
     * minimum - take the larger neighbour and currMin stays as high as it can for that width.
     *
     * Why no backtracking is needed, and how this differs from 11 (Container With Most Water):
     * in 11, moving a pointer DESTROYS those possibilities forever, and the proof is that nothing
     * better could have existed on that side. Here nothing is destroyed - the smaller neighbour is
     * still there on a later step. You are only choosing the ORDER in which elements are absorbed,
     * and larger-first dominates at every width. "Deferred" rather than "eliminated", which is the
     * more robust kind of greedy argument.
     *
     * Gotchas:
     * - COMPARE THE NEIGHBOURS, not the window's own endpoints. The candidates are nums[left-1]
     *   and nums[right+1]; nums[left] and nums[right] are already inside the window, so comparing
     *   them picks a side on stale information AND folds an already-counted value into currMin,
     *   never absorbing the real one. [7,1,1,1,1] with k=0 then answers 14 instead of 7, claiming
     *   the window [7,1] has minimum 7
     * - The 0 SENTINEL for an exhausted side works ONLY because nums[i] >= 1. With zero or negative
     *   values an exhausted side would tie or win and the pointer would run off the array.
     *   Integer.MIN_VALUE is the version that does not depend on the constraints
     * - SEED result WITH nums[k], not 0. The pivot alone can be the answer ([7,1,1,1,1], k=0 -> 7),
     *   and widening from it only ever lowers currMin
     * - The loop guards (left >= 1, right < size-1) are about whether a NEIGHBOUR EXISTS, not
     *   whether the pointer is in range - easy to write as left > 0 || right < size and be wrong
     * - Ties between the two neighbours can go either way; both lead to the same optimum
     * - No overflow: 2e4 * 1e5 = 2e9 against Integer.MAX_VALUE = 2,147,483,647, about 7% of room.
     *   Test 10 sits exactly on that boundary
     *
     * Complexity: O(n) time - left only decreases, right only increases, one moves per iteration,
     * so exactly n-1 iterations. O(1) space.
     *
     * Template:
     *   left = right = k;  currMin = result = nums[k]
     *   while (left >= 1 || right < n-1):
     *     L = (left  >= 1)   ? nums[left-1]  : SENTINEL
     *     R = (right < n-1)  ? nums[right+1] : SENTINEL
     *     if L > R:  currMin = min(currMin, L); left--
     *     else:      currMin = min(currMin, R); right++
     *     result = max(result, currMin * (right - left + 1))
     *
     * Alternative - monotonic stack, no pivot expansion: compute previous-smaller and next-smaller
     * for every index, so nums[m] is the minimum over (prev[m], next[m]); keep that window if it
     * spans k. O(n) time, O(n) space. Same "span of dominance" machinery as 907 and 84, and it
     * survives variants where there is no single pivot to expand from.
     */

    public static void main(String[] args) {
        MaximumScoreOfAGoodSubarray M = new MaximumScoreOfAGoodSubarray();

        System.out.println("Test 1: " + M.maximumScore(new int[]{1,4,3,7,4,5}, 3) + " (Expected: 15)");
        System.out.println("Test 2: " + M.maximumScore(new int[]{5,5,4,5,4,1,1,1}, 0) + " (Expected: 20)");
        System.out.println("Test 3: " + M.maximumScore(new int[]{1}, 0) + " (Expected: 1)");            // n=1, window can only be [k]
        System.out.println("Test 4: " + M.maximumScore(new int[]{2,2,2}, 1) + " (Expected: 6)");        // all equal, best is the whole array
        System.out.println("Test 5: " + M.maximumScore(new int[]{1,2,3,4}, 0) + " (Expected: 4)");      // k at the LEFT edge, can only expand right
        System.out.println("Test 6: " + M.maximumScore(new int[]{4,3,2,1}, 3) + " (Expected: 4)");      // k at the RIGHT edge, can only expand left
        System.out.println("Test 7: " + M.maximumScore(new int[]{7,1,1,1,1}, 0) + " (Expected: 7)");    // never widen - the pivot alone wins
        System.out.println("Test 8: " + M.maximumScore(new int[]{1,1,1,1,7}, 4) + " (Expected: 7)");    // mirror of 7
        System.out.println("Test 9: " + M.maximumScore(new int[]{6,5,6,5,6}, 2) + " (Expected: 25)"); // ties on both sides

        // n = 1e5 all equal at the value ceiling: score = 2e4 * 1e5 = 2e9, just inside int
        int[] big = new int[100000];
        Arrays.fill(big, 20000);
        long t0 = System.nanoTime();
        int got = M.maximumScore(big, 50000);
        long ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("Test 10: " + got + " (Expected: 2000000000)  - n=1e5 at the int boundary, " + ms + "ms");
    }
}
