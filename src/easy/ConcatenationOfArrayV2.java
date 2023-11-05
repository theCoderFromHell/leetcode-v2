package easy;

// https://leetcode.com/problems/concatenation-of-array/description/

public class ConcatenationOfArrayV2 {
    public static int[] getConcatenation(int[] nums) {
        if (nums == null || nums.length == 0)
            return new int[0];
        int length = nums.length;
        int [] result = new int[2*length];
        for (int j = 0; j< 2* length; j++)
            result[j] = nums[j % length];
        return result;
    }

    public static void main(String[] args) {
        int[] arr = new int[] {1, 2, 3, 4, 5};
        int[] result = getConcatenation(arr);
        int length = result.length;
        for (int i = 0; i < length; i++)
            System.out.print(result[i] + " ");

    }
}
