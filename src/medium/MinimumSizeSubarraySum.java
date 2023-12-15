package medium;

public class MinimumSizeSubarraySum {
    public static int minSubArrayLen(int target, int[] nums) {
        int N = nums.length;
        int minLength = Integer.MAX_VALUE;
        int start = 0, end = 0;
        int currSum = nums[0];
        while (start <= end && end < N) {
            if (currSum >= target)
                minLength = Math.min(minLength, end-start+1);
            end++;
            if (end < N)
                currSum += nums[end];
            while (start <= end && (currSum - nums[start]) >= target) {
                currSum -= nums[start];
                start++;
            }
        }
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    public static void main(String[] args) {
        System.out.println(minSubArrayLen(7, new int[]{2,3,1,2,4,3}));
        System.out.println(minSubArrayLen(4, new int[]{1,4,4}));
        System.out.println(minSubArrayLen(11, new int[]{1,1,1,1,1,1,1,1}));
    }
}
