package medium;

// https://leetcode.com/problems/sum-of-beauty-in-the-array/
public class SumOfBeautyInTheArray {
    public int sumOfBeauties(int[] nums) {
        int size = nums.length;
        int[] leftMax = new int[size];
        int[] rightMin = new int[size];
        int curr = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            leftMax[i] = curr;
            curr = Math.max(curr, nums[i]);
        }
        curr = Integer.MAX_VALUE;
        for (int i = size-1; i >= 0; i--) {
            rightMin[i] = curr;
            curr = Math.min(curr, nums[i]);
        }
        int result = 0;
        for (int i = 1; i < size-1; i++) {
            if (leftMax[i] < nums[i] && nums[i] < rightMin[i])
                result += 2;
            else if (nums[i-1] < nums[i] && nums[i] < nums[i+1]) {
                result += 1;
            }
        }
        return result;
    }

    /*
     * Revision Note - Sum of Beauty in the Array (Medium)
     *
     * Pattern: exclusive prefix-max / suffix-min arrays, combined per index.
     *
     * Key Insight: "beats everything on the left and loses to everything on the right" is
     * leftMax[i] < nums[i] < rightMin[i], where both arrays EXCLUDE i itself. Precompute each
     * side in one pass, then score every index in O(1).
     *
     * Gotchas:
     * - Exclusion comes from ordering: write leftMax[i] BEFORE folding nums[i] into the running
     *   max. Folding first would compare nums[i] against itself and the strict < always fails.
     * - Check the global condition (2) before the neighbour one (1). Every 2 also satisfies the
     *   neighbour check, so the else-if order is what keeps a 2 from being scored as a 1.
     * - Strict < on both sides: ties kill both conditions, e.g. [1,2,2,3] scores 0.
     * - The MIN/MAX sentinels land in leftMax[0] and rightMin[n-1], which the 1..n-2 scoring
     *   loop never reads.
     * - Unlike 42 (Trapping Rain Water), no O(1)-space two-pointer version exists. 42 needs only
     *   min(L, R), so the smaller side can be settled early. This needs L < x < R, both bounds
     *   exactly, so a partial view of one side can rule an index out but never confirm it.
     *
     * Complexity: O(n) time (three linear passes), O(n) space (two arrays; one is enough if the
     * prefix max is carried inside the scoring loop).
     *
     * Template:
     *   run = -INF; for i in 0..n-1:  leftMax[i] = run;  run = max(run, a[i])
     *   run = +INF; for i in n-1..0:  rightMin[i] = run; run = min(run, a[i])
     *   for i in 1..n-2:
     *       if leftMax[i] < a[i] < rightMin[i]: sum += 2
     *       elif a[i-1] < a[i] < a[i+1]:        sum += 1
     */

    public static void main(String[] args) {
        SumOfBeautyInTheArray S = new SumOfBeautyInTheArray();

        System.out.println("Test 1: " + S.sumOfBeauties(new int[]{1,2,3}) + " (Expected: 2)");
        System.out.println("Test 2: " + S.sumOfBeauties(new int[]{2,4,6,4}) + " (Expected: 1)");
        System.out.println("Test 3: " + S.sumOfBeauties(new int[]{3,2,1}) + " (Expected: 0)");
        System.out.println("Test 4: " + S.sumOfBeauties(new int[]{5,5,5}) + " (Expected: 0)");         // n=3 minimum, all ties
        System.out.println("Test 5: " + S.sumOfBeauties(new int[]{1,2,2,3}) + " (Expected: 0)");       // a tie breaks both conditions
        System.out.println("Test 6: " + S.sumOfBeauties(new int[]{1,2,3,4,5}) + " (Expected: 6)");     // strictly increasing: every inner index is a 2
        System.out.println("Test 7: " + S.sumOfBeauties(new int[]{3,1,2,3,0}) + " (Expected: 1)");     // index 2 is a 1 only: the 3 at index 0 blocks the global check
        System.out.println("Test 8: " + S.sumOfBeauties(new int[]{1,5,2,6,7}) + " (Expected: 2)");     // index 3 is a 2 even though index 1 failed
    }
}
