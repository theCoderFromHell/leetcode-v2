package contests.weekly_526;

// https://leetcode.com/problems/minimum-value-of-merged-integers/
public class MinimumValueOfMergedIntegers {
    public int minMergedInteger(int[] nums) {
        int size = nums.length;
        int minValue = Integer.MAX_VALUE;
        for (int i = 0; i < size-1; i+= 2) {
            Integer newNumber = Integer.valueOf(String.valueOf(nums[i]) + String.valueOf(nums[i+1]));
            minValue = Math.min(minValue, newNumber);
        }
        return minValue;
    }

    /*
     * Revision Note — Minimum Value of Merged Integers (Easy, Weekly 526 Q1)
     *
     * Pattern: Direct simulation: concatenate each fixed pair (nums[0]+nums[1], nums[2]+nums[3], ...)
     * and keep the minimum.
     *
     * Gotchas:
     *  - Compare the merged values as NUMBERS, not strings: "55" < "1999" numerically, but a string
     *    compare would pick "1999" ('1' < '5').
     *  - The pair order is fixed: [7, 3] merges to 73, never 37.
     *  - Largest merge is 1000 ‖ 1000 = 10001000, so int is enough.
     *
     * Complexity: O(n · d) time (d ≤ 4 digits per value), O(d) extra space for each string.
     *
     * Alternative without strings: merged = a * 10^digits(b) + b, where digits(b) is found by
     * multiplying a power of 10 up until it exceeds b.
     */
    public static void main(String[] args) {
        MinimumValueOfMergedIntegers M = new MinimumValueOfMergedIntegers();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + M.minMergedInteger(new int[]{23, 55, 42, 21}) + " (Expected: 2355)");

        // Test 2: LeetCode example 2 — a shorter merge (347) beats a smaller leading digit (1111)
        System.out.println("Test 2: " + M.minMergedInteger(new int[]{34, 7, 11, 11}) + " (Expected: 347)");

        // Test 3: LeetCode example 3 — a single pair
        System.out.println("Test 3: " + M.minMergedInteger(new int[]{9, 20}) + " (Expected: 920)");

        // Test 4: numeric vs string compare — 55 wins, though "1999" < "55" as strings
        System.out.println("Test 4: " + M.minMergedInteger(new int[]{5, 5, 1, 999}) + " (Expected: 55)");

        // Test 5: pair order is fixed — 73, not 37
        System.out.println("Test 5: " + M.minMergedInteger(new int[]{7, 3}) + " (Expected: 73)");

        // Test 6: both values at the 1000 limit — the largest possible merge still fits an int
        System.out.println("Test 6: " + M.minMergedInteger(new int[]{1000, 1000}) + " (Expected: 10001000)");
    }
}
