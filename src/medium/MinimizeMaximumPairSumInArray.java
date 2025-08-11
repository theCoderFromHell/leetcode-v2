package medium;

import java.util.Arrays;

public class MinimizeMaximumPairSumInArray {
    public int minPairSum(int[] nums) {
        int size = nums.length;
        Arrays.sort(nums);
        int left = 0, right = size-1;
        int result = 0;
        while (left < right) {
            result = Math.max(result, nums[left] + nums[right]);
            left++;
            right--;
        }
        return result;
    }

    public static void main(String[] args) {
        MinimizeMaximumPairSumInArray M = new MinimizeMaximumPairSumInArray();
        System.out.println(M.minPairSum(new int[]{3,5,2,3}));
        System.out.println(M.minPairSum(new int[]{3,5,4,2,4,6}));
    }
}
