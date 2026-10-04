package medium;

import java.util.Random;

// https://leetcode.com/problems/longest-mountain-in-array/
public class LongestMountainInArray {
    public int longestMountain(int[] arr) {
        int size = arr.length;
        int[] left = new int[size];
        int[] right = new int[size];
        left[0] = 0;
        for (int i = 1; i < size; i++) {
            if (arr[i-1] < arr[i])
                left[i] = left[i-1] + 1;
            else
                left[i] = 0;
        }
        right[size-1] = 0;
        for (int i = size-2; i >= 0; i--) {
            if (arr[i] > arr[i+1])
                right[i] = right[i+1] + 1;
            else
                right[i] = 0;
        }
        int result = 0;
        for (int i = 0; i < size; i++) {
            if (left[i] > 0 && right[i] > 0)
                result = Math.max(result, left[i] + 1 + right[i]);
        }
        return result;
    }

    /*
     * Revision Note — Longest Mountain in Array (Medium)
     *
     * Pattern: Prefix/suffix STREAK arrays, combined at each peak
     *
     * Key Insight: Same two-sided shape as 238, except the accumulation is a run length
     * rather than a running total. left[i] = strictly ascending STEPS ending at i;
     * right[i] = strictly descending STEPS starting at i. Index i is a peak exactly when
     * both are > 0, and the mountain spans left[i] + 1 + right[i].
     *
     * Gotchas:
     * - Counting STEPS, not elements, is what makes the arithmetic clean: left[i] elements
     *   before the peak, the peak itself (+1), right[i] after. Count elements instead and
     *   the peak gets double-counted
     * - BOTH sides must be strictly positive. left[i] == 0 means no ascent reaches i (so a
     *   pure descent like [3,2,1] scores 0); right[i] == 0 means no descent leaves it (so
     *   [1,2,3] scores 0). Requiring both is what enforces length >= 3 and keeps the peak
     *   off the endpoints — no explicit bounds check needed
     * - Strict < and > everywhere. A plateau resets the streak to 0, which is why [2,2,2]
     *   and [1,2,2,1] both yield 0
     * - Reset to 0 on a break, do not carry the previous value forward
     *
     * Template:
     *   left[0] = 0
     *   for i in 1..n-1:   left[i]  = arr[i-1] < arr[i] ? left[i-1] + 1  : 0
     *   right[n-1] = 0
     *   for i in n-2..0:   right[i] = arr[i] > arr[i+1] ? right[i+1] + 1 : 0
     *   for each i: if left[i] > 0 and right[i] > 0:
     *                 best = max(best, left[i] + 1 + right[i])
     *
     * O(1)-space follow-up: a single pass with two counters (up, down), resetting both when
     * the direction flips from down back to up. Same answer, no arrays.
     */
    // O(n^2) reference, used only to cross-check
    private static int bruteForce(int[] arr) {
        int n = arr.length, best = 0;
        for (int i = 1; i < n - 1; i++) {
            int l = i, r = i;
            while (l > 0 && arr[l - 1] < arr[l]) l--;
            while (r < n - 1 && arr[r] > arr[r + 1]) r++;
            if (l < i && r > i) best = Math.max(best, r - l + 1);
        }
        return best;
    }

    public static void main(String[] args) {
        LongestMountainInArray L = new LongestMountainInArray();

        System.out.println("Test 1: " + L.longestMountain(new int[]{2, 1, 4, 7, 3, 2, 5}) + " (Expected: 5)"); // [1,4,7,3,2]
        System.out.println("Test 2: " + L.longestMountain(new int[]{2, 2, 2})             + " (Expected: 0)"); // plateau, never strict
        System.out.println("Test 3: " + L.longestMountain(new int[]{1})                   + " (Expected: 0)"); // single element
        System.out.println("Test 4: " + L.longestMountain(new int[]{1, 2})                + " (Expected: 0)"); // length 2, too short
        System.out.println("Test 5: " + L.longestMountain(new int[]{1, 2, 3})             + " (Expected: 0)"); // ascent only, no peak
        System.out.println("Test 6: " + L.longestMountain(new int[]{3, 2, 1})             + " (Expected: 0)"); // descent only
        System.out.println("Test 7: " + L.longestMountain(new int[]{1, 2, 2, 1})          + " (Expected: 0)"); // plateau at the peak
        System.out.println("Test 8: " + L.longestMountain(new int[]{0, 1, 0})             + " (Expected: 3)"); // minimal mountain
        System.out.println("Test 9: " + L.longestMountain(new int[]{1, 3, 1, 4, 6, 2, 1}) + " (Expected: 5)"); // two mountains, take longer

        // cross-check against brute force, tiny value range so plateaus are common
        boolean agree = true;
        Random rnd = new Random(23);
        for (int t = 0; t < 500; t++) {
            int[] r = new int[1 + rnd.nextInt(15)];
            for (int i = 0; i < r.length; i++) r[i] = rnd.nextInt(4);
            if (L.longestMountain(r) != bruteForce(r)) agree = false;
        }
        System.out.println("Test 10: " + agree + " (Expected: true)  — matches brute force, 500 random");
    }
}
