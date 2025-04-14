package medium;

import java.util.HashMap;

public class SplitArrayIntoConsecutiveSubsequences {
    public boolean isPossible(int[] nums) {
        int size = nums.length;
        HashMap<Integer, Integer> frequency = new HashMap<>();
        HashMap<Integer, Integer> subs = new HashMap<>();
        for (int i = 0; i < size; i++)
            frequency.put(nums[i], frequency.getOrDefault(nums[i], 0) + 1);
        for (int i = 0; i < size; i++) {
            if (frequency.getOrDefault(nums[i], 0) == 0)
                continue;
            if (subs.getOrDefault(nums[i]-1, 0) > 0) {
                subs.put(nums[i]-1, subs.getOrDefault(nums[i]-1, 0) - 1);
                subs.put(nums[i], subs.getOrDefault(nums[i], 0) + 1);
            } else if (frequency.getOrDefault(nums[i]+1, 0) > 0 && frequency.getOrDefault(nums[i]+2, 0) > 0) {
                subs.put(nums[i]+2, subs.getOrDefault(nums[i]+2, 0) + 1);
                frequency.put(nums[i]+1, frequency.getOrDefault(nums[i]+1, 0) - 1);
                frequency.put(nums[i]+2, frequency.getOrDefault(nums[i]+2, 0) - 1);
            } else
                return false;
            frequency.put(nums[i], frequency.getOrDefault(nums[i], 0) - 1);
        }
        return true;
    }

    public static void main(String[] args) {
        SplitArrayIntoConsecutiveSubsequences S = new SplitArrayIntoConsecutiveSubsequences();
        System.out.println(S.isPossible(new int[]{1,2,3,3,4,5}));
        System.out.println(S.isPossible(new int[]{1,2,3,3,4,4,5,5}));
        System.out.println(S.isPossible(new int[]{1,2,3,4,4,5}));
    }
}
