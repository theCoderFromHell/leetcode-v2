package medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class CountGoodMeals {
    public int countPairs(int[] deliciousness) {
        int N = deliciousness.length;
        if (N == 1)
            return 0;
        int MOD = 1000000007;
        HashMap<Integer,Integer> odds = new HashMap<>();
        HashMap<Integer,Integer> evens = new HashMap<>();
        int result = 0;
        for (int food : deliciousness) {
            if (food == 0)
                result = (result + odds.getOrDefault(1,0)) % MOD;
            if (food == 1)
                result = (result + evens.getOrDefault(0,0)) % MOD;
            HashMap<Integer,Integer> lookupMap = (food % 2 == 0) ? evens : odds;
            for (int i = 1; i < 22; i++) {
                int total = 1 << i;
                if (lookupMap.containsKey(total - food)) {
                    result = (result + lookupMap.getOrDefault(total - food, 0)) % MOD;
                }
                total = 2 * total;
            }
            lookupMap.put(food, lookupMap.getOrDefault(food, 0) + 1);
        }
        return result;
    }

    public static void main(String[] args) {
        CountGoodMeals c = new CountGoodMeals();
        System.out.println(c.countPairs(new int[]{1,3,5,7,9}));
        System.out.println(c.countPairs(new int[]{1,1,1,3,3,3,7}));
    }
}
