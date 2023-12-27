package medium;

import java.util.Arrays;

public class SingleNumberII {
    public int singleNumber(int[] nums) {
        if (nums == null || nums.length == 0)
            return -1;
        if (nums.length == 1)
            return nums[0];
        int N = nums.length;
        Arrays.sort(nums);
        if (nums[0] != nums[1])
            return nums[0];
        if (nums[N-2] != nums[N-1])
            return nums[N-1];
        int i = 1;
        while (i < N) {
            if (nums[i-1] != nums[i])
                return nums[i-1];
            i += 3;
        }
        return -1;
    }
    public static void main(String[] args) {

    }
}
