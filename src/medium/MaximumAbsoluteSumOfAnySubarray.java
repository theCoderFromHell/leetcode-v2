package medium;

public class MaximumAbsoluteSumOfAnySubarray {
    public int maxAbsoluteSum(int[] nums) {
        int size = nums.length;
        int minSum = Integer.MAX_VALUE;
        int maxSum = Integer.MIN_VALUE;
        int currMinSum = 0, currMaxSum = 0;
        for (int i = 0; i < size; i++) {
            if (currMaxSum < 0)
                currMaxSum = nums[i];
            else
                currMaxSum += nums[i];
            maxSum = Math.max(maxSum, currMaxSum);

            if (currMinSum > 0)
                currMinSum = nums[i];
            else
                currMinSum += nums[i];
            minSum = Math.min(minSum, currMinSum);
        }
        return Math.max(Math.abs(maxSum), Math.abs(minSum));
    }

    public static void main(String[] args) {
        MaximumAbsoluteSumOfAnySubarray M = new MaximumAbsoluteSumOfAnySubarray();
        System.out.println(M.maxAbsoluteSum(new int[]{1,-3,2,3,-4}));
        System.out.println(M.maxAbsoluteSum(new int[]{2,-5,1,-4,3,-2}));
    }
}
