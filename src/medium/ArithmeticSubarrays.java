package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArithmeticSubarrays {
    public List<Boolean> checkArithmeticSubarrays(int[] nums, int[] l, int[] r) {
        List<Boolean> result = new ArrayList<>();
        int size = l.length;
        for (int i = 0; i < size; i++) {
            int start = l[i];
            int end = r[i];
            boolean isAM = true;
            int[] sub = Arrays.copyOfRange(nums, start, end + 1);
            Arrays.sort(sub);
            if ((sub[end - start] - sub[0]) % (end - start) != 0)
                isAM = false;
            int diff = (sub[end - start] - sub[0])/(end - start);
            for (int j = 0; j < end - start; j++) {
                if (sub[j+1] - sub[j] != diff) {
                    isAM = false;
                    break;
                }
            }
            result.add(isAM);
        }
        return result;
    }

    public static void main(String[] args) {
        ArithmeticSubarrays A = new ArithmeticSubarrays();

        // Basic: overlapping queries on same array
        System.out.println(A.checkArithmeticSubarrays(new int[]{4, 6, 5, 9, 3, 7}, new int[]{0, 0, 2}, new int[]{2, 3, 5}));
        // Expected: [true, false, true]

        // All equal elements (diff = 0)
        System.out.println(A.checkArithmeticSubarrays(new int[]{3, 3, 3, 3}, new int[]{0}, new int[]{3}));
        // Expected: [true]

        // Negative numbers
        System.out.println(A.checkArithmeticSubarrays(new int[]{-3, -1, 1, 3}, new int[]{0}, new int[]{3}));
        // Expected: [true]

        // Two elements (minimum subarray size)
        System.out.println(A.checkArithmeticSubarrays(new int[]{1, 5}, new int[]{0}, new int[]{1}));
        // Expected: [true]

        // Non-arithmetic subarray (total range divisible by len-1, but values don't match)
        System.out.println(A.checkArithmeticSubarrays(new int[]{3, 5, 1, 2, 4}, new int[]{0, 2}, new int[]{2, 4}));
        // Expected: [true, false]

        // Queries on non-overlapping ranges
        System.out.println(A.checkArithmeticSubarrays(new int[]{1, 2, 3, 7, 9, 11}, new int[]{0, 3}, new int[]{2, 5}));
        // Expected: [true, true]

        // Single query, not arithmetic
        System.out.println(A.checkArithmeticSubarrays(new int[]{1, 2, 4}, new int[]{0}, new int[]{2}));
        // Expected: [false]
    }
}
