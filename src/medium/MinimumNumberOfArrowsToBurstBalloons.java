package medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class MinimumNumberOfArrowsToBurstBalloons {
    public static int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0)
            return -1;
        int N = points.length;
        Arrays.sort(points, Comparator.comparingInt(o -> o[0]));
        Stack<List<Integer>> stack = new Stack<>();
        stack.add(Arrays.asList(points[0][0], points[0][1]));
        for (int i = 1; i < N; i++) {
            List<Integer> peek = stack.peek();
            if (!outOfRange(new int[]{peek.get(0), peek.get(1)}, points[i] )) {
                int rangeMin = Math.max(peek.get(0), points[i][0]);
                int rangeMax = Math.min(peek.get(1), points[i][1]);
                stack.pop();
                stack.push(Arrays.asList(rangeMin, rangeMax));
            } else
                stack.push(Arrays.asList(points[i][0], points[i][1]));
        }
        return stack.size();
    }

    private static boolean outOfRange(int[] range, int[] interval) {
        return range[1] < interval[0] || range[0] > interval[1];
    }

    public static void main(String[] args) {

    }
}
