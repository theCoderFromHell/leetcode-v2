package medium;

import java.util.Arrays;
import java.util.HashSet;

public class CountTheNumberOfKFreeSubsets {
    public long countTheNumOfKFreeSubsets(int[] nums, int k) {
        Arrays.sort(nums);
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums)
            set.add(num);
        long result = 1;
        int N = nums.length;
        for (int value : nums) {
            int v = value;
            int count = 0;
            while (set.contains(v)) {
                set.remove(v);
                count++;
                v += k;
            }
            result *= fibo(count);
        }
        return result;
    }

    private long fibo(int N) {
        if (N == 0)
            return 1;
        if (N == 1)
            return 2;
        long a = 1, b = 2;
        long c = 0;
        for (int i = 2; i <= N; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }

    public static void main(String[] args) {
        CountTheNumberOfKFreeSubsets c = new CountTheNumberOfKFreeSubsets();
        System.out.println(c.countTheNumOfKFreeSubsets(new int[]{5,4,6}, 1));
        System.out.println(c.countTheNumOfKFreeSubsets(new int[]{2,3,5,8}, 5));
        System.out.println(c.countTheNumOfKFreeSubsets(new int[]{10,5,9,11}, 20));
    }
}
