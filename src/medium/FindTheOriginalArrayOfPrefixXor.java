package medium;

import java.util.Arrays;

// https://leetcode.com/problems/find-the-original-array-of-prefix-xor/
public class FindTheOriginalArrayOfPrefixXor {
    public int[] findArray(int[] pref) {
        int size = pref.length;
        int[] arr = new int[size];
        arr[0] = pref[0];
        for (int i = 1; i < size; i++)
            arr[i] = pref[i - 1] ^ pref[i];
        return arr;
    }

    /*
     * Revision Note - Find The Original Array of Prefix Xor (Medium)
     *
     * Pattern: undo a prefix fold. For prefix sums, arr[i] = pref[i] - pref[i - 1]; XOR is its own
     * inverse, so arr[i] = pref[i] ^ pref[i - 1].
     *
     * Key Insight: pref[i] = pref[i - 1] ^ arr[i]. XOR both sides with pref[i - 1]; since x ^ x = 0
     * and x ^ 0 = x, what's left is arr[i] = pref[i - 1] ^ pref[i].
     *
     * Gotchas:
     * - arr[0] = pref[0]: there's no previous prefix (or treat pref[-1] as 0, the XOR identity).
     * - Equal neighbours give 0 (Test 3), and a zero after x gives x back (Test 4).
     * - No overflow: XOR never produces a bit higher than its inputs' highest bit.
     *
     * Complexity: O(n) time, O(n) for the output (O(1) extra).
     *
     * Alternative (in place, O(1) space beyond the input): walk RIGHT to LEFT so pref[i - 1] is
     * still unmodified when it's needed: for i = n - 1 down to 1: pref[i] ^= pref[i - 1].
     *
     * Template:
     *   arr[0] = pref[0]; for i in 1..n-1: arr[i] = pref[i - 1] ^ pref[i]
     */

    public static void main(String[] args) {
        FindTheOriginalArrayOfPrefixXor F = new FindTheOriginalArrayOfPrefixXor();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + Arrays.toString(F.findArray(new int[]{5, 2, 0, 3, 1})) + " (Expected: [5, 7, 2, 3, 2])");

        // Test 2: LeetCode example 2: single element, arr[0] = pref[0]
        System.out.println("Test 2: " + Arrays.toString(F.findArray(new int[]{13})) + " (Expected: [13])");

        // Test 3: all values equal: x ^ x = 0, so everything after the first is 0
        System.out.println("Test 3: " + Arrays.toString(F.findArray(new int[]{7, 7, 7})) + " (Expected: [7, 0, 0])");

        // Test 4: max value, then zero: x ^ 0 = x, so the second element is the max again
        System.out.println("Test 4: " + Arrays.toString(F.findArray(new int[]{1000000, 0})) + " (Expected: [1000000, 1000000])");

        // Test 5: powers of two: each arr[i] has exactly the two neighbouring bits set
        System.out.println("Test 5: " + Arrays.toString(F.findArray(new int[]{1, 2, 4, 8})) + " (Expected: [1, 3, 6, 12])");
    }
}
