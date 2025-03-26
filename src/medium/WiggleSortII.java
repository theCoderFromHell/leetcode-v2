package medium;

import java.util.Arrays;

public class WiggleSortII {
    public void wiggleSort(int[] nums) {
        int size = nums.length;
        int[] numsCopy = Arrays.copyOf(nums, size);
        int j = (size-1)/2, mid = (size-1)/2 + 1, k = size-1;
        Arrays.sort(numsCopy);
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0 && j >= 0)
                nums[i] = numsCopy[j--];
            if (i % 2 == 1)
                nums[i] = numsCopy[k--];
        }
    }

    public static void main(String[] args) {
        WiggleSortII W = new WiggleSortII();
        int[] nums = new int[]{1,4,3,4,1,2,1,3,1,3,2,3,3};
        W.wiggleSort(nums);

        nums = new int[]{1,3,2,2,3,1};
        W.wiggleSort(nums);

    }
}
