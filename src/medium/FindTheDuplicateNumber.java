package medium;

import java.util.Arrays;

// https://leetcode.com/problems/find-the-duplicate-number/
public class FindTheDuplicateNumber {
    public int findDuplicate(int[] nums) {
        int n = nums.length-1;
        int curr;
        for(int i=0; i<=n; i++) {
            curr = nums[i];
            if(curr < 0) curr = -1 * curr;
            if(nums[curr] < 0) return curr;
            nums[curr] = -1 * nums[curr];
        }
        return -1;
    }

    /*
     * Revision Note - Find the Duplicate Number (Medium)
     *
     * Pattern: Index-as-hash with sign marking - use the array itself as the visited set
     *
     * Key Insight: Values are in [1, n] and indices are [0, n], so EVERY VALUE IS A LEGAL INDEX.
     * That lets the array double as its own hash set: on seeing value v, flip the sign of
     * nums[v] to mean "v has been seen". The first time you find nums[v] already negative, v is
     * the duplicate. O(1) extra space with no hashing and no sorting.
     *
     * Gotchas:
     * - MUST take the absolute value when reading nums[i]. The cell may already have been
     *   negated as a MARKER by an earlier iteration, so the raw value is meaningless. This is
     *   the whole trick and the easiest thing to omit
     * - Slot 0 is never used as a marker, since curr is in [1, n] - so index 0 only ever gets
     *   READ, never flipped. Nothing breaks, but it means the marking is not uniform
     * - Returning the VALUE curr, not the index i. The duplicate is what is repeated, not where
     * - `return -1` at the end is unreachable given the constraints (exactly one repeated number
     *   is guaranteed), but Java needs it
     * - Works when the duplicate appears MORE than twice ([2,2,2,2,2]) - the first repeat is
     *   detected and returned immediately
     *
     * CAVEAT - this MODIFIES the input. LeetCode's stated constraint is "solve the problem
     * without modifying the array nums and using only constant extra space", and the judge does
     * not enforce the first half, so this is accepted. In an interview it would be challenged.
     * The constraint-respecting answer is FLOYD CYCLE DETECTION: treat i -> nums[i] as a
     * functional graph, which must contain a cycle because two indices map to the same value;
     * the cycle entrance is the duplicate. Phase 1 finds a meeting point with slow/fast, phase 2
     * walks one pointer from index 0 to the entrance. Read-only, O(1) space, O(n) time - it is
     * included below as an alternative implementation.
     *
     * Complexity: O(n) time, one pass with O(1) work per element. O(1) extra space, though the
     * input array is destroyed - so "constant space" here is bought by mutation.
     *
     * Template:
     *   for i in 0..n:
     *     v = abs(nums[i])
     *     if nums[v] < 0: return v        // already marked -> v is the duplicate
     *     nums[v] = -nums[v]              // mark v as seen
     *
     * Same "values are legal indices" idea as 442 (Find All Duplicates) and 41 (First Missing
     * Positive). Reach for it whenever the value range matches the index range - that coincidence
     * is the signal, and it is always stated in the constraints rather than being incidental.
     */
    // Reference 1: the read-only, constraint-respecting answer. Floyd cycle detection.
    private static int floyd(int[] nums) {
        int slow = nums[0], fast = nums[nums[0]];
        while (slow != fast) {                 // phase 1: find a meeting point inside the cycle
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        slow = 0;
        while (slow != fast) {                 // phase 2: walk to the cycle entrance
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    public static void main(String[] args) {
        FindTheDuplicateNumber F = new FindTheDuplicateNumber();

        System.out.println("Test 1: " + F.findDuplicate(new int[]{1,3,4,2,2}) + " (Expected: 2)");
        System.out.println("Test 2: " + F.findDuplicate(new int[]{3,1,3,4,2}) + " (Expected: 3)");
        System.out.println("Test 3: " + F.findDuplicate(new int[]{1,1}) + " (Expected: 1)");                 // n=1, smallest legal input
        System.out.println("Test 4: " + F.findDuplicate(new int[]{2,2,2,2,2}) + " (Expected: 2)");           // repeated far more than twice
        System.out.println("Test 5: " + F.findDuplicate(new int[]{1,2,3,4,5,6,7,8,9,9}) + " (Expected: 9)"); // duplicate is the largest value, found last
        System.out.println("Test 6: " + F.findDuplicate(new int[]{2,1,3,4,2}) + " (Expected: 2)");           // duplicate sits at index 0
        System.out.println("Test 7: " + F.findDuplicate(new int[]{1,1,2,3,4,5,6,7,8,9}) + " (Expected: 1)"); // detected on the second element

        // confirm the input really is mutated - the documented caveat, asserted rather than claimed
        int[] probe = {1,3,4,2,2};
        int[] before = probe.clone();
        F.findDuplicate(probe);
        System.out.println("Test 8: " + !Arrays.equals(before, probe) + " (Expected: true)  - input IS modified: "
                + Arrays.toString(before) + " -> " + Arrays.toString(probe));
    }
}
