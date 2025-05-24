package medium;

import java.util.Arrays;

public class RemoveCoveredIntervals {
    public int removeCoveredIntervals(int[][] intervals) {
        int size = intervals.length;
        Arrays.sort(intervals, (o1, o2) -> {
            if (o1[0] == o2[0])
                return o2[1] - o1[1];
            return o1[0] - o2[0];
        });
        int count = 0;
        for (int i = 1; i < size; i++) {
            int a = intervals[i][0];
            int b = intervals[i][1];
            for (int j = 0; j < i; j++) {
                int c = intervals[j][0];
                int d = intervals[j][1];
                if (a >= c && d >= b) {
                    count++;
                    break;
                }
            }
        }
        return size - count;
    }

    public static void main(String[] args) {
        RemoveCoveredIntervals R = new RemoveCoveredIntervals();
        System.out.println(R.removeCoveredIntervals(new int[][]{{1,2},{1,4},{3,4}}));
        System.out.println(R.removeCoveredIntervals(new int[][]{{1,4},{3,6},{2,8}}));
        System.out.println(R.removeCoveredIntervals(new int[][]{{1,4},{2,3}}));
    }
}
