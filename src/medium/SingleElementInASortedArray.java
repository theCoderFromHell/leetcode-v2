package medium;

import java.util.Arrays;

public class SingleElementInASortedArray {
    public int singleNonDuplicate(int[] nums) {
        if(nums.length == 1)
            return nums[0];
        return search(nums, 0, nums.length-1);
    }

    private int search(int[] nums, int start, int end) {
        int size = nums.length;
        int mid;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if ((mid == 0 || nums[mid-1] != nums[mid]) && (mid == size-1 || nums[mid] != nums[mid+1]))
                return nums[mid];
            if (mid == 0 || (mid%2 == 0 && nums[mid-1] != nums[mid]) || (mid % 2 == 1 && nums[mid-1] == nums[mid]))
                start = mid + 1;
            if (mid == size-1 || (mid%2 == 0 && nums[mid] != nums[mid+1]) || (mid % 2 == 1 && nums[mid] == nums[mid+1]))
                end = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        SingleElementInASortedArray S = new SingleElementInASortedArray();
        System.out.println(S.singleNonDuplicate(new int[]{1,1,2,3,3,4,4,8,8}));
        System.out.println(S.singleNonDuplicate(new int[]{3,3,7,7,10,11,11}));
    }
}
