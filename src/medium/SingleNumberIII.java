package medium;

import java.util.Arrays;

// https://leetcode.com/problems/single-number-iii/
public class SingleNumberIII {
    public int[] singleNumber(int[] nums) {
        int size = nums.length;
        int xor = 0;
        for (int i = 0; i < size; i++)
            xor ^= nums[i];
        xor = xor & -xor;
        int xor1 = 0, xor2 = 0;
        for (int i = 0; i < size; i++) {
            if ((nums[i] & xor) == 0)
                xor1 ^= nums[i];
            else
                xor2 ^= nums[i];
        }
        return new int[]{xor1, xor2};
    }

    /*
     * Revision Note - Single Number III (Medium)
     *
     * Pattern: XOR to cancel the pairs, then SPLIT on a differing bit and XOR each half
     *
     * Key Insight: Two steps, and the second is the whole problem.
     *   (1) XOR everything. Pairs annihilate, leaving exactly a ^ b.
     *   (2) a ^ b cannot be recovered into a and b on its own - but every SET BIT in it is a
     *       position where a and b DIFFER. Pick any one as a mask, and it partitions the array
     *       into two groups with a in one and b in the other. Both copies of any duplicate are
     *       the same number, so they test identically against the mask and always land in the
     *       SAME group, where they cancel. Each group therefore XORs down to a single survivor.
     *
     * The partition needs NO extra space - you never store the groups, only two int accumulators.
     * Same fold as 136, just with two destinations and a bit test choosing between them.
     *
     * Gotchas:
     * - `x & -x` isolates the LOWEST set bit. In two's complement -x is ~x + 1, so the +1 ripples
     *   through the trailing ones and stops exactly at the lowest set bit, leaving every higher
     *   bit inverted - so the AND keeps that one bit alone
     * - THE TRAP: when a and b differ only in bit 31, the mask IS Integer.MIN_VALUE. Negating
     *   MIN_VALUE overflows back to itself, so x & -x == x, which is still correct - but the mask
     *   is NEGATIVE. So the membership test must be `(num & mask) == 0` or `!= 0`, NEVER `> 0`.
     *   With `> 0` everything routes into one group and you return [a^b, 0]. Reachable with
     *   a = 0, b = MIN_VALUE, which the full-int-range constraint permits. Tests 7, 8, 9, 10
     * - a ^ b is never 0, since the two singles are distinct - so a set bit always exists and the
     *   mask is well defined. No guard needed
     * - ANY differing bit works, not just the lowest. x & -x is simply the cheapest to extract.
     *   Integer.lowestOneBit(x) is the JDK equivalent and compiles to the same thing
     * - Two passes are required, not one: the mask has to exist before you can route anything
     * - Return order is unspecified, [3,5] and [5,3] both accepted - so tests must compare
     *   order-insensitively, which is why `same()` sorts before comparing
     *
     * Complexity: O(n) time, two passes. O(1) space - two accumulators and a mask, nothing that
     * grows with n. The constant-space requirement is what rules out a hash map, and the full int
     * range is what rules out a counting array, leaving XOR as the only route.
     *
     * Template:
     *   xor = 0;  for v in nums: xor ^= v          // xor == a ^ b
     *   mask = xor & -xor                          // any one bit where a and b differ
     *   g1 = g2 = 0
     *   for v in nums:
     *     if (v & mask) == 0: g1 ^= v
     *     else:               g2 ^= v
     *   return {g1, g2}
     *
     * The family: 136 is one XOR fold; this is two folds plus a split; 137 (every element thrice
     * except one) cannot use XOR cancellation at all, because three copies do not annihilate -
     * it needs per-bit counting mod 3 instead. Worth knowing which generalisation each takes.
     */

    // The answer may come back in either order, so every check is order-insensitive.
    private static boolean same(int[] got, int[] want) {
        if (got == null || got.length != 2) return false;
        int[] a = got.clone(), b = want.clone();
        Arrays.sort(a);
        Arrays.sort(b);
        return Arrays.equals(a, b);
    }

    public static void main(String[] args) {
        SingleNumberIII S = new SingleNumberIII();

        System.out.println("Test 1: " + Arrays.toString(S.singleNumber(new int[]{1,2,1,3,2,5}))
                + " (Expected: [3, 5] in any order -> " + same(S.singleNumber(new int[]{1,2,1,3,2,5}), new int[]{3,5}) + ")");
        System.out.println("Test 2: " + same(S.singleNumber(new int[]{-1,0}), new int[]{-1,0}) + " (Expected: true)");          // no duplicates at all
        System.out.println("Test 3: " + same(S.singleNumber(new int[]{0,1}), new int[]{0,1}) + " (Expected: true)");            // n=2, the minimum
        System.out.println("Test 4: " + same(S.singleNumber(new int[]{2,3}), new int[]{2,3}) + " (Expected: true)");            // differ only in the lowest bit
        System.out.println("Test 5: " + same(S.singleNumber(new int[]{-5,5}), new int[]{-5,5}) + " (Expected: true)");          // negatives of each other
        System.out.println("Test 6: " + same(S.singleNumber(new int[]{-3,-3,-1,-2,-2,7}), new int[]{-1,7}) + " (Expected: true)"); // negative duplicates

        // THE bit-31 cases: xor == Integer.MIN_VALUE, so the mask itself is negative.
        // These are what `(num & mask) > 0` would silently fail; `!= 0` / `== 0` passes.
        System.out.println("Test 7: " + same(S.singleNumber(new int[]{0, Integer.MIN_VALUE}), new int[]{0, Integer.MIN_VALUE})
                + " (Expected: true)  - mask is MIN_VALUE, negative");
        System.out.println("Test 8: " + same(S.singleNumber(new int[]{7,7,0,Integer.MIN_VALUE}), new int[]{0, Integer.MIN_VALUE})
                + " (Expected: true)  - same, with duplicates present");
        System.out.println("Test 9: " + same(S.singleNumber(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE}), new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE})
                + " (Expected: true)  - the two extremes");
        System.out.println("Test 10: " + same(S.singleNumber(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, 1, Integer.MIN_VALUE}), new int[]{1, Integer.MIN_VALUE})
                + " (Expected: true)");
    }
}
