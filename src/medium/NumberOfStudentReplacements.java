package medium;

// https://leetcode.com/problems/number-of-student-replacements/
public class NumberOfStudentReplacements {
    public int totalReplacements(int[] ranks) {
        int size = ranks.length;
        int result = 0;
        int curr = ranks[0];
        for (int i = 1; i < size; i++) {
            if (ranks[i] < curr) {
                result++;
                curr = ranks[i];
            }
        }
        return result;
    }

    /*
     * Revision Note — Number of Student Replacements (Medium)
     * Pattern: One-pass running minimum — count left-to-right strict new minimums
     * Key Insight: A replacement happens exactly when the current student has a strictly
     *              lower rank than the best seen so far; track the running minimum and count drops.
     * Gotchas:
     *   - Strict less-than (<) means equal ranks do not trigger a replacement.
     *   - ranks[0] initialises curr, so the loop starts at i=1 — don't double-count the first student.
     *   - Single-element input: loop never runs, returns 0 correctly.
     */
    public static void main(String[] args) {
        NumberOfStudentReplacements N = new NumberOfStudentReplacements();

        // Test 1: two replacements — 3 and 2 are new minimums
        System.out.println("Test 1: " + N.totalReplacements(new int[]{5, 3, 6, 2, 4}) + " (Expected: 2)");

        // Test 2: ascending — no element beats the first, no replacements
        System.out.println("Test 2: " + N.totalReplacements(new int[]{1, 2, 3, 4, 5}) + " (Expected: 0)");

        // Test 3: descending — every student is a new minimum, n-1 replacements
        System.out.println("Test 3: " + N.totalReplacements(new int[]{5, 4, 3, 2, 1}) + " (Expected: 4)");

        // Test 4: single element — no replacements possible
        System.out.println("Test 4: " + N.totalReplacements(new int[]{7}) + " (Expected: 0)");

        // Test 5: duplicate ranks — equal rank does not trigger a replacement
        System.out.println("Test 5: " + N.totalReplacements(new int[]{3, 3, 3}) + " (Expected: 0)");
    }
}
