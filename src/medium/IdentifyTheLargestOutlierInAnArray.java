package medium;

import java.util.Arrays;
import java.util.HashMap;

public class IdentifyTheLargestOutlierInAnArray {
    public int getLargestOutlier(int[] nums) {
        int size = nums.length;
        HashMap<Integer,Integer> count = new HashMap<>();
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += nums[i];
            count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);
        }
        for (int i = size-1; i >= 0; i--) {
            count.put(nums[i], count.getOrDefault(nums[i], 0) - 1);
            if ((sum - nums[i]) % 2 == 0 && count.containsKey((sum - nums[i])/2) && count.get((sum - nums[i])/2) >= 1)
                return nums[i];
            count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);
        }
        return -1;
    }

    public static void main(String[] args) {
        IdentifyTheLargestOutlierInAnArray I = new IdentifyTheLargestOutlierInAnArray();
        System.out.println(I.getLargestOutlier(new int[]{2,3,5,10}));
        System.out.println(I.getLargestOutlier(new int[]{-2,-1,-3,-6,4}));
        System.out.println(I.getLargestOutlier(new int[]{1,1,1,1,1,5,5}));
        System.out.println(I.getLargestOutlier(new int[]{958,777,-746,566,989}));
    }
}
