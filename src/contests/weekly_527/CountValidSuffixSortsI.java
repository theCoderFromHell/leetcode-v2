package contests.weekly_527;

import java.util.Arrays;
import java.util.HashMap;

// https://leetcode.com/problems/count-valid-suffix-sorts-i/
public class CountValidSuffixSortsI {
    public int countSuffix(int[] nums, int[] target) {
        int size = nums.length;
        int result = 0;
        int previous = size-1;
        int count = 0;
        boolean[] same = new boolean[size+1];
        same[0] = true;
        for (int i = 0; i < size; i++) {
            if (nums[i] == target[i] && same[i])
                same[i+1] = true;
            else
                break;
        }
        HashMap<Integer,Integer> diff = new HashMap<>();
        int index = size-1;
        while (index >= 0) {
            if (index == size-1 || target[index] >= target[previous]) {
                count += update(diff, nums[index], 1);
                count += update(diff, target[index], -1);
                if (count == 0 && same[index])
                    result++;
            } else
                break;
            previous = index;
            index--;
        }
        return result;
    }

    private int update(HashMap<Integer,Integer> diff, int value, int d) {
        int old = diff.getOrDefault(value, 0);
        int now = old + d;
        if (now == 0)
            diff.remove(value);
        else
            diff.put(value, now);
        return (old == 0 ? 1 : 0) - (now == 0 ? 1 : 0);
    }

    /*
     * Revision Note — Count Valid Suffix Sorts I (Medium, Weekly Contest 527)
     *
     * Pattern: split "sorted suffix equals target" into three independent conditions. Check each in
     * O(1) per index: a prefix flag, a right-to-left monotonic scan, and a running multiset balance.
     *
     * Key insight: sorting nums[i..] leaves nums[0..i-1] untouched. So index i is valid iff ALL of:
     *   1. nums[0..i-1] == target[0..i-1]            → same[i], true only up to the first mismatch
     *   2. target[i..] is non-increasing              → true from the start of target's last
     *                                                    non-increasing run; scan right-to-left, stop when it breaks
     *   3. nums[i..] and target[i..] are equal MULTISETS → diff map, +1 for nums, -1 for target,
     *                                                    plus a count of keys whose diff ≠ 0
     * Condition 3 is a balance, so it's tracked incrementally: count == 0 ⇔ multisets equal.
     *
     * Gotchas:
     *  - Multiset, not set: one extra duplicate makes it fail (Test 7: {7,6,8} vs {8,6,8}).
     *  - Non-increasing allows ties, so compare with >= (Test 5: every index valid, duplicates included).
     *  - When nums[i] == target[i], the +1 and -1 hit the same key. update() returns the change in
     *    the non-zero-key count for each call, so the pair nets to 0 correctly.
     *  - Breaking out once target stops being non-increasing is safe: every smaller i fails condition 2.
     *  - Prefix equality and multiset equality are separate conditions (Test 6: same multiset, prefix differs).
     *  - Re-sorting every suffix is O(n² log n), far too slow at n = 1e5 (Test 9).
     *
     * Complexity: O(n) time, O(n) space.
     *
     * Template:
     *   same[0] = true; same[i+1] = same[i] && nums[i] == target[i]
     *   for i = n-1 down to 0:
     *       if i < n-1 && target[i] < target[i+1]: break
     *       add(nums[i], +1); add(target[i], -1)          // track # of keys with diff ≠ 0
     *       if nonZero == 0 && same[i]: ans++
     *
     * Alternative: values ≤ 1e5, so an int[100001] diff array replaces the HashMap. It's faster, with no boxing.
     */
    public static void main(String[] args) {
        CountValidSuffixSortsI C = new CountValidSuffixSortsI();

        // Test 1: LeetCode example 1 — only the full sort works
        System.out.println("Test 1: " + C.countSuffix(new int[]{1, 2, 3}, new int[]{3, 2, 1}) + " (Expected: 1)");

        // Test 2: LeetCode example 2 — two different suffixes give the same result
        System.out.println("Test 2: " + C.countSuffix(new int[]{1, 5, 6, 4, 6}, new int[]{1, 5, 6, 6, 4}) + " (Expected: 2)");

        // Test 3: LeetCode example 3 — target[0..] isn't non-increasing, and prefixes never match
        System.out.println("Test 3: " + C.countSuffix(new int[]{7, 6, 8}, new int[]{8, 6, 7}) + " (Expected: 0)");

        // Test 4: n = 1, equal — the one-element suffix is trivially sorted
        System.out.println("Test 4: " + C.countSuffix(new int[]{4}, new int[]{4}) + " (Expected: 1)");

        // Test 5: nums == target, already non-increasing with a tie — every index is valid
        System.out.println("Test 5: " + C.countSuffix(new int[]{5, 4, 4, 1}, new int[]{5, 4, 4, 1}) + " (Expected: 4)");

        // Test 6: same multiset overall, but the prefix never matches
        System.out.println("Test 6: " + C.countSuffix(new int[]{3, 1, 2}, new int[]{1, 3, 2}) + " (Expected: 0)");

        // Test 7: multisets differ by one duplicate (target has two 8s, nums one)
        System.out.println("Test 7: " + C.countSuffix(new int[]{7, 6, 8}, new int[]{8, 6, 8}) + " (Expected: 0)");

        // Test 8: nums == target, unsorted — valid only where target's tail is non-increasing (i = 2, 3)
        System.out.println("Test 8: " + C.countSuffix(new int[]{1, 2, 5, 3}, new int[]{1, 2, 5, 3}) + " (Expected: 2)");

        // Test 9: n = 1e5, all values equal — every index is valid; re-sorting each suffix would time out
        int[] big = new int[100000];
        Arrays.fill(big, 100000);
        System.out.println("Test 9: " + C.countSuffix(big, big.clone()) + " (Expected: 100000)");
    }
}
