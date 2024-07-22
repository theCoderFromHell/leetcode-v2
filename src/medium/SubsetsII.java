package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class SubsetsII {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        int N = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        Stack<Integer> combination = new Stack<>();
        solve(nums, N, 0, combination, result);
        return result;
    }

    private void solve(int[] nums, int N, int index, Stack<Integer> combination, List<List<Integer>> result) {
        if (index == N) {
            result.add(new ArrayList<>(combination));
            return;
        }
        combination.add(nums[index]);
        solve(nums, N, index+1, combination, result);
        combination.pop();

        while (index+1 < N && nums[index] == nums[index+1])
            index++;
        solve(nums, N, index+1, combination, result);
    }

    public static void main(String[] args) {
        SubsetsII S = new SubsetsII();
        System.out.println(S.subsetsWithDup(new int[]{1,2,2}));
        System.out.println(S.subsetsWithDup(new int[]{0}));
    }
}
