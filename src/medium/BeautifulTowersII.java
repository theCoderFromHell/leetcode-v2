package medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

// https://leetcode.com/problems/beautiful-towers-ii/
public class BeautifulTowersII {
    public long maximumSumOfHeights(List<Integer> maxHeights) {
        int size = maxHeights.size();
        long[] left = new long[size];
        long[] right = new long[size];
        Stack<Integer> stack = new Stack<>();
        left[0] = maxHeights.get(0);
        stack.push(0);
        for (int i = 0; i < size; i++) {
            while (!stack.isEmpty() && maxHeights.get(stack.peek()) > maxHeights.get(i))
                stack.pop();
            if (stack.isEmpty())
                left[i] = maxHeights.get(i) * (long)(i + 1);
            else {
                int previous = stack.peek();
                left[i] = left[previous] + maxHeights.get(i) * (long)(i - previous);
            }
            stack.push(i);
        }
        stack.clear();
        for (int i = size-1; i >= 0; i--) {
            while (!stack.isEmpty() && maxHeights.get(stack.peek()) > maxHeights.get(i))
                stack.pop();
            if (stack.isEmpty())
                right[i] = maxHeights.get(i) * (long)(size - i);
            else {
                int next = stack.peek();
                right[i] = right[next] + maxHeights.get(i) * (long)(next - i);
            }
            stack.push(i);
        }
        long maxSum =Integer.MIN_VALUE;
        for (int i = 0; i < size; i++)
            maxSum = Math.max(maxSum, left[i] + right[i] - maxHeights.get(i));
        return maxSum;
    }

    /*
     * Revision Note - Beautiful Towers II (Medium)
     *
     * Pattern: monotonic stack, two passes (prefix + suffix "running-min sums"), then combine.
     * Same family as 84 Largest Rectangle in Histogram and 907 Sum of Subarray Minimums.
     *
     * Key Insight: for a fixed peak p, every tower walking outward is min(maxHeights[j], the
     * neighbour nearer p), so the peak stays at full height. Trying every peak is O(n^2).
     * Instead, left[i] = best sum of 0..i when i is the peak of the left slope. Everything
     * between i and the nearest j < i with maxHeights[j] <= maxHeights[i] is flattened to
     * maxHeights[i], and everything from j leftwards is exactly left[j]:
     *     left[i]  = left[j]  + h[i] * (i - j)        (no j: h[i] * (i + 1))
     *     right[i] = right[j] + h[i] * (j - i)        (no j: h[i] * (n - i))
     *     answer   = max over i of left[i] + right[i] - h[i]      (peak counted twice)
     * The nearest smaller-or-equal j is the stack top after popping everything strictly taller.
     *
     * Gotchas:
     * - Reset the stack between the two passes. Leftovers from the left pass make the right
     *   pass read its own index (right[n-1] = 0, so [7] returns 0) and indices on the wrong side.
     * - Overflow happens in the multiply, not only in storage: Integer * int is int arithmetic
     *   and wraps before it reaches the long[]. Widen one operand: h * (long) (i - j).
     *   1e9 * 1e5 = 1e14.
     * - Comparing Integer with > is safe (unboxes); == would compare references above 127.
     * - Amortised O(n): one index can pop O(n) items, but every index is pushed once and popped
     *   at most once, so a whole pass is at most 2n stack operations.
     *
     * Complexity: O(n) time (two stack passes + combine), O(n) space (left, right, stack).
     *
     * Template:
     *   for i in 0..n-1:
     *       while stack and h[top] > h[i]: pop
     *       left[i] = stack empty ? h[i]*(i+1) : left[top] + h[i]*(i-top)
     *       push i
     *   clear stack; mirror for right[] from n-1 down to 0
     *   return max(left[i] + right[i] - h[i])
     */

    /*
     * O(n^2) reference: tries every peak and walks outward with a running min, straight from the
     * problem statement. No stack and no recurrence, so a mistake in either shows as a mismatch.
     */
    private static long bruteForce(List<Integer> m) {
        int n = m.size();
        long best = 0;
        for (int p = 0; p < n; p++) {
            long sum = m.get(p), cur = m.get(p);
            for (int j = p - 1; j >= 0; j--) { cur = Math.min(cur, m.get(j)); sum += cur; }
            cur = m.get(p);
            for (int j = p + 1; j < n; j++) { cur = Math.min(cur, m.get(j)); sum += cur; }
            best = Math.max(best, sum);
        }
        return best;
    }

    public static void main(String[] args) {
        BeautifulTowersII B = new BeautifulTowersII();

        System.out.println("Test 1: " + B.maximumSumOfHeights(List.of(5,3,4,1,1)) + " (Expected: 13)");
        System.out.println("Test 2: " + B.maximumSumOfHeights(List.of(6,5,3,9,2,7)) + " (Expected: 22)");
        System.out.println("Test 3: " + B.maximumSumOfHeights(List.of(3,2,5,5,2,3)) + " (Expected: 18)");
        System.out.println("Test 4: " + B.maximumSumOfHeights(List.of(7)) + " (Expected: 7)");                  // single tower: was 0 with the shared stack
        System.out.println("Test 5: " + B.maximumSumOfHeights(List.of(1,2,3)) + " (Expected: 6)");              // peak at the right edge, no flattening
        System.out.println("Test 6: " + B.maximumSumOfHeights(List.of(3,2,1)) + " (Expected: 6)");              // peak at the left edge
        System.out.println("Test 7: " + B.maximumSumOfHeights(List.of(2,2,2,2)) + " (Expected: 8)");            // plateau: equal heights never pop
        System.out.println("Test 8: " + B.maximumSumOfHeights(List.of(2,6,1,7,5,9,3)) + " (Expected: 25)");     // peak at 9, the 1 caps the far left

        // cross-check against the O(n^2) reference
        boolean agree = true;
        String firstBad = "";
        Random rnd = new Random(2866);
        for (int t = 0; t < 1000; t++) {
            int n = 1 + rnd.nextInt(12);
            List<Integer> m = new ArrayList<>();
            for (int i = 0; i < n; i++) m.add(1 + rnd.nextInt(6));      // small range -> frequent ties
            long got = B.maximumSumOfHeights(m), want = bruteForce(m);
            if (got != want) {
                agree = false;
                if (firstBad.isEmpty()) firstBad = m + " got " + got + " want " + want;
            }
        }
        System.out.println("Test 9: " + agree + " (Expected: true)  - matches O(n^2) reference, 1000 random"
                + (agree ? "" : ", first mismatch: " + firstBad));

        // n = 1e5, every height 1e9: sum is 1e14, overflows int in the multiply if not widened
        List<Integer> big = new ArrayList<>(Collections.nCopies(100000, 1_000_000_000));
        long t0 = System.nanoTime();
        long got = B.maximumSumOfHeights(big);
        long ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("Test 10: " + got + " (Expected: 100000000000000)  - n=1e5, all 1e9, " + ms + "ms");

        // n = 1e5 strictly increasing then one 1 at the end: the last index pops the whole stack
        List<Integer> deep = new ArrayList<>();
        for (int i = 1; i < 100000; i++) deep.add(i);
        deep.add(1);
        t0 = System.nanoTime();
        got = B.maximumSumOfHeights(deep);
        ms = (System.nanoTime() - t0) / 1_000_000;
        System.out.println("Test 11: " + got + " (Expected: 4999950001)  - n=1e5, one O(n) pop, still amortised O(n), " + ms + "ms");
    }
}
