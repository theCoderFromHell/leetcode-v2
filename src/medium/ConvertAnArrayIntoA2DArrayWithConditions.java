package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConvertAnArrayIntoA2DArrayWithConditions {
    public List<List<Integer>> findMatrix(int[] nums) {
        HashMap<Integer,Integer> map = new HashMap<>();
        int N = nums.length;
        for (int num : nums)
            map.put(num, map.getOrDefault(num, 0) + 1);
        List<List<Integer>> result = new ArrayList<>();
        while (N > 0) {
            List<Integer> curr = new ArrayList<>();
            for (int number : map.keySet()) {
                if (map.get(number) > 0) {
                    curr.add(number);
                    N--;
                    map.put(number, map.getOrDefault(number, 0) - 1);
                }
            }
            result.add(curr);
        }
        return result;
    }
}
