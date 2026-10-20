package medium;

import java.util.Arrays;

// https://leetcode.com/problems/heaters/
public class Heaters {
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(heaters);
        int size = houses.length;
        int result = 0;
        for (int i = 0; i < size; i++)
            result = Math.max(result, minRadiusForHouse(houses[i], heaters));
        return result;
    }

    private int minRadiusForHouse(int house, int[] heaters) {
        int start = 0, end = heaters.length - 1;
        if (house <= heaters[start])
            return heaters[start] - house;
        if (house >= heaters[end])
            return house - heaters[end];
        int mid;
        while (start < end) {
            mid = start + (end - start) / 2;
            if (house == heaters[mid])
                return 0;
            if (house < heaters[mid]) {
                if (heaters[mid-1] <= house)
                    return Math.min(house - heaters[mid-1], heaters[mid] - house);
                else
                    end = mid - 1;
            } else if (house > heaters[mid]) {
                if (heaters[mid+1] >= house)
                    return Math.min(heaters[mid+1] - house, house - heaters[mid]);
                else
                    start = mid + 1;
            }
        }
        return -1;
    }

    /*
     * Revision Note — Heaters (Medium)
     * Pattern: nearest neighbour in a sorted array by binary search, then minimax —
     *          answer = max over houses of (distance to that house's nearest heater).
     * Key Insight: one shared radius must satisfy the WORST house, and each house only cares
     *              about its nearest heater, which is always one of the two heaters bracketing
     *              its insertion point (658's two-neighbour check). So the radius is computed
     *              directly — no need to binary-search on it.
     * Gotchas:
     *   - Sort heaters first. The input isn't sorted, and with [4, 1] unsorted the edge check
     *     sees house 1 <= heaters[0] = 4 and returns 3 instead of 0.
     *   - A house left of every heater or right of every heater has only ONE neighbour; the
     *     two early returns handle it, and they also cover a single heater.
     *   - The bracket search keeps heaters[start] < house < heaters[end], so mid - 1 and mid + 1
     *     never leave the array, and the loop always returns — `return -1` is unreachable.
     *     A plain lower bound (first heater >= house) is shorter and harder to get wrong.
     *   - First idea was binary search on the radius R: valid, because coverage is monotone in R,
     *     and checkable in O(n + m) with both arrays sorted and a heater pointer that only
     *     moves forward. It just costs an extra log(10^9) factor over the direct answer.
     *   - Two-pointer variant: advance while |heaters[j + 1] - h| <= |heaters[j] - h|. It must
     *     be <=, not <, or duplicate heaters [1, 1, 7] stall j on the first 1.
     *   - Distances stay below 10^9, so int arithmetic is safe.
     * Complexity: O(m log m + n log m) time — sort heaters, then one O(log m) search per house.
     *             O(log m) space for the sort; note that Arrays.sort mutates the caller's array.
     * Template:
     *   sort(heaters)
     *   for h in houses:
     *       i = lowerBound(heaters, h)                     // first heater >= h
     *       right = i < m ? heaters[i] - h : INF
     *       left  = i > 0 ? h - heaters[i - 1] : INF
     *       best = max(best, min(left, right))
     */
    public static void main(String[] args) {
        Heaters H = new Heaters();

        // Test 1: LeetCode example 1 — one heater in the middle of the houses
        System.out.println("Test 1: " + H.findRadius(new int[]{1, 2, 3}, new int[]{2}) + " (Expected: 1)");

        // Test 2: LeetCode example 2 — heaters at both ends, the middle houses decide
        System.out.println("Test 2: " + H.findRadius(new int[]{1, 2, 3, 4}, new int[]{1, 4}) + " (Expected: 1)");

        // Test 3: LeetCode example 3 — the farther house (5, three away) sets the radius
        System.out.println("Test 3: " + H.findRadius(new int[]{1, 5}, new int[]{2}) + " (Expected: 3)");

        // Test 4: unsorted heaters — without the sort, house 1 hits the edge check against 4 and returns 3
        System.out.println("Test 4: " + H.findRadius(new int[]{1, 2, 3, 4}, new int[]{4, 1}) + " (Expected: 1)");

        // Test 5: houses on BOTH sides of a single heater — both early returns, the right one wins
        System.out.println("Test 5: " + H.findRadius(new int[]{1, 10}, new int[]{5}) + " (Expected: 5)");

        // Test 6: both search branches, and the nearer neighbour differs — 3 is nearer its right
        // heater (4), 12 is nearer its left heater (10)
        System.out.println("Test 6: " + H.findRadius(new int[]{3, 12}, new int[]{1, 4, 10, 20}) + " (Expected: 2)");

        // Test 7: every house sits exactly on a heater — the house == heaters[mid] exit
        System.out.println("Test 7: " + H.findRadius(new int[]{1, 5, 9}, new int[]{9, 5, 1}) + " (Expected: 0)");

        // Test 8: duplicate heaters — a two-pointer sweep that advances on < instead of <= stalls
        // on the first 1 and reports 5 for house 6
        System.out.println("Test 8: " + H.findRadius(new int[]{2, 6}, new int[]{7, 1, 7, 1}) + " (Expected: 1)");

        // Test 9: the widest gap the constraints allow — 10^9 - 1 still fits in an int
        System.out.println("Test 9: " + H.findRadius(new int[]{1, 1000000000}, new int[]{1}) + " (Expected: 999999999)");
    }
}
