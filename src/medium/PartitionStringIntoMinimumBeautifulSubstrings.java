package medium;

import java.util.Arrays;
import java.util.Set;

// https://leetcode.com/problems/partition-string-into-minimum-beautiful-substrings/
public class PartitionStringIntoMinimumBeautifulSubstrings {
    private static final Set<String> POWERS_OF_FIVE = Set.of(
            "1", "101", "11001", "1111101", "1001110001", "110000110101", "11110100001001");
    public int minimumBeautifulSubstrings(String s) {
        if (POWERS_OF_FIVE.contains(s))
            return 1;
        int size = s.length();
        int[] dp = new int[size+1];
        int INF = Integer.MAX_VALUE / 2;
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int i = 0; i < size; i++) {
            for (int j = i; j >= 0; j--) {
                if (dp[j] != -1 && POWERS_OF_FIVE.contains(s.substring(j, i+1))) {
                    dp[i+1] = Math.min(dp[i+1], dp[j] + 1);
                }
            }
        }
        return dp[size] >= INF ? -1 : dp[size];
    }

    /*
     * Revision Note — Partition String Into Minimum Beautiful Substrings (Medium)
     *
     * Pattern: minimum-partition DP, the Word Break (139) shape with a min. dp[i] = the fewest
     * beautiful pieces that cover s[0..i), or INF if none works. dp[i + 1] = min over every j where
     * dp[j] is reachable and s[j..i] is beautiful.
     *
     * Key insight: s is at most 15 bits, so only 5^0..5^6 can appear (5^7 = 78,125 needs 17 bits).
     * Seven binary strings in a Set make "is this piece beautiful?" a lookup. Comparing STRINGS,
     * not numbers, also enforces the no-leading-zeros rule: "001" isn't in the set, although
     * its value, 1, is a power of 5.
     *
     * Gotchas:
     *  - Divisible by 5 isn't a power of 5: "1111" is 15, so it is four "1"s (Test 4).
     *  - Leading zeros: a numeric check accepts "001" as 1 and splits "1001" into "1" + "001" for 2
     *    instead of -1 (Test 6). The string set rejects it.
     *  - Any string ending in '0' is -1. Every power of 5 is odd, so every piece ends in '1'
     *    (Test 3).
     *  - Take the min; don't overwrite. The first version wrote dp[i + 1] = 1 + dp[j], so the last
     *    j (the longest valid final piece) won. That happens to be optimal for powers of 5:
     *    exhaustively right on all 65,534 strings up to length 15. But it isn't a valid DP: with
     *    the dictionary {w, x, y, z, wxy, yz}, "wxyz" gives 3 (w + x + yz) instead of 2 (wxy + z).
     *  - INF needs headroom. dp[j] + 1 on an unreachable j is INF + 1. With INF = MAX_VALUE / 2
     *    that is harmless, and min keeps INF. With INF = Integer.MAX_VALUE it wraps to MIN_VALUE,
     *    min picks it, and "01" returns -2147483648 (wrong on 32,752 of the 65,534 inputs). The
     *    guard should be dp[j] < INF. The leftover dp[j] != -1 from the -1 version is always true,
     *    so only the headroom keeps the answer right.
     *  - The early return for a whole-string power is redundant: at j = 0 the loop finds it
     *    anyway, with dp[0] = 0 (Test 5).
     *
     * Complexity: O(n^2) (j, i) pairs, each building and hashing a substring of up to n chars, so
     * O(n^3), about 3.4k character steps at n = 15. O(n) space. Capping j at i - 13 (the longest
     * power is 14 bits) makes it O(n · 14²).
     *
     * Template:
     *   dp[0] = 0, dp[1..n] = INF
     *   for i in 1..n: for j in 0..i-1:
     *       if dp[j] < INF and s[j..i) in DICT: dp[i] = min(dp[i], dp[j] + 1)
     *   return dp[n] >= INF ? -1 : dp[n]
     *
     * Alternative: a forward DP with no substrings. From each reachable i with s[i] == '1', extend
     * j to the right, keeping v = 2v + bit, and relax dp[j + 1] whenever 15625 % v == 0 (the only
     * divisors of 5^6 are the powers of 5). Or memoised recursion over start index, as the
     * Backtracking tag suggests.
     */
    public static void main(String[] args) {
        PartitionStringIntoMinimumBeautifulSubstrings P = new PartitionStringIntoMinimumBeautifulSubstrings();

        // Test 1: LeetCode example 1 — "101" (5) + "1" (1)
        System.out.println("Test 1: " + P.minimumBeautifulSubstrings("1011") + " (Expected: 2)");

        // Test 2: LeetCode example 2 — 7 and 3 aren't powers of 5, so three "1"s
        System.out.println("Test 2: " + P.minimumBeautifulSubstrings("111") + " (Expected: 3)");

        // Test 3: LeetCode example 3 — a lone '0' can never be a piece
        System.out.println("Test 3: " + P.minimumBeautifulSubstrings("0") + " (Expected: -1)");

        // Test 4: "1111" is 15, divisible by 5 but not a power of 5, so it is four "1"s
        System.out.println("Test 4: " + P.minimumBeautifulSubstrings("1111") + " (Expected: 4)");

        // Test 5: the whole string is 25 = 5^2, a single piece
        System.out.println("Test 5: " + P.minimumBeautifulSubstrings("11001") + " (Expected: 1)");

        // Test 6: 9 isn't a power, and "1" + "001" is illegal (leading zeros). A numeric check
        // accepts "001" as 1 and returns 2
        System.out.println("Test 6: " + P.minimumBeautifulSubstrings("1001") + " (Expected: -1)");

        // Test 7: length 15 — "1" + "11110100001001" (5^6 = 15625, the longest power that fits)
        System.out.println("Test 7: " + P.minimumBeautifulSubstrings("111110100001001") + " (Expected: 2)");

        // Test 8: two ways to end — "1" + "1111101" (125) is 2; "101" last leaves "11111", which
        // needs five "1"s, for 6
        System.out.println("Test 8: " + P.minimumBeautifulSubstrings("11111101") + " (Expected: 2)");
    }
}
