package medium;

import java.util.Arrays;

public class SubarraySumsDivisibleByK {
    public int subarraysDivByK(int[] nums, int k) {
        int length = nums.length;
        int currentRemainder = 0;
        int result = 0;
        int[] prefixSumRemainderCount = new int[10000];
        Arrays.fill(prefixSumRemainderCount, 0);
        prefixSumRemainderCount[0] = 1;
        for (int i = 0; i < length; i++) {
            currentRemainder = (currentRemainder + nums[i]) % k;
            currentRemainder = (currentRemainder + k) % k;
            result += prefixSumRemainderCount[currentRemainder];
            prefixSumRemainderCount[currentRemainder]++;
        }
        return result;
    }

    public static void main(String[] args) {
        SubarraySumsDivisibleByK S = new SubarraySumsDivisibleByK();
        System.out.println(S.subarraysDivByK(new int[]{4,5,0,-2,-3,1}, 5));
        System.out.println(S.subarraysDivByK(new int[]{5}, 9));
        System.out.println(S.subarraysDivByK(new int[]{7,4,-10}, 5));
    }
}
