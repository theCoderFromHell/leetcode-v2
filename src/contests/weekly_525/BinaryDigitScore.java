package contests.weekly_525;

// https://leetcode.com/problems/binary-digit-score/
public class BinaryDigitScore {
    public int calculateScore(int a, int b) {
        int s1 = 0;
        while (a > 0) {
            if ((a & 1) == 1)
                s1++;
            a >>= 1;
        }
        int s2 = 0;
        while (b > 0) {
            if ((b & 1) == 1)
                s2++;
            b >>= 1;
        }
        return (s1 >= s2) ? s1 * s2 : s2/s1;
    }

    /*
     * Revision Note — Binary Digit Score (Easy, Weekly 525 Q1)
     *
     * Pattern: Popcount — count set bits by testing the low bit and shifting right.
     *
     * Key insight: just compute s1 and s2 and apply the rule; Integer.bitCount(x) does the
     * counting in one call.
     *
     * Gotchas:
     *  - s2 / s1 is safe only because a >= 1 guarantees s1 >= 1 — a = 0 would divide by zero.
     *  - Integer division floors: s1 = 2, s2 = 5 scores 2, not 2.5.
     *  - while (x > 0) with >>= is fine for positive inputs; a negative x needs >>> (or bitCount),
     *    or the sign bit keeps it negative forever.
     *
     * Complexity: O(log max(a, b)) = O(1) time (<= 7 bits for values <= 100), O(1) space.
     *
     * Template:
     *   s1 = bitCount(a); s2 = bitCount(b)
     *   return s1 >= s2 ? s1 * s2 : s2 / s1
     */
    public static void main(String[] args) {
        BinaryDigitScore B = new BinaryDigitScore();

        // Test 1: LeetCode example 1 — s1 > s2, product
        System.out.println("Test 1: " + B.calculateScore(14, 6) + " (Expected: 6)");

        // Test 2: LeetCode example 2 — s1 < s2, division
        System.out.println("Test 2: " + B.calculateScore(5, 15) + " (Expected: 2)");

        // Test 3: LeetCode example 3 — s1 == s2 takes the product branch
        System.out.println("Test 3: " + B.calculateScore(10, 12) + " (Expected: 4)");

        // Test 4: division with a remainder — 5 / 2 floors to 2
        System.out.println("Test 4: " + B.calculateScore(3, 31) + " (Expected: 2)");

        // Test 5: a is a power of two — s1 = 1, the smallest divisor
        System.out.println("Test 5: " + B.calculateScore(64, 127) + " (Expected: 7)");

        // Test 6: smallest inputs
        System.out.println("Test 6: " + B.calculateScore(1, 1) + " (Expected: 1)");

        // Test 7: most set bits under 100 (63 = 111111)
        System.out.println("Test 7: " + B.calculateScore(63, 1) + " (Expected: 6)");
    }
}
