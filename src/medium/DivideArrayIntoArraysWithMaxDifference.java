package medium;

// https://leetcode.com/problems/divide-array-into-arrays-with-max-difference/

import java.util.Arrays;

public class DivideArrayIntoArraysWithMaxDifference {
    public int[][] divideArray(int[] nums, int k) {
        int size = nums.length;
        Arrays.sort(nums);
        int[][] result = new int[size/3][3];
        int index = 0;
        for (int i = 0; i < size / 3; i++) {
            if (nums[i * 3 + 2] - nums[i * 3] > k)
                return new int[0][0];
            for (int j = 0; j < 3; j++) {
                result[i][j] = nums[index++];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        DivideArrayIntoArraysWithMaxDifference D = new DivideArrayIntoArraysWithMaxDifference();
        System.out.println(Arrays.deepToString(D.divideArray(new int[]{1, 3, 4, 8, 7, 9, 3, 5, 1}, 2)));  // [[1,1,3],[3,4,5],[7,8,9]]
        System.out.println(Arrays.deepToString(D.divideArray(new int[]{1, 3, 3, 2, 7, 3}, 3)));           // []
        System.out.println(Arrays.deepToString(D.divideArray(new int[]{4, 2, 9, 8, 2, 12, 7, 12, 10}, 5))); // valid groups
        System.out.println(Arrays.deepToString(D.divideArray(new int[]{1, 10, 20}, 5)));                  // [] — impossible
        System.out.println(Arrays.deepToString(D.divideArray(new int[]{5, 5, 5}, 0)));                    // [[5,5,5]]
    }
}
