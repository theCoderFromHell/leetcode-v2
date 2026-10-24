package contests.biweekly_194;

// https://leetcode.com/problems/difference-between-peaks-and-valleys/
public class DifferenceBetweenPeaksAndValleys {
    public int peakValleyDifference(int[] nums) {
        int size = nums.length;
        int peakSum = 0;
        int vallySum = 0;
        for (int i = 1; i < size-1; i++) {
            if (nums[i-1] < nums[i] && nums[i] > nums[i+1])
                peakSum += nums[i];
            else if (nums[i-1] > nums[i] && nums[i] < nums[i+1])
                vallySum += nums[i];
        }
        return peakSum - vallySum;
    }

    /*
     * Revision Note — Difference Between Peaks and Valleys (Easy, Biweekly 194 Q1)
     *
     * Pattern: Single pass over the interior indices, comparing each element with both neighbours.
     *
     * Key insight: peak and valley are mutually exclusive (nums[i] can't be both > and < its left
     * neighbour), so one if / else-if per index, add peaks and subtract valleys.
     *
     * Gotchas:
     *  - Endpoints never qualify (0 < i < n - 1) — loop 1..n-2, which also keeps i ± 1 in bounds.
     *  - Comparisons are STRICT: an equal neighbour (plateau) makes the index neither.
     *  - Valleys are subtracted, so a negative valley INCREASES the answer ([0, -100, 0] = 100).
     *  - |sum| <= 100 * 100, so int is plenty.
     *
     * Complexity: O(n) time, O(1) space.
     *
     * Template:
     *   for i in 1..n-2:
     *       a[i-1] < a[i] > a[i+1] → result += a[i]
     *       a[i-1] > a[i] < a[i+1] → result -= a[i]
     */
    public static void main(String[] args) {
        DifferenceBetweenPeaksAndValleys D = new DifferenceBetweenPeaksAndValleys();

        // Test 1: LeetCode example 1 — alternating peaks and valleys
        System.out.println("Test 1: " + D.peakValleyDifference(new int[]{1, 5, 2, 4, 1, 3, 0}) + " (Expected: 9)");

        // Test 2: LeetCode example 2 — negative valleys add to the answer
        System.out.println("Test 2: " + D.peakValleyDifference(new int[]{-5, -6, -1, -2, 5, 3, 2}) + " (Expected: 12)");

        // Test 3: LeetCode example 3 — no peaks (5, 5 plateau), answer negative
        System.out.println("Test 3: " + D.peakValleyDifference(new int[]{6, 5, 2, 5, 5, 3, 6}) + " (Expected: -5)");

        // Test 4: minimum n = 3 — exactly one interior index
        System.out.println("Test 4: " + D.peakValleyDifference(new int[]{1, 3, 2}) + " (Expected: 3)");

        // Test 5: plateau top — equal neighbours make both indices neither peak nor valley
        System.out.println("Test 5: " + D.peakValleyDifference(new int[]{1, 2, 2, 1}) + " (Expected: 0)");

        // Test 6: strictly increasing — no interior index qualifies
        System.out.println("Test 6: " + D.peakValleyDifference(new int[]{1, 2, 3, 4}) + " (Expected: 0)");

        // Test 7: value extremes — the -100 valleys count as +100 each
        System.out.println("Test 7: " + D.peakValleyDifference(new int[]{100, -100, 100, -100, 100}) + " (Expected: 300)");
    }
}
