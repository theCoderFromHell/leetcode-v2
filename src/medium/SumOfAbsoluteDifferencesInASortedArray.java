package medium;

import java.util.Arrays;

public class SumOfAbsoluteDifferencesInASortedArray {
    public int[] getSumAbsoluteDifferences(int[] nums) {
        int N = nums.length;
        int[] forward = new int[N];
        int[] backward = new int[N];
        forward[0] = 0;
        backward[N-1] = 0;
        int currentSum = 0;

        for (int i = 1; i < N; i++) {
            forward[i] = currentSum;
            currentSum += (nums[0] - nums[i]);
        }
        currentSum = 0;
        for (int i = N-1; i >= 0; i--) {
            backward[i] = currentSum;
            currentSum += (nums[i] - nums[0]);
        }
        int[] result = new int[N];
        for (int i = 0; i < N; i++)
            result[i] = (forward[i] + backward[i] + (2 * i - N + 1) * (nums[i] - nums[0]));
        return result;
    }

    public static void main(String[] args) {
        SumOfAbsoluteDifferencesInASortedArray S = new SumOfAbsoluteDifferencesInASortedArray();
        System.out.println(S.getSumAbsoluteDifferences(new int[]{2,3,5}));
        System.out.println(S.getSumAbsoluteDifferences(new int[]{1,4,6,8,10}));
    }
}
