package medium;

import java.util.Arrays;

// https://leetcode.com/problems/maximum-number-of-weeks-for-which-you-can-work/
public class MaximumNumberOfWeeksForWhichYouCanWork {
    public long numberOfWeeks(int[] milestones) {
        int size = milestones.length;
        long totalSum = 0L;
        int highest = 0;
        for (int i = 0; i < size; i++) {
            highest = Math.max(highest, milestones[i]);
            totalSum += milestones[i];
        }
        return (highest > totalSum - highest) ? 1 + 2 * (totalSum - highest) : totalSum;
    }

    /*
     * Revision Note — Maximum Number of Weeks for Which You Can Work (Medium)
     *
     * Pattern: Counting argument on the dominant element (the "frame" idea from 621 Task Scheduler,
     * with a cooldown of 1 and no idling allowed).
     *
     * Key insight: only the largest project can get you stuck. The other projects supply rest
     * separators, so the largest fits at most rest + 1 times (L r L r ... r L):
     *  - largest > rest → you stop with leftovers after 2·rest + 1 weeks.
     *  - otherwise      → every milestone gets done: total weeks.
     *
     * Gotchas (from this session):
     *  - Heap simulation (pick the biggest project that isn't the last one) gives the right answer
     *    but loops once per WEEK. The answer reaches 1e5 · 1e9 = 1e14 → TLE. [1e9, 1e9] alone took 10s.
     *    Second time in two days (Task Scheduler II stepped per day): when the answer can be huge,
     *    iterations must scale with n, not with the answer.
     *  - The condition is strict: largest > rest. With largest == rest ([3, 3]) the separators are
     *    enough, and largest >= rest would return 7 instead of 6.
     *  - At largest == rest + 1 both branches give the same answer (2·rest + 1 == total), so the
     *    boundary is safe either way.
     *  - total reaches 1e14 → long.
     *
     * Complexity: O(n) time (one pass for max and sum), O(1) space.
     *
     * Template:
     *   total = sum(a); largest = max(a); rest = total - largest
     *   return largest > rest ? 2 * rest + 1 : total
     *
     * Alternative: the max-heap greedy (always take the largest project that isn't the last one)
     * is correct but O(W log n) for W weeks. Use it only when W is small, or when you must output
     * the actual schedule (e.g. 767 Reorganize String).
     */
    public static void main(String[] args) {
        MaximumNumberOfWeeksForWhichYouCanWork M = new MaximumNumberOfWeeksForWhichYouCanWork();

        // Test 1: LeetCode example 1 — largest (3) == rest (3), so everything gets done
        System.out.println("Test 1: " + M.numberOfWeeks(new int[]{1, 2, 3}) + " (Expected: 6)");

        // Test 2: LeetCode example 2 — largest (5) > rest (3), capped at 2·3 + 1
        System.out.println("Test 2: " + M.numberOfWeeks(new int[]{5, 2, 1}) + " (Expected: 7)");

        // Test 3: single project — rest = 0, so only one milestone fits
        System.out.println("Test 3: " + M.numberOfWeeks(new int[]{5}) + " (Expected: 1)");

        // Test 4: largest == rest — the condition must be strict (>= gives 7, this session's bug)
        System.out.println("Test 4: " + M.numberOfWeeks(new int[]{3, 3}) + " (Expected: 6)");

        // Test 5: one project far larger than the rest — 8 of its milestones are left over
        System.out.println("Test 5: " + M.numberOfWeeks(new int[]{10, 1, 1}) + " (Expected: 5)");

        // Test 6: largest == rest + 1 — both branches agree (2·3 + 1 == total)
        System.out.println("Test 6: " + M.numberOfWeeks(new int[]{4, 3}) + " (Expected: 7)");

        // Test 7: max n, every value 1e9 — total 1e14 overflows an int, and a per-week simulation
        // would need 1e14 steps
        int[] big = new int[100000];
        Arrays.fill(big, 1000000000);
        System.out.println("Test 7: " + M.numberOfWeeks(big) + " (Expected: 100000000000000)");

        // Test 8: two projects at the 1e9 limit — n is tiny, but the answer (2e9) is past int max,
        // and the heap simulation took 10s here
        System.out.println("Test 8: " + M.numberOfWeeks(new int[]{1000000000, 1000000000}) + " (Expected: 2000000000)");
    }
}
