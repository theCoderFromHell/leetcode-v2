package medium;

import java.util.*;

// https://leetcode.com/problems/the-k-strongest-values-in-an-array/
public class TheKStrongestValuesInAnArray {
    public int[] getStrongest(int[] arr, int k) {
        int size = arr.length;
        Arrays.sort(arr);
        int middle = arr[(size-1)/2];
        List<Integer> copy = new ArrayList<>();
        for (int i = 0; i < size; i++)
            copy.add(arr[i]);
        Collections.sort(copy, (a, b) -> {
            if (Math.abs(a - middle) == Math.abs(b - middle))
                return (b - a);
            return Math.abs(b - middle) - Math.abs(a - middle);
        });
        int[] result = new int[k];
        for (int i = 0; i < k; i++)
            result[i] = copy.get(i);
        return result;
    }

    /*
     * Revision Note — The k Strongest Values in an Array (Medium)
     * Pattern: top-k by a derived key — sort once to find the centre, then rank by
     *          (|a - m| descending, a descending) and take the first k.
     * Key Insight: strength only depends on the distance from m, so in the SORTED array the
     *              strongest values always sit at the two ends. Two pointers moving inward pick
     *              the k strongest in O(k) after the sort — no second sort, no boxing.
     * Gotchas:
     *   - The centre is arr[(n - 1) / 2] of the sorted array — the LOWER median for even n.
     *     [-7, 22, 17, 3] has m = 3, not 17; n / 2 picks the wrong centre and the wrong answer.
     *   - Equal distance -> the larger value is stronger: the comparator falls back to b - a,
     *     and in the two-pointer version the right end wins a tie (>=, not >).
     *   - Values are in [-10^5, 10^5], so distances and comparator differences stay below
     *     2 * 10^5 — subtraction in the comparator is overflow-safe here.
     *   - Any order is accepted, so the k values don't need to come out sorted by strength.
     * Complexity: O(n log n) time — the first sort, plus a second boxed sort of the copy.
     *             O(n) space for the List<Integer> copy. Arrays.sort mutates the caller's array.
     *             Two pointers drops the second sort and the copy: O(n log n) time, O(1) extra.
     * Template:
     *   sort(arr); m = arr[(n - 1) / 2]
     *   lo = 0, hi = n - 1
     *   repeat k times:
     *       if arr[hi] - m >= m - arr[lo]: take arr[hi--]
     *       else: take arr[lo++]
     */
    public static void main(String[] args) {
        TheKStrongestValuesInAnArray T = new TheKStrongestValuesInAnArray();

        // Test 1: LeetCode example 1 — 5 and 1 are both 2 from the centre 3, the larger ranks first
        System.out.println("Test 1: " + Arrays.toString(T.getStrongest(new int[]{1, 2, 3, 4, 5}, 2)) + " (Expected: [5, 1])");

        // Test 2: LeetCode example 2 — duplicates at the strongest distance
        System.out.println("Test 2: " + Arrays.toString(T.getStrongest(new int[]{1, 1, 3, 5, 5}, 2)) + " (Expected: [5, 5])");

        // Test 3: LeetCode example 3 — unsorted input, centre 7, a three-way tie at distance 1
        System.out.println("Test 3: " + Arrays.toString(T.getStrongest(new int[]{6, 7, 11, 7, 6, 8}, 5)) + " (Expected: [11, 8, 6, 6, 7])");

        // Test 4: even length — the centre is the LOWER median 3; n / 2 would pick 17 and return [-7, 3]
        System.out.println("Test 4: " + Arrays.toString(T.getStrongest(new int[]{-7, 22, 17, 3}, 2)) + " (Expected: [22, 17])");

        // Test 5: negative values — the strongest value sits at the LEFT end of the sorted array
        System.out.println("Test 5: " + Arrays.toString(T.getStrongest(new int[]{6, -3, 7, 2, 11}, 3)) + " (Expected: [-3, 11, 2])");

        // Test 6: single element — it is its own centre and the only answer
        System.out.println("Test 6: " + Arrays.toString(T.getStrongest(new int[]{5}, 1)) + " (Expected: [5])");
    }
}
