package easy;

import java.util.HashSet;

public class RemoveDuplicatesFromSortedArray {

    public static int removeDuplicatesV1(int[] nums) {
        int size = nums.length;
        int index = 0;
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < size; i++) {
            if (!set.contains(nums[i])) {
                set.add(nums[i]);
                swap(nums, i, index);
                index++;
            }
        }
        for (int i = 0; i < index; i++) {
            System.out.print(nums[i] + " ");
        }
        return index;

    }
    public static int removeDuplicatesV2(int[] nums) {
        int size = nums.length;
        int index = 0;
        int[] hash = new int[201];
        for (int i = 0; i < size; i++) {
            if (hash[nums[i] + 100] == 0) {
                hash[nums[i] + 100] = 1;
                swap(nums, i, index);
                index++;
            }
        }
        for (int i = 0; i < index; i++) {
            System.out.print(nums[i] + " ");
        }
        return index;
    }
    public static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int result = removeDuplicatesV2(new int[]{0,0,1,1,1,2,2,3,3,4});
        System.out.println("Result = " + result);
    }
}
