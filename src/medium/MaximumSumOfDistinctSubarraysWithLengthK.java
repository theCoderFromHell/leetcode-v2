package medium;

import java.util.HashMap;
import java.util.Map;

public class MaximumSumOfDistinctSubarraysWithLengthK {
    public long maximumSubarraySum(int[] nums, int k) {
        int N = nums.length;
        Map<Integer,Integer> map = new HashMap<>();
        int start = 0;
        int end = 1;
        map.put(nums[0], 0);
        long currentSum = nums[0];
        long maxSum = 0;
        while (end <= N) {
            if (end - start == k) {
                maxSum = Math.max(maxSum, currentSum);
                map.remove(nums[start]);
                currentSum -= nums[start];
                start++;
            }
            if (end < N ) {
                if (map.containsKey(nums[end])) {
                    int lastIndex = map.get(nums[end]);
                    for (int i = start; i <= lastIndex; i++) {
                        currentSum -= nums[i];
                        map.remove(nums[i]);
                    }
                    start = lastIndex+1;
                }
                map.put(nums[end], end);
                currentSum += nums[end];
            }
            end++;
        }
        return maxSum;
    }

    public static void main(String[] args) {
        MaximumSumOfDistinctSubarraysWithLengthK m = new MaximumSumOfDistinctSubarraysWithLengthK();
        System.out.println(m.maximumSubarraySum(new int[]{3,2,3,1}, 3));
    }
}
