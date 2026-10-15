package medium;

// https://leetcode.com/problems/longest-nice-subarray/
public class LongestNiceSubarray {
    public int longestNiceSubarray(int[] nums) {
        int size = nums.length;
        int start = 0;
        int maxWindow = 1;
        int mask = nums[0];
        for (int end = 1; end < size; end++) {
            while (start < end && (mask & nums[end]) != 0) {
                mask ^= nums[start];
                start++;
            }
            mask |= nums[end];
            maxWindow = Math.max(maxWindow, end - start + 1);
        }
        return maxWindow;
    }

    /*
     * Revision Note — Longest Nice Subarray (Medium)
     * Pattern: Variable sliding window with a bitmask as the window state — 3. Longest Substring
     *          Without Repeating Characters, with bit positions playing the role of characters.
     * Key Insight: "every pair ANDs to 0" == "no two numbers share a set bit", so the OR of the
     *              window records every claimed bit and one AND tests a newcomer against ALL of
     *              them. Every sub-window of a nice window is nice, which is what makes shrinking safe.
     * Gotchas:
     *   - The window state is the OR, not the AND. An AND of the window drifts to 0 and says
     *     nothing about which bits are taken.
     *   - Precedence: `mask & nums[end] == 0` parses as `mask & (nums[end] == 0)` and won't
     *     compile, because `==` binds tighter than `&`. Write `(mask & nums[end]) != 0`.
     *   - Evicting with XOR is safe only because bits in the window are disjoint: every bit of
     *     nums[start] is set in mask and owned by nobody else, so XOR can never SET a bit here.
     *     A general OR can't be undone without per-bit counts; `mask &= ~nums[start]` is the
     *     defensive alternative.
     *   - `while`, not `if`: one newcomer can collide with several members — 7 against [1, 2, 4].
     *   - The answer never exceeds 30 (values < 2^30, each number owns at least one bit), so
     *     scanning at most 30 back from each index is an O(30n) alternative.
     * Complexity: O(n) time — each index enters and leaves the window once; O(1) space.
     * Template:
     *   mask = 0; left = 0
     *   for right in 0..n-1:
     *       while ((mask & nums[right]) != 0) mask ^= nums[left++];
     *       mask |= nums[right]; best = max(best, right - left + 1)
     */
    public static void main(String[] args) {
        LongestNiceSubarray L = new LongestNiceSubarray();

        // Test 1: LeetCode example 1 — [3, 8, 48] wins only after 1 is evicted for colliding with 3
        System.out.println("Test 1: " + L.longestNiceSubarray(new int[]{1, 3, 8, 48, 10}) + " (Expected: 3)");

        // Test 2: LeetCode example 2 — every adjacent pair shares a bit, so only singletons are nice
        System.out.println("Test 2: " + L.longestNiceSubarray(new int[]{3, 1, 5, 11, 13}) + " (Expected: 1)");

        // Test 3: single element — the loop never runs, answer comes from the initial window
        System.out.println("Test 3: " + L.longestNiceSubarray(new int[]{5}) + " (Expected: 1)");

        // Test 4: duplicates — x & x = x != 0, so equal neighbours always collide
        System.out.println("Test 4: " + L.longestNiceSubarray(new int[]{5, 5, 5}) + " (Expected: 1)");

        // Test 5: 7 collides with ALL of 1, 2, 4 — evicting with `if` instead of `while` drops
        // only the 1, leaves [2, 4, 7] in the window, and then reports [2, 4, 7, 8] = 4
        System.out.println("Test 5: " + L.longestNiceSubarray(new int[]{1, 2, 4, 7, 8}) + " (Expected: 3)");

        // Test 6: the best window [1, 2, 4, 8] runs to the last index — max must be taken every step
        System.out.println("Test 6: " + L.longestNiceSubarray(new int[]{3, 1, 2, 4, 8}) + " (Expected: 4)");

        // Test 7: the longest possible answer — values <= 10^9 < 2^30, so 30 single-bit numbers
        // 1, 2, 4, ..., 2^29 are the most that can coexist, and bit 29 is the highest in range
        int[] powersOfTwo = new int[30];
        for (int i = 0; i < 30; i++)
            powersOfTwo[i] = 1 << i;
        System.out.println("Test 7: " + L.longestNiceSubarray(powersOfTwo) + " (Expected: 30)");
    }
}
