package medium;

import java.util.ArrayList;
import java.util.List;

public class CombinationSumIII {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> current = new ArrayList<>();
        backtrack(k, n, 0, 1, current, result);
        return result;
    }

    private void backtrack(int k, int n, int sum, int low, List<Integer> current, List<List<Integer>> result) {
        if (sum == n && current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (current.size() == k)
            return;
        for (int i = low; i <= 9; i++) {
            current.addLast(i);
            sum += i;
            backtrack(k, n, sum, i+1, current, result);
            sum -= i;
            current.removeLast();
        }
    }

    public static void main(String[] args) {
        CombinationSumIII C = new CombinationSumIII();
        System.out.println(C.combinationSum3(3, 7));
        System.out.println(C.combinationSum3(3, 9));
        System.out.println(C.combinationSum3(4, 1));
        System.out.println(C.combinationSum3(4, 15));
    }
}
