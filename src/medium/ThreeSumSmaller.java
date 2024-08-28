package medium;

import java.util.Arrays;

public class ThreeSumSmaller {
    public int threeSumSmaller(int[] nums, int target) {
        Arrays.sort(nums);
        int N = nums.length;
        int result = 0;
        for (int i = 0; i < N; i++) {
            result += twoSumSmaller(nums, i+1, N-1, target - nums[i]);
        }
        return result;
    }

    private int twoSumSmaller(int[] nums, int start, int end, int target) {
        if (start >= end)
            return 0;
        int count = 0;
        while (start < end) {
            if (nums[start] + nums[end] < target) {
                count += (end - start);
                start++;
            } else
                end--;
        }
        return count;
    }
}
