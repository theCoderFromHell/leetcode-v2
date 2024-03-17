package medium;

import java.util.ArrayList;
import java.util.List;

public class MaximumLengthOfSemiDecreasingSubarrays {

    public static int maxSubarrayLength(int[] nums) {
        int N = nums.length;
        int maxLength = 0;
        int[] minRight = new int[N];
        minRight[N-1] = nums[N-1];
        for (int i = N-2; i >= 0 ; i--)
            minRight[i] = Math.min(nums[i], minRight[i + 1]);
        int idx = 0;
        for (int i = 0; i < N; i++) {
            idx = Math.max(idx, i);
            while (idx < N && nums[i] > minRight[idx])
                idx++;
            maxLength = Math.max(maxLength, idx - i);
        }
        return maxLength;
    }

    public static int maxSubarrayLengthV2(int[] nums) {
        int N = nums.length;
        int maxLength = 0;
        int maxSoFar = nums[0];
        List<Integer> peaks = new ArrayList<>();
        peaks.add(0);
        for (int i = 1; i < N; i++) {
            if (nums[i-1] < nums[i])
                peaks.add(i);
            if (nums[i] < maxSoFar) {
                int size = peaks.size();
                for (int j = 0; j < size; j++) {
                    if (nums[peaks.get(j)] > nums[i]) {
                        maxLength = Math.max(maxLength, i - peaks.get(j) + 1);
                        break;
                    }
                }
            } else {
                maxSoFar = nums[i];
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
//        System.out.println(maxSubarrayLength(new int[]{7,6,5,4,3,2,1,6,10,11}));
//        System.out.println(maxSubarrayLength(new int[]{57,55,50,60,61,58,63,59,64,60,63}));
//        System.out.println(maxSubarrayLength(new int[]{1,2,3,4}));
        System.out.println(maxSubarrayLength(new int[]{ 46,88,76}));


    }
}
