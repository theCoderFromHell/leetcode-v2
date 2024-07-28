package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LargestDivisibleSubset {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        int N = nums.length;
        int[] dp = new int[N];
        Arrays.fill(dp, 1);
        Arrays.sort(nums);
        int maxCount = 1;
        int maxIndex = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0 && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    if (dp[i] > maxCount) {
                        maxCount = dp[i];
                        maxIndex = i;
                    }
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        while (maxCount > 0) {
            result.add(nums[maxIndex]);
            maxCount--;
            int index = maxIndex-1;
            while (index >= 0) {
                if (dp[index] == maxCount &&  nums[maxIndex] % nums[index] == 0) {
                    maxIndex = index;
                    break;
                }
                index--;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        LargestDivisibleSubset L = new LargestDivisibleSubset();
        System.out.println(L.largestDivisibleSubset(new int[] {1,2,3}));
        System.out.println(L.largestDivisibleSubset(new int[] {1,2,4,8}));
    }
}
