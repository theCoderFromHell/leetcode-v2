package medium;

import java.util.HashMap;

public class LengthOfLongestSubarrayWithAtMostKFrequency {
    public int maxSubarrayLength(int[] nums, int k) {
        int size = nums.length;
        HashMap<Integer,Integer> map = new HashMap<>();
        int start = 0, end = 0;
        map.put(nums[0], 1);
        int maxLength = 0;
        while (start <= end && end < size) {
            if (map.getOrDefault(nums[end], 0) <= k)
                maxLength = Math.max(maxLength, end - start + 1);
            end++;
            if (end < size) {
                map.put(nums[end], map.getOrDefault(nums[end], 0) + 1);
                while (start < end && map.getOrDefault(nums[end], 0) > k) {
                    if (map.get(nums[start]) == 1)
                        map.remove(nums[start]);
                    else
                        map.put(nums[start], map.getOrDefault(nums[start], 0) - 1);
                    start++;
                }
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        LengthOfLongestSubarrayWithAtMostKFrequency L = new LengthOfLongestSubarrayWithAtMostKFrequency();
        System.out.println(L.maxSubarrayLength(new int[]{1,2,3,1,2,3,1,2}, 2));
        System.out.println(L.maxSubarrayLength(new int[]{1,2,1,2,1,2,1,2}, 1));
        System.out.println(L.maxSubarrayLength(new int[]{5,5,5,5,5,5,5}, 4));
    }
}
