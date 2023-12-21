package easy;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class ContainsDuplicateII {
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        int N = nums.length;
        if (N == 1)
            return false;
        HashMap<Integer, Stack<Integer>> occurances = new HashMap<>();
        for (int i = 0; i < N; i++) {
            Stack<Integer> stack = occurances.getOrDefault(nums[i], new Stack<>());
            if (!stack.empty() && i-stack.peek() <= k)
                return true;
            stack.add(i);
            occurances.put(nums[i], stack);
        }
        return false;
    }
    public static boolean containsNearbyDuplicateV2(int[] nums, int k) {
        int N = nums.length;
        if (N == 1)
            return false;
        HashSet<Integer> occurances = new HashSet<>();
        for (int i = 0; i < N; i++) {
            if (i > k)
                occurances.remove(nums[i-k-1]);
            if (occurances.contains(nums[i]))
                return true;
            occurances.add(nums[i]);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(containsNearbyDuplicateV2(new int[]{1,2,3,1}, 3));
        System.out.println(containsNearbyDuplicateV2(new int[]{1,0,1,1}, 1));
        System.out.println(containsNearbyDuplicateV2(new int[]{1,2,3,1,2,3}, 2));
    }
}
