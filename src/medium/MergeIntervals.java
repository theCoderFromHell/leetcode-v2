package medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

public class MergeIntervals {
    public int[][] merge(int[][] intervals) {
        if(null == intervals || intervals.length == 0 || intervals[0].length <= 1)
            return intervals;
        Stack<int[]> stack = new Stack<>();
        int N = intervals.length;
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        stack.add(new int[]{intervals[0][0], intervals[0][1]});
        for(int i = 1; i < N ; i++) {
            int[] pair = stack.peek();
            int rangeMin = intervals[i][0];
            int rangeMax = intervals[i][1];
            if(!outOfRange(new int[] {pair[0], pair[1]}, intervals[i])) {
                rangeMin = Math.min(pair[0], intervals[i][0]);
                rangeMax = Math.max(pair[1], intervals[i][1]);
                stack.pop();
            }
            stack.add(new int[]{rangeMin, rangeMax});
        }
        int[][] result =  new int[stack.size()][2];
        int idx = 0;
        for(int[] pair : stack) {
            int[] arrayPair = new int[2];
            arrayPair[0] = pair[0];
            arrayPair[1] = pair[1];
            result[idx++] = arrayPair;
        }
        return result;
    }
    private boolean outOfRange(int[] range, int[] interval) {
        return range[1] < interval[0] || interval[1] < range[0];
    }

    public static void main(String[] args) {
        MergeIntervals M = new MergeIntervals();
        System.out.println(Arrays.deepToString(M.merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}})));
    }
}
