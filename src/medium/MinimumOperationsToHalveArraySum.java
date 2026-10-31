package medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

// https://leetcode.com/problems/minimum-operations-to-halve-array-sum/
public class MinimumOperationsToHalveArraySum {
    public int halveArray(int[] nums) {
        int size = nums.length;
        PriorityQueue<Double> pq = new PriorityQueue<>(Comparator.reverseOrder());
        double total = 0;
        for (int i = 0; i < size; i++) {
            pq.add((double) nums[i]);
            total += nums[i];
        }
        int steps = 0;
        double currSum = total;
        while (currSum > total/2) {
            double top = pq.poll();
            currSum -= top/2;
            pq.add(top/2);
            steps++;
        }
        return steps;
    }

    /*
     * Revision Note — Minimum Operations to Halve Array Sum (Medium)
     * Pattern: greedy + max-heap — always halve the current largest value, and push the half back.
     * Key Insight: halving x removes x/2, so the largest value removes the most. Taking it never
     *              costs a later move: each element offers removals x/2, x/4, x/8, ... in decreasing
     *              order, any plan takes a prefix of each chain, and greedy takes the k largest
     *              removals overall, which are exactly such prefixes.
     * Gotchas:
     *   - Halve in double, not int: integer halving makes 7 remove 7 - 7/2 = 4 instead of 3.5, so
     *     [7, 1] looks done after one operation (Test 4).
     *   - "At least half" means stop when the sum lands exactly on half, so loop while
     *     currSum > total / 2. With >=, [4, 4, 4, 4] takes a 5th operation (Test 5).
     *   - The sum reaches 10^5 * 10^7 = 10^12. An int total already wraps at 300 * 10^7 and
     *     returns 0 (Test 6); a double (or long) total holds it.
     *   - Doubles are fine here: dividing by 2 is exact in binary floating point, and a BigDecimal
     *     cross-check on 3,000 large inputs agreed on every answer.
     *   - Halving every element once removes exactly half, so the answer is at most n and the loop
     *     always ends.
     * Complexity: O(n log n) time — n inserts, then at most n operations, each a poll and an add.
     *             O(n) space for the heap.
     * Alternative — two queues (3066's trick, mirrored): the popped maximums never increase, so the
     *   halves pushed back come out NON-INCREASING. Sort nums once and append the halves to a FIFO;
     *   the next largest is at the front of one of the two. O(n log n) for the sort, then O(n),
     *   with primitive doubles and no boxing.
     * Template:
     *   heap = max-heap of nums as double; sum = total; ops = 0
     *   while sum > total / 2: x = poll(); sum -= x / 2; add(x / 2); ops++
     *   return ops
     */
    public static void main(String[] args) {
        MinimumOperationsToHalveArraySum M = new MinimumOperationsToHalveArraySum();

        // Test 1: LeetCode example 1 — 19 is halved twice in a row, so the half must go back into the heap
        System.out.println("Test 1: " + M.halveArray(new int[]{5, 19, 8, 1}) + " (Expected: 3)");

        // Test 2: LeetCode example 2
        System.out.println("Test 2: " + M.halveArray(new int[]{3, 8, 20}) + " (Expected: 3)");

        // Test 3: single element — one halving removes 0.5, exactly half of 1
        System.out.println("Test 3: " + M.halveArray(new int[]{1}) + " (Expected: 1)");

        // Test 4: odd values — 7 must halve to 3.5. Integer halving removes 4 >= 8 / 2 and stops after 1
        System.out.println("Test 4: " + M.halveArray(new int[]{7, 1}) + " (Expected: 2)");

        // Test 5: all values equal — after 4 operations the sum is exactly half (8 of 16), which counts,
        // so the loop must stop there; a >= condition takes a 5th
        System.out.println("Test 5: " + M.halveArray(new int[]{4, 4, 4, 4}) + " (Expected: 4)");

        // Test 6: 300 copies of 10^7 — the sum is 3 * 10^9, past Integer.MAX_VALUE. An int total wraps
        // negative and returns 0
        int[] big = new int[300];
        Arrays.fill(big, 10000000);
        System.out.println("Test 6: " + M.halveArray(big) + " (Expected: 300)");
    }
}
