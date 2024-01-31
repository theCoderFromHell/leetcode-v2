package medium;

import java.util.Arrays;

public class MinimumOperationsToReduceXToZero {
    public static int minOperations(int[] nums, int x) {
        int N = nums.length;
        long sum = 0;
        for (int num : nums)
            sum += num;
        if (x > sum)
            return -1;
        int start = 0;
        int end = 0;
        long currSum = nums[0];
        int maxLength = 0;
        while (start < N && end < N) {
            if (start > end) {
                start = ++end;
                if (end < N)
                    currSum = nums[end];
            }
            if (currSum == sum-x) {
                maxLength = Math.max(maxLength, end-start+1);
                currSum -= nums[start++];
            }else if (currSum < sum-x) {
                end++;
                if (end < N)
                    currSum += nums[end];
            }
            else if (currSum > sum-x)
                currSum -= nums[start++];
        }
        if (maxLength == 0)
            return (sum == x) ? N : -1;
        return (N - maxLength);
    }

    public static void main(String[] args) {
//        System.out.println(minOperations(new int[]{1,1,4,2,3}, 5));
//        System.out.println(minOperations(new int[]{5,6,7,8,9}, 4));
//        System.out.println(minOperations(new int[]{3,2,20,1,1,3}, 10));
        System.out.println(minOperations(new int[]{8828,9581,49,9818,9974,9869,9991,10000,10000,10000,9999,9993,9904,8819,1231,6309}, 134365));
    }
}
