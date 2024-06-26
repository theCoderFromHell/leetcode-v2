package medium;

import java.util.HashMap;

public class MaximumSizeSubarraySumEqualsK {
    public int maxSubArrayLen(int[] nums, int k) {
        int N = nums.length;
        HashMap<Integer,Integer> map = new HashMap<>();
        map.put(0, -1);
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += nums[i];
            if (!map.containsKey(sum))
                map.put(sum, i);
        }
        sum = 0;
        int maxLength = 0;
        for (int i = 0; i < N; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k))
                maxLength = Math.max(maxLength, i - map.get(sum - k));
        }
        return maxLength;
    }

    public static void main(String[] args) {
        MaximumSizeSubarraySumEqualsK M = new MaximumSizeSubarraySumEqualsK();
        System.out.println(M.maxSubArrayLen(new int[]{1,-1,5,-2,3},3));
        System.out.println(M.maxSubArrayLen(new int[]{-2,-1,2,1},1));
    }
}
