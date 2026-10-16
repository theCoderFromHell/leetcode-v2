package medium;

// https://leetcode.com/problems/replace-the-substring-for-balanced-string/
public class ReplaceTheSubstringForBalancedString {
    public int balancedString(String s) {
        int size = s.length();
        int[] count = new int[4];
        char[] sChars = s.toCharArray();
        for (int i = 0; i < size; i++)
            count["QWER".indexOf(sChars[i])]++;
        int target = size/4;
        if (isValid(count, target))
            return 0;
        int start = 0;
        int minWindow = Integer.MAX_VALUE;
        for (int end = 0; end < size; end++) {
            count["QWER".indexOf(sChars[end])]--;
            while (start <= end && isValid(count, target)) {
                minWindow = Math.min(minWindow, end - start + 1);
                count["QWER".indexOf(sChars[start])]++;
                start++;
            }
        }
        return minWindow;
    }

    private boolean isValid(int[] count, int target) {
        return (count[0] <= target && count[1] <= target && count[2] <= target && count[3] <= target);
    }

    /*
     * Revision Note - Replace the Substring for Balanced String (Medium)
     *
     * Pattern: variable sliding window minimising length (209's grow-then-shrink loop), with the
     * validity test on the counts OUTSIDE the window.
     *
     * Key Insight: the window's contents are free (replaced by anything), and the outside never
     * changes. Excess outside can't be removed; a shortage outside can always be filled, since the
     * window length is exactly the total shortage. So a window works iff every letter's OUTSIDE
     * count is <= n/4. Track it by starting from the whole-string counts: count-- as a char enters
     * the window, count++ as it leaves. Growing the window only lowers outside counts, so the
     * condition is monotonic and shrinking from the left is safe.
     *
     * Gotchas:
     * - while, not if: with if, each valid step releases one char and the window never gets
     *   shorter, so the first valid length is all you ever see ("WQQQ" gave 3, answer 2).
     * - Already balanced -> 0. The loop only measures windows of length >= 1, so check the
     *   whole-string counts right after the counting pass and return 0 there. (An earlier version
     *   let the window go empty with start <= end + 1 to record 0: correct, since an empty window
     *   is only valid on a balanced string, but it then released a char that never entered.)
     * - Guard the shrink with start <= end, so the window never goes empty mid-loop.
     * - The whole string is always a valid window, so minWindow never stays at MAX_VALUE.
     *
     * Complexity: O(n) time - start and end each advance at most n times (amortised inner while).
     * O(n) space for sChars; O(1) with s.charAt.
     *
     * Template:
     *   count = counts of whole string; if all <= n/4: return 0
     *   for end in 0..n-1:
     *       count[s[end]]--
     *       while start <= end and all count <= n/4:
     *           best = min(best, end - start + 1); count[s[start++]]++
     *   return best
     */

    public static void main(String[] args) {
        ReplaceTheSubstringForBalancedString R = new ReplaceTheSubstringForBalancedString();

        System.out.println("Test 1: " + R.balancedString("QWER") + " (Expected: 0)");          // already balanced: nothing to replace
        System.out.println("Test 2: " + R.balancedString("QQWE") + " (Expected: 1)");
        System.out.println("Test 3: " + R.balancedString("QQQW") + " (Expected: 2)");
        System.out.println("Test 4: " + R.balancedString("WQQQ") + " (Expected: 2)");          // needs the while: "QQ" at [1..2] is only seen by shrinking twice
        System.out.println("Test 5: " + R.balancedString("QQQQ") + " (Expected: 3)");          // one letter only: replace all but n/4 of it
        System.out.println("Test 6: " + R.balancedString("WQWRQQQW") + " (Expected: 3)");      // two letters in excess (Q, W)
        System.out.println("Test 7: " + R.balancedString("QWERQWER") + " (Expected: 0)");      // balanced, n > 4
        System.out.println("Test 8: " + R.balancedString("QQQWEEER") + " (Expected: 3)");      // excess Q and E: the window must span one of each, "QWE"
    }
}
