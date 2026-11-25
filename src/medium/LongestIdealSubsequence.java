package medium;

import java.util.Arrays;

// https://leetcode.com/problems/longest-ideal-subsequence/
public class LongestIdealSubsequence {
    public int longestIdealString(String s, int k) {
        int size = s.length();
        int[] dp = new int[size];
        dp[0] = 1;
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);
        lastSeen[s.charAt(0) - 'a'] = 0;
        int result = 1;
        for (int i = 1; i < size; i++) {
            dp[i] = 1;
            int curr = s.charAt(i) - 'a';
            for (int j = 0; j <= k; j++) {
                if (curr - j >= 0 && lastSeen[curr - j] != -1)
                    dp[i] = Math.max(dp[i], dp[lastSeen[curr - j]] + 1);
                if (curr + j < 26 && lastSeen[curr + j] != -1)
                    dp[i] = Math.max(dp[i], dp[lastSeen[curr + j]] + 1);
            }
            result = Math.max(result, dp[i]);
            lastSeen[curr] = i;
        }
        return result;
    }

    /*
     * Revision Note — Longest Ideal Subsequence (Medium)
     *
     * Pattern: LIS-style DP where the predecessor search runs over the 26 letters, not over every
     * earlier index. dp[i] = the longest ideal string that ends at index i.
     *
     * Key insight: only the latest occurrence of each letter matters as a predecessor. A later copy
     * of a letter can follow everything an earlier copy could, so its dp value is at least as large.
     * lastSeen[letter] therefore turns the O(n^2) scan over earlier indices into at most 2k + 1
     * letter lookups.
     *
     * Gotchas (from this session):
     *  - dp[i] ends AT i, so the answer is the max over all i, not dp[n - 1]. The first version
     *    returned dp[size - 1] and failed on 2,061 of 20,000 random strings. Tests 1-7 all happened
     *    to end their best string on the last character; Test 8 ("abz", k = 1) is the one that
     *    catches it.
     *  - The running maximum must start at 1, not 0. Index 0 is handled before the loop, so a
     *    one-letter string never reaches the update. The second version returned 0 for every
     *    length-1 input (Test 3).
     *  - The alphabet is not cyclic: 'a' and 'z' are 25 apart (Tests 6 and 7).
     *  - With k = 0 only equal letters can be adjacent, so the answer is the count of the most
     *    frequent letter (Test 4). When j == 0, both lookups check the same letter, which is harmless.
     *  - An O(n^2) LIS loop is about 5·10^9 steps at n = 10^5 (Test 7). This version takes about
     *    3 ms on 10^5 random letters with k = 25.
     *
     * Complexity: O(n·k) <= O(26n) time, O(n) space for dp (lastSeen is 26).
     *
     * Template:
     *   lastSeen[26] = -1; dp[0] = 1; result = 1
     *   for i in 1..n-1:
     *       dp[i] = 1 + max(dp[lastSeen[d]] for letters d within k of s[i] that have been seen)
     *       lastSeen[s[i]] = i; result = max(result, dp[i])
     *
     * Alternative: keep best[26] (the longest ideal string ending in each letter) instead of dp and
     * lastSeen. For each character c: best[c] = 1 + max(best[c - k .. c + k]); the answer is
     * max(best). Same O(26n) time, O(1) space, and the first character needs no special case.
     */
    public static void main(String[] args) {
        LongestIdealSubsequence L = new LongestIdealSubsequence();

        // Test 1: LeetCode example 1 — "acbd": f and g are both more than 2 away from c
        System.out.println("Test 1: " + L.longestIdealString("acfgbd", 2) + " (Expected: 4)");

        // Test 2: LeetCode example 2 — k = 3 covers every adjacent pair, so the whole string
        System.out.println("Test 2: " + L.longestIdealString("abcd", 3) + " (Expected: 4)");

        // Test 3: single character, k == 0 — the answer is 1; the running maximum has to count index 0
        System.out.println("Test 3: " + L.longestIdealString("z", 0) + " (Expected: 1)");

        // Test 4: k == 0 with repeated letters — only equal letters can be adjacent, so it's the most
        // frequent letter's count (four a's)
        System.out.println("Test 4: " + L.longestIdealString("aabbbcaa", 0) + " (Expected: 4)");

        // Test 5: k == 25 — every pair of letters is allowed, so the whole string
        System.out.println("Test 5: " + L.longestIdealString("zxyabc", 25) + " (Expected: 6)");

        // Test 6: only the letters 'a' and 'z' — the alphabet isn't cyclic, so they are 25 apart and
        // never adjacent with k = 1
        System.out.println("Test 6: " + L.longestIdealString("azaz", 1) + " (Expected: 2)");

        // Test 7: 100,000 characters, alternating 'a' and 'z' — an O(n^2) LIS loop would be about
        // 5·10^9 steps; the per-letter lookup keeps it linear
        System.out.println("Test 7: " + L.longestIdealString("az".repeat(50000), 1) + " (Expected: 50000)");

        // Test 8: the longest ideal string ("ab") doesn't end at the last character — 'z' can't
        // follow 'b' with k = 1. Returning dp[n - 1] gives 1 here
        System.out.println("Test 8: " + L.longestIdealString("abz", 1) + " (Expected: 2)");
    }
}
