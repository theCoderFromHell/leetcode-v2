package medium;

import java.util.Arrays;

public class MinimumMovesToEqualArrayElementsII {
    public int minMoves2(int[] nums) {
        int N = nums.length;
        Arrays.sort(nums);
        int median  = nums[N/2];
        int result = 0;
        for (int i = 0; i < N; i++)
            result += Math.abs(nums[i] - median);
        return result;
    }

    public static void main(String[] args) {
        MinimumMovesToEqualArrayElementsII M = new MinimumMovesToEqualArrayElementsII();
        System.out.println(M.minMoves2(new int[]{1,2,3}));
        System.out.println(M.minMoves2(new int[]{1,10,2,9}));
        System.out.println(M.minMoves2(new int[]{1,0,0,8,6}));
    }
}
