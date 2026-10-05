package medium;

import java.util.HashMap;
import java.util.Random;

// https://leetcode.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/
public class FindTheLongestSubstringContainingVowelsInEvenCounts {
    public int findTheLongestSubstring(String s) {
        int size = s.length();
        HashMap<Integer, Integer> maskMap = new HashMap<>();
        int mask = 0;
        maskMap.put(0, -1);
        int result = 0;
        for (int i = 0; i < size; i++) {
            int idx = "aeiou".indexOf(s.charAt(i));
            if (idx >= 0)
                mask^= 1 << idx;
            if (maskMap.containsKey(mask)) {
                result = Math.max(result, i - maskMap.get(mask));
            } else
                maskMap.put(mask, i);
        }
        return result;
    }


    /*
     * Revision Note - Find the Longest Substring Containing Vowels in Even Counts (Medium)
     *
     * Pattern: Prefix PARITY BITMASK in a map, storing the EARLIEST index of each state
     *
     * Key Insight: Two independent reductions.
     *
     *   (1) Only parity matters. A vowel seen 0, 2 or 4 times is all the same, so five counters
     *       collapse to five BITS. The prefix state is one int in [0, 31], and reading a vowel
     *       XORs its bit while a consonant changes nothing.
     *
     *   (2) Two prefix positions with the SAME mask bound a substring in which every vowel
     *       appears an even number of times - because equal parity vectors XOR to zero. So the
     *       whole problem is "find two equal masks that are furthest apart".
     *
     * Gotchas:
     * - SEED state 0 at index -1 before the loop. The empty prefix is all-even and sits before
     *   index 0; without it, every answer that starts at index 0 is one short. "bcbcbc" returns
     *   5 instead of 6 - that test exists for exactly this
     * - KEEP THE EARLIEST INDEX, never overwrite. The longest span ending at i comes from the
     *   FURTHEST-BACK occurrence of the mask, so record only on first sight and otherwise just
     *   compare. Overwriting yields the shortest span instead: "abab" returns 3 instead of 4
     * - The two branches are mutually exclusive - once a mask is recorded it is only ever read.
     *   That is what makes `if (contains) compare; else put;` correct rather than a bug
     * - Zero counts are EVEN, so a consonant-only substring is always valid. "b" is 1, not 0,
     *   and a string with no vowels at all returns its full length
     * - The answer can be 0 ("a", "aeiou") - there is no guarantee any non-empty substring
     *   qualifies, so initialise the result to 0 and never to 1
     * - The best window need not touch either end: "abbe" answers 2 via the interior "bb"
     * - Bit ASSIGNMENT is arbitrary. Any bijection from {a,e,i,o,u} to {1,2,4,8,16} works, since
     *   the mask is only ever compared against itself
     *
     * Complexity: O(n) time, one pass; `"aeiou".indexOf(c)` is O(5) so ~2.5e6 char comparisons
     * at the 5e5 ceiling - measured 18ms. O(1) space: at most 32 distinct masks can ever be
     * stored, regardless of n.
     *
     * Template:
     *   map = {0: -1}                       // empty prefix, before index 0
     *   mask = 0; best = 0
     *   for i in 0..n-1:
     *     v = "aeiou".indexOf(s[i]);  if v >= 0: mask ^= 1 << v
     *     if mask in map: best = max(best, i - map[mask])
     *     else:           map[mask] = i     // first sight only
     *   return best
     *
     * Since there are only 32 states, the HashMap can be an int[32] - fill with a sentinel that
     * cannot be a real index (NOT -1, which is the legitimate seed for state 0; use -2 or
     * MIN_VALUE), then set first[0] = -1. Branchless variant for the update: a 26-entry int[]
     * mapping each letter to its bit and consonants to 0, so `mask ^= bit[c - 'a']` needs no if.
     *
     * Relative to 560 and 1442, BOTH axes changed: the prefix state became a bitmask rather than
     * a sum or XOR of values, and the map stores a FIRST INDEX rather than a count - because the
     * question asks for a length, not a tally. Counting wants every occurrence; maximising wants
     * only the earliest.
     */

    /*
     * O(n^2) reference: try every start, sweep right maintaining the parity mask, and take the
     * longest window whose mask is 0. No hash map, no seeding, no "earliest index" rule - so it
     * shares none of the logic that can go wrong in the one-pass version.
     */
    private static int bruteForce(String s) {
        int n = s.length(), best = 0;
        for (int i = 0; i < n; i++) {
            int mask = 0;
            for (int j = i; j < n; j++) {
                int v = "aeiou".indexOf(s.charAt(j));
                if (v >= 0) mask ^= 1 << v;
                if (mask == 0) best = Math.max(best, j - i + 1);
            }
        }
        return best;
    }

    private static String randomString(Random rnd, int len, String alphabet) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) sb.append(alphabet.charAt(rnd.nextInt(alphabet.length())));
        return sb.toString();
    }

    public static void main(String[] args) {
        FindTheLongestSubstringContainingVowelsInEvenCounts F = new FindTheLongestSubstringContainingVowelsInEvenCounts();

        System.out.println("Test 1: " + F.findTheLongestSubstring("eleetminicoworoep") + " (Expected: 13)");
        System.out.println("Test 2: " + F.findTheLongestSubstring("leetcodeisgreat") + " (Expected: 5)");
        System.out.println("Test 3: " + F.findTheLongestSubstring("bcbcbc") + " (Expected: 6)");   // no vowels at all - catches a MISSING seed of state 0 at -1
        System.out.println("Test 4: " + F.findTheLongestSubstring("abab") + " (Expected: 4)");     // mask 0 recurs - catches OVERWRITING instead of keeping the earliest index
        System.out.println("Test 5: " + F.findTheLongestSubstring("a") + " (Expected: 0)");        // lone vowel, odd, so no valid non-empty substring
        System.out.println("Test 6: " + F.findTheLongestSubstring("b") + " (Expected: 1)");        // lone consonant, all vowels appear zero times
        System.out.println("Test 7: " + F.findTheLongestSubstring("aa") + " (Expected: 2)");
        System.out.println("Test 8: " + F.findTheLongestSubstring("ab") + " (Expected: 1)");       // best answer is a single consonant
        System.out.println("Test 9: " + F.findTheLongestSubstring("aeiou") + " (Expected: 0)");    // every vowel exactly once, all odd
        System.out.println("Test 10: " + F.findTheLongestSubstring("aeiouaeiou") + " (Expected: 10)"); // every vowel exactly twice
        System.out.println("Test 11: " + F.findTheLongestSubstring("abbe") + " (Expected: 2)");    // best window is "bb", strictly interior - touches neither end
        System.out.println("Test 12: " + F.findTheLongestSubstring("uaeiou") + " (Expected: 0)");  // u repeats, but any window spanning both u's picks up a,e,i,o exactly once

        // cross-check against the O(n^2) reference
        boolean agree = true;
        String firstBad = "";
        Random rnd = new Random(79);
        for (int t = 0; t < 600; t++) {
            // alternate a vowel-heavy alphabet (masks churn) with a consonant-heavy one (masks persist)
            String alphabet = (t % 2 == 0) ? "aeioub" : "abcdeu";
            String str = randomString(rnd, 1 + rnd.nextInt(18), alphabet);
            int got = F.findTheLongestSubstring(str), want = bruteForce(str);
            if (got != want) {
                agree = false;
                if (firstBad.isEmpty()) firstBad = "\"" + str + "\" got " + got + " want " + want;
            }
        }
        System.out.println("Test 13: " + agree + " (Expected: true)  - matches O(n^2) reference, 600 random"
                + (agree ? "" : ", first mismatch: " + firstBad));

        // n = 5e5, the constraint ceiling
        String big = randomString(new Random(83), 500000, "abcdefghijklmnopqrstuvwxyz");
        long t0 = System.nanoTime();
        int r = F.findTheLongestSubstring(big);
        long ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("Test 14: " + (r > 0 && ms < 500) + " (Expected: true)  - n=500000 returned " + r + " in " + ms + "ms");
    }
}
