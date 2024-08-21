package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Permutations {
    public List<List<Integer>> permute(int[] nums) {
        if(null == nums || nums.length == 0)
            return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        Integer[] numbers = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++)
            numbers[i] = nums[i];
        compute(numbers, nums.length, 0, result);
        return result;
    }

    private void compute(Integer[] nums, int length, int idx, List<List<Integer>> result) {
        if(idx == length) {
            result.add(new ArrayList<>(Arrays.asList(nums)));
            return;
        }
        for(int i=idx; i<length; i++) {
            swap(nums, i, idx);
            compute(nums, length, idx+1, result);
            swap(nums, i, idx);
        }
    }

    private void swap(Integer[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }
}
