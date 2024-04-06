package hard;

import java.util.Arrays;
import java.util.HashSet;

public class MinimumNumberOfOperationsToMakeArrayContinuous {
    public int minOperations(int[] nums) {
        int N = nums.length;
        int result = N;
        HashSet<Integer> uniques = new HashSet<>();
        for (int i = 0; i < N; i++)
            uniques.add(nums[i]);
        int[] uniqueArray = new int[uniques.size()];
        int idx = 0;
        for (int num : uniques)
            uniqueArray[idx++] = num;
        Arrays.sort(uniqueArray);
        int M = uniqueArray.length;
        for (int i = 0; i < M; i++) {
            int value = uniqueArray[i] + N - 1;
            int index = binarySearch(uniqueArray, value);
            result = Math.min(result, N - (index - i));
        }
        return result;
    }

    private int binarySearch(int[] uniqueArray, int value) {
        int mid;
        int start = 0, end = uniqueArray.length;
        while (start < end) {
            mid = start + (end - start)/2;
            if (uniqueArray[mid] <= value)
                start = mid + 1;
            else
                end = mid;
        }
        return start;
    }

    public static void main(String[] args) {
        MinimumNumberOfOperationsToMakeArrayContinuous m = new MinimumNumberOfOperationsToMakeArrayContinuous();
        System.out.println(m.minOperations(new int[]{1,10,100,1000}));
        System.out.println(m.minOperations(new int[]{4,2,5,3}));
    }
}
