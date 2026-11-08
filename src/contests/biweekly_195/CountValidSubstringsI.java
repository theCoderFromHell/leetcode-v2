package contests.biweekly_195;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/count-valid-substrings-i/
public class CountValidSubstringsI {
    public long countValidSubstrings(String s) {
        int size = s.length();
        List<Integer> matchIndices = new ArrayList<>();
        for (int i = 0; i <= size - 3; i++) {
            if ("abc".equals(s.substring(i, i+3)))
                matchIndices.add(i);
        }
        matchIndices.add(size-2);
        long result = 0L;
        int prev = 0;
        for (int index : matchIndices) {
            long length = index + 1 - prev + 1;
            result += (length * (length + 1))/2;
            prev = index + 1;
        }
        return result - matchIndices.size() + 1;
    }

    /*
     * Revision Note — Count Valid Substrings I (Medium, Biweekly Contest 195)
     *
     * Pattern: cut the string at every "abc" occurrence and count substrings segment by segment.
     *
     * Key insight: a substring contains the occurrence at i iff start ≤ i AND end ≥ i + 2. So a valid
     * substring can't hold both s[i] and s[i+2]: it lies entirely in [.., i+1] or in [i+1, ..].
     * Cutting at every match gives segments [prev, i+1] that share exactly one character with the next
     * segment (the 'b' at i+1). Sum len·(len+1)/2 over the segments, then subtract 1 per real match,
     * because that shared single-character substring was counted in both segments.
     *
     * Gotchas:
     *  - The sentinel size-2 closes the last segment at size-1. It adds a segment but no overlap,
     *    hence "- matchIndices.size() + 1". For n = 1 the sentinel is -1 and it still works (Test 4).
     *  - Back-to-back matches (Test 5) and a match at the very end (Test 6) are where the overlap
     *    bookkeeping goes wrong if it's off by one.
     *  - len·(len+1)/2 needs long: n = 1e5 with no match gives 5,000,050,000 (Test 8).
     *
     * Complexity: O(n) time, O(number of matches) space. s.substring(i, i+3) allocates a String per
     * index; three charAt comparisons avoid that.
     *
     * Template:
     *   cuts = [i for each "abc" at i] + [n-2]; prev = 0
     *   for i in cuts: len = i + 2 - prev; total += len·(len+1)/2; prev = i + 1
     *   return total - (cuts.size() - 1)
     *
     * Alternative, one pass, O(1) space: count by right end. Let last = start of the latest "abc"
     * that ends at or before end (-1 if none). Substrings ending at end are valid iff they start
     * after last, so add end - last. This is exactly the Count Valid Substrings II loop, with
     * "contains as a substring" in place of "contains as a subsequence".
     */
    public static void main(String[] args) {
        CountValidSubstringsI C = new CountValidSubstringsI();

        // Test 1: LeetCode example 1 — no "abc", so all 4·5/2 = 10 substrings are valid
        System.out.println("Test 1: " + C.countValidSubstrings("abac") + " (Expected: 10)");

        // Test 2: LeetCode example 2 — only the whole string is invalid
        System.out.println("Test 2: " + C.countValidSubstrings("abc") + " (Expected: 5)");

        // Test 3: LeetCode example 3
        System.out.println("Test 3: " + C.countValidSubstrings("ac") + " (Expected: 3)");

        // Test 4: n = 1 — the loop never runs and the sentinel is size-2 = -1
        System.out.println("Test 4: " + C.countValidSubstrings("a") + " (Expected: 1)");

        // Test 5: back-to-back matches — segments "ab", "bcab", "bc" overlap on the two inner 'b's
        System.out.println("Test 5: " + C.countValidSubstrings("abcabc") + " (Expected: 14)");

        // Test 6: match at the very end (i = n-3) — the sentinel segment is just "bc"
        System.out.println("Test 6: " + C.countValidSubstrings("ccabc") + " (Expected: 12)");

        // Test 7: a, b, c in order but never adjacent — no substring match, all 10 valid
        // (Count Valid Substrings II, which uses subsequences, gives 9 here)
        System.out.println("Test 7: " + C.countValidSubstrings("abbc") + " (Expected: 10)");

        // Test 8: n = 1e5, all 'a' — the answer 5,000,050,000 overflows int
        System.out.println("Test 8: " + C.countValidSubstrings("a".repeat(100000)) + " (Expected: 5000050000)");
    }
}
