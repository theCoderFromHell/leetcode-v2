package medium;

import java.util.Arrays;

public class SmallestRangeII {
    public int smallestRangeII(int[] nums, int k) {
        int size = nums.length;
        if (size == 1)
            return 0;
        Arrays.sort(nums);
        int result = nums[size-1] - nums[0];
        for (int i = 0; i < size - 1; i++) {
            int minimum = Math.min(nums[0] + k, nums[i+1] - k);
            int maximum = Math.max(nums[i] + k, nums[size-1] -k);
            result = Math.min(result, maximum - minimum);
        }
        return result;
    }

    public static void main(String[] args) {
        SmallestRangeII S = new SmallestRangeII();
        System.out.println(S.smallestRangeII(new int[]{7,8,8}, 5));
        System.out.println(S.smallestRangeII(new int[]{1}, 0));
        System.out.println(S.smallestRangeII(new int[]{0,10}, 2));
        System.out.println(S.smallestRangeII(new int[]{1,3,6}, 3));
    }
}
