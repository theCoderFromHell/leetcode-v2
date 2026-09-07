package medium;

import java.util.PriorityQueue;

// https://leetcode.com/problems/maximum-total-sum-with-threshold-constraints/
public class MaximumTotalSumWithThresholdConstraints {
    public long maxSum(int[] nums, int[] threshold) {
        int size = nums.length;
        int step = 1;
        long result = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> {
            if (a[0] == b[0])
                return nums[b[1]] - nums[a[1]];
            return a[0] - b[0];
        });
        for (int i = 0; i < size; i++)
            pq.add(new int[]{threshold[i], i});
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            if (top[0] > step)
                continue;
            result += nums[top[1]];
            step++;
        }
        return result;
    }

    /*
     * Revision Note — Maximum Total Sum with Threshold Constraints (Medium)
     * Pattern: Greedy + PriorityQueue ordered by (threshold asc, nums desc)
     * Key Insight: Process candidates in threshold order. At each step only indices with
     *              threshold <= step are eligible; among ties pick the largest nums value.
     *              Since step only advances on a successful pick, once a polled element has
     *              threshold > step every remaining element does too — the process is over,
     *              which matches "if no such index exists, the process ends".
     * Gotchas:
     *   - result must be long, not int — the return type is long and the sum can exceed
     *     Integer.MAX_VALUE; an int accumulator overflows before the widening cast on return.
     *   - Comparator tie-break is nums[b[1]] - nums[a[1]] (descending value) so the most
     *     valuable of equally-eligible indices is consumed first.
     *   - continue (not break) is safe here: the remaining polls all fail the same check.
     *     Elements skipped this way are genuinely unreachable, they are not lost prematurely.
     *   - Comparator must return a difference, never a bare value.
     */
    public static void main(String[] args) {
        MaximumTotalSumWithThresholdConstraints M = new MaximumTotalSumWithThresholdConstraints();

        // Test 1: threshold gap stalls the process — only step 1 can be filled
        System.out.println("Test 1: " + M.maxSum(new int[]{10, 5, 8}, new int[]{1, 3, 3}) + " (Expected: 10)");

        // Test 2: all thresholds 1 — every element is eligible, take all
        System.out.println("Test 2: " + M.maxSum(new int[]{10, 5, 8}, new int[]{1, 1, 1}) + " (Expected: 23)");

        // Test 3: dense thresholds 1,1,3 — steps 1,2,3 all fill
        System.out.println("Test 3: " + M.maxSum(new int[]{10, 5, 8}, new int[]{1, 1, 3}) + " (Expected: 23)");

        // Test 4: single element eligible at step 1
        System.out.println("Test 4: " + M.maxSum(new int[]{7}, new int[]{1}) + " (Expected: 7)");

        // Test 5: single element needs step 2 — never reachable, process ends immediately
        System.out.println("Test 5: " + M.maxSum(new int[]{7}, new int[]{2}) + " (Expected: 0)");

        // Test 6: tie on threshold — larger value consumed first, both still fit
        System.out.println("Test 6: " + M.maxSum(new int[]{3, 9}, new int[]{1, 1}) + " (Expected: 12)");
    }
}
