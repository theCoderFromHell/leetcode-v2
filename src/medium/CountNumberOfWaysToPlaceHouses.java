package medium;

// https://leetcode.com/problems/count-number-of-ways-to-place-houses/
public class CountNumberOfWaysToPlaceHouses {
    int MOD = 1000000007;
    public int countHousePlacements(int n) {
        int include = 0;
        int exclude = 1;
        for (int i = 1; i <= n; i++) {
            int temp = include;
            include = exclude;
            exclude = (exclude + temp) % MOD;
        }
        long value = (include + exclude) % MOD;
        long result = (value * value) % MOD;
        return (int) result;
    }

    /*
     * Revision Note — Count Number of Ways to Place Houses (Medium)
     *
     * Pattern: House Robber–style include/exclude DP (Fibonacci), then square.
     *
     * Key insight: the two sides never constrain each other, so
     *   answer = (ways for ONE side)^2. One side is "no two adjacent houses"
     *   on n plots: f(n) = f(n-1) + f(n-2), i.e. 2, 3, 5, 8, ... (Fibonacci).
     *
     * State per plot i:
     *   include = arrangements ending with a house on plot i  (previous must be empty)
     *   exclude = arrangements ending with plot i empty        (previous anything)
     *   include' = exclude;  exclude' = include + exclude
     *
     * Gotchas:
     *   - Square the ONE-side count, don't add the sides: n = 2 is 3 * 3 = 9, not 3 + 3.
     *   - Mod BEFORE squaring and square in long: value < 1e9+7, so value * value
     *     needs ~60 bits. Squaring in int overflows silently.
     *   - include + exclude in int is safe only because both are already < MOD:
     *     2 * (1e9 + 6) = 2,000,000,012 < Integer.MAX_VALUE (2,147,483,647).
     *
     * Complexity: O(n) time, O(1) space. (Fast-doubling or 2x2 matrix power
     * would give O(log n), which isn't needed for n <= 1e4.)
     *
     * Template:
     *   inc = 0, exc = 1
     *   for each plot: (inc, exc) = (exc, (inc + exc) % MOD)
     *   side = (inc + exc) % MOD
     *   return side * side % MOD   // in long
     */
    public static void main(String[] args) {
        CountNumberOfWaysToPlaceHouses C = new CountNumberOfWaysToPlaceHouses();

        // Test 1: LeetCode example 1 — one side has 2 ways (empty / house), 2 * 2 = 4
        System.out.println("Test 1: " + C.countHousePlacements(1) + " (Expected: 4)");

        // Test 2: LeetCode example 2 — one side has 3 ways (HH is illegal), 3 * 3 = 9, not 3 + 3
        System.out.println("Test 2: " + C.countHousePlacements(2) + " (Expected: 9)");

        // Test 3: n = 3 — one side f(3) = 5, guards the recurrence beyond the examples
        System.out.println("Test 3: " + C.countHousePlacements(3) + " (Expected: 25)");

        // Test 4: n = 5 — one side f(5) = 13
        System.out.println("Test 4: " + C.countHousePlacements(5) + " (Expected: 169)");

        // Test 5: n = 100 — values already wrap modulo 1e9+7
        System.out.println("Test 5: " + C.countHousePlacements(100) + " (Expected: 20522904)");

        // Test 6: n = 10000 (max constraint) — the square must be taken in long after modding
        System.out.println("Test 6: " + C.countHousePlacements(10000) + " (Expected: 402613600)");
    }
}
