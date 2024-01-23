package medium;

import java.util.Arrays;

public class SingleElementInASortedArray {
    public static int singleNonDuplicate(int[] nums) {
        if(nums.length == 1)
            return nums[0];
        return search(nums, 0, nums.length-1);
    }

    private static int search(int[] nums, int start, int end) {
//        int mid = start + (end-start)/2;
//        switch (mid-start) {
//            case
//        }
        return start;
    }

    public static void main(String[] args) {

    }
}
