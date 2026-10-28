package hard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// https://leetcode.com/problems/minimum-cost-to-make-array-equal/
public class MinimumCostToMakeArrayEqual {
    public long minCost(int[] nums, int[] cost) {
        int size = nums.length;
        List<int[]> numbers = new ArrayList<>();
        long totalCost = 0;
        for (int i = 0; i < size; i++) {
            numbers.add(new int[]{nums[i], cost[i]});
            totalCost += cost[i];
        }
        Collections.sort(numbers, Comparator.comparingInt(o -> o[0]));
        long[] prefixCost = new long[size];
        for (int i = 1; i < size; i++)
            prefixCost[i] = prefixCost[i - 1] + numbers.get(i - 1)[1];
        long currCost = 0;
        for (int i = 1; i < size; i++)
            currCost += ((long) (numbers.get(i)[0] - numbers.get(0)[0]) * numbers.get(i)[1]);
        long result = currCost;
        for (int i = 1; i < size; i++) {
            int diff = numbers.get(i)[0] - numbers.get(i-1)[0];
            currCost = currCost + diff * (prefixCost[i] - (totalCost - prefixCost[i]));
            result = Math.min(result, currCost);
        }
        return result;
    }

    /*
     * Revision Note — Minimum Cost to Make Array Equal (Hard)
     * Pattern: weighted median by sweep — sort (value, cost) pairs by value, price the first value
     *          as the target, then slide the target from value to value in O(1) per step.
     * Key Insight: moving the target right by d costs d × (cost of everything at or left of it) and
     *              saves d × (cost of everything right of it). So the total falls while the cost
     *              ahead outweighs the cost behind, then rises: it bottoms out at the weighted median,
     *              and since every bend sits on an existing value, only those need testing.
     * Gotchas:
     *   - The highest single cost doesn't pick the target: [1,10,10,10] / [5,4,4,4] -> 45 at 10,
     *     not 108 at 1. Three 4s outweigh one 5.
     *   - Majority by COUNT is the unweighted median of 2033 / 462 and fails here:
     *     [1,10,10,10] / [100,1,1,1] -> 27 at 1, not 900 at 10. Weigh each side by its cost.
     *   - long everywhere: one value × cost product reaches 10^12, and the starting total at the
     *     smallest value can reach ~10^17 before it falls. Only the answer is promised <= 2^53 - 1.
     *     The sweep's diff * (long) widens correctly; `(long) diff * ...` is the safer habit.
     *   - prefixCost[i] covers indices 0..i-1 only, i.e. the elements left BEHIND by the move to v[i].
     *   - Duplicates give diff = 0, so they need no special case.
     * Complexity: O(n log n) time for the sort, plus three O(n) passes. O(n) space for the pairs and
     *             the prefix array.
     * Alternatives:
     *   - Find the weighted median directly: walk the sorted pairs until 2 × (cost so far) >= total,
     *     then sum cost × |v - target| once. Same O(n log n), no prefix array.
     *   - Binary search the target on [min, max]: each step right changes the total by an amount
     *     that only grows, so compare f(mid) with f(mid + 1). O(n log M) time, O(1) space, no sort.
     * Template:
     *   sort pairs by value; total = sum(cost)
     *   cur = sum(cost[i] * (v[i] - v[0])); best = cur; before = 0
     *   for i in 1..n-1:
     *       before += cost[i-1]
     *       cur += (v[i] - v[i-1]) * (before - (total - before))
     *       best = min(best, cur)
     *   return best
     */
    public static void main(String[] args) {
        MinimumCostToMakeArrayEqual M = new MinimumCostToMakeArrayEqual();

        // Test 1: LeetCode example 1 — the cost of 14 at value 2 outweighs the other three (6), so the target is 2
        System.out.println("Test 1: " + M.minCost(new int[]{1, 3, 5, 2}, new int[]{2, 3, 1, 14}) + " (Expected: 8)");

        // Test 2: LeetCode example 2 — already equal: every diff is 0, nothing moves
        System.out.println("Test 2: " + M.minCost(new int[]{2, 2, 2, 2, 2}, new int[]{4, 2, 8, 1, 3}) + " (Expected: 0)");

        // Test 3: single element — the sweep loop never runs, the starting total 0 is the answer
        System.out.println("Test 3: " + M.minCost(new int[]{7}, new int[]{5}) + " (Expected: 0)");

        // Test 4: two elements — move the cheap one (9 x 1), never the expensive one (9 x 100)
        System.out.println("Test 4: " + M.minCost(new int[]{1, 10}, new int[]{1, 100}) + " (Expected: 9)");

        // Test 5: values and costs at the limits — 999999 x 10^6 is about 10^12, past int; the products must be long
        System.out.println("Test 5: " + M.minCost(new int[]{1, 1000000, 1000000}, new int[]{1000000, 1000000, 1}) + " (Expected: 999999000000)");

        // Test 6: n = 10^5, nums = 1..n, cost[i] = i % 1000 + 1 — trying every value with an O(n) total
        // is 10^10 operations; the sweep is O(n log n). The answer, about 1.25 x 10^12, also needs long
        int[] nums = new int[100000];
        int[] cost = new int[100000];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i + 1;
            cost[i] = i % 1000 + 1;
        }
        System.out.println("Test 6: " + M.minCost(nums, cost) + " (Expected: 1251250000000)");

        // Test 7: the highest single cost doesn't pick the target — three 4s at 10 outweigh the 5 at 1 (45, not 108)
        System.out.println("Test 7: " + M.minCost(new int[]{1, 10, 10, 10}, new int[]{5, 4, 4, 4}) + " (Expected: 45)");

        // Test 8: a majority by count fails — one cost-100 element at 1 outweighs three cost-1 elements at 10 (27, not 900)
        System.out.println("Test 8: " + M.minCost(new int[]{1, 10, 10, 10}, new int[]{100, 1, 1, 1}) + " (Expected: 27)");
    }
}
