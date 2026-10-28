package medium;

import java.util.Arrays;

// https://leetcode.com/problems/longest-resilient-subarray-ii/
public class LongestResilientSubarrayII {
    public int resilientSubarray(int[] nums, int k) {
        int size = nums.length;
        int result = 1;
        int currRemainder = nums[0] % k;
        int start = 0, end = 1;
        long currTotal = nums[0];
        while (end < size) {
            while (end < size && nums[end] % k == currRemainder) {
                currTotal += nums[end];
                end++;
                if (currTotal % k == currRemainder)
                    result = Math.max(result, end - start);
            }
            if (end < size) {
                currTotal = 0;
                start = end;
                currRemainder = nums[start] % k;
            }
        }
        return result;
    }

    /*
     * Revision Note — Longest Resilient Subarray II (Medium)
     *
     * Same code as LongestResilientSubarrayI. Only the constraints change: n ≤ 1e5 instead of 2500.
     *
     * Pattern: Split the array into maximal runs of equal remainder (mod k), then scan each run's
     * prefixes with a running sum.
     *
     * Key insight: S - x ≡ 0 and S - y ≡ 0 (mod k) force x ≡ y, so every element of a resilient
     * subarray has the same remainder r, and the condition collapses to S ≡ r. With L elements
     * S ≡ L·r, so the test is (L - 1)·r ≡ 0 (mod k). It depends ONLY on the length, so checking
     * the prefixes of each run (every length 1..m) is enough. No need to try every start.
     *
     * What II adds over I:
     *  - I's intended O(n^2) "fix left, extend right" is ~5e9 steps at n = 1e5 → TLE. The run
     *    scan is O(n).
     *  - The sum reaches 1e5 · 1e5 = 1e10 → currTotal MUST be long. An int sum wraps and the
     *    sum % k == r check fires on the wrong lengths (Test 11).
     *
     * Gotchas (same as I):
     *  - Length 1 is always resilient, so result starts at 1.
     *  - On a remainder break, end is NOT advanced: the inner loop re-adds the new run's first
     *    element, so currTotal = 0 is the right reset.
     *  - Valid lengths step by d = k / gcd(r, k), not by k: r = 0 → every length works.
     *
     * Complexity: O(n) time (end only moves forward), O(1) space.
     *
     * Template:
     *   result = 1
     *   for each maximal run with remainder r:
     *       sum = 0 (long)
     *       for len = 1..m: sum += next; if sum % k == r → result = max(result, len)
     *
     * Alternative (closed form per run, no sum, so no overflow risk at all):
     *   d = k / gcd(r, k); best = 1 + (m - 1) / d * d.
     */
    public static void main(String[] args) {
        LongestResilientSubarrayII L = new LongestResilientSubarrayII();

        // Test 1: LeetCode example 1 — the trailing 3 breaks the remainder-0 run
        System.out.println("Test 1: " + L.resilientSubarray(new int[]{2, 4, 6, 3}, 2) + " (Expected: 3)");

        // Test 2: LeetCode example 2 — remainder 1 everywhere, length 4 works since (4 - 1)·1 ≡ 0 mod 3
        System.out.println("Test 2: " + L.resilientSubarray(new int[]{1, 4, 7, 1}, 3) + " (Expected: 4)");

        // Test 3: LeetCode example 3 — the [5, 5] run fails, the best is in the second run
        System.out.println("Test 3: " + L.resilientSubarray(new int[]{5, 5, 4, 8}, 4) + " (Expected: 2)");

        // Test 4: single element — always resilient (remaining sum 0)
        System.out.println("Test 4: " + L.resilientSubarray(new int[]{7}, 3) + " (Expected: 1)");

        // Test 5: k == 1 — every remainder is 0, so the whole array qualifies
        System.out.println("Test 5: " + L.resilientSubarray(new int[]{3, 5, 8, 2}, 1) + " (Expected: 4)");

        // Test 6: one run of 7 with r = 1, k = 5 — lengths 2..5 fail, 6 works, 7 fails (not monotonic)
        System.out.println("Test 6: " + L.resilientSubarray(new int[]{1, 1, 1, 1, 1, 1, 1}, 5) + " (Expected: 6)");

        // Test 7: r = 3, k = 6 — d = k / gcd(3, 6) = 2, only odd lengths work, so the full run of 4 fails
        System.out.println("Test 7: " + L.resilientSubarray(new int[]{3, 3, 3, 3}, 6) + " (Expected: 3)");

        // Test 8: best run sits between two breaks — exercises the reset path on both sides
        System.out.println("Test 8: " + L.resilientSubarray(new int[]{5, 1, 1, 1, 1, 1, 1, 2}, 5) + " (Expected: 6)");

        // Test 9: remainder-0 run after a break — r = 0 makes every length valid
        System.out.println("Test 9: " + L.resilientSubarray(new int[]{10, 20, 3, 30, 40, 50}, 10) + " (Expected: 3)");

        // Test 10: r = 2, k = 6 — step d = 3, not k: valid lengths 1, 4, 7, so 7 out of a run of 9
        System.out.println("Test 10: " + L.resilientSubarray(new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2}, 6) + " (Expected: 7)");

        // Test 11: max n, max values, r = 1, k = 99999 — only lengths 1 and 100000 are valid.
        // The sum hits 1e10 (an int sum wraps), and n = 1e5 rules out an O(n^2) scan
        int[] big = new int[100000];
        Arrays.fill(big, 100000);
        System.out.println("Test 11: " + L.resilientSubarray(big, 99999) + " (Expected: 100000)");
    }
}
