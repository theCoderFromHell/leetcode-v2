package contests.weekly_529;

// https://leetcode.com/problems/count-dominant-elements/
public class CountDominantElements {
    public int countDominantElements(int[] nums) {
        int size = nums.length;
        int result = 0;
        for (int i = 0; i < size; i++) {
            double sum = 0, count = 0;
            if (i-1 >= 0) {
                sum += nums[i-1];
                count++;
            }
            if (i+1 < size) {
                sum += nums[i+1];
                count++;
            }
            if (count == 0)
                result++;
            else if (sum / count <= nums[i])
                result++;
        }
        return result;
    }

    /*
     * Revision Note — Count Dominant Elements (Easy, Weekly Contest 529)
     *
     * Pattern: one pass, comparing each element with the average of the neighbours that exist.
     *
     * Key insight: an end element has one neighbour, an interior one has two, and a lone element has
     * none and counts as dominant. Compare without dividing: sum <= count * nums[i] is exact in
     * integers.
     *
     * Gotchas:
     *  - "Greater than or equal": equality counts (Example 2, and Test 6 where all are equal).
     *  - The average can be x.5 (Tests 5 and 8). The double division here is still exact: sums are at
     *    most 200, and dividing by 1 or 2 is exact in binary floating point. The integer comparison
     *    avoids having to argue that.
     *  - A single element has count 0 and is dominant by definition (Example 3).
     *
     * Complexity: O(n) time, O(1) space.
     *
     * Template:
     *   for each i: sum, count over the neighbours i - 1 and i + 1 that exist
     *       if count == 0 or sum <= count * nums[i]: result++
     */
    public static void main(String[] args) {
        CountDominantElements C = new CountDominantElements();

        // Test 1: LeetCode example 1 — only the middle 10 beats its neighbours' average of 1.5
        System.out.println("Test 1: " + C.countDominantElements(new int[]{1, 10, 2}) + " (Expected: 1)");

        // Test 2: LeetCode example 2 — 4 equals the average of 6 and 2, and equality counts
        System.out.println("Test 2: " + C.countDominantElements(new int[]{6, 4, 2}) + " (Expected: 2)");

        // Test 3: LeetCode example 3 — a single element has no neighbours and is dominant
        System.out.println("Test 3: " + C.countDominantElements(new int[]{7}) + " (Expected: 1)");

        // Test 4: both ends are dominant against one neighbour; the middle 10 is below the average 11
        System.out.println("Test 4: " + C.countDominantElements(new int[]{12, 10, 10}) + " (Expected: 2)");

        // Test 5: average 1.5 for the middle element — 2 >= 1.5; the last 2 equals its one neighbour
        System.out.println("Test 5: " + C.countDominantElements(new int[]{1, 2, 2}) + " (Expected: 2)");

        // Test 6: all equal — every element equals its neighbours' average
        System.out.println("Test 6: " + C.countDominantElements(new int[]{5, 5, 5, 5}) + " (Expected: 4)");

        // Test 7: two elements — each has exactly one neighbour
        System.out.println("Test 7: " + C.countDominantElements(new int[]{3, 8}) + " (Expected: 1)");

        // Test 8: the middle 3 is just below the average 3.5
        System.out.println("Test 8: " + C.countDominantElements(new int[]{3, 3, 4}) + " (Expected: 2)");
    }
}
