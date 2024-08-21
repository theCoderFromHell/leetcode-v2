package medium;

import java.util.*;

public class PermutationsII {
    public List<List<Integer>> permuteUnique(int[] nums) {
        int N = nums.length;
        List<List<Integer>> result = new ArrayList<>();
        HashMap<Integer,Integer> frequency = new HashMap<>();
        for (int i = 0; i < N; i++)
            frequency.put(nums[i], frequency.getOrDefault(nums[i], 0) + 1);
        List<Integer> combo = new LinkedList<>();
        compute(frequency, combo, N, result);
        return result;
    }

    private void compute(HashMap<Integer,Integer> frequency, List<Integer> combo, int N, List<List<Integer>> result) {
        if (combo.size() == N) {
            result.add(new ArrayList<>(combo));
        }
        for (int num : frequency.keySet()) {
            int count = frequency.get(num);
            if (count == 0)
                continue;
            frequency.put(num, count-1);
            combo.addLast(num);
            compute(frequency, combo, N, result);
            combo.removeLast();
            frequency.put(num, count);
        }
    }

    public static void main(String[] args) {
        PermutationsII P = new PermutationsII();
        System.out.println(P.permuteUnique(new int[]{1,1,2}));
        System.out.println(P.permuteUnique(new int[]{1,2,3}));
        System.out.println(P.permuteUnique(new int[]{0,1,0,0,9}));
    }
}
