package medium;

import java.util.Arrays;

public class CombinationSumIV {
    public int combinationSum4(int[] nums, int target) {
        int N = nums.length;
        int[] dp = new int[target+1];
        dp[0] = 1;
        Arrays.sort(nums);
        for (int i = 0; i <= target; i++) {
            for (int j = 0; j < N; j++) {
                if (i + nums[j] > target)
                    break;
                dp[i + nums[j]] += dp[i];
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        CombinationSumIV C = new CombinationSumIV();
        System.out.println(C.combinationSum4(new int[]{1,2,3}, 4));
        System.out.println(C.combinationSum4(new int[]{9}, 3));
        System.out.println(C.combinationSum4(new int[]{3,5,1,8,12,4}, 7));

    }
}