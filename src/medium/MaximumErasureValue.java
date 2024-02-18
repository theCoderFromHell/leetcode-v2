package medium;

import java.util.HashSet;

public class MaximumErasureValue {
    public static int maximumUniqueSubarray(int[] nums) {
        int N = nums.length;
        int start = 0;
        int end = 1;
        int currSum = nums[0];
        int maxSum = currSum;
        HashSet<Integer> currentNumber = new HashSet<>();
        currentNumber.add(nums[0]);
        while (end < N && start < end) {
            while (start < end && currentNumber.contains(nums[end])) {
                currentNumber.remove(nums[start]);
                currSum -= nums[start];
                start++;
            }
            currentNumber.add(nums[end]);
            currSum += nums[end];
            maxSum = Math.max(maxSum, currSum);
            end++;
        }
        return maxSum;
    }

    public static void main(String[] args) {
        System.out.println(maximumUniqueSubarray(new int[]{4,2,4,5,6}));
        System.out.println(maximumUniqueSubarray(new int[]{5,2,1,2,5,2,1,2,5}));
    }
}
