package medium;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class MinimumAbsoluteDifferenceBetweenElementsWithConstraint {
    public int minAbsoluteDifference(List<Integer> nums, int x) {
        int N = nums.size();
        int minDiff = Integer.MAX_VALUE;
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = x; i < N; i++) {
            int value = nums.get(i);
            set.add(nums.get(i - x));
            Integer floor = set.floor(value);
            Integer ceiling = set.ceiling(value);
            if (floor != null)
                minDiff = Math.min(minDiff, Math.abs(value - floor));
            if (ceiling != null)
                minDiff = Math.min(minDiff, Math.abs(value - ceiling));
        }
        return minDiff;
    }

    public static void main(String[] args) {
        MinimumAbsoluteDifferenceBetweenElementsWithConstraint m = new MinimumAbsoluteDifferenceBetweenElementsWithConstraint();
        System.out.println(m.minAbsoluteDifference(Arrays.asList(4,3,2,4), 2));
        System.out.println(m.minAbsoluteDifference(Arrays.asList(5,3,2,10,15), 1));
    }
}
