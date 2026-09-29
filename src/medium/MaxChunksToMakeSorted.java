package medium;

// https://leetcode.com/problems/max-chunks-to-make-sorted/
public class MaxChunksToMakeSorted {
    public int maxChunksToSorted(int[] arr) {
        int size = arr.length;
        int currMax = Integer.MIN_VALUE;
        int result = 0;
        for (int i = 0; i < size; i++) {
            currMax = Math.max(currMax, arr[i]);
            if (currMax == i)
                result++;
        }
        return result;
    }

    /*
     * Revision Note — Max Chunks To Make Sorted (Medium)
     *
     * Pattern: Running prefix max, cut where it equals the index
     *
     * Key Insight: arr is a PERMUTATION of [0, n-1], so the sorted result is exactly
     * 0,1,...,n-1. A chunk covering 0..i must therefore hold precisely the values {0..i}.
     * Checking that set membership directly is O(n) per index — but the prefix has i+1
     * DISTINCT values, so if its max equals i they are all <= i, and i+1 distinct values
     * all <= i can only be {0..i}. Pigeonhole makes the min check redundant.
     * So: cut after i whenever max(arr[0..i]) == i.
     *
     * Gotchas:
     * - This works ONLY because of the permutation guarantee. 768 (Max Chunks To Make
     *   Sorted II) drops it — arbitrary values with duplicates — and prefix-max == index
     *   is meaningless there. That one needs a monotonic stack of chunk maxima
     * - A chunk i..j needs min == i AND max == j in general; scanning cumulatively from 0
     *   is what reduces it to the max alone
     * - The last index always satisfies the condition (the full prefix is everything),
     *   so the answer is never 0 for a non-empty array
     *
     * Template:
     *   currMax = MIN; result = 0
     *   for i in 0..n-1:
     *     currMax = max(currMax, arr[i])
     *     if currMax == i: result++
     *   return result
     */
    public static void main(String[] args) {
        MaxChunksToMakeSorted M = new MaxChunksToMakeSorted();

        System.out.println("Test 1: " + M.maxChunksToSorted(new int[]{4, 3, 2, 1, 0}) + " (Expected: 1)"); // reverse sorted
        System.out.println("Test 2: " + M.maxChunksToSorted(new int[]{4, 0, 2, 3, 1}) + " (Expected: 1)"); // 4 blocks every cut
        System.out.println("Test 3: " + M.maxChunksToSorted(new int[]{1, 0, 2, 3, 4}) + " (Expected: 4)");
        System.out.println("Test 4: " + M.maxChunksToSorted(new int[]{0})             + " (Expected: 1)"); // single element
        System.out.println("Test 5: " + M.maxChunksToSorted(new int[]{0, 1, 2, 3, 4}) + " (Expected: 5)"); // already sorted
        System.out.println("Test 6: " + M.maxChunksToSorted(new int[]{2, 0, 1})       + " (Expected: 1)"); // 3-cycle, one chunk
        System.out.println("Test 7: " + M.maxChunksToSorted(new int[]{1, 2, 0, 3})    + " (Expected: 2)"); // [1,2,0] then [3]
    }
}
