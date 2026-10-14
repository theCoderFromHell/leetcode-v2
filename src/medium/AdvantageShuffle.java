package medium;

import java.util.Arrays;

// https://leetcode.com/problems/advantage-shuffle/
public class AdvantageShuffle {
    public int[] advantageCount(int[] nums1, int[] nums2) {
        int size = nums1.length;
        Arrays.sort(nums1);
        Integer[] idx = new Integer[size];
        for (int i = 0; i < size; i++)
            idx[i] = i;
        Arrays.sort(idx, (a, b) -> nums2[b] - nums2[a]);
        int[] result = new int[size];
        int left = 0, right = size-1;
        for (int i = 0; i < size; i++) {
            if (nums2[idx[i]] >= nums1[right]) {
                result[idx[i]] = nums1[left++];
            } else {
                result[idx[i]] = nums1[right--];
            }
        }
        return result;
    }

    /*
     * Revision Note — Advantage Shuffle (Medium)
     *
     * Pattern: Greedy on two sorted arrays — "the extreme decides" (cf. Boats to Save People).
     *
     * Key insight: face nums2 from LARGEST to smallest. Against the current max, either your
     * biggest card wins — and any card that beats it beats everything smaller too, so spending
     * the biggest costs nothing later — or nothing you hold can win, so throw away your SMALLEST.
     *
     * Gotchas:
     *  - Answer must align with nums2's ORIGINAL positions → sort an index array, not nums2.
     *  - Ties are losses: win only on nums1[right] > nums2[i]; on >= sacrifice nums1[left].
     *  - Comparator nums2[b] - nums2[a] is safe only because values are in [0, 1e9];
     *    use Integer.compare when values may be negative or span the full int range.
     *  - Integer[] (boxed) is required for a comparator sort; int[][] {value, index} pairs avoid boxing.
     *
     * Complexity: O(n log n) time (two sorts), O(n) space (index array + result).
     *
     * Template:
     *   sort(nums1); idx = indices of nums2 sorted by value DESC
     *   left = 0, right = n-1
     *   for i in idx:
     *       if nums1[right] > nums2[i]: result[i] = nums1[right--]   // win with the biggest
     *       else:                       result[i] = nums1[left++]    // sacrifice the smallest
     *
     * Alternative: TreeMap<Integer,count> of nums1 — for each nums2[i] take higherKey(nums2[i]),
     * else the smallest key. Same O(n log n), no index sort needed, but heavier constants.
     */
    public static void main(String[] args) {
        AdvantageShuffle A = new AdvantageShuffle();

        // Any arrangement with the maximum advantage is accepted; expected = this greedy's output
        System.out.println("Test 1: " + Arrays.toString(A.advantageCount(new int[]{2, 7, 11, 15}, new int[]{1, 10, 4, 11})) + " (Expected: [2, 11, 7, 15])");
        System.out.println("Test 2: " + Arrays.toString(A.advantageCount(new int[]{12, 24, 8, 32}, new int[]{13, 25, 32, 11})) + " (Expected: [24, 32, 8, 12])");
        // single element — win / loss
        System.out.println("Test 3: " + Arrays.toString(A.advantageCount(new int[]{5}, new int[]{3})) + " (Expected: [5])");
        System.out.println("Test 4: " + Arrays.toString(A.advantageCount(new int[]{3}, new int[]{5})) + " (Expected: [3])");
        // tie is a loss — counting it as a win gives [1, 2] with advantage 0 instead of 1
        System.out.println("Test 5: " + Arrays.toString(A.advantageCount(new int[]{1, 2}, new int[]{1, 2})) + " (Expected: [2, 1])");
        // all ties — advantage 0
        System.out.println("Test 6: " + Arrays.toString(A.advantageCount(new int[]{4, 4, 4}, new int[]{4, 4, 4})) + " (Expected: [4, 4, 4])");
        // nums1 entirely weaker — every card is a sacrifice
        System.out.println("Test 7: " + Arrays.toString(A.advantageCount(new int[]{1, 2, 3}, new int[]{4, 5, 6})) + " (Expected: [3, 2, 1])");
        // duplicates on both sides — advantage 4
        System.out.println("Test 8: " + Arrays.toString(A.advantageCount(new int[]{2, 0, 4, 1, 2}, new int[]{1, 3, 0, 0, 2})) + " (Expected: [2, 4, 2, 1, 0])");
        // value extremes — comparator subtraction stays in range
        System.out.println("Test 9: " + Arrays.toString(A.advantageCount(new int[]{0, 1000000000}, new int[]{1000000000, 0})) + " (Expected: [0, 1000000000])");
    }
}
