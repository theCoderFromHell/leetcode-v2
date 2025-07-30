package medium;

import java.util.Arrays;
import java.util.HashMap;

public class DistinctNumbersInEachSubarray {
    public int[] distinctNumbers(int[] nums, int k) {
        int size = nums.length;
        HashMap<Integer,Integer> count = new HashMap<>();
        int[] result = new int[size - k + 1];
        for (int i = 0; i < k; i++)
            count.put(nums[i], count.getOrDefault(nums[i], 0) + 1);
        int index = 0;
        while (index + k < size) {
            result[index] = count.size();
            count.put(nums[index + k], count.getOrDefault(nums[index + k], 0) + 1);
            int c = count.get(nums[index]);
            if (c > 1)
                count.put(nums[index], count.getOrDefault(nums[index], 0) - 1);
            else
                count.remove(nums[index]);
            index++;
        }
        result[index] = count.size();
        return result;
    }

    public static void main(String[] args) {
        DistinctNumbersInEachSubarray D = new DistinctNumbersInEachSubarray();
        System.out.println(Arrays.toString(D.distinctNumbers(new int[]{1,2,3,2,2,1,3}, 3)));
        System.out.println(Arrays.toString(D.distinctNumbers(new int[]{1,1,1,1,2,3,4}, 4)));
    }
}
