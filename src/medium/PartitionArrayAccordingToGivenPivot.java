package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PartitionArrayAccordingToGivenPivot {
    public int[] pivotArray(int[] nums, int pivot) {
        int size = nums.length;
        List<Integer> less = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();
        List<Integer> more = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (nums[i] < pivot)
                less.add(nums[i]);
            else if (nums[i] > pivot)
                more.add(nums[i]);
            else
                equal.add(nums[i]);
        }
        int index = 0;
        for (int l : less)
            nums[index++] = l;
        for (int e : equal)
            nums[index++] = e;
        for (int m : more)
            nums[index++] = m;
        return nums;
    }

    public static void main(String[] args) {
        PartitionArrayAccordingToGivenPivot P = new PartitionArrayAccordingToGivenPivot();
        System.out.println(Arrays.toString(P.pivotArray(new int[]{9,12,5,10,14,3,10}, 10)));
        System.out.println(Arrays.toString(P.pivotArray(new int[]{-3,4,3,2}, 2)));
    }
}
