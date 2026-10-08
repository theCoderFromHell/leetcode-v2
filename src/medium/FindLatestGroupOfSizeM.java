package medium;

// https://leetcode.com/problems/find-latest-group-of-size-m/
public class FindLatestGroupOfSizeM {
    public int findLatestStep(int[] arr, int m) {
        int size = arr.length;
        int[] block = new int[size+2];
        int countM = 0;
        int result = -1;
        for (int i = 0; i < size; i++) {
            int p = arr[i];
            int left = block[p-1];
            if (left == m)
                countM--;
            int right = block[p+1];
            if (right == m)
                countM--;
            int newBlock = left + 1 + right;
            if (newBlock == m)
                countM++;
            block[p - left] = newBlock;
            block[p + right] = newBlock;
            if (countM > 0)
                result = i+1;
        }
        return result;
    }

    /*
     * Revision Note - Find Latest Group of Size M (Medium)
     *
     * Pattern: Run lengths stored AT BOTH ENDPOINTS + a counter of runs having the target length
     *
     * Key Insight: Two separate tricks, and the problem needs both.
     *
     *   (1) block[i] = length of the run containing i, but kept correct ONLY at the run's two
     *       endpoints. An insertion at p only ever reads block[p-1] and block[p+1], which are
     *       endpoints by construction, so interior cells are allowed to go stale and are never
     *       cleaned up. That is what makes each step O(1) instead of O(run length).
     *
     *   (2) countM = how many runs currently have length exactly m. Rescanning for a size-m
     *       group after every step is O(n^2) and dies at n = 1e5. Instead, every insertion
     *       removes 0-2 runs and creates exactly 1, so the counter is maintainable in O(1):
     *       decrement for each consumed run whose length was m, increment if the new length is m.
     *
     * With L = block[p-1] and R = block[p+1] (both naturally 0 when that side is a zero), the
     * four neighbour cases collapse into ONE code path with no branching:
     *       newLen   = L + 1 + R
     *       leftEnd  = p - L        // the left run spans [p-L, p-1]
     *       rightEnd = p + R        // the right run spans [p+1, p+R]
     *
     * Gotchas:
     * - WRITE AT BOTH ENDS. A run is looked up from whichever side the next insertion lands on,
     *   so block[p-L] and block[p+R] must both get newLen. Writing only the side you grew from
     *   passes [3,5,1,2,4] but fails [3,2,1,5,4] m=1, where step 3 reads a LEFT end that step 2
     *   created. Both are in the tests for exactly this reason
     * - READ L AND R BEFORE WRITING ANYTHING. The decrements test the old lengths; the writes
     *   clobber those cells (p-L == p-1 whenever L == 1). Capture into locals first
     * - countM CAN LAPSE AND RETURN. In [3,2,1,5,4] m=1 it goes 1,0,0,1,0 - so the answer is
     *   step 4, with a two-step gap in the middle. Never break early when it hits 0; just
     *   overwrite result on every step where it is positive and let the last one win
     * - Array must be size n+2: p-1 reaches index 0 and p+1 reaches n+1 at the extremes. Those
     *   two guard cells are never WRITTEN, since p-L >= 1 and p+R <= n always
     * - Steps are 1-indexed in the problem, arr is 0-indexed - result is i+1, not i
     * - Initialise result to -1, not 0; a size-m group may never appear ([3,1,5,4,2] m=2)
     * - L == 0 && R == 0 makes both writes hit cell p with the same value. Harmless, so the
     *   "brand new run of length 1" case needs no special handling
     *
     * Complexity: O(n) time, one pass with O(1) work per step. O(n) space for block[].
     *
     * Template:
     *   block = int[n+2]; countM = 0; result = -1
     *   for step = 1..n:
     *     p = arr[step-1]
     *     L = block[p-1]; R = block[p+1]          // read FIRST
     *     if L == m: countM--
     *     if R == m: countM--
     *     newLen = L + 1 + R
     *     if newLen == m: countM++
     *     block[p-L] = newLen; block[p+R] = newLen // BOTH ends
     *     if countM > 0: result = step
     *   return result
     *
     * The generalisable half is (2): when a question asks "does any X with property P exist
     * right now" inside a loop, maintain a COUNT of X-with-P rather than rescanning. Works
     * whenever each update touches O(1) objects.
     */

    public static void main(String[] args) {
        FindLatestGroupOfSizeM F = new FindLatestGroupOfSizeM();

        System.out.println("Test 1: " + F.findLatestStep(new int[]{3, 5, 1, 2, 4}, 1) + " (Expected: 4)");
        System.out.println("Test 2: " + F.findLatestStep(new int[]{3, 1, 5, 4, 2}, 2) + " (Expected: -1)");
        System.out.println("Test 3: " + F.findLatestStep(new int[]{3, 2, 1, 5, 4}, 1) + " (Expected: 4)");  // countM lapses then returns; reads a LEFT end
        System.out.println("Test 4: " + F.findLatestStep(new int[]{1}, 1) + " (Expected: 1)");              // n = 1
        System.out.println("Test 5: " + F.findLatestStep(new int[]{1, 2, 3}, 3) + " (Expected: 3)");        // m == n, only the final step qualifies
        System.out.println("Test 6: " + F.findLatestStep(new int[]{2, 1}, 1) + " (Expected: 1)");
        System.out.println("Test 7: " + F.findLatestStep(new int[]{1, 2, 3, 4, 5}, 1) + " (Expected: 1)");  // strictly growing run, qualifies only at step 1
        System.out.println("Test 8: " + F.findLatestStep(new int[]{5, 4, 3, 2, 1}, 1) + " (Expected: 1)");  // mirror of 7, grows leftward
        System.out.println("Test 9: " + F.findLatestStep(new int[]{1, 3, 5, 2, 4}, 5) + " (Expected: 5)");  // whole string, last step
        System.out.println("Test 10: " + F.findLatestStep(new int[]{1, 3, 5, 2, 4}, 3) + " (Expected: 4)"); // merge produces exactly m mid-way
    }
}
