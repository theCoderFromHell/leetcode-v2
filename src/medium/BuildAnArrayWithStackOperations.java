package medium;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BuildAnArrayWithStackOperations {
    public List<String> buildArray(int[] target, int n) {
        List<String> result = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        int maximum = Integer.MIN_VALUE;
        for (int num : target) {
            set.add(num);
            maximum = Math.max(maximum, num);
        }
        for (int i = 1; i <= Math.min(maximum, n); i++) {
            result.add("Push");
            if (!set.contains(i)) {
                result.add("Pop");
            }
        }
        return result;
    }

    public static void main(String[] args) {
        BuildAnArrayWithStackOperations b = new BuildAnArrayWithStackOperations();
        System.out.println(b.buildArray(new int[]{1, 3}, 3));
        System.out.println(b.buildArray(new int[]{1, 2, 3}, 3));
        System.out.println(b.buildArray(new int[]{1, 2}, 4));
    }
}
