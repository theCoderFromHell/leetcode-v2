package medium;

import java.util.Arrays;

public class KRadiusSubarrayAverages {
    public int[] getAverages(int[] nums, int k) {
        int size = nums.length;
        int[] result = new int[size];
        Arrays.fill(result, -1);
        if (size < 2 * k + 1)
            return result;
        long currSum = 0;
        for (int i = 0; i < 2 * k + 1; i++)
            currSum += nums[i];
        for (int i = k; i < size - k; i++) {
            result[i] = Math.toIntExact(currSum / (2L * k + 1));
            currSum -= nums[i - k];
            if (i + k + 1 < size)
                currSum += nums[i + k + 1];
        }
        return result;
    }

    public static void main(String[] args) {
        KRadiusSubarrayAverages K = new KRadiusSubarrayAverages();
        System.out.println(Arrays.toString(K.getAverages(new int[]{7,4,3,9,1,8,5,2,6}, 3)));
        System.out.println(Arrays.toString(K.getAverages(new int[]{100000}, 0)));
        System.out.println(Arrays.toString(K.getAverages(new int[]{8}, 100000)));
    }
}