package medium;

import java.util.Arrays;
import java.util.HashMap;

public class DivideArrayInSetsOfKConsecutiveNumbers {
    public boolean isPossibleDivide(int[] nums, int k) {
        int size = nums.length;
        if (size % k != 0)
            return false;
        HashMap<Integer, Integer> frequency = new HashMap<>();
        Arrays.sort(nums);
        for (int i = 0; i < size; i++)
            frequency.put(nums[i], frequency.getOrDefault(nums[i], 0) + 1);
        for (int i = 0; i < size; i++) {
            if (!frequency.containsKey(nums[i]) || frequency.get(nums[i]) == 0)
                continue;
            int count = frequency.get(nums[i]);
            if (count == 1)
                frequency.remove(nums[i]);
            else
                frequency.put(nums[i], count - 1);
            for (int j = 1; j < k; j++) {
                if (frequency.containsKey(nums[i] + j)) {
                    frequency.put(nums[i] + j, frequency.get(nums[i] + j) - 1);
                    if (frequency.get(nums[i] + j) == 0)
                        frequency.remove(nums[i] + j);
                    if (frequency.isEmpty())
                        return true;
                } else
                    return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        DivideArrayInSetsOfKConsecutiveNumbers D = new DivideArrayInSetsOfKConsecutiveNumbers();
        System.out.println(D.isPossibleDivide(new int[]{3,3,2,2,1,1}, 3));
        System.out.println(D.isPossibleDivide(new int[]{1,2,3,3,4,4,5,6}, 4));
        System.out.println(D.isPossibleDivide(new int[]{3,2,1,2,3,4,3,4,5,9,10,11}, 3));
        System.out.println(D.isPossibleDivide(new int[]{1,2,3,4}, 3));
    }
}
