package medium;

import java.util.HashMap;

public class SubarraySumEqualsK {
    public int subarraySum(int[] nums, int k) {
        if(null == nums || nums.length == 0)
            return 0;
        int length = nums.length;
        int currentSum = 0;
        int result = 0;
        HashMap<Integer, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0, 1);
        for (int i = 0; i < length; i++) {
            currentSum += nums[i];
            result += prefixSumCount.getOrDefault(currentSum-k, 0);
            prefixSumCount.put(currentSum, prefixSumCount.getOrDefault(currentSum, 0) + 1);
        }
        return result;
    }
}
