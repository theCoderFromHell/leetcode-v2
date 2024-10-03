package medium;

import java.util.HashMap;
import java.util.HashSet;

public class FindTheMaximumNumberOfElementsInSubset {
    public int maximumLength(int[] nums) {
        int N = nums.length;
        HashMap<Integer,Integer> values = new HashMap<>();
        for (int i = 0; i < N; i++)
            values.put(nums[i], values.getOrDefault(nums[i], 0) + 1);
        int maxLength = 1;
        if (values.containsKey(1) && values.get(1) > 0)
            maxLength = (values.get(1) % 2 == 0) ? values.get(1) - 1 : values.get(1);
        for (int i = 0; i < N; i++) {
            int smallest = nums[i];
            if (smallest == 1)
                continue;
            int length = 1;
            while (values.get(smallest) >= 2 && values.containsKey(smallest*smallest)) {
                length += 2;
                smallest = smallest * smallest;
            }
            maxLength = Math.max(maxLength, length);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        FindTheMaximumNumberOfElementsInSubset F = new FindTheMaximumNumberOfElementsInSubset();
        System.out.println(F.maximumLength(new int[]{5,4,1,2,2}));
        System.out.println(F.maximumLength(new int[]{1,3,2,4}));
        //System.out.println(F.maximumLength(new int[]{}));
        //System.out.println(F.maximumLength(new int[]{}));
    }
}
