package contests.biweekly_196;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// https://leetcode.com/problems/minimum-range-after-sum-operations/
public class MinimumRangeAfterSumOperations {
    public int minRange(int a, int b, int c) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(a);
        numbers.add(b);
        numbers.add(c);
        numbers.sort(Comparator.reverseOrder());
        return Math.min(numbers.get(0) - numbers.get(2), numbers.get(1));
    }

    /*
     * Revision Note — Minimum Range After Sum Operations (Medium, Biweekly Contest 196)
     *
     * Pattern: math / case analysis on the sorted triple x <= y <= z. Answer = min(z - x, y).
     *
     * Key insight: replacing the maximum z with x + y gives (x, y, x + y), whose range is exactly y.
     * Nothing beats that. Every operation keeps two values and replaces the third with their sum,
     * so the new value is the largest. The range is then the sum minus the smaller kept value: the
     * larger kept value. Because everything is positive, the middle value never shrinks, so that is
     * always at least y. So the choice is between doing nothing (range z - x) and one operation on
     * the maximum (range y).
     *
     * Gotchas:
     *  - Zero operations are allowed, and sometimes that is the best (Example 2; Test 5, where the
     *    original range 49 beats y = 50).
     *  - Position doesn't matter once the three are sorted (Test 6, where the maximum is b).
     *  - Values are at most 100, so the sum x + y can't overflow.
     *  - Checked against a BFS over every reachable triple for all a, b, c in 1..10.
     *
     * Complexity: O(1).
     *
     * Template:
     *   sort to x <= y <= z
     *   return min(z - x, y)
     *
     * Alternative without a list: x = min(a, min(b, c)), z = max(a, max(b, c)), y = a + b + c - x - z.
     */
    public static void main(String[] args) {
        MinimumRangeAfterSumOperations M = new MinimumRangeAfterSumOperations();

        // Test 1: LeetCode example 1 — replacing the maximum 9 with 2 + 5 leaves range y = 5
        System.out.println("Test 1: " + M.minRange(9, 2, 5) + " (Expected: 5)");

        // Test 2: LeetCode example 2 — doing nothing (range 2) beats any operation (range >= 6)
        System.out.println("Test 2: " + M.minRange(5, 7, 6) + " (Expected: 2)");

        // Test 3: LeetCode example 3 — all equal, range already 0
        System.out.println("Test 3: " + M.minRange(3, 3, 3) + " (Expected: 0)");

        // Test 4: one large value and two small — one operation brings the range from 99 down to 1
        System.out.println("Test 4: " + M.minRange(100, 1, 1) + " (Expected: 1)");

        // Test 5: two equal large values — the original range 49 beats y = 50
        System.out.println("Test 5: " + M.minRange(1, 50, 50) + " (Expected: 49)");

        // Test 6: the maximum is the middle argument — only the sorted order matters
        System.out.println("Test 6: " + M.minRange(1, 100, 2) + " (Expected: 2)");
    }
}
