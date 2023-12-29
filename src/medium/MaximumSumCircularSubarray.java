package medium;

public class MaximumSumCircularSubarray {
    public int maxSubarraySumCircular(int[] nums) {
        int N = nums.length;
        int maxSum = nums[0], minSum = nums[0];
        int currMin = 0, currMax = 0;
        int total = 0;
        for (int i = 0; i < N; i++) {
            total += nums[i];
            currMin = Math.min(currMin + nums[i], nums[i]);
            currMax = Math.max(currMax + nums[i], nums[i]);
            minSum = Math.min(minSum, currMin);
            maxSum = Math.max(maxSum, currMax);
        }
        return (maxSum >= 0) ? Math.max(maxSum, total -minSum) : maxSum;
    }
    public static void main(String[] args) {

    }
}
