package contests.weekly_526;

// https://leetcode.com/problems/minimize-the-maximum-value-of-gcd-connected-array/
public class MinimizeTheMaximumValueOfGCDConnectedArray {
    public long minimumMaxValue(int n, int index, int value) {
        if (value == 1)
            return -1;
        int lowestPrimeFactor = lowestPrimeFactor(value);
        if ((value - lowestPrimeFactor) / 2 <= (index-1))
            return -1;
        if (index == n-1)
            return value;
        return value + lowestPrimeFactor + (long) (n - 2 - index) * 2;
    }

    int lowestPrimeFactor(int n) {
        if (n % 2 == 0)
            return 2;
        for (int i = 3; (long) i * i <= n; i += 2) {
            if (n % i == 0)
                return i;
        }
        return n;
    }

    /*
     * Revision Note — Minimize the Maximum Value of GCD-Connected Array (Medium, Weekly 526 Q3)
     *
     * Pattern: Number theory + closed form. n goes up to 1e9, so the array is never built.
     *
     * Key insight: gcd(x, x + 1) = 1, so every step is at least +2, and an even number can always
     * step +2. Let p be value's smallest prime factor.
     *  - Right side: the first step after value is at least value + p (the next multiple of any prime
     *    factor of value). value + p is always even (even + 2, or odd + odd), so every later step is +2:
     *      answer = value + p + 2 · (n - 2 - index)
     *  - Left side: the closest legal predecessor is value - p, also even, then -2 steps down to 2.
     *    (1 can't be used: its gcd with anything is 1.) That gives room for (value - p) / 2 elements,
     *    so -1 if index > (value - p) / 2.
     *
     * Gotchas:
     *  - index == n - 1: value IS the maximum. The general formula gives value + p - 2, which only
     *    equals value when p = 2. That made [n=2, index=1, value=9] return 10 instead of 9 (this
     *    contest's bug, Test 4).
     *  - value == 1 shares no factor with anything → -1 (n ≥ 2 means it always has a neighbour).
     *  - value prime → p = value, so nothing fits on the left (index must be 0), and the right side
     *    starts at 2 · value.
     *  - The answer reaches ~4e9 → long. value + p ≤ 2 · 999999937 still fits an int, and the long
     *    cast on the (n - 2 - index) term promotes the rest.
     *
     * Complexity: O(√value) time for the smallest prime factor by trial division, O(1) space.
     *
     * Template:
     *   if value == 1 → -1;  p = spf(value)
     *   if index > (value - p) / 2 → -1
     *   return index == n - 1 ? value : value + p + 2L * (n - 2 - index)
     */
    public static void main(String[] args) {
        MinimizeTheMaximumValueOfGCDConnectedArray M = new MinimizeTheMaximumValueOfGCDConnectedArray();

        // Test 1: LeetCode example 1 — even value: 8 → 10, p = 2
        System.out.println("Test 1: " + M.minimumMaxValue(4, 2, 8) + " (Expected: 10)");

        // Test 2: LeetCode example 2 — prime value with an element needed before it
        System.out.println("Test 2: " + M.minimumMaxValue(2, 1, 3) + " (Expected: -1)");

        // Test 3: LeetCode example 3 — prime value at index 0: the next element is 2 · value
        System.out.println("Test 3: " + M.minimumMaxValue(2, 0, 3) + " (Expected: 6)");

        // Test 4: index == n - 1 with an odd composite value — the answer is value itself ([3, 9]).
        // The contest version returned 10 here
        System.out.println("Test 4: " + M.minimumMaxValue(2, 1, 9) + " (Expected: 9)");

        // Test 5: value == 1 — gcd with any neighbour is 1
        System.out.println("Test 5: " + M.minimumMaxValue(3, 0, 1) + " (Expected: -1)");

        // Test 6: prime value in the middle — nothing can come before it
        System.out.println("Test 6: " + M.minimumMaxValue(4, 2, 5) + " (Expected: -1)");

        // Test 7: odd prime at index 0 — 7, 14, 16
        System.out.println("Test 7: " + M.minimumMaxValue(3, 0, 7) + " (Expected: 16)");

        // Test 8: left side exactly full — (9 - 3) / 2 = 3 slots for index 3: 2, 4, 6, 9, 12
        System.out.println("Test 8: " + M.minimumMaxValue(5, 3, 9) + " (Expected: 12)");

        // Test 9: one slot too many on the left — index 4 > 3
        System.out.println("Test 9: " + M.minimumMaxValue(5, 4, 9) + " (Expected: -1)");

        // Test 10: n = 1e9 with the largest prime below 1e9 — the answer ~4e9 needs long, and the
        // trial division runs its full √value loop
        System.out.println("Test 10: " + M.minimumMaxValue(1000000000, 0, 999999937) + " (Expected: 3999999870)");
    }
}
