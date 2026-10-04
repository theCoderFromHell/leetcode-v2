package medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

// https://leetcode.com/problems/non-overlapping-intervals/
public class NonOverlappingIntervals {
    public int eraseOverlapIntervals(int[][] intervals) {
        if(null == intervals || intervals.length == 0 || intervals[0].length <= 1)
            return 0;
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        int N = intervals.length;
        int maxEnd = intervals[0][1];
        int count = 0;
        for (int i = 1; i < N; i++) {
            if(intervals[i][0] < maxEnd) {
                count++;
                maxEnd = Math.min(maxEnd, intervals[i][1]);
            } else
                maxEnd = intervals[i][1];
        }
        return count;
    }

    /*
     * Revision Note - Non-overlapping Intervals (Medium)
     *
     * Pattern: Sort, then a one-pass greedy with an exchange argument
     *
     * Key Insight: Minimising removals == maximising the kept non-overlapping set. Sweeping by
     * start, whenever two intervals clash you must drop one, and the one to drop is always the
     * one ENDING LATER - keeping the earlier end can only leave more room for everything after
     * it, never less. That is the exchange argument, and it is why `maxEnd` takes the MIN on a
     * clash: the min end is the survivor, and the later-ending one is what got erased.
     *
     * Gotchas:
     * - `maxEnd = Math.min(maxEnd, intervals[i][1])` on a clash is the whole trick. Writing
     *   `= intervals[i][1]` unconditionally keeps the later end and over-removes on nested
     *   intervals: [[1,10],[2,3],[4,5]] would answer 2 instead of 1
     * - Touching endpoints are NOT an overlap. [1,2] and [2,3] coexist, so the test must be
     *   strict `<`, not `<=`. Using `<=` turns [[1,2],[2,3],[3,4]] from 0 into 2
     * - Sorting by START and keeping the min end is equivalent to sorting by END and keeping
     *   everything that fits. The by-end version needs no min() but is a different loop; do not
     *   half-mix them
     * - `Comparator.comparingInt(o -> o[0])` infers fine here because Arrays.sort fixes the
     *   target type to Comparator<int[]>. It STOPS inferring the moment you chain `.reversed()`
     *   on an int[] comparator, which then needs an explicit `(int[] o)` or a type witness
     * - Counting removals, not keeps - the answer is the number dropped, so `count` increments
     *   on the clash rather than on the survivor
     *
     * Complexity: O(n log n) time, dominated by the sort; the sweep is O(n). O(log n) to O(n)
     * space for the sort itself, O(1) auxiliary. Note the sort MUTATES the caller's array.
     *
     * Template:
     *   sort by start
     *   maxEnd = intervals[0][1]; count = 0
     *   for i in 1..n-1:
     *     if intervals[i][0] < maxEnd:                 // clash -> erase one
     *       count++
     *       maxEnd = min(maxEnd, intervals[i][1])      // keep the earlier end
     *     else:
     *       maxEnd = intervals[i][1]
     *   return count
     *
     * Same exchange argument drives 452 (Burst Balloons), 646 (Pair Chain) and 1353 (Events I).
     * It dies as soon as intervals carry WEIGHTS - then greedy is wrong and you need DP over
     * sorted ends plus binary search (2008, 1235).
     */

    /*
     * O(n^2) reference, deliberately a different paradigm: weighted-interval-scheduling DP
     * rather than a greedy. Sort by END, let dp[i] be the largest non-overlapping set that ends
     * with interval i, then the answer is n minus the best dp. No exchange argument needed.
     */
    private static int bruteForce(int[][] input) {
        int n = input.length;
        if (n == 0) return 0;
        int[][] a = new int[n][];
        for (int i = 0; i < n; i++) a[i] = input[i].clone();
        Arrays.sort(a, Comparator.comparingInt(o -> o[1]));
        int[] dp = new int[n];
        int best = 0;
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++)
                if (a[j][1] <= a[i][0]) dp[i] = Math.max(dp[i], dp[j] + 1);
            best = Math.max(best, dp[i]);
        }
        return n - best;
    }

    private static int[][] randomIntervals(Random rnd, int n, int spread) {
        int[][] a = new int[n][2];
        for (int i = 0; i < n; i++) {
            int s = rnd.nextInt(spread);
            a[i] = new int[]{s, s + 1 + rnd.nextInt(spread)};   // width >= 1: the constraint is start < end
        }
        return a;
    }

    public static void main(String[] args) {
        NonOverlappingIntervals N = new NonOverlappingIntervals();

        System.out.println("Test 1: " + N.eraseOverlapIntervals(new int[][]{{1,2},{2,3},{3,4},{1,3}}) + " (Expected: 1)");
        System.out.println("Test 2: " + N.eraseOverlapIntervals(new int[][]{{1,2},{1,2},{1,2}}) + " (Expected: 2)");      // all identical
        System.out.println("Test 3: " + N.eraseOverlapIntervals(new int[][]{{1,2},{2,3}}) + " (Expected: 0)");            // touching, not overlapping
        System.out.println("Test 4: " + N.eraseOverlapIntervals(new int[][]{{1,100},{11,22},{1,11},{2,12}}) + " (Expected: 2)");
        System.out.println("Test 5: " + N.eraseOverlapIntervals(new int[][]{{1,2}}) + " (Expected: 0)");                  // single interval
        System.out.println("Test 6: " + N.eraseOverlapIntervals(new int[][]{{1,2},{2,3},{3,4},{4,5}}) + " (Expected: 0)"); // chain of touches
        System.out.println("Test 7: " + N.eraseOverlapIntervals(new int[][]{{1,10},{2,3},{4,5}}) + " (Expected: 1)");     // nested: min(end) is what saves this
        System.out.println("Test 8: " + N.eraseOverlapIntervals(new int[][]{{1,10},{2,9},{3,8},{4,7}}) + " (Expected: 3)"); // fully nested cascade
        System.out.println("Test 9: " + N.eraseOverlapIntervals(new int[][]{{-100,-50},{-60,-40},{-45,0}}) + " (Expected: 1)"); // negatives

        // cross-check against the DP reference, small spread so clashes and ties are dense
        boolean agree = true;
        String firstBad = "";
        Random rnd = new Random(43);
        for (int t = 0; t < 600; t++) {
            int[][] a = randomIntervals(rnd, 1 + rnd.nextInt(9), 6);
            int[][] copy = new int[a.length][];
            for (int i = 0; i < a.length; i++) copy[i] = a[i].clone();
            int got = N.eraseOverlapIntervals(copy), want = bruteForce(a);
            if (got != want) {
                agree = false;
                if (firstBad.isEmpty()) firstBad = Arrays.deepToString(a) + " got " + got + " want " + want;
            }
        }
        System.out.println("Test 10: " + agree + " (Expected: true)  - matches DP reference, 600 random sets"
                + (agree ? "" : ", first mismatch: " + firstBad));
    }
}
