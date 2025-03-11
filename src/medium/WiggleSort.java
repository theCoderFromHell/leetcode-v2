package medium;

import java.util.Arrays;

public class WiggleSort {
    public void wiggleSort(int[] nums) {
        int size = nums.length;
        for (int i = 0; i < size-1; i++) {
            if (i % 2 == 0 && nums[i] > nums[i+1] || i % 2 == 1 && nums[i] < nums[i+1]) {
                int temp = nums[i];
                nums[i] = nums[i + 1];
                nums[i + 1] = temp;
            }
        }
    }

    public static void main(String[] args) {
        WiggleSort w = new WiggleSort();

        // Test case 1
        int[] nums1 = {3, 5, 2, 1, 6, 4};
        System.out.println("Original array: " + Arrays.toString(nums1));
        w.wiggleSort(nums1);
        System.out.println("Wiggled array: " + Arrays.toString(nums1));

        // Test case 2
        int[] nums2 = {1, 1, 2, 2, 3, 3};
        System.out.println("Original array: " + Arrays.toString(nums2));
        w.wiggleSort(nums2);
        System.out.println("Wiggled array: " + Arrays.toString(nums2));

        // Test case 3
        int[] nums3 = {9, 1, 7, 3, 2, 6};
        System.out.println("Original array: " + Arrays.toString(nums3));
        w.wiggleSort(nums3);
        System.out.println("Wiggled array: " + Arrays.toString(nums3));
    }
    
}
