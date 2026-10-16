package hard;

import java.util.ArrayDeque;
import java.util.Deque;

// https://leetcode.com/problems/max-chunks-to-make-sorted-ii/
public class MaxChunksToMakeSortedII {
    public int maxChunksToSorted(int[] arr) {
        int size = arr.length;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < size; i++) {
            if (!stack.isEmpty() && stack.peek() > arr[i]) {
                int top = stack.pop();
                while (!stack.isEmpty() && stack.peek() > arr[i])
                    stack.pop();
                stack.push(top);
            } else
                stack.push(arr[i]);
        }
        return stack.size();
    }

    /*
     * Revision Note — Max Chunks To Make Sorted II (Hard)
     *
     * Pattern: Monotonic stack of chunk maxima
     *
     * Key Insight: The stack holds one entry per chunk so far — its MAXIMUM — kept
     * non-decreasing bottom to top. A new value >= the top starts its own chunk (push it).
     * A value < the top cannot be separated from those chunks, so merge them: save the top
     * (the merged chunk's max), pop everything still greater, push the saved max back.
     * The answer is the stack size.
     *
     * Gotchas:
     * - 769's `currMax == i` DOES NOT transfer. That compared a value to an index and only
     *   worked because arr was a permutation of [0, n-1]. Here arr[i] reaches 1e8 while i is
     *   small, so the comparison is meaningless. Restate the rule purely in values:
     *   cut after i when max(arr[0..i]) <= min(arr[i+1..])
     * - Strict > in both conditions. Using >= would merge equal values and wrongly report
     *   [1,1,1] as 1 instead of 3 — duplicates are separable
     * - Push the SAVED top back, not arr[i]. The merged chunk's max is the largest thing
     *   absorbed, which is the first popped value, not the incoming one
     * - No overflow risk: comparisons only, values <= 1e8
     *
     * Template:
     *   for x in arr:
     *     if stack nonempty and stack.peek() > x:
     *       top = stack.pop()
     *       while stack nonempty and stack.peek() > x: stack.pop()
     *       stack.push(top)
     *     else: stack.push(x)
     *   return stack.size()
     *
     * V2 — equivalent O(n), stating the cut rule literally:
     *   max(arr[0..i]) <= min(arr[i+1..])
     * prefixMax runs in the SAME direction as the sweep, so a scalar suffices. suffixMin runs
     * the OPPOSITE way — it needs indices not yet reached — so it is precomputed backward into
     * an array. Sentinel suffixMin[n] = MAX_VALUE encodes "min of an empty suffix is +infinity",
     * which makes the last index always cut and removes the need for a special case.
     *   [1,5,2,3,4,6]  suffixMin = [1,2,2,3,4,6,MAX]
     *   prefixMax pins at 5 for i=1..3 (the 5 belongs at index 4, trapping everything between),
     *   so cuts land only at i=0, 4, 5  ->  [1] [5,2,3,4] [6]  =  3
     * Uses <= for the same reason the stack uses strict >: equal values are separable.
     * Same O(n) time; O(n) extra space for the suffix array vs the stack's worst case.
     */
    public int maxChunksToSortedV2(int[] arr) {
        int n = arr.length;
        int[] suffixMin = new int[n + 1];
        suffixMin[n] = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 0; i--)
            suffixMin[i] = Math.min(suffixMin[i + 1], arr[i]);
        int chunks = 0, prefixMax = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            prefixMax = Math.max(prefixMax, arr[i]);
            if (prefixMax <= suffixMin[i + 1]) chunks++;
        }
        return chunks;
    }

    public static void main(String[] args) {
        MaxChunksToMakeSortedII M = new MaxChunksToMakeSortedII();

        System.out.println("Test 1: " + M.maxChunksToSorted(new int[]{5, 4, 3, 2, 1})    + " (Expected: 1)"); // reverse sorted
        System.out.println("Test 2: " + M.maxChunksToSorted(new int[]{2, 1, 3, 4, 4})    + " (Expected: 4)"); // duplicates split
        System.out.println("Test 3: " + M.maxChunksToSorted(new int[]{1})                + " (Expected: 1)"); // single element
        System.out.println("Test 4: " + M.maxChunksToSorted(new int[]{1, 1, 1})          + " (Expected: 3)"); // all equal, each its own chunk
        System.out.println("Test 5: " + M.maxChunksToSorted(new int[]{0, 1, 2, 3})       + " (Expected: 4)"); // already sorted
        System.out.println("Test 6: " + M.maxChunksToSorted(new int[]{1, 0, 1, 0})       + " (Expected: 1)"); // interleaved duplicates
        System.out.println("Test 7: " + M.maxChunksToSorted(new int[]{100000000, 0})     + " (Expected: 1)"); // max value range
    }
}
