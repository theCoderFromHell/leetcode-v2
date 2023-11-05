package easy;

// https://leetcode.com/problems/concatenation-of-array/description/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConcatenationOfArray {

    public static int[] getConcatenation(int[] nums) {
        if (nums == null || nums.length == 0)
            return new int[0];
        List<Integer> result = new ArrayList<>();
        result.addAll(Arrays.stream(nums).boxed().toList());
        result.addAll(Arrays.stream(nums).boxed().toList());
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    public static void main(String[] args) {
        int[] arr = new int[] {1, 2, 3, 4, 5};
        int[] result = getConcatenation(arr);
        int length = result.length;
        for (int i = 0; i < length; i++)
            System.out.print(result[i] + " ");

    }
}
