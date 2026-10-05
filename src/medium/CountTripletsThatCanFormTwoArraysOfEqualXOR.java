package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

// https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/
public class CountTripletsThatCanFormTwoArraysOfEqualXOR {
    public int countTriplets(int[] arr) {
        int size = arr.length;
        HashMap<Integer, List<Integer>> xorPoint = new HashMap<>();
        List<Integer> seed = new ArrayList<>();
        seed.add(-1);
        xorPoint.put(0, seed);
        int XOR = 0;
        int result = 0;
        for (int i = 0; i < size; i++) {
            XOR ^= arr[i];
            if (xorPoint.containsKey(XOR)) {
                List<Integer> indices = xorPoint.get(XOR);
                for (int index : indices)
                    result += (i - index - 1);
            }
            xorPoint.computeIfAbsent(XOR, k -> new ArrayList<>()).add(i);
        }
        return result;
    }


    /*
     * Revision Note - Count Triplets That Can Form Two Arrays of Equal XOR (Medium)
     *
     * Pattern: Prefix XOR + hash map of earlier positions, with each match WEIGHTED by its span
     *
     * Key Insight: Two steps, and j vanishes in the first one.
     *
     *   (1) a and b are ADJACENT slices covering [i, j-1] and [j, k] with no gap or overlap, so
     *       a ^ b == XOR(arr[i..k]). And a == b forces a ^ b == 0. So the condition is
     *       "the XOR of the whole range [i, k] is zero" - j IS NOT MENTIONED. In prefix terms,
     *       with p[t] = arr[0]^...^arr[t-1]:   XOR(arr[i..k]) == 0  <=>  p[i] == p[k+1]
     *
     *   (2) So for every PAIR of equal prefix values, j ranges freely over i < j <= k, giving
     *       k - i triplets. Not one per pair - the count is WEIGHTED by the span.
     *
     * That second half is what separates this from 560. There, each matching prefix contributes
     * exactly 1. Here a pair of prefix indices (x, y) contributes y - 1 - x, so three equal
     * prefixes at 2, 5, 9 give C(3,2) = 3 pairs worth 2 + 6 + 3 = 11, not 3.
     *
     * Gotchas:
     * - `List.of(-1)` IS IMMUTABLE. Seeding the map with it then calling
     *   `computeIfAbsent(key, k -> new ArrayList<>()).add(i)` throws
     *   UnsupportedOperationException, because computeIfAbsent returns the EXISTING value and
     *   never runs the factory when the key is present. Seed with a mutable ArrayList. It fires
     *   the first time the running XOR returns to 0 - i = 2 in [2,3,1,6,7], so example 1
     * - WEIGHTED, not counted. `result += 1` per match gives 4 instead of 6 on [2,2,2,2].
     *   That input is in the tests precisely because it has one prefix value appearing three
     *   times and another twice
     * - SEED prefix 0 before the sweep (here as index -1, standing in for prefix index 0), or
     *   every range starting at index 0 is invisible. Same off-by-one as `map.put(0, 1)` in 560
     * - A pair with y == x + 1 means k == i, so k - i == 0 and it contributes nothing. The
     *   formula handles this by itself - no guard needed
     * - i < j <= k, so j may equal k (b can be a single element) but i < j always (a is never
     *   empty). Triplets like (0,2,2) in example 1 rely on j == k being legal
     * - n <= 300, so the O(n^3) triple loop also passes. The constraints do not force the
     *   insight, which makes it easy to stop early and miss the point of the problem
     *
     * Complexity: storing a LIST of indices per prefix value and iterating all matches is
     * O(n^2) worst case - n=300 all-equal gives ~45k inner steps, which is why Test 10 is
     * instant. O(n) space.
     *
     * The true single pass: pull the constant out of the sum.
     *     sum over matching x of (y - 1 - x)  ==  count * (y - 1) - sum_of_x
     * so the map only needs TWO LONGS per prefix value - how many times seen, and the sum of
     * those indices - instead of the indices themselves. O(n) time, no inner loop.
     *
     * Template:
     *   map = {0: (count 1, sumIdx 0)}          // prefix index 0, pre-seeded
     *   xor = 0
     *   for y in 1..n:                          // y = prefix index = k+1
     *     xor ^= arr[y-1]
     *     if xor in map:
     *       (c, sx) = map[xor]
     *       result += c * (y - 1) - sx
     *     map[xor].count++;  map[xor].sumIdx += y
     *   return result
     *
     * Same prefix-state-in-a-hash-map engine as 560, with the group operation changed from +
     * to ^ and the per-match contribution changed from 1 to a span. Both axes are worth varying
     * deliberately when a prefix-map problem does not fit the 560 template exactly.
     */

    /*
     * O(n^3) reference: enumerate every (i, j, k) directly and compare a against b, using a
     * prefix-XOR table so each comparison is O(1). Shares nothing with the hash-map counting,
     * and n <= 300 keeps it usable as a check on real-sized inputs.
     */
    private static int bruteForce(int[] arr) {
        int n = arr.length;
        int[] p = new int[n + 1];
        for (int t = 0; t < n; t++) p[t + 1] = p[t] ^ arr[t];
        int count = 0;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                for (int k = j; k < n; k++) {
                    int a = p[j] ^ p[i];          // arr[i..j-1]
                    int b = p[k + 1] ^ p[j];      // arr[j..k]
                    if (a == b) count++;
                }
        return count;
    }

    public static void main(String[] args) {
        CountTripletsThatCanFormTwoArraysOfEqualXOR C = new CountTripletsThatCanFormTwoArraysOfEqualXOR();

        System.out.println("Test 1: " + C.countTriplets(new int[]{2,3,1,6,7}) + " (Expected: 4)");
        System.out.println("Test 2: " + C.countTriplets(new int[]{1,1,1,1,1}) + " (Expected: 10)");
        System.out.println("Test 3: " + C.countTriplets(new int[]{1}) + " (Expected: 0)");           // n=1, no valid triplet exists
        System.out.println("Test 4: " + C.countTriplets(new int[]{1,2}) + " (Expected: 0)");         // n=2, the only triplet fails
        System.out.println("Test 5: " + C.countTriplets(new int[]{1,1}) + " (Expected: 1)");         // n=2, the only triplet works
        System.out.println("Test 6: " + C.countTriplets(new int[]{1,2,4,8}) + " (Expected: 0)");     // all prefixes distinct, never matches
        System.out.println("Test 7: " + C.countTriplets(new int[]{2,2,2,2}) + " (Expected: 6)");     // three equal prefixes AND a separate pair
        System.out.println("Test 8: " + C.countTriplets(new int[]{1,3,2,5,4,6}) + " (Expected: " + bruteForce(new int[]{1,3,2,5,4,6}) + ")");

        // cross-check against the O(n^3) reference; small value range so prefixes collide often
        boolean agree = true;
        String firstBad = "";
        Random rnd = new Random(73);
        for (int t = 0; t < 500; t++) {
            int[] a = new int[1 + rnd.nextInt(12)];
            for (int i = 0; i < a.length; i++) a[i] = 1 + rnd.nextInt(4);
            int got = C.countTriplets(a.clone()), want = bruteForce(a);
            if (got != want) {
                agree = false;
                if (firstBad.isEmpty())
                    firstBad = Arrays.toString(a) + " got " + got + " want " + want;
            }
        }
        System.out.println("Test 9: " + agree + " (Expected: true)  - matches O(n^3) reference, 500 random"
                + (agree ? "" : ", first mismatch: " + firstBad));

        // n = 300 all equal: the constraint ceiling AND the worst case for matching prefixes
        int[] big = new int[300];
        Arrays.fill(big, 7);
        long t0 = System.nanoTime();
        int got = C.countTriplets(big);
        long ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("Test 10: " + got + " (Expected: " + bruteForce(big) + ")  - n=300 all equal, " + ms + "ms");
    }
}
