package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CombinationSumII {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        LinkedList<Integer> combination = new LinkedList<>();
        solve(candidates, candidates.length, 0, target, combination, result);
        return result;
    }

    private void solve(int[] candidates, int N, int index, int target, LinkedList<Integer> combination, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(combination));
            return;
        }
        for (int i = index; i < N; i++) {
            if (i > index && candidates[i-1] == candidates[i])
                continue;
            if (candidates[i] > target)
                break;
            combination.addLast(candidates[i]);
            solve(candidates, N, i+1, target - candidates[i], combination, result);
            combination.removeLast();
        }
    }

    public static void main(String[] args) {
        CombinationSumII c = new CombinationSumII();
        System.out.println(c.combinationSum2(new int[]{10,1,2,7,6,1,5}, 8));
        System.out.println(c.combinationSum2(new int[]{2,5,2,1,2}, 5));
        //System.out.println(c.combinationSum2(new int[]{}, ));


    }
}
