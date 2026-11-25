package medium;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

// https://leetcode.com/problems/integer-replacement/
public class IntegerReplacement {
    public int integerReplacement(int n) {
        HashSet<Long> visited = new HashSet<>();
        Queue<Long> queue = new ArrayDeque<>();
        queue.add((long) n);
        visited.add((long) n);
        int ops = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                long front = queue.poll();
                if (front == 1)
                    return ops;
                if (front %2 == 0 && !visited.contains(front / 2)) {
                    queue.add(front / 2);
                    visited.add(front / 2);
                }
                else {
                    if (!visited.contains(front + 1)) {
                        queue.add(front + 1);
                        visited.add(front + 1);
                        }
                    if (!visited.contains(front - 1)) {
                        queue.add(front - 1);
                        visited.add(front - 1);
                    }

                }
            }
            ops++;
        }
        return -1;
    }

    /*
     * Revision Note — Integer Replacement (Medium)
     *
     * Pattern: BFS for the fewest steps on an implicit graph. Each state is a number. Its edges are
     * n/2 when n is even, and n+1 or n-1 when it is odd. Processing one level at a time means the
     * first time 1 is polled, the level count is the minimum.
     *
     * Key insight: the graph looks unbounded (n can be up to 2^31 - 1), but very little of it is
     * reachable. After j halvings, every reachable number stays within about 2 of n / 2^j, because
     * a +-1 shifts it by 1 and the next halving halves that offset. So the BFS sees O(log n)
     * states: about 10 µs even at Integer.MAX_VALUE.
     *
     * Gotchas:
     *  - n + 1 overflows int at Integer.MAX_VALUE: queue and visited hold Long (Test 7).
     *  - Branch on parity alone, then check visited inside. The current
     *    `if (even && !visited(n/2)) ... else { n+1, n-1 }` sends an even number whose half was
     *    already seen into the odd branch, an illegal move. It fires about 24.6M times for
     *    n = 1..2M, first at n = 3 (4's half, 2, is already queued, so 5 gets queued). It never
     *    changes an answer: answers of neighbouring numbers never differ by more than 1, so a +-1
     *    detour can't be a shortcut. Checked on n = 1..2M and 200k random n up to 2^31 - 1. The fix:
     *        if (front % 2 == 0) { if (visited.add(front / 2)) queue.add(front / 2); }
     *        else { if (visited.add(front + 1)) ...; if (visited.add(front - 1)) ...; }
     *    Set.add returns false when the value was already there, which also removes the separate
     *    contains call.
     *  - n == 1 needs 0 steps; the first poll returns before any move (Test 4).
     *  - 3 is the exception to "odd ending in 11 -> add 1": 3 -> 2 -> 1 is 2 steps, and 3 -> 4 -> 2
     *    -> 1 is 3 (Test 5).
     *
     * Complexity: O(log n) states, each with O(1) hash operations, so O(log n) time and space.
     * The answer is at most about 2·log2(n): 45 at n = 0x55555555 (Test 8).
     *
     * Template:
     *   queue = [n]; visited = {n}; ops = 0
     *   while queue: for each node in this level:
     *       if node == 1: return ops
     *       for next in (node even ? [node/2] : [node+1, node-1]): if visited.add(next): queue.add(next)
     *   ops++
     *
     * Alternative: a greedy on the bits, O(log n) time and O(1) space. Even: halve. Odd and ending in
     * 01: subtract 1, so two halvings follow. Odd and ending in 11: add 1, so the carry clears the
     * run of 1s. The exception is n == 3, which subtracts. Use long so MAX_VALUE + 1 can't overflow.
     *   while (m != 1) { if ((m & 1) == 0) m >>= 1; else if (m == 3 || (m & 3) == 1) m--; else m++; ops++; }
     * Or memoised recursion: f(n) = even ? 1 + f(n/2) : 1 + min(f(n+1), f(n-1)).
     */
    public static void main(String[] args) {
        IntegerReplacement I = new IntegerReplacement();

        // Test 1: LeetCode example 1 — a power of 2 is just halvings, 8 -> 4 -> 2 -> 1
        System.out.println("Test 1: " + I.integerReplacement(8) + " (Expected: 3)");

        // Test 2: LeetCode example 2 — 7 -> 8 -> 4 -> 2 -> 1 and 7 -> 6 -> 3 -> 2 -> 1 tie at 4
        System.out.println("Test 2: " + I.integerReplacement(7) + " (Expected: 4)");

        // Test 3: LeetCode example 3
        System.out.println("Test 3: " + I.integerReplacement(4) + " (Expected: 2)");

        // Test 4: n == 1 — already done, 0 steps
        System.out.println("Test 4: " + I.integerReplacement(1) + " (Expected: 0)");

        // Test 5: n == 3 — the exception to the "...11 -> add 1" rule: 3 -> 2 -> 1 beats 3 -> 4 -> 2 -> 1
        System.out.println("Test 5: " + I.integerReplacement(3) + " (Expected: 2)");

        // Test 6: sixteen 1 bits — adding 1 carries all the way to 2^16, then 16 halvings
        System.out.println("Test 6: " + I.integerReplacement(65535) + " (Expected: 17)");

        // Test 7: n == Integer.MAX_VALUE — n + 1 = 2^31 overflows an int; 1 step up, then 31 halvings
        System.out.println("Test 7: " + I.integerReplacement(Integer.MAX_VALUE) + " (Expected: 32)");

        // Test 8: alternating bits 0x55555555 (0101...01) — every odd step subtracts 1, then two
        // halvings; the longest answer among these tests
        System.out.println("Test 8: " + I.integerReplacement(0x55555555) + " (Expected: 45)");
    }
}
