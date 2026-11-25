package medium;

// https://leetcode.com/problems/count-number-of-texts/
public class CountNumberOfTexts {
    int MOD = 1000_000_007;
    public int countTexts(String pressedKeys) {
        int size = pressedKeys.length();
        char[] keysPressed = pressedKeys.toCharArray();
        int[] dp = new int[size+1];
        dp[0] = 1;
        for (int i = 0; i < size; i++) {
            dp[i+1] = dp[i];
            if (i >= 1 && keysPressed[i-1] == keysPressed[i]) {
                dp[i + 1] = (dp[i + 1] + dp[i - 1]) % MOD;
                if (i >= 2 && keysPressed[i - 2] == keysPressed[i]) {
                    dp[i + 1] = (dp[i + 1] + dp[i - 2]) % MOD;
                    if (i >= 3 && keysPressed[i - 3] == keysPressed[i] && (keysPressed[i] == '7' || keysPressed[i] == '9')) {
                        dp[i + 1] = (dp[i + 1] + dp[i - 3]) % MOD;
                    }
                }
            }
        }
        return dp[size];
    }

    /*
     * Revision Note — Count Number of Texts (Medium)
     *
     * Pattern: linear counting DP, the same shape as Decode Ways (91). dp[i] is the number of
     * messages the first i presses could spell.
     *
     * Key insight: the last letter of the message used the last 1, 2 or 3 presses (4 on keys 7 and
     * 9), and all of them must be the same key. So dp[i + 1] is the sum of dp[i + 1 - k] over every
     * k that fits. Runs of different keys multiply together, but the DP never has to split the
     * string into runs: a different key simply stops the look-back.
     *
     * Gotchas:
     *  - Looking back k presses needs every one of them to match, not just the two ends. The nested
     *    ifs give that for free: the 3-back check only runs if the 2-back one passed.
     *  - Keys 7 (pqrs) and 9 (wxyz) have 4 letters; the rest have 3. "2222" is 7, not 8 (Test 7),
     *    and even a 7 caps at four presses: "77777" is 15, not 16 (Test 8).
     *  - Mod after every single addition. Two values below MOD add up to at most about 2.0e9, which
     *    still fits in an int (max 2.147e9). Adding three or four before the mod would overflow.
     *  - MOD is the same for every instance, so `static final int MOD = 1_000_000_007` is the idiom.
     *
     * Complexity: O(n) time (at most 4 look-backs per press), O(n) space for dp. Only the last four
     * dp values are ever read, so four rolling variables bring it to O(1).
     *
     * Template:
     *   dp[0] = 1
     *   for i in 0..n-1:
     *       dp[i+1] = dp[i]
     *       for k in 2..letters(key[i]):
     *           if i - k + 1 < 0 or key[i-k+1] != key[i]: break
     *           dp[i+1] = (dp[i+1] + dp[i+1-k]) % MOD
     *   return dp[n]
     *
     * Alternative: split into runs of one key. A run of m presses on a 3-letter key has t3[m] texts,
     * where t3[m] = t3[m-1] + t3[m-2] + t3[m-3] (tribonacci); a 4-letter key uses t4 with four
     * terms. Precompute both up to n once, then multiply t[run length] over the runs. Same O(n).
     */
    public static void main(String[] args) {
        CountNumberOfTexts C = new CountNumberOfTexts();

        // Test 1: LeetCode example 1 — "222" has 4 texts and "33" has 2, so 4 * 2 = 8
        System.out.println("Test 1: " + C.countTexts("22233") + " (Expected: 8)");

        // Test 2: LeetCode example 2 — the true count, 2082876103, passes 2^31 - 1, so it only works
        // with the mod applied at every step
        System.out.println("Test 2: " + C.countTexts("222222222222222222222222222222222222") + " (Expected: 82876089)");

        // Test 3: a single key press — one letter, dp[1] = dp[0]
        System.out.println("Test 3: " + C.countTexts("2") + " (Expected: 1)");

        // Test 4: 7 pressed four times — 7 has 4 letters, so all four presses can be one letter (s)
        System.out.println("Test 4: " + C.countTexts("7777") + " (Expected: 8)");

        // Test 5: the same key in two separate runs — the 3 between them breaks the look-back, so it's
        // 4 * 1 * 4, not the count for six 2s in a row
        System.out.println("Test 5: " + C.countTexts("2223222") + " (Expected: 16)");

        // Test 6: 10^5 presses of one key — needs O(n) and the mod; a recursive top-down version
        // would also go 10^5 calls deep
        System.out.println("Test 6: " + C.countTexts("9".repeat(100000)) + " (Expected: 472399532)");

        // Test 7: 2 pressed four times — 2 has only 3 letters, so four presses can't be one letter;
        // 8 here means the 7/9 check is missing
        System.out.println("Test 7: " + C.countTexts("2222") + " (Expected: 7)");

        // Test 8: 7 pressed five times — even a 4-letter key caps at four presses per letter
        System.out.println("Test 8: " + C.countTexts("77777") + " (Expected: 15)");
    }
}
