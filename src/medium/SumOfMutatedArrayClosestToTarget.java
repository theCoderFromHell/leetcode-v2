package medium;

import java.util.Arrays;

// https://leetcode.com/problems/sum-of-mutated-array-closest-to-target/
public class SumOfMutatedArrayClosestToTarget {
    public int findBestValue(int[] arr, int target) {
        int size = arr.length;
        Arrays.sort(arr);
        int start = 0;
        int end = arr[size-1];
        int mid;
        int result = Integer.MAX_VALUE;
        int minDiff = Integer.MAX_VALUE;
        while (start <= end) {
            mid = start + (end - start) / 2;
            int sum = sum(arr, size, mid);
            int diff = Math.abs(sum - target);
            if (diff < minDiff || (diff == minDiff && mid < result)) {
                minDiff = diff;
                result = mid;
            }
            if (sum == target)
                return mid;
            else if (sum < target)
                start = mid + 1;
            else
                end = mid - 1;
        }
        return result;
    }

    private int sum(int[] arr, int size, int value) {
        int total = 0;
        for (int i = 0; i < size; i++)
            total += Math.min(arr[i], value);
        return total;
    }

    /*
     * Revision Note — Sum of Mutated Array Closest to Target (Medium)
     * Pattern: binary search on the answer value over a monotone function
     *          S(v) = sum of min(a, v), scoring every probed mid against the target.
     * Key Insight: S is strictly increasing on [0, max(arr)] — each +1 to v adds count(a >= v) >= 1 —
     *              so |S(v) - target| is V-shaped and the best value sits on one of the two sides of
     *              the point where S crosses target. A standard `start <= end` search probes both:
     *              start only ever becomes mid + 1 and end only ever becomes mid - 1, so the final
     *              start - 1 and start were each a mid at some point. Scoring every mid is enough.
     * Gotchas:
     *   - Search from 0, not min(arr): [10, 10, 10], target 5 -> 2, below every element.
     *   - `start <= end`, not `<`: with `<` the last point is never probed, which loses max(arr)
     *     when target >= total ([2, 3, 5], 10 -> 5) and exact hits like [1, 4], 2 -> 1.
     *   - Ties go to the smaller value, so compare (diff, value), not diff alone:
     *     [4, 9, 3], 10 has S(3) = 9 and S(4) = 11, both off by 1 -> 3.
     *   - Earlier versions recorded a candidate only when the neighbour across the crossing was
     *     strictly closer. Every patch of those neighbour checks left another gap (ties, the final
     *     point, no crossing at all). Let the loop only move bounds; score each probe the same way.
     *   - S <= 10^4 * 10^5 = 10^9, so int is safe.
     *   - The sort is only used for max(arr); sum() doesn't need sorted input. A max pass drops it.
     *   - Alternatives: ternary search on the V (LeetCode's hint), or sort + prefix sums — at the
     *     first i where prefix + arr[i] * (n - i) >= target, the cap is floor or floor + 1 of
     *     (target - prefix) / (n - i). O(n log n), no search on the value.
     * Complexity: O(n log n + n log M) time, M = max(arr) <= 10^5 — about 17 probes of O(n) each.
     *             O(log n) space for the sort; note that Arrays.sort mutates the caller's array.
     * Template:
     *   lo = 0, hi = max(arr), best = INF, bestDiff = INF
     *   while lo <= hi:
     *       mid = lo + (hi - lo) / 2; s = S(mid); d = |s - target|
     *       if d < bestDiff or (d == bestDiff and mid < best): best = mid, bestDiff = d
     *       if s == target: return mid
     *       if s < target: lo = mid + 1 else hi = mid - 1
     *   return best
     */
    public static void main(String[] args) {
        SumOfMutatedArrayClosestToTarget S = new SumOfMutatedArrayClosestToTarget();

        // Test 1: LeetCode example 1 — S(3) = 9 and S(4) = 11 tie, the smaller value wins
        System.out.println("Test 1: " + S.findBestValue(new int[]{4, 9, 3}, 10) + " (Expected: 3)");

        // Test 2: LeetCode example 2 — exact hit at max(arr), the last point of the range
        System.out.println("Test 2: " + S.findBestValue(new int[]{2, 3, 5}, 10) + " (Expected: 5)");

        // Test 3: LeetCode example 3 — answer is not an element of arr
        System.out.println("Test 3: " + S.findBestValue(new int[]{60864, 25176, 27249, 21296, 20204}, 56803) + " (Expected: 11361)");

        // Test 4: target above the total sum — no crossing at all, answer is max(arr)
        System.out.println("Test 4: " + S.findBestValue(new int[]{5}, 100) + " (Expected: 5)");

        // Test 5: answer below min(arr) — the search must start at 0
        System.out.println("Test 5: " + S.findBestValue(new int[]{10, 10, 10}, 5) + " (Expected: 2)");

        // Test 6: exact hit only reached when start == end, missed by `while (start < end)`
        System.out.println("Test 6: " + S.findBestValue(new int[]{1, 4}, 2) + " (Expected: 1)");
    }
}
