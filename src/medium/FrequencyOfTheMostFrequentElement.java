package medium;

import java.util.Arrays;

public class FrequencyOfTheMostFrequentElement {

    public static int maxFrequency(int[] nums, int k) {
        int N = nums.length;
        Arrays.sort(nums);
        int start = N-1;
        int end = N-1;
        int currMax = nums[N-1];
        long currSum = nums[N-1];
        int maxLength = 1;
        while (start <= end) {
            if (start > 0 && ((long) (end - start + 2) * currMax - (currSum + nums[start - 1])) <= k) {
                start--;
                maxLength = Math.max(maxLength, end - start + 1);
                currSum += nums[start];
            } else {
                currSum -= nums[end];
                end--;
                if (end < 0)
                    break;
                if (end < start) {
                    start = end;
                    currSum = nums[end];
                }
                currMax = nums[end];
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(maxFrequency(new int[]{8,8,7,5,10}, 1));


    }
}
