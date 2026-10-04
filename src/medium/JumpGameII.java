package medium;

import java.util.Arrays;
import java.util.Random;

// https://leetcode.com/problems/jump-game-ii/
public class JumpGameII {
    public int jump(int[] nums) {
        if(nums == null || nums.length == 0)
        return 0;
        int length = nums.length;
        int start = 0, end = 0;
        int count = 0;
        while (end < length-1) {
            int maxJump = 0;
            for(int i=start; i<=end && i<length; i++) {
                maxJump = Math.max(maxJump, i + nums[i]);
            }
            start = end;
            end = maxJump;
            count++;
        }
        return count;
    }

    /*
     * Revision Note - Jump Game II (Medium)
     *
     * Pattern: BFS by layers on an implicit graph, done with two pointers instead of a queue
     *
     * Key Insight: Jumps are LEVELS, not choices. Every index reachable in exactly k jumps forms
     * one contiguous band [start, end], and the band for k+1 jumps runs from end+1 to the
     * furthest reach of anything in the current band. So you never decide WHICH index to jump
     * from - you scan the whole band, take max(i + nums[i]), and that is the next frontier. The
     * answer is the number of bands crossed before length-1 is inside one.
     *
     * Because the bands are contiguous and processed in order, this is literally BFS with the
     * queue replaced by two indices - same O(n), no Queue<Integer> and no boxing.
     *
     * Gotchas:
     * - LOOP ON `end < length-1`, not `end < length`. Arriving AT the last index is the goal, so
     *   once the frontier covers length-1 you are done. Using `< length` adds one phantom jump
     * - COUNT THE BAND TRANSITIONS, not the indices visited. count++ happens once per frontier
     *   expansion, not once per element scanned
     * - nums = [0] must return 0, not 1. The guard falls out of `end < length-1` being false
     *   immediately when length == 1 - no special case needed
     * - `start = end` re-scans one index per layer (the previous frontier). Harmless for a max,
     *   and still O(n) overall since bands otherwise do not overlap. Using `start = end + 1`
     *   would also be correct and marginally tighter
     * - The `i < length` guard inside the for loop matters: `end` is set to a REACH value that
     *   can exceed length-1, so without it the scan reads past the array
     * - INFINITE LOOP on unreachable input. If a band's max reach does not exceed its own end
     *   (e.g. [1,0,1]), `end` stops growing and the while never terminates. LeetCode 45
     *   guarantees reachability, so this is safe HERE - but 55 (Jump Game) does not, and the
     *   same shape would hang. Add `if (maxJump <= end) return -1` if reachability is in doubt
     *
     * Complexity: O(n) time - each index is scanned at most twice across all layers, since
     * consecutive bands overlap in exactly one element. O(1) space, which is the whole reason to
     * prefer this over an explicit BFS queue or the O(n) DP.
     *
     * Template:
     *   start = end = count = 0
     *   while end < n-1:
     *     reach = max(i + nums[i]) for i in [start, end]
     *     start = end
     *     end   = reach
     *     count++
     *   return count
     *
     * Greedy-by-layers beats DP here. The O(n^2) DP (dp[i] = min jumps to i) is the obvious first
     * answer and is what the band argument replaces - worth being able to write both.
     */
    // O(n^2) DP reference: dp[i] = fewest jumps to reach i. Used only to cross-check.
    private static int bruteForce(int[] nums) {
        int n = nums.length;
        if (n <= 1) return 0;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i] == Integer.MAX_VALUE) continue;
            for (int j = i + 1; j <= Math.min(n - 1, i + nums[i]); j++)
                dp[j] = Math.min(dp[j], dp[i] + 1);
        }
        return dp[n - 1];
    }

    public static void main(String[] args) {
        JumpGameII J = new JumpGameII();

        System.out.println("Test 1: " + J.jump(new int[]{2,3,1,1,4}) + " (Expected: 2)");
        System.out.println("Test 2: " + J.jump(new int[]{2,3,0,1,4}) + " (Expected: 2)");
        System.out.println("Test 3: " + J.jump(new int[]{0}) + " (Expected: 0)");                  // already at the end
        System.out.println("Test 4: " + J.jump(new int[]{1}) + " (Expected: 0)");                  // single element
        System.out.println("Test 5: " + J.jump(new int[]{1,2}) + " (Expected: 1)");
        System.out.println("Test 6: " + J.jump(new int[]{1,1,1,1}) + " (Expected: 3)");            // forced single steps
        System.out.println("Test 7: " + J.jump(new int[]{10,1,1,1,1}) + " (Expected: 1)");         // reach overshoots the array
        System.out.println("Test 8: " + J.jump(new int[]{2,1}) + " (Expected: 1)");                // overshoot at n=2
        System.out.println("Test 9: " + J.jump(new int[]{1,2,1,1,1}) + " (Expected: 3)");          // greedy must look past the nearest option

        // cross-check against the O(n^2) DP. nums[i] >= 1 for i < n-1 guarantees reachability.
        boolean agree = true;
        String firstBad = "";
        Random rnd = new Random(53);
        for (int t = 0; t < 800; t++) {
            int n = 1 + rnd.nextInt(12);
            int[] a = new int[n];
            for (int i = 0; i < n - 1; i++) a[i] = 1 + rnd.nextInt(3);   // small steps -> many layers
            if (n > 0) a[n - 1] = rnd.nextInt(3);
            int got = J.jump(a.clone()), want = bruteForce(a);
            if (got != want) {
                agree = false;
                if (firstBad.isEmpty())
                    firstBad = Arrays.toString(a) + " got " + got + " want " + want;
            }
        }
        System.out.println("Test 10: " + agree + " (Expected: true)  - matches O(n^2) DP, 800 random"
                + (agree ? "" : ", first mismatch: " + firstBad));
    }
}
