package medium;

import java.util.HashMap;

public class CountSpecialSubsequences {
    public long numberOfSubsequences(int[] nums) {
        int size = nums.length;
        HashMap<Double, Long> productMap = new HashMap<>();
        long count = 0;
        for (int r = size-3; r >= 4; r--) {
            for (int s = r+2; s < size; s++) {
                double ratio = nums[r] / (double)nums[s];
                productMap.put(ratio, productMap.getOrDefault(ratio, 0L) + 1);
            }
            int q = r-2;
            for (int p = 0; p <= q-2; p++) {
                double ratio = nums[q] / (double)nums[p];
                count += productMap.getOrDefault(ratio, 0L);
            }
        }
        return count;
    }

    public static void main(String[] args) {
        CountSpecialSubsequences C = new CountSpecialSubsequences();
        System.out.println(C.numberOfSubsequences(new int[]{1,2,3,4,3,6,1}));
        System.out.println(C.numberOfSubsequences(new int[]{3,4,3,4,3,4,3,4}));
        System.out.println(C.numberOfSubsequences(new int[]{1,2,3,4,3,6,1}));
    }
}
