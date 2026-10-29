package medium;

import java.util.Arrays;
import java.util.HashMap;

// https://leetcode.com/problems/task-scheduler-ii/
public class TaskSchedulerII {
    public long taskSchedulerII(int[] tasks, int space) {
        int size = tasks.length;
        int index = 0;
        long day = 0;
        HashMap<Integer,Long> nextAllowed = new HashMap<>();
        while (index < size) {
            day++;
            int task = tasks[index];
            long allowedDay = nextAllowed.getOrDefault(task, 1L);
            if (allowedDay > day)
                day = allowedDay;
            nextAllowed.put(task, day + space + 1);
            index++;
        }
        return day;
    }

    /*
     * Revision Note — Task Scheduler II (Medium)
     *
     * Pattern: Event-jumping simulation with a hash map of "next allowed day" per task type.
     *
     * Key insight: the order is fixed, so there's nothing to choose. Each task runs on
     * max(previous day + 1, nextAllowed[type]), and the breaks are just the gap. One loop
     * iteration per TASK, never per calendar day.
     *
     * Gotchas (both from this session):
     *  - Stepping day by day (day++ until allowed) is O(answer), not O(n). The answer can be
     *    ~n · (space + 1) ≈ 1e10, so it is TLE even when it fits an int (1e9 steps took 2.4s).
     *  - The answer reaches 1 + (n - 1)(space + 1) = 1e10 at n = space = 1e5, so day AND the map
     *    values must be long. An int day wrapped to -512167296 on Test 6.
     *  - Gap rule: a type done on day d can run again on day d + space + 1 (exactly `space` days
     *    in between), hence the +1 and the `allowedDay > day` check.
     *  - Task types go up to 1e9, so they're HashMap keys, not array indices.
     *
     * Complexity: O(n) time (one map lookup and put per task), O(distinct types) space.
     *
     * Template:
     *   day = 0
     *   for t in tasks:
     *       day = max(day + 1, next.getOrDefault(t, 0))
     *       next[t] = day + space + 1
     *   return day
     *
     * Contrast with 621 Task Scheduler: there you may REORDER, so it becomes a counting / greedy
     * problem on the most frequent type. Here the order is fixed and it's pure simulation.
     */
    public static void main(String[] args) {
        TaskSchedulerII T = new TaskSchedulerII();

        // Test 1: LeetCode example 1 — breaks land in two separate places
        System.out.println("Test 1: " + T.taskSchedulerII(new int[]{1, 2, 1, 2, 3, 1}, 3) + " (Expected: 9)");

        // Test 2: LeetCode example 2 — back-to-back same type forces the breaks
        System.out.println("Test 2: " + T.taskSchedulerII(new int[]{5, 8, 8, 5}, 2) + " (Expected: 6)");

        // Test 3: single task — day 1, no breaks
        System.out.println("Test 3: " + T.taskSchedulerII(new int[]{7}, 1) + " (Expected: 1)");

        // Test 4: one type throughout — every task waits space days: 1 + 3 · 5
        System.out.println("Test 4: " + T.taskSchedulerII(new int[]{3, 3, 3, 3}, 4) + " (Expected: 16)");

        // Test 5: task type at the 1e9 limit — rules out an int[] indexed by type
        System.out.println("Test 5: " + T.taskSchedulerII(new int[]{1000000000, 1, 1000000000}, 2) + " (Expected: 4)");

        // Test 6: max n, one type, space == n — answer 1e10 overflows an int, and a day-by-day
        // loop needs 1e10 steps (both bugs from this session)
        int[] big = new int[100000];
        Arrays.fill(big, 1);
        System.out.println("Test 6: " + T.taskSchedulerII(big, 100000) + " (Expected: 10000000000)");

        // Test 7: two jumps in a row — the second type's wait starts from the day the first jump landed
        System.out.println("Test 7: " + T.taskSchedulerII(new int[]{1, 1, 2, 2}, 3) + " (Expected: 10)");

        // Test 8: the gap is exactly `space` — one other task in between is enough, no break
        System.out.println("Test 8: " + T.taskSchedulerII(new int[]{4, 9, 4}, 1) + " (Expected: 3)");
    }
}
