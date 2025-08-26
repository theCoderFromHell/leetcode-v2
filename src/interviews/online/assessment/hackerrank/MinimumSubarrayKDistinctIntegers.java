package interviews.online.assessment.hackerrank;

import java.util.HashMap;

public class MinimumSubarrayKDistinctIntegers {
    public int minLengthSubarrayWithKDistinct(int[] nums, int k) {
        int size = nums.length;
        int left = 0, minLen = Integer.MAX_VALUE;
        HashMap<Integer, Integer> freq = new HashMap<>();

        for (int right = 0; right < size; right++) {
            freq.put(nums[right], freq.getOrDefault(nums[right], 0) + 1);
            while (freq.size() >= k) {
                minLen = Math.min(minLen, right - left + 1);
                freq.put(nums[left], freq.get(nums[left]) - 1);
                if (freq.get(nums[left]) == 0) {
                    freq.remove(nums[left]);
                }
                left++;
            }
        }
        return (minLen == Integer.MAX_VALUE) ? -1 : minLen;
    }
}
