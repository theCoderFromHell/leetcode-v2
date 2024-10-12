package contests.biweekly_141;

import java.util.Arrays;
import java.util.List;

public class ConstructTheMinimumBitwiseArrayII {
    public int[] minBitwiseArray(List<Integer> nums) {
        int N = nums.size();
        int[] result = new int[N];
        for (int i = 0; i < N; i++) {
            int value = nums.get(i);
            if (value % 2 == 0) {
                result[i] = -1;
            } else if ((value & (value+1)) == 0)
                result[i] = value / 2;
            else
                result[i] = value - solve(value);
        }
        return result;
    }

    private int solve(Integer value) {
        if ((value & (value+1)) == 0)
            return (value - value / 2);
        if (value == 1)
            return 1;
        int log2 = (int) (Math.floor (Math.log(value) / Math.log(2)));
        return solve(value - pow(2, log2));
    }

    int pow(int base, int power) {
        int result = 1;
        for (int i = 0; i < power; i++)
            result *= base;
        return result;
    }

    public static void main(String[] args) {
        ConstructTheMinimumBitwiseArrayII C = new ConstructTheMinimumBitwiseArrayII();
        System.out.println(Arrays.toString(C.minBitwiseArray(Arrays.asList(2, 3, 5, 7))));
        System.out.println(Arrays.toString(C.minBitwiseArray(Arrays.asList(11, 13, 31))));

    }
}
