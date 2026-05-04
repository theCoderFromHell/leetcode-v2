package medium;

// https://leetcode.com/problems/check-if-there-is-a-valid-partition-for-the-array/
public class CheckIfThereIsAValidPartitionForTheArray {
    public boolean validPartition(int[] nums) {
        int size = nums.length;
        Boolean[] dp = new Boolean[size+1];
        dp[0] = true;
        dp[1] = false;
        solve(nums, size-1, dp);
        return dp[size];
    }

    private Boolean solve(int[] nums, int index, Boolean[] dp) {
        if (dp[index + 1] != null)
            return dp[index + 1];
        boolean pair = (nums[index] == nums[index - 1] && solve(nums, index - 2, dp));
        boolean triple = false;
        boolean AP = false;
        if (index >= 2) {
            triple = (nums[index] == nums[index - 1] && nums[index - 1] == nums[index - 2] && solve(nums, index - 3, dp));
            AP = (nums[index] == nums[index - 1] + 1 && nums[index - 1] == nums[index - 2] + 1 && solve(nums, index - 3, dp));
        }
        dp[index + 1] = pair || triple || AP;
        return dp[index + 1];
    }

    /*
     * Revision Note — Check if There is a Valid Partition For The Array (Medium)
     *
     * Pattern: Top-down DP (memoization) with fixed lookback of 2 or 3 positions
     *
     * Key Insight: dp[i] = "can nums[0..i-1] be validly partitioned?" — each state
     * only depends on dp[i-2] or dp[i-3], so check each of the 3 rules independently.
     *
     * Gotchas:
     * - Seed dp[0]=true (empty prefix valid) and dp[1]=false (single element invalid)
     * - solve(-1) safely returns dp[0]=true via early return — no array access occurs
     * - Use || not | so short-circuit kicks in once a valid rule is found
     * - Guard triple/AP checks with index >= 2 to avoid out-of-bounds
     *
     * Template (bottom-up alternative):
     *   dp[0]=true; dp[1]=false (implicit);
     *   for i in 1..size-1:
     *       if nums[i]==nums[i-1]:                          dp[i+1] |= dp[i-1]   // pair
     *       if i>=2 && nums[i]==nums[i-1]==nums[i-2]:      dp[i+1] |= dp[i-2]   // triple
     *       if i>=2 && nums[i]==nums[i-1]+1==nums[i-2]+2:  dp[i+1] |= dp[i-2]   // AP
     *   return dp[size]
     */
    public static void main(String[] args) {
        CheckIfThereIsAValidPartitionForTheArray C = new CheckIfThereIsAValidPartitionForTheArray();
        System.out.println(C.validPartition(new int[]{4, 4, 4, 5, 6}));    // true  ([4,4]+[4,5,6])
        System.out.println(C.validPartition(new int[]{1, 1, 1, 2}));        // false (2 stranded)
        System.out.println(C.validPartition(new int[]{1, 1, 1}));            // true  ([1,1,1])
        System.out.println(C.validPartition(new int[]{1, 2, 3}));            // true  ([1,2,3])
        System.out.println(C.validPartition(new int[]{1, 1}));               // true  ([1,1])
        System.out.println(C.validPartition(new int[]{1, 2}));               // false (no valid rule)
        System.out.println(C.validPartition(new int[]{2, 2, 3, 3, 3, 4}));  // false ([4] stranded, no valid rule)
    }
}
