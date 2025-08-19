package medium;

public class MaximumNumberOfJumpsToReachTheLastIndex {
    public int maximumJumps(int[] nums, int target) {
        int size = nums.length;
        Integer[] dp = new Integer[size];
        int result = dfs(nums, size, 0, target, dp);
        return result < 0 ? -1 : result;
    }

    private int dfs(int[] nums, int size, int index, int target, Integer[] dp) {
        if (index == size - 1)
            return 0;
        if (dp[index] != null)
            return dp[index];
        int maxJumps = Integer.MIN_VALUE;
        long min = (long) nums[index] - target;
        long max = (long) nums[index] + target;
        for (int i = index + 1; i < size; i++) {
            if (min <= nums[i] && nums[i] <= max) {
                int jumpsFromHere = dfs(nums, size, i, target, dp);
                if (jumpsFromHere != Integer.MIN_VALUE)
                    maxJumps = Math.max(maxJumps, 1 + jumpsFromHere);
            }
        }
        dp[index] = maxJumps;
        return dp[index];
    }

    public static void main(String[] args) {
        MaximumNumberOfJumpsToReachTheLastIndex M = new MaximumNumberOfJumpsToReachTheLastIndex();
        System.out.println(M.maximumJumps(new int[]{758043978,79060681,785252849,287889790,-983845055,224430896,-477101480}, 1769097904));
        System.out.println(M.maximumJumps(new int[]{1,3,6,4,1,2}, 2));
        System.out.println(M.maximumJumps(new int[]{1,3,6,4,1,2}, 3));
        System.out.println(M.maximumJumps(new int[]{1,3,6,4,1,2}, 0));
    }
}
