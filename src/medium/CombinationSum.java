package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if(candidates == null || candidates.length == 0 || target == 0)
            return result;
        int length = candidates.length;
        Arrays.sort(candidates);
        Stack<Integer> combo =  new Stack<>();
        findCombinations(candidates, length, target, 0, 0, combo, result);
        return result;
    }

    private void findCombinations(int[] candidates, int length, int target, int currentSum, int index, Stack<Integer> combo, List<List<Integer>> result) {
        if(currentSum == target) {
            result.add(new ArrayList<>(combo));
            return;
        }
        for(int i=index; i<length; i++) {
            if(currentSum + candidates[i] > target)
                break;
            currentSum += candidates[i];
            combo.push(candidates[i]);
            findCombinations(candidates, length, target, currentSum, i, combo, result);
            int last = combo.pop();
            currentSum -= last;
        }
    }
}
