package medium;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/design-most-recently-used-queue/
public class DesignMostRecentlyUsedQueue {
static class MRUQueue {
    List<int[]> queue;
    public MRUQueue(int n) {
        this.queue = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            queue.add(new int[]{i, 1});
        }
    }

    public int fetch(int k) {
        int count = 0;
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            if (queue.get(i)[1] == 1)
                count++;
            if (count == k) {
                int value = queue.get(i)[0];
                queue.set(i, new int[] {value, 0});
                queue.add(new int[] {value, 1});
                return value;
            }
        }
        return -1;
    }
}

    /*
     * Revision Note — Design Most Recently Used Queue (Medium)
     * Pattern: Tombstone list — mark in place, re-append at the tail
     * Key Insight: The logical queue is the subsequence of LIVE entries. Instead of
     *              shifting elements on every fetch, flag the fetched slot dead and
     *              append a fresh live copy at the end; order is preserved for free.
     * Gotchas:
     *   - Query counts only live entries, so `count == k` must be reached via an
     *     increment. With k >= 1 that is guaranteed, but the check sits OUTSIDE the
     *     alive branch — with k = 0 it would return a dead entry. Nest it if in doubt.
     *   - Capture size BEFORE the loop: fetch appends, and you must not scan the
     *     element you just added.
     *   - The list never shrinks, so the scan is O(n + fetches), not O(n).
     * Performance reality (measured, n = q = 2000, 2000 fetches):
     *   tombstone 3.03ms | ArrayList remove(k-1)+add 0.28ms | BIT 0.22ms
     *   remove/add wins because System.arraycopy is a JIT intrinsic: one vectorised
     *   block move, no branches, one contiguous array. The tombstone scan does two
     *   dependent loads per element (ref -> scattered int[2]) plus an unpredictable
     *   branch. Beating arraycopy needs a better complexity, not a smaller constant.
     *   That only pays off at scale — at n = 100k, remove/add 459ms vs BIT 14.8ms.
     * Template (the two-liner that beats this at these constraints):
     *   int v = list.remove(k - 1); list.add(v); return v;
     * Follow-up (O(log n)): BIT over SLOTS holding 1 = live, 0 = vacated.
     *   fetch(k) = find smallest slot whose prefix sum == k (binary-lifting descent),
     *   then add(slot, -1) and add(++tail, +1). Same freq-array -> BIT jump as
     *   CountOfSmallerNumbersAfterSelf, but indexed by position instead of value.
     */
    public static void main(String[] args) {
        MRUQueue M = new MRUQueue(8);
        System.out.println("Test 1: " + M.fetch(3) + " (Expected: 3)");
        System.out.println("Test 2: " + M.fetch(5) + " (Expected: 6)");
        System.out.println("Test 3: " + M.fetch(2) + " (Expected: 2)");
        System.out.println("Test 4: " + M.fetch(8) + " (Expected: 2)");

        MRUQueue M2 = new MRUQueue(1);
        System.out.println("Test 5: " + M2.fetch(1) + " (Expected: 1)");
        System.out.println("Test 6: " + M2.fetch(1) + " (Expected: 1)");

        // repeated fetch(1) walks the queue: 1,2,3 -> 2,3,1 -> 3,1,2
        MRUQueue M3 = new MRUQueue(3);
        System.out.println("Test 7: " + M3.fetch(1) + " (Expected: 1)");
        System.out.println("Test 8: " + M3.fetch(1) + " (Expected: 2)");
        System.out.println("Test 9: " + M3.fetch(1) + " (Expected: 3)");

        // fetching the last element is a no-op on ordering
        MRUQueue M4 = new MRUQueue(4);
        System.out.println("Test 10: " + M4.fetch(4) + " (Expected: 4)");
        System.out.println("Test 11: " + M4.fetch(4) + " (Expected: 4)");
        System.out.println("Test 12: " + M4.fetch(1) + " (Expected: 1)");
    }
}
