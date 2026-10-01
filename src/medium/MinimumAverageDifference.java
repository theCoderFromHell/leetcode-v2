package medium;

// https://leetcode.com/problems/minimum-average-difference/
public class MinimumAverageDifference {
    public int minimumAverageDifference(int[] nums) {
        int size = nums.length;
        long total = 0;
        for (int i = 0; i < size; i++) {
            total += nums[i];
        }
        long minAverage = total/size;
        int index = size-1;
        long currSum = 0;
        for (int i = 0; i < size-1; i++) {
            currSum += nums[i];
            long average = Math.abs(currSum/(i+1) - ((total - currSum)/(size-i-1)));
            if (average <= minAverage) {
                if (average == minAverage)
                    index = Math.min(index, i);
                if (average < minAverage) {
                    minAverage = average;
                    index = i;
                }

            }
        }
        return index;
    }

    /*
     * Revision Note — Minimum Average Difference (Medium)
     *
     * Pattern: Running prefix sum vs derived suffix sum, tracking the best INDEX
     *
     * Key Insight: Seed minAverage with total/size and index with size-1 — that IS the last
     * index's answer, since its suffix is empty and defined to average 0. One initialisation
     * removes three edge cases at once: the division by zero at i = n-1, the need to include
     * that index in the loop, and the n == 1 case where the loop never runs.
     *
     * Gotchas:
     * - Return the INDEX, not the difference. The accumulator is named after the metric, so
     *   it is easy to return the wrong variable
     * - Ties go to the SMALLEST index, which lives entirely in the comparison operator.
     *   Strict < keeps the first optimum; <= keeps the last. Sample tests rarely contain
     *   ties, so the wrong operator passes everything you are given — force one with a
     *   uniform array like [1,1,1,1]
     * - Floor EACH average before subtracting: |19/4 - 8/2| = |4-4| = 0. Computing the exact
     *   difference and rounding afterwards gives a different answer
     * - MUST use long. 1e5 elements x 1e5 = 1e10, five times past Integer.MAX_VALUE
     * - Seeking the minimum, so Math.min / strict < — not max
     *
     * Template:
     *   total = sum(nums)
     *   best = total / n ; index = n-1        // the empty-suffix case, pre-seeded
     *   currSum = 0
     *   for i in 0..n-2:
     *     currSum += nums[i]
     *     diff = abs(currSum/(i+1) - (total-currSum)/(n-i-1))
     *     if diff < best: best = diff; index = i
     *   return index
     *
     * The seed trick only works because the special case is an ENDPOINT. When it is not,
     * run the loop over all n indices with an explicit guard on the empty side instead.
     */
    // O(n^2) reference, used only to cross-check
    private static int bruteForce(int[] nums) {
        int n = nums.length;
        long best = Long.MAX_VALUE;
        int bestIdx = 0;
        for (int i = 0; i < n; i++) {
            long left = 0, right = 0;
            for (int j = 0; j <= i; j++) left += nums[j];
            for (int j = i + 1; j < n; j++) right += nums[j];
            long leftAvg = left / (i + 1);
            long rightAvg = (n - i - 1 == 0) ? 0 : right / (n - i - 1);
            long diff = Math.abs(leftAvg - rightAvg);
            if (diff < best) { best = diff; bestIdx = i; }
        }
        return bestIdx;
    }

    public static void main(String[] args) {
        MinimumAverageDifference M = new MinimumAverageDifference();

        System.out.println("Test 1: " + M.minimumAverageDifference(new int[]{2, 5, 3, 9, 5, 3}) + " (Expected: 3)");
        System.out.println("Test 2: " + M.minimumAverageDifference(new int[]{0})                + " (Expected: 0)"); // n=1, right side empty
        System.out.println("Test 3: " + M.minimumAverageDifference(new int[]{4, 2, 0})          + " (Expected: 2)"); // best is the LAST index
        System.out.println("Test 4: " + M.minimumAverageDifference(new int[]{1, 2, 3})          + " (Expected: 0)");
        System.out.println("Test 5: " + M.minimumAverageDifference(new int[]{1, 1, 1, 1})       + " (Expected: 0)"); // three-way tie -> smallest index

        // overflow: 1e5 elements of 1e5 -> total 1e10
        int[] big = new int[100000];
        java.util.Arrays.fill(big, 100000);
        System.out.println("Test 6: " + M.minimumAverageDifference(big) + " (Expected: 0)");

        // cross-check against brute force, small values so ties are frequent
        boolean agree = true;
        java.util.Random rnd = new java.util.Random(17);
        for (int t = 0; t < 400; t++) {
            int[] r = new int[1 + rnd.nextInt(20)];
            for (int i = 0; i < r.length; i++) r[i] = rnd.nextInt(5);
            if (M.minimumAverageDifference(r) != bruteForce(r)) agree = false;
        }
        System.out.println("Test 7: " + agree + " (Expected: true)  — matches brute force, 400 random");
    }
}
