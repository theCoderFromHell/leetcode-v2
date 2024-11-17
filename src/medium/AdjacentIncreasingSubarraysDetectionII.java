package medium;

import java.util.Arrays;
import java.util.List;

public class AdjacentIncreasingSubarraysDetectionII {
    public int maxIncreasingSubarrays(List<Integer> nums) {
        int N = nums.size();
        int[] LIS = new int[N];
        LIS[0] = 1;
        int currMax = nums.get(0);
        for (int i = 1; i < N; i++) {
            if (nums.get(i) > currMax)
                LIS[i] = 1 + LIS[i - 1];
            else
                LIS[i] = 1;
            currMax = nums.get(i);
        }
        int result = 1;
        for (int i = 0; i < N; i++) {
            if (i >= LIS[i] && LIS[i] <= LIS[i - LIS[i]])
                result = Math.max(result, LIS[i]);
            else
                result = Math.max(result, LIS[i]/2);
        }
        return result;
    }
    public int maxIncreasingSubarraysV2(List<Integer> nums) {
        int N = nums.size();
        int[] LIS = new int[N];
        LIS[0] = 1;
        int currMax = nums.get(0);
        for (int i = 1; i < N; i++) {
            if (nums.get(i) > currMax)
                LIS[i] = 1 + LIS[i - 1];
            else
                LIS[i] = 1;
            currMax = nums.get(i);
        }
        int result = 1;
        int low = 1, high = N/2;
        while (low <= high) {
            int mid = low + (high - low)/2;
            if (possible(LIS, N, mid)) {
                result = Math.max(result, mid);
                low = mid+1;
            }
            else
                high = mid-1;
        }
        return result;
    }

    private boolean possible(int[] LIS, int size, int k) {
        for (int i = 0; i < size - 1; i++) {
            if (LIS[i] >= k && i+k < size && (LIS[i+k] >= 2*k || LIS[i+k] >= k))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        AdjacentIncreasingSubarraysDetectionII A = new AdjacentIncreasingSubarraysDetectionII();
        System.out.println(A.maxIncreasingSubarrays(Arrays.asList(2,5,7,8,9,2,3,4,3,1)));
        System.out.println(A.maxIncreasingSubarrays(Arrays.asList(1,2,3,4,4,4,4,5,6,7)));
    }
}
