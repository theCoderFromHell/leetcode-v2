package medium;

public class NumberOfLongestIncreasingSubsequence {
    public int findNumberOfLIS(int[] nums) {
        int N = nums.length;
        int[] LIS = new int[N];
        int[] lisCount = new int[N];
        int maxLis = 1;
        for (int i = 0; i < N; i++) {
            LIS[i] = 1;
            lisCount[i] = 1;
        }
        for (int i = 0; i < N; i++) {
            for (int j = i-1; j >= 0; j--) {
                if (nums[j] < nums[i] && LIS[j] + 1 >= LIS[i]) {
                    if (LIS[j] + 1 > LIS[i])
                        lisCount[i] = lisCount[j];
                    else
                        lisCount[i] += lisCount[j];
                    LIS[i] = LIS[j] + 1;
                    if (LIS[i] > maxLis)
                        maxLis = LIS[i];
                }
            }
        }
        int count = 0;
        for (int i = 0; i < N; i++) {
            if (LIS[i] == maxLis)
                count += lisCount[i];
        }
        return count;
    }

    public static void main(String[] args) {
        NumberOfLongestIncreasingSubsequence N = new NumberOfLongestIncreasingSubsequence();
        System.out.println(N.findNumberOfLIS(new int[]{1,3,2}));
        System.out.println(N.findNumberOfLIS(new int[]{1,2,4,3,5,4,7,2}));
        System.out.println(N.findNumberOfLIS(new int[]{1,3,5,4,7}));
    }
}
