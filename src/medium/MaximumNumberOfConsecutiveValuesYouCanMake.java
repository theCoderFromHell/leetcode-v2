package medium;

import java.util.Arrays;
import java.util.Random;

// https://leetcode.com/problems/maximum-number-of-consecutive-values-you-can-make/
public class MaximumNumberOfConsecutiveValuesYouCanMake {
    public int getMaximumConsecutive(int[] coins) {
        int size = coins.length;
        Arrays.sort(coins);
        int reach = 0;
        for (int i = 0; i < size; i++) {
            if (coins[i] > reach + 1)
                return (reach + 1);
            reach += coins[i];
        }
        return (reach + 1);
    }


    /*
     * Revision Note - Maximum Number of Consecutive Values You Can Make (Medium)
     *
     * Pattern: Sort ascending, then one pass maintaining "I can make everything in [0, reach]"
     *
     * Key Insight: The reachable set is ALWAYS A CONTIGUOUS RANGE [0, reach], never a set with
     * holes - so it collapses from a HashSet of up to 1.6e9 values to a single int.
     *
     * Why contiguous: adding coin c to a range [0, reach] makes available the old sums [0, reach]
     * plus each of them with c added, i.e. [c, c + reach]. Two intervals. They JOIN with no gap
     * exactly when c <= reach + 1, merging into [0, reach + c]. If instead c > reach + 1, the
     * value reach + 1 is unreachable - and since the array is sorted ascending, EVERY remaining
     * coin is >= c > reach + 1 too, so nothing later can ever fill that hole. Return immediately.
     *
     * Gotchas:
     * - MUST SORT. The greedy is only valid ascending: a large coin arriving early is rejected
     *   against a small reach, even though the small coins that follow would have lifted reach
     *   high enough to accept it. [3,1] returns 1 instead of 2, and LeetCode's own example 3
     *   ([1,4,10,3,1]) is unsorted, so this fails on submission, not just locally
     * - The answer COUNTS values including 0, which the empty subset always makes. So the answer
     *   is never 0 - the minimum is 1, from an input like [2] or [5,5,5]
     * - `reach` is the largest MAKEABLE value; the answer is reach + 1, the count of 0..reach.
     *   Returning `reach` is the obvious off-by-one
     * - The comparison is `c > reach + 1`, strictly. `c == reach + 1` is exactly makeable and
     *   must be accepted - [1,2,4,8,16] relies on every coin hitting that boundary and returns 32,
     *   while [1,2,4,8,17] has one coin too big by exactly 1 and stops at 16
     * - Arrays.sort MUTATES the caller's array
     * - No overflow: 4e4 coins x 4e4 each caps the sum at 1.6e9, about 25% under
     *   Integer.MAX_VALUE, so int holds the answer. Verified at the ceiling in Test 13
     *
     * Complexity: O(n log n) time, dominated by the sort; the sweep is O(n) and often exits
     * early. O(1) auxiliary space - the whole point, versus O(total sum) for the reachable-set
     * approach, which is not merely slow but out of memory at these constraints.
     *
     * Template:
     *   sort(coins)
     *   reach = 0
     *   for c in coins:
     *     if c > reach + 1: break        // gap at reach+1, nothing later is smaller
     *     reach += c
     *   return reach + 1
     *
     * This is the "first gap" half of 41 (First Missing Positive) in isolation - find the
     * smallest value you CANNOT make - but reached by a sorted greedy rather than by index-as-
     * hash. 330 (Patching Array) is the same invariant promoted from answer to loop driver: you
     * may insert coins to close gaps, so the gap steers the greedy instead of ending it.
     */

    /*
     * Exponential-free reference: a genuine subset-sum reachability table over the whole sum,
     * then scan for the first value that cannot be made. This is the HashSet idea done honestly,
     * so it shares no logic with the greedy - only usable when the total sum is small.
     */
    private static int bruteForce(int[] coins) {
        int total = 0;
        for (int c : coins) total += c;
        boolean[] reachable = new boolean[total + 2];
        reachable[0] = true;
        for (int c : coins)
            for (int v = total; v >= c; v--)       // descending: each coin used at most once
                if (reachable[v - c]) reachable[v] = true;
        int k = 0;
        while (k <= total && reachable[k]) k++;
        return k;                                  // count of 0..k-1, i.e. the first gap
    }

    public static void main(String[] args) {
        MaximumNumberOfConsecutiveValuesYouCanMake M = new MaximumNumberOfConsecutiveValuesYouCanMake();

        System.out.println("Test 1: " + M.getMaximumConsecutive(new int[]{1,3}) + " (Expected: 2)");
        System.out.println("Test 2: " + M.getMaximumConsecutive(new int[]{1,1,1,4}) + " (Expected: 8)");
        System.out.println("Test 3: " + M.getMaximumConsecutive(new int[]{1,4,10,3,1}) + " (Expected: 20)");  // UNSORTED input - needs the sort
        System.out.println("Test 4: " + M.getMaximumConsecutive(new int[]{3,1}) + " (Expected: 2)");          // unsorted, minimal case
        System.out.println("Test 5: " + M.getMaximumConsecutive(new int[]{10,1,1,1,1}) + " (Expected: 5)");   // the big coin comes first in the input
        System.out.println("Test 6: " + M.getMaximumConsecutive(new int[]{1}) + " (Expected: 2)");            // 0 and 1
        System.out.println("Test 7: " + M.getMaximumConsecutive(new int[]{2}) + " (Expected: 1)");            // only 0 - answer is never 0
        System.out.println("Test 8: " + M.getMaximumConsecutive(new int[]{5,5,5}) + " (Expected: 1)");        // nothing reachable but 0
        System.out.println("Test 9: " + M.getMaximumConsecutive(new int[]{1,1}) + " (Expected: 3)");
        System.out.println("Test 10: " + M.getMaximumConsecutive(new int[]{1,2,4,8,16}) + " (Expected: 32)"); // exact doubling, every coin just fits
        System.out.println("Test 11: " + M.getMaximumConsecutive(new int[]{1,2,4,8,17}) + " (Expected: 16)"); // one coin too big by exactly 1 -> stop early

        // cross-check against the subset-sum reference; shuffled and unsorted on purpose
        boolean agree = true;
        String firstBad = "";
        Random rnd = new Random(89);
        for (int t = 0; t < 600; t++) {
            int[] a = new int[1 + rnd.nextInt(8)];
            for (int i = 0; i < a.length; i++) a[i] = 1 + rnd.nextInt(7);
            int got = M.getMaximumConsecutive(a.clone()), want = bruteForce(a.clone());
            if (got != want) {
                agree = false;
                if (firstBad.isEmpty()) firstBad = Arrays.toString(a) + " got " + got + " want " + want;
            }
        }
        System.out.println("Test 12: " + agree + " (Expected: true)  - matches subset-sum reference, 600 random unsorted"
                + (agree ? "" : ", first mismatch: " + firstBad));

        // overflow probe: 16 doubling coins lift reach past 40000, then 39984 coins of 40000 each.
        // Total lands at ~1.6e9 - inside int, but close enough to Integer.MAX_VALUE to be worth asserting.
        int[] big = new int[40000];
        int idx = 0;
        for (int p = 0; p < 16; p++) big[idx++] = 1 << p;      // 1,2,4,...,32768  -> reach 65535
        while (idx < big.length) big[idx++] = 40000;
        long total = 0;
        for (int c : big) total += c;
        long t0 = System.nanoTime();
        int got = M.getMaximumConsecutive(big);
        long ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("Test 13: " + (got == total + 1) + " (Expected: true)  - n=40000, sum=" + total
                + ", returned " + got + " in " + ms + "ms");
    }
}
