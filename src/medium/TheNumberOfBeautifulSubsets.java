package medium;

import java.util.Arrays;
import java.util.HashMap;

public class TheNumberOfBeautifulSubsets {
    public int beautifulSubsets(int[] nums, int k) {
        int N = nums.length;
        Arrays.sort(nums);
        HashMap<Integer,Integer> exists = new HashMap<>();
        return solve(nums, N, k, exists, 0) - 1;
    }

    private int solve(int[] nums, int N, int k, HashMap<Integer, Integer> exists, int index) {
        if(index == N)
            return 1;
        int total;
        total = solve(nums, N, k, exists, index+1);
        if (!exists.containsKey(nums[index] - k)) {
            exists.put(nums[index], exists.getOrDefault(nums[index], 0) + 1);
            total += solve(nums, N, k, exists, index+1);
            exists.put(nums[index], exists.getOrDefault(nums[index], 0) - 1);
            if (exists.get(nums[index]) == 0)
                exists.remove(nums[index]);
        }
        return total;
    }

    public static void main(String[] args) {
        TheNumberOfBeautifulSubsets t = new TheNumberOfBeautifulSubsets();
        System.out.println(t.beautifulSubsets(new int[]{2,4,6}, 2));
        System.out.println(t.beautifulSubsets(new int[]{1}, 1));
    }
}
