package medium;

import java.util.HashMap;
import java.util.Map;

public class FruitIntoBaskets {
    public static int totalFruit(int[] fruits) {
        int N = fruits.length;
        if (N <= 2)
            return N;
        int start = 0, end = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(fruits[0], 1);
        int maxLength = 1;

        while (end < N && start <= end) {
            if ((map.isEmpty() || map.keySet().size() <= 2)) {
                maxLength = Math.max(maxLength, end-start+1);
                end++;
                if (end < N)
                    map.put(fruits[end], map.getOrDefault(fruits[end], 0) + 1);

            }
            while (!map.isEmpty() && map.keySet().size() > 2 && start <= end) {
                if (map.containsKey(fruits[start])) {
                    if (map.get(fruits[start]) == 1)
                        map.remove(fruits[start]);
                    else
                        map.put(fruits[start], map.get(fruits[start]) - 1);
                }
                start++;
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(totalFruit(new int[]{1,2,1}));
        System.out.println(totalFruit(new int[]{0,1,2,2}));
        System.out.println(totalFruit(new int[]{1,2,3,2,2}));
    }
}
