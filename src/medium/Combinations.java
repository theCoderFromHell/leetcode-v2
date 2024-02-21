package medium;

import java.util.ArrayList;
import java.util.List;

public class Combinations {
    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        generate(n, k, result, 1, 0, new ArrayList<>());
        return result;
    }

    private static void generate(int n, int k, List<List<Integer>> result, int value, int index, ArrayList<Integer> curr) {
        if (index == k) {
            result.add(new ArrayList<>(curr));
            return;
        }
        for (int i = value; i <= n; i++) {
            curr.add(i);
            generate(n, k, result, i+1, index+1, curr);
            curr.removeLast();
        }
    }

    public static void main(String[] args) {
        System.out.println(combine(4,2));
        System.out.println(combine(1,1));
    }
}
