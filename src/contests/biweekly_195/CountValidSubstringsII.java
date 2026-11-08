package contests.biweekly_195;

// https://leetcode.com/problems/count-valid-substrings-ii/
public class CountValidSubstringsII {
    public long countValidSubstrings(String s) {
        int size = s.length();
        int prevA = -1, prevAB = -1, prevABC = -1;
        long result = 0;
        for (int end = 0; end < size; end++) {
            if (s.charAt(end) == 'a')
                prevA = end;
            else if (s.charAt(end) == 'b')
                prevAB = prevA;
            else if (s.charAt(end) == 'c')
                prevABC = prevAB;
            result += (end - prevABC);
        }
        return result;
    }

    /*
     * Revision Note — Count Valid Substrings II (Medium, Biweekly Contest 195)
     *
     * Pattern: count by right end, with a chain of "last occurrence" indices. This is the complement
     * of 1358 (Number of Substrings Containing All Three Characters), with order now mattering.
     *
     * Key insight: fix end. s[start..end] contains "abc" as a subsequence iff start ≤ prevABC, the
     * largest index of an 'a' that begins some a…b…c subsequence finishing by end. So the valid starts
     * are prevABC+1 .. end, i.e. end - prevABC substrings. The chain keeps each index as late as possible:
     *   'a' → prevA   = end        (latest a)
     *   'b' → prevAB  = prevA      (latest a that has a b after it)
     *   'c' → prevABC = prevAB     (latest a that has b, then c, after it)
     *
     * Gotchas:
     *  - Plain assignment is already the max: prevA only grows, so the latest 'b' always sees the
     *    latest 'a'. A 'b' before any 'a' sets prevAB = -1, correctly (Test 5).
     *  - The -1 sentinels make end - prevABC = end + 1 (every start valid) with no special case.
     *  - Order matters: "abbc" has no "abc" substring but does have the subsequence, so its whole
     *    string is invalid here (Test 6: 9, while Count Valid Substrings I gives 10).
     *  - long result: n = 1e5 with no subsequence gives 5,000,050,000 (Test 9).
     *
     * Complexity: O(n) time, O(1) space.
     *
     * Template:
     *   pA = pAB = pABC = -1
     *   for end: if a: pA = end; elif b: pAB = pA; elif c: pABC = pAB
     *            ans += end - pABC
     *
     * Generalises to any pattern p of length k: last[j] = latest start of a p[0..j] subsequence. On
     * character p[j], set last[j] = last[j-1] (last[0] = end). Add end - last[k-1].
     */
    public static void main(String[] args) {
        CountValidSubstringsII C = new CountValidSubstringsII();

        // Test 1: LeetCode example 1 — "abac" itself has a…b…c, so 10 - 1 = 9
        System.out.println("Test 1: " + C.countValidSubstrings("abac") + " (Expected: 9)");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + C.countValidSubstrings("abc") + " (Expected: 5)");

        // Test 3: LeetCode example 3
        System.out.println("Test 3: " + C.countValidSubstrings("ac") + " (Expected: 3)");

        // Test 4: n = 1
        System.out.println("Test 4: " + C.countValidSubstrings("a") + " (Expected: 1)");

        // Test 5: 'b' before any 'a' — prevAB stays -1, so the later 'c' completes nothing
        System.out.println("Test 5: " + C.countValidSubstrings("bac") + " (Expected: 6)");

        // Test 6: a…b…c spread out — only the whole string is invalid
        System.out.println("Test 6: " + C.countValidSubstrings("abbc") + " (Expected: 9)");

        // Test 7: repeated letters — each 'c' must take the LATEST 'a' that has a 'b' after it
        System.out.println("Test 7: " + C.countValidSubstrings("aabbcc") + " (Expected: 17)");

        // Test 8: two full "abc" blocks
        System.out.println("Test 8: " + C.countValidSubstrings("abcabc") + " (Expected: 14)");

        // Test 9: n = 1e5, all 'a' — the answer 5,000,050,000 overflows int
        System.out.println("Test 9: " + C.countValidSubstrings("a".repeat(100000)) + " (Expected: 5000050000)");
    }
}
