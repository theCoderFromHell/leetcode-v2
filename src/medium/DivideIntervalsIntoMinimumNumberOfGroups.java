package medium;

import java.util.*;

public class DivideIntervalsIntoMinimumNumberOfGroups {
    public int minGroups(int[][] intervals) {
        int N = intervals.length;
        int low = Integer.MAX_VALUE, high = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            low = Math.min(low, intervals[i][0]);
            high = Math.max(high, intervals[i][1]);
        }
        int[] range = new int[high + 2];
        for (int i = 0; i < N; i++) {
            range[intervals[i][0]]++;
            range[intervals[i][1] + 1]--;
        }
        int maxOverlaps = 0;
        int currentOverlap = 0;
        for (int i = low; i <= high+1; i++) {
            currentOverlap += range[i];
            maxOverlaps = Math.max(maxOverlaps, currentOverlap);
        }
        return maxOverlaps;
    }

    public static void main(String[] args) {
        DivideIntervalsIntoMinimumNumberOfGroups D = new DivideIntervalsIntoMinimumNumberOfGroups();
        System.out.println(D.minGroups(new int[][]{{5,10},{6,8},{1,5},{2,3},{1,10}}));
    }
}
