package medium;

import java.util.Stack;

public class MaximumWidthRamp {
    public int maxWidthRamp(int[] nums) {
        int N = nums.length;
        int low = 0, high = N-1, diff;
        int result = 0;
        while (low <= high) {
            diff = (high - low)/2 + low;
            if (worksForMoreThanOrEqualToDiff(nums, N, diff)) {
                result = Math.max(result, diff);
                low = diff + 1;
            }
            else
                high = diff - 1;
        }
        return result;
    }

    private boolean worksForMoreThanOrEqualToDiff(int[] nums, int size, int difference) {
        int start = 0, end = difference;
        int min = nums[start];
        while (end < size) {
            if (min <= nums[end])
                return true;
            start++;
            end++;
            min = Math.min(min, nums[start]);
        }
        return false;
    }

    public int maxWidthRampV2(int[] nums) {
        int N = nums.length;
        Stack<Integer> stack = new Stack<>();
        stack.add(0);
        for (int i = 1; i < N; i++) {
            if (stack.empty() || nums[stack.peek()] > nums[i])
                stack.add(i);
        }
        int result = 0;
        for (int i = N-1; i > 0; i--) {
            while (!stack.empty() && nums[i] >= nums[stack.peek()]) {
                result = Math.max(result, i - stack.peek());
                stack.pop();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        MaximumWidthRamp M = new MaximumWidthRamp();
        System.out.println(M.maxWidthRamp(new int[]{6,0,8,2,1,5}));
        System.out.println(M.maxWidthRamp(new int[]{9,8,1,0,1,9,4,0,4,1}));
        System.out.println(M.maxWidthRamp(new int[]{2,4,1,3}));
    }

}
