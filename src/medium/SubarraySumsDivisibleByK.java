package medium;

import java.util.HashMap;

public class SubarraySumsDivisibleByK {
    public int subarraysDivByK(int[] nums, int k) {
        int length = nums.length;
        int currentRemainder = 0;
        int result = 0;
        HashMap<Integer, Integer> prefixSumRemainderCount = new HashMap<>();
        prefixSumRemainderCount.put(0, 1);
        for (int i = 0; i < length; i++) {
            currentRemainder = (currentRemainder + nums[i]) % k;
            currentRemainder = (currentRemainder + k) % k;
            result += prefixSumRemainderCount.getOrDefault(currentRemainder, 0);
            prefixSumRemainderCount.put(currentRemainder, prefixSumRemainderCount.getOrDefault(currentRemainder, 0) + 1);
        }
        return result;
    }

    public static void main(String[] args) {
        SubarraySumsDivisibleByK S = new SubarraySumsDivisibleByK();
//        System.out.println(S.subarraysDivByK(new int[]{4,5,0,-2,-3,1}, 5));
//        System.out.println(S.subarraysDivByK(new int[]{5}, 9));
        System.out.println(S.subarraysDivByK(new int[]{7,4,-10}, 5));
    }
}
