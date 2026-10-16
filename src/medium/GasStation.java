package medium;

// https://leetcode.com/problems/gas-station/
public class GasStation {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        if(gas == null || cost == null || gas.length == 0 || cost.length == 0)
            return -1;
        int N = gas.length;
        if (N ==1)
            return gas[0] >= cost[0] ? 0 : -1;
        int[] diff = new int[N];
        int total = 0;
        for (int i = 0; i < N; i++) {
            diff[i] = gas[i] - cost[i];
            total += diff[i];
        }
        if (total < 0)
            return -1;
        int start = 0;
        int current = 1;
        int currentSum = diff[0];
        while (current < N) {
            while (start <= current && currentSum < 0) {
                currentSum -= diff[start];
                start++;
            }
            currentSum += diff[current];
            current++;
        }
        if (current == N && currentSum >= 0)
            return start;
        return -1;
    }

    /*
     * Revision Note - Gas Station (Medium)
     *
     * Pattern: Reduce to adjacent deltas, then a shrinking window over the delta array
     *
     * Key Insight: Two independent facts do all the work.
     *   (1) FEASIBILITY is global: a solution exists iff sum(gas) >= sum(cost). Nothing about
     *       the order matters, so one pass over diff[] answers "is it -1?" outright.
     *   (2) Given feasibility, the ANSWER is forced: the only possible start is the index just
     *       after the point where the running prefix of diff[] is at its minimum. Any station
     *       inside a prefix that dips negative cannot be the start, because you would have run
     *       dry before leaving that stretch.
     *
     * This implementation finds (2) by shrinking from the left rather than resetting: whenever
     * the window sum goes negative, drop diff[start] and advance start. Equivalent to the
     * textbook "sum < 0 -> start = i+1, sum = 0" reset, because dropping a negative-sum prefix
     * one element at a time lands on the same index.
     *
     * Gotchas:
     * - CHECK total < 0 FIRST and return -1. Without it the window logic has no guarantee to
     *   lean on, and the final `currentSum >= 0` test is doing feasibility and position at once
     * - The answer is an INDEX, not a sum. Easy to return currentSum or total by reflex
     * - Only ONE start can ever work when the total is non-negative, so there is no "best" to
     *   track - the first index that survives IS the answer
     * - Circular array, but NO modular arithmetic needed. The global-feasibility check is what
     *   removes the wraparound: you never have to simulate past N-1
     * - diff[] can be all zeros (gas == cost everywhere). Then total == 0, every start works,
     *   and 0 is returned - correct, since the problem says the answer is unique when it exists
     * - N == 1 is handled separately here, though the general path would also cover it
     *
     * Complexity: O(n) time - `current` advances n times and `start` only ever moves forward, so
     * the inner while is amortised O(n) overall. O(n) space for diff[], reducible to O(1) by
     * computing gas[i]-cost[i] inline.
     *
     * Template:
     *   total = sum(gas[i] - cost[i]);  if total < 0: return -1
     *   start = 0; sum = 0
     *   for i in 0..n-1:
     *     sum += gas[i] - cost[i]
     *     if sum < 0: start = i + 1; sum = 0      // the reset form
     *   return start
     *
     * Shares the adjacent-delta decomposition with 122, but the question is "where does the
     * accumulation start" rather than "what does it total", which is what forces the reset.
     */
    public static void main(String[] args) {
        GasStation G = new GasStation();

        System.out.println("Test 1: " + G.canCompleteCircuit(new int[]{1,2,3,4,5}, new int[]{3,4,5,1,2}) + " (Expected: 3)");
        System.out.println("Test 2: " + G.canCompleteCircuit(new int[]{2,3,4}, new int[]{3,4,3}) + " (Expected: -1)");
        System.out.println("Test 3: " + G.canCompleteCircuit(new int[]{5}, new int[]{4}) + " (Expected: 0)");          // n=1, feasible
        System.out.println("Test 4: " + G.canCompleteCircuit(new int[]{3}, new int[]{4}) + " (Expected: -1)");         // n=1, infeasible
        System.out.println("Test 5: " + G.canCompleteCircuit(new int[]{0,0,0}, new int[]{0,0,0}) + " (Expected: 0)");  // all deltas zero
        System.out.println("Test 6: " + G.canCompleteCircuit(new int[]{3,1,1}, new int[]{1,2,2}) + " (Expected: 0)");  // start is index 0
        System.out.println("Test 7: " + G.canCompleteCircuit(new int[]{1,1,3}, new int[]{2,2,1}) + " (Expected: 2)");  // start is the LAST index
        System.out.println("Test 8: " + G.canCompleteCircuit(new int[]{2,0,0}, new int[]{0,1,1}) + " (Expected: 0)");  // total exactly 0
        System.out.println("Test 9: " + G.canCompleteCircuit(new int[]{1,2}, new int[]{2,1}) + " (Expected: 1)");      // n=2
    }
}
