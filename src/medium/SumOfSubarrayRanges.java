package medium;

// https://leetcode.com/problems/sum-of-subarray-ranges/
public class SumOfSubarrayRanges {
    public long subArrayRanges(int[] nums) {
        int size = nums.length;
        long result = 0;
        for (int i = 0; i < size; i++) {
            int min = nums[i];
            int max = nums[i];
            for (int j = i+1; j < size; j++) {
                min = Math.min(min, nums[j]);
                max = Math.max(max, nums[j]);
                result += (max - min);
            }
        }
        return result;
    }

    /*
     * Revision Note - Sum of Subarray Ranges (Medium)
     *
     * Pattern: O(n^2) enumeration with an INCREMENTAL running min and max
     *
     * Key Insight: Fix the left endpoint i, then extend j rightwards carrying min and max forward.
     * Extending a window by one element updates both extremes in O(1), so each of the ~n^2/2
     * subarrays costs two comparisons instead of a rescan - that is the whole difference between
     * O(n^2) and O(n^3). n <= 1000 is chosen so the quadratic solution is the intended answer.
     *
     * Gotchas:
     * - Inner loop starts at j = i+1, not j = i. A single-element subarray has range 0, so it
     *   contributes nothing; seeding min and max from nums[i] BEFORE the loop means every
     *   iteration adds a real range. Starting at j = i is equally correct, just n wasted adds
     * - RESULT MUST BE long. n=1000 gives ~500k subarrays, each range up to 2e9, so the total
     *   reaches ~1e15. The signature already returns long - take the hint
     * - `max - min` itself stays in int, but only just: 1e9 - (-1e9) = 2e9 against
     *   Integer.MAX_VALUE = 2,147,483,647, about 7% of room. Were the value range wider, the
     *   SUBTRACTION would wrap before the long accumulator ever saw it, and the cast would have
     *   to move inside: (long) max - min
     * - Negative values are fine - a range is max minus min, so it is non-negative regardless
     * - All-equal input gives 0, not n. Easy to assume some positive floor
     *
     * Complexity: O(n^2) time, ~500k iterations at the limit, 1ms measured. O(1) space.
     *
     * Template:
     *   for i in 0..n-1:
     *     mn = mx = nums[i]
     *     for j in i+1..n-1:
     *       mn = min(mn, nums[j]);  mx = max(mx, nums[j])
     *       total += mx - mn
     *
     * The O(n) FOLLOW-UP, worth knowing because it reuses machinery already written for 907:
     *     sum of ranges  =  sum of maxes  -  sum of mins
     * Both terms are independent sums over the same subarray set, and each is one monotonic-stack
     * pass counting, for every element, how many subarrays it is the extreme of:
     *     contribution = value * (distance to previous dominating element)
     *                          * (distance to next dominating element)
     * THE TIE-BREAKING MUST BE ASYMMETRIC between the two passes - one strict, one non-strict.
     * With equal values, using the same comparison in both would count a subarray's extreme twice
     * in one pass and zero times in the other. [1,3,3] -> 4 is the test that catches it.
     * Note also that 2104 has NO modulus, unlike 907 - do not carry the % MOD across by reflex.
     *
     * The decomposition is the transferable part: when an objective splits into independent
     * additive pieces, solve each piece with machinery you already own.
     */

    public static void main(String[] args) {
        SumOfSubarrayRanges S = new SumOfSubarrayRanges();

        System.out.println("Test 1: " + S.subArrayRanges(new int[]{1,2,3}) + " (Expected: 4)");
        System.out.println("Test 2: " + S.subArrayRanges(new int[]{1,3,3}) + " (Expected: 4)");          // duplicates
        System.out.println("Test 3: " + S.subArrayRanges(new int[]{4,-2,-3,4,1}) + " (Expected: 59)");
        System.out.println("Test 4: " + S.subArrayRanges(new int[]{1}) + " (Expected: 0)");              // n=1, no subarray has a range
        System.out.println("Test 5: " + S.subArrayRanges(new int[]{5,5,5}) + " (Expected: 0)");          // all equal, every range is 0
        System.out.println("Test 6: " + S.subArrayRanges(new int[]{3,1}) + " (Expected: 2)");            // n=2
        System.out.println("Test 7: " + S.subArrayRanges(new int[]{-5,-1,-3}) + " (Expected: 10)"); // all negative
        System.out.println("Test 8: " + S.subArrayRanges(new int[]{1000000000,-1000000000}) + " (Expected: 2000000000)"); // widest single range: 2e9, just inside int

        // the accumulator must be long: n=1000 alternating +-1e9 gives ~5e14
        int[] big = new int[1000];
        for (int i = 0; i < big.length; i++) big[i] = (i % 2 == 0) ? 1000000000 : -1000000000;
        long t0 = System.nanoTime();
        long got = S.subArrayRanges(big);
        long ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("Test 9: " + (got > 4_000_000_000_000L) + " (Expected: true)  - n=1000 gave " + got + " in " + ms + "ms, far past int range");
    }
}
