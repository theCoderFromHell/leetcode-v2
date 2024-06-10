package medium;

import java.util.Arrays;

public class NextPermutation {
    public void nextPermutation(int[] nums) {
        int N = nums.length;
        int breakPoint = N-2;
        while (breakPoint >=0 && nums[breakPoint] >= nums[breakPoint+1])
            breakPoint--;
        if (breakPoint == -1) {
            Arrays.sort(nums);
            return;
        }
        int i = N-1;
        while (i > breakPoint && nums[i] <= nums[breakPoint])
            i--;
        swap(nums, breakPoint, i);
        reverse(nums, breakPoint+1, N-1);
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            swap(nums, start, end);
            start++;
            end--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        NextPermutation n = new NextPermutation();
        n.nextPermutation(new int[]{1,2,3});
        n.nextPermutation(new int[]{3,2,1});
        n.nextPermutation(new int[]{1,1,5});
    }
}
