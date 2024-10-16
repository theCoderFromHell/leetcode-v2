package medium;

import java.util.HashMap;

public class MakeSumDivisibleByP {
    public int minSubarray(int[] nums, int p) {
        int N = nums.length;
        long sum = 0;
        for (int i = 0; i < N; i++)
            sum += nums[i];
        int remainder = (int) (sum % p);
        if (remainder == 0)
            return 0;
        HashMap<Long,Integer> remainderIndices = new HashMap<>();
        remainderIndices.put(0L, -1);
        long currSum = 0;
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            currSum += nums[i];
            if (currSum >= remainder && remainderIndices.containsKey((currSum - remainder) % p))
                result = Math.min(result, i - remainderIndices.get((currSum - remainder) % p));
            remainderIndices.put(currSum % p, i);
        }
        return (result >= N) ? -1 : result;
    }

    public static void main(String[] args) {
        MakeSumDivisibleByP M = new MakeSumDivisibleByP();
        System.out.println(M.minSubarray(new int[]{3,1,4,2}, 6));
        System.out.println(M.minSubarray(new int[]{6,3,5,2}, 9));
        System.out.println(M.minSubarray(new int[]{1,2,3}, 3));
        System.out.println(M.minSubarray(new int[]{45,33,40,31,7,28,23,10,21,22,22,27,43,7,17,4,25,23,45,22,40,22,21}, 197));

    }
}
