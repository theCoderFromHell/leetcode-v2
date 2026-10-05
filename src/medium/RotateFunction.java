package medium;

import java.util.Arrays;
import java.util.Random;

// https://leetcode.com/problems/rotate-function/
public class RotateFunction {
    public int maxRotateFunction(int[] nums) {
        int size = nums.length;
        int total = 0;
        long currFk = 0;
        for (int i = 0; i < size; i++) {
            total += nums[i];
            currFk += (i * nums[i]);
        }
        long result = currFk;
        for (int i = size-1; i >= 0; i--) {
            currFk = currFk - nums[i] * (size - 1) + (total - nums[i]);
            result = Math.max(result, currFk);
        }
        return (int)result;
    }


    /*
     * Revision Note - Rotate Function (Medium)
     *
     * Pattern: O(1)-step recurrence over rotations - never build a rotation, derive F(k) from F(k-1)
     *
     * Key Insight: Rotating clockwise by one step raises EVERY element's coefficient by exactly 1,
     * except the single element that wraps from coefficient n-1 down to 0. So:
     *       +1 to every coefficient  ->  + total
     *       one element drops n-1 to 0 ->  - n * thatValue
     *       F(k) = F(k-1) + total - n * nums[n-k]
     * Recomputing each F(k) from scratch is O(n) each, O(n^2) overall = 1e10 at n = 1e5. The
     * recurrence collapses it to one pass.
     *
     * Gotchas:
     * - DO NOT let the DP carry double as the answer accumulator. `result = max(result, next)`
     *   writing back into the variable the recurrence reads from corrupts the base the moment a
     *   rotation scores lower than the best so far. Two variables: `currFk` carries F(k),
     *   `result` tracks the maximum. [4,3,2,6] returns 35 instead of 26 when they are merged
     * - RETURN THE BEST, NOT THE CARRY. The loop ends with currFk back at F(0) (see below), so
     *   `return currFk` silently returns F(0) - 25 instead of 26 on example 1
     * - OVERFLOW, and the constraint is narrower than it reads. "The answer fits in a 32-bit
     *   integer" covers the RETURNED MAXIMUM only - individual F(k) may sit outside int range.
     *   Judge case: nums = [1, -100 x 6554], n = 6555, where
     *       F(0) = -100 * (6554*6555/2) = -2,148,073,500  <  Integer.MIN_VALUE
     *   while the answer is -2,147,411,546, which does fit. An int accumulator wraps on F(0) and
     *   never recovers, returning 2,147,483,636. Accumulate in long, cast once at the return
     * - The per-step terms are safe in int: nums[i]*(size-1) peaks near 1e7. It is only the
     *   ACCUMULATOR that overflows, which is why the fix is `long currFk` and `long result`
     * - Iterating i from size-1 down to 0 picks the wrapping element in the right order: step 1
     *   wraps nums[n-1], step 2 wraps nums[n-2], and so on
     * - The loop runs one redundant iteration. Stepping size times from F(0) lands on F(size),
     *   which IS F(0) and is already covered by the seed. Harmless for a max; `i > 0` would be
     *   exact
     * - SEED result WITH F(0), never 0. An all-negative array has an all-negative answer, and a
     *   0 seed would return 0. Test 6 ([-1,-2,-3] -> -5) exists for this
     *
     * Complexity: O(n) time, two passes. O(1) space - no rotation is ever materialised, which is
     * the entire point.
     *
     * Template:
     *   total = sum(nums);  f = sum(i * nums[i])      // F(0)
     *   best = f
     *   for i = n-1 down to 1:
     *     f = f + total - n * nums[i]                 // wrap nums[i] from coefficient n-1 to 0
     *     best = max(best, f)
     *   return (int) best
     *
     * Alternative framing: F(k) is a weighted sum over a length-n window of nums+nums with fixed
     * weights 0..n-1, so a prefix-sum sweep over the doubled array gives the same O(n). Worth
     * knowing because "consider all rotations" generalises to the doubled-array window - which is
     * exactly what 798 needs, where each element contributes over a RANGE of offsets and the
     * aggregation becomes a difference array rather than a recurrence.
     */

    /*
     * O(n^2) reference: actually build every rotation and score it. Uses long throughout so a
     * mismatch can never be blamed on the reference overflowing. Only usable for small n.
     */
    private static int bruteForce(int[] nums) {
        int n = nums.length;
        long best = Long.MIN_VALUE;
        for (int k = 0; k < n; k++) {
            long f = 0;
            for (int i = 0; i < n; i++)
                f += (long) i * nums[(i - k + n) % n];      // arr_k[i] = nums[(i-k+n)%n]
            best = Math.max(best, f);
        }
        return (int) best;
    }

    public static void main(String[] args) {
        RotateFunction R = new RotateFunction();

        System.out.println("Test 1: " + R.maxRotateFunction(new int[]{4,3,2,6}) + " (Expected: 26)");
        System.out.println("Test 2: " + R.maxRotateFunction(new int[]{100}) + " (Expected: 0)");          // n=1, only F(0) = 0
        System.out.println("Test 3: " + R.maxRotateFunction(new int[]{1,2,3}) + " (Expected: 8)");        // max is F(0) - no rotation wins
        System.out.println("Test 4: " + R.maxRotateFunction(new int[]{1,2}) + " (Expected: 2)");          // n=2
        System.out.println("Test 5: " + R.maxRotateFunction(new int[]{5,5,5,5}) + " (Expected: 30)");     // all equal, every F identical
        System.out.println("Test 6: " + R.maxRotateFunction(new int[]{-1,-2,-3}) + " (Expected: -5)");    // all negative, so the answer is negative
        System.out.println("Test 7: " + R.maxRotateFunction(new int[]{0,0,0,0}) + " (Expected: 0)");
        System.out.println("Test 8: " + R.maxRotateFunction(new int[]{100,-100,100,-100}) + " (Expected: " + bruteForce(new int[]{100,-100,100,-100}) + ")"); // value extremes
        System.out.println("Test 9: " + R.maxRotateFunction(new int[]{2,6,4,3}) + " (Expected: " + bruteForce(new int[]{2,6,4,3}) + ")"); // max in the middle, not at either end

        // cross-check against the O(n^2) reference
        boolean agree = true;
        String firstBad = "";
        Random rnd = new Random(107);
        for (int t = 0; t < 600; t++) {
            int n = 1 + rnd.nextInt(12);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(201) - 100;
            int got = R.maxRotateFunction(a.clone()), want = bruteForce(a);
            if (got != want) {
                agree = false;
                if (firstBad.isEmpty()) firstBad = Arrays.toString(a) + " got " + got + " want " + want;
            }
        }
        System.out.println("Test 10: " + agree + " (Expected: true)  - matches O(n^2) reference, 600 random"
                + (agree ? "" : ", first mismatch: " + firstBad));

        // a larger case still checkable by brute force: n = 3000
        int[] mid = new int[3000];
        Random r2 = new Random(109);
        for (int i = 0; i < mid.length; i++) mid[i] = (i % 2 == 0 ? 1 : -1) * (1 + r2.nextInt(3));
        System.out.println("Test 11: " + (R.maxRotateFunction(mid.clone()) == bruteForce(mid)) + " (Expected: true)  - n=3000 vs brute force");

        // OVERFLOW. LeetCode guarantees only that the ANSWER fits in a 32-bit int - individual
        // F(k) values may not. Here n = 6555 and nums = [1, -100 x 6554], so
        //     F(0) = -100 * (6554*6555/2) = -2,148,073,500   <   Integer.MIN_VALUE
        // while the answer (the 1 landing on the highest coefficient) is -2,147,411,546, which
        // does fit. An int accumulator wraps on F(0) and never recovers.
        int[] edge = new int[6555];
        edge[0] = 1;
        Arrays.fill(edge, 1, edge.length, -100);
        System.out.println("Test 13: " + R.maxRotateFunction(edge.clone()) + " (Expected: -2147411546)");
        System.out.println("Test 14: " + (R.maxRotateFunction(edge.clone()) == bruteForce(edge)) + " (Expected: true)  - same case vs the long-based reference");

        // n = 1e5, the constraint ceiling. Alternating +-1 keeps every F(k) small.
        int[] big = new int[100000];
        for (int i = 0; i < big.length; i++) big[i] = (i % 2 == 0) ? 1 : -1;
        long t0 = System.nanoTime();
        int got = R.maxRotateFunction(big);
        long ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("Test 15: " + (ms < 200) + " (Expected: true)  - n=100000 returned " + got + " in " + ms + "ms");
    }
}
