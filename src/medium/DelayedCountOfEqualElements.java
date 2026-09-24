package medium;

// https://leetcode.com/problems/delayed-count-of-equal-elements/
public class DelayedCountOfEqualElements {
    public int[] delayedCount(int[] nums, int k) {
        int[] count = new int[100001];
        int size = nums.length;
        int[] result = new int[size];
        for (int i = size-1; i >= 0; i--) {
            count[nums[i]]++;
            if (i >= k + 1) {
                result[i - k - 1] = count[nums[i - k - 1]];
            }
        }
        return result;
    }

    /*
     * Pattern: reverse iteration with a running frequency count (suffix counting).
     * Key Insight: walking right-to-left, increment count[nums[i]] BEFORE checking
     * whether to emit a result. By the time index i-k-1 is answered, count already
     * reflects exactly the suffix window (i-k-1+k, n-1] = (i-1, n-1]... more
     * precisely the window [i, n-1], which is exactly (idx+k, n-1] for idx=i-k-1 —
     * no second pass, no binary search needed.
     * Gotchas:
     * - The result written at this step belongs to i-k-1, not i — easy to mix up.
     * - Indices near the end where idx+k+1 > n-1 are never explicitly assigned;
     *   correctness relies on Java's default 0-initialization of int[] result.
     * - count is sized to the max value bound (10^5 + 1), not to n — bounded value
     *   range is what makes the array trick viable over a HashMap.
     * Template:
     *   count = new int[MAX_VAL+1]; result = new int[n];
     *   for i from n-1 down to 0:
     *       count[nums[i]]++
     *       if i >= k+1: result[i-k-1] = count[nums[i-k-1]]
     *   return result
     */
    public static void main(String[] args) {
        DelayedCountOfEqualElements D = new DelayedCountOfEqualElements();
        System.out.println("Test 1: " + java.util.Arrays.toString(D.delayedCount(new int[]{1,2,1,1}, 1)) + " (Expected: [2, 0, 0, 0])");
        System.out.println("Test 2: " + java.util.Arrays.toString(D.delayedCount(new int[]{3,1,3,1}, 0)) + " (Expected: [1, 1, 0, 0])");
        System.out.println("Test 3: " + java.util.Arrays.toString(D.delayedCount(new int[]{5}, 0)) + " (Expected: [0])");
        System.out.println("Test 4: " + java.util.Arrays.toString(D.delayedCount(new int[]{1,1,1}, 2)) + " (Expected: [0, 0, 0])");
        System.out.println("Test 5: " + java.util.Arrays.toString(D.delayedCount(new int[]{2,2,2,2,2}, 1)) + " (Expected: [3, 2, 1, 0, 0])");
    }
}
