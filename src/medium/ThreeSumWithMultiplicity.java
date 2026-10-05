package medium;

import java.util.Arrays;
import java.util.Random;

// https://leetcode.com/problems/3sum-with-multiplicity/
public class ThreeSumWithMultiplicity {
    int MOD = 1000000007;
    public int threeSumMulti(int[] arr, int target) {
        int size = arr.length;
        int[] count = new int[101];
        for (int i = 0; i < size; i++)
            count[arr[i]]++;
        long result = 0;
        for (int x = 0; x < 101; x++) {
            if (count[x] == 0)
                continue;
            for (int y = x; y < 101; y++) {
                if (count[y] == 0)
                    continue;
                int z = target - x - y;
                if (z < y || z > 100 || count[z] == 0)
                    continue;
                if (x == y && y == z)
                    result = (result + combo(count, x, 3)) % MOD;
                else if (x == y)
                    result = (result + combo(count, x, 2) * combo(count, z, 1)) % MOD;
                else if (y == z)
                    result = (result + combo(count, x, 1) * combo(count, y, 2)) % MOD;
                else
                    result = (result + combo(count, x, 1) * combo(count, y, 1) * combo(count, z, 1)) % MOD;
            }
        }
        return (int) result;
    }

    /*
     * Must be computed in long and divided BEFORE any modulo. C(n,R) is only an integer after
     * the division, so reducing mod MOD first destroys the divisibility and the /2 or /6 then
     * truncates a wrong value. count[X] <= 3000, so 3000*2999*2998 = 2.7e10 needs long but is
     * nowhere near overflowing it.
     */
    private long combo(int[] count, int X, int R) {
        long c = count[X];
        switch (R) {
            case 1 :
                return c;
            case 2 :
                return c * (c - 1) / 2;
            case 3 :
                return c * (c - 1) * (c - 2) / 6;
        }
        return -1;
    }


    /*
     * Revision Note - 3Sum With Multiplicity (Medium)
     *
     * Pattern: Frequency table over the VALUE range, then enumerate value triples x <= y <= z
     * and multiply binomial coefficients
     *
     * Key Insight: `0 <= arr[i] <= 100` is a licence to change WHAT YOU ITERATE OVER. Only 101
     * distinct values exist, so instead of scanning array positions (O(n^2) with two pointers,
     * O(n^3) naively) you scan value PAIRS x <= y and derive z = target - x - y. That is ~5,151
     * iterations regardless of n - 3,000 elements and 300,000 elements cost the same.
     *
     * Enumerating in sorted order (y starts AT x, not x+1) is what makes the case analysis
     * finite. With x <= y <= z, x == z would force all three equal, so only two comparisons
     * matter and there are exactly four shapes:
     *
     *     x <  y <  z   ->  count[x] * count[y] * count[z]
     *     x == y <  z   ->  C(count[x], 2) * count[z]
     *     x <  y == z   ->  count[x] * C(count[y], 2)
     *     x == y == z   ->  C(count[x], 3)
     *
     * The shapes are not searched for - they ARE the iterations where y lands on x, or where the
     * computed z lands on y. Starting y at x+1 would silently skip every (a,a,b) triple.
     *
     * Gotchas:
     * - MOD DOES NOT COMMUTE WITH INTEGER DIVISION. C(n,R) is only an integer AFTER dividing by
     *   2 or 6, so `(c*(c-1)*(c-2) % MOD) / 6` is wrong even when nothing overflows - reducing
     *   first destroys the divisibility and the /6 truncates garbage. Compute the exact product,
     *   divide, THEN mod. Mod belongs at the accumulation, never inside the binomial
     * - That forces long. count can be 3000, and 3000*2999*2998 = 26,973,006,000 overflows int
     *   BEFORE the /6 - it wraps, then divides the wrapped value. The threshold is count ~1291,
     *   well inside n <= 3000. Test 14 returned -154452382 before the fix, which is the
     *   unmistakable signature
     * - z NEEDS BOTH BOUNDS. `z < y` catches negatives (y >= 0) but nothing catches z > 100, and
     *   target can be 300: [0,0,0] with target 300 gives z = 300 and reads off count[]. Valid
     *   input, ArrayIndexOutOfBoundsException. Tests 10 and 11
     * - `z >= y` is also what prevents double counting, not just an index guard - without it the
     *   pair (2,2) with z=1 would re-count a triple already seen as (1,2,2)
     * - C(c,2) and C(c,3) must come out as 0, not negative, when c is too small. c*(c-1)/2 gives
     *   0 at c=1; c*(c-1)*(c-2)/6 gives 0 at c=1 and c=2. Both fine, worth confirming
     * - The (a,a,a) case appears in NEITHER provided example. Test 4 ([1,1,1,1,1], target 3 ->
     *   C(5,3) = 10) exists because a missing fourth branch passes both samples
     *
     * Complexity: O(V^2 + n) time with V = 101 - one counting pass, then ~5,151 value pairs at
     * O(1) each. O(V) = O(1) space. Independent of n beyond the initial count, which is the
     * whole reason to prefer this over the two-pointer form at these constraints.
     *
     * Template:
     *   count[v] for v in 0..100
     *   for x in 0..100, count[x] > 0:
     *     for y in x..100, count[y] > 0:          // y FROM x, so (a,a,b) is reachable
     *       z = target - x - y
     *       if z < y or z > 100 or count[z] == 0: continue
     *       add the matching one of the four formulas, mod MOD
     *
     * Alternative: sort, fix the smallest element, converge two pointers on the rest - O(n^2),
     * no value-range assumption, so it is the version that survives if arr[i] <= 100 is relaxed.
     * Its equal-window case (arr[lo] == arr[hi] -> C(hi-lo+1, 2) and break) is the same
     * combinatorics in a different disguise.
     */

    /*
     * O(n^3) reference: enumerate index triples directly. Accumulates in long and mods at the
     * end, so it shares none of the counting-formula logic that can go wrong. Only usable for
     * small n, which is why the large cases below carry analytically derived expected values.
     */
    private static int bruteForce(int[] arr, int target) {
        long count = 0;
        int n = arr.length;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                for (int k = j + 1; k < n; k++)
                    if (arr[i] + arr[j] + arr[k] == target) count++;
        return (int) (count % 1000000007L);
    }

    private static int[] repeat(int value, int times) {
        int[] a = new int[times];
        Arrays.fill(a, value);
        return a;
    }

    public static void main(String[] args) {
        ThreeSumWithMultiplicity T = new ThreeSumWithMultiplicity();

        System.out.println("Test 1: " + T.threeSumMulti(new int[]{1,1,2,2,3,3,4,4,5,5}, 8) + " (Expected: 20)");
        System.out.println("Test 2: " + T.threeSumMulti(new int[]{1,1,2,2,2,2}, 5) + " (Expected: 12)");
        System.out.println("Test 3: " + T.threeSumMulti(new int[]{2,1,3}, 6) + " (Expected: 1)");
        System.out.println("Test 4: " + T.threeSumMulti(new int[]{1,1,1,1,1}, 3) + " (Expected: 10)");       // (a,a,a) - C(5,3), no example covers this
        System.out.println("Test 5: " + T.threeSumMulti(new int[]{1,1,1,5}, 7) + " (Expected: 3)");          // (a,a,b) - C(3,2)*1
        System.out.println("Test 6: " + T.threeSumMulti(new int[]{1,5,5,5}, 11) + " (Expected: 3)");         // (a,b,b) - 1*C(3,2)
        System.out.println("Test 7: " + T.threeSumMulti(new int[]{0,0,0}, 0) + " (Expected: 1)");            // all zeros, target 0
        System.out.println("Test 8: " + T.threeSumMulti(new int[]{1,2,3}, 7) + " (Expected: 0)");            // no tuple sums to target
        System.out.println("Test 9: " + T.threeSumMulti(new int[]{100,100,100}, 300) + " (Expected: 1)");    // value and target both at their maximum

        // z can exceed 100: target 300 with small x and y makes z = 300, past the end of count[]
        System.out.println("Test 10: " + T.threeSumMulti(new int[]{0,0,0}, 300) + " (Expected: 0)");
        System.out.println("Test 11: " + T.threeSumMulti(new int[]{0,0,1,2}, 299) + " (Expected: 0)");

        // cross-check against the O(n^3) reference
        boolean agree = true;
        String firstBad = "";
        Random rnd = new Random(101);
        for (int t = 0; t < 500; t++) {
            int n = 3 + rnd.nextInt(18);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(6);        // tiny value range -> lots of ties
            int target = rnd.nextInt(16);
            int got = T.threeSumMulti(a.clone(), target), want = bruteForce(a, target);
            if (got != want) {
                agree = false;
                if (firstBad.isEmpty())
                    firstBad = Arrays.toString(a) + " target " + target + " got " + got + " want " + want;
            }
        }
        System.out.println("Test 12: " + agree + " (Expected: true)  - matches O(n^3) reference, 500 random"
                + (agree ? "" : ", first mismatch: " + firstBad));

        // OVERFLOW: 3000 identical values, so the answer is C(3000,3).
        // 3000*2999*2998 = 26,973,006,000 which blows past Integer.MAX_VALUE BEFORE the /6.
        // C(3000,3) = 4,495,501,000;  4,495,501,000 mod 1e9+7 = 495,500,972.
        System.out.println("Test 13: " + T.threeSumMulti(repeat(7, 3000), 21) + " (Expected: 495500972)");

        // same overflow, smaller: C(1500,3) = 561,375,500 (1500*1499*1498 = 3,368,253,000 > int)
        System.out.println("Test 14: " + T.threeSumMulti(repeat(4, 1500), 12) + " (Expected: 561375500)");

        // large (a,a,b): C(2000,2) * 1000 = 1,999,000,000 -> mod 1e9+7 = 998,999,993
        int[] mixed = new int[3000];
        Arrays.fill(mixed, 0, 2000, 0);
        Arrays.fill(mixed, 2000, 3000, 5);
        System.out.println("Test 15: " + T.threeSumMulti(mixed, 5) + " (Expected: 998999993)");
    }
}
