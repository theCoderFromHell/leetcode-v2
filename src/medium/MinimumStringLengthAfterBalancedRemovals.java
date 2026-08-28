package medium;

public class MinimumStringLengthAfterBalancedRemovals {
    public int minLengthAfterRemovals(String s) {
        int size = s.length();
        int count = 0;
        for (int i = 0; i < size; i++)
            count += (s.charAt(i) == 'a' ? 1 : -1);
        return Math.abs(count);
    }

    /*
     * Revision Note — Minimum String Length After Balanced Removals (Medium)
     *
     * Pattern: Character count balance (greedy)
     *
     * Key Insight: Any balanced substring (equal 'a' count and non-'a' count) can be removed;
     * as long as both types coexist, an adjacent mismatched pair always exists to eliminate.
     * The minimum achievable length is therefore simply |count_a - count_nonA|.
     *
     * Gotchas:
     * - Prefix sum / "longest balanced subarray" approach is wrong — it removes only one
     *   contiguous region and misses cases where multiple removals across the string are optimal
     * - The correct solution is O(n) one-pass with O(1) space — far simpler than prefix sum
     *
     * Template:
     *   int count = 0;
     *   for (char c : s.toCharArray()) count += (c == 'a' ? 1 : -1);
     *   return Math.abs(count);
     */
    public static void main(String[] args) {
        MinimumStringLengthAfterBalancedRemovals M = new MinimumStringLengthAfterBalancedRemovals();
        System.out.println("Test 1: " + M.minLengthAfterRemovals("aab")    + " (Expected: 1)");   // remove "ab" → "a"
        System.out.println("Test 2: " + M.minLengthAfterRemovals("aaabb")  + " (Expected: 1)");   // remove "aabb" → "a"
        System.out.println("Test 3: " + M.minLengthAfterRemovals("aabb")   + " (Expected: 0)");   // remove all
        System.out.println("Test 4: " + M.minLengthAfterRemovals("ab")     + " (Expected: 0)");   // remove "ab"
        System.out.println("Test 5: " + M.minLengthAfterRemovals("a")      + " (Expected: 1)");   // nothing to remove
        System.out.println("Test 6: " + M.minLengthAfterRemovals("aaabbb") + " (Expected: 0)");   // remove all
    }
}
