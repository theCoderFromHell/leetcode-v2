package medium;

public class MaximumNumberOfOperationsWithTheSameScoreII {
    public int maxOperations(int[] nums) {
        int N = nums.length;
        if (N == 2)
            return 1;
        Integer[][] dp = new Integer[N][N];
        return 1 + Math.max(find(nums, 2, N-1, nums[0] + nums[1], dp),
                Math.max(find(nums, 0, N-3, nums[N-2] + nums[N-1], dp),
                        find(nums, 1, N-2, nums[0] + nums[N-1], dp)));

    }

    private int find(int[] nums, int start, int end, int score,  Integer[][] dp) {
        if (end - start + 1 < 2)
            return dp[start][end] = 0;
        if (end - start + 1 == 2)
            return dp[start][end] = (nums[start] + nums[end] == score ? 1 : 0);
        if (dp[start][end] != null)
            return dp[start][end];
        int a = 0, b = 0, c = 0;
        if (nums[start] + nums[start+1] == score)
            a =  1 + find(nums, start+2, end, score, dp);
        if (nums[end-1] + nums[end] == score)
            b = 1 + find(nums, start, end-2, score, dp);
        if (nums[start] + nums[end] == score)
            c = 1 + find(nums, start+1, end-1, score, dp);
        dp[start][end] =  Math.max(a, Math.max(b, c));
        return dp[start][end];
    }

    public static void main(String[] args) {
        MaximumNumberOfOperationsWithTheSameScoreII m = new MaximumNumberOfOperationsWithTheSameScoreII();
        System.out.println(m.maxOperations(new int[]{3,2,1,2,3,4}));
        System.out.println(m.maxOperations(new int[]{3,2,6,1,4}));

    }
}
