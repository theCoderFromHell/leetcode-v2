package medium;

import java.util.*;

public class MinimumTimeDifference {
    public int findMinDifference(List<String> timePoints) {
        Set<Integer> times = new TreeSet<>();
        int N = timePoints.size();
        for (int i = 0; i < N; i++) {
            String[] time = timePoints.get(i).split(":");
            int currTime = 60 * Integer.parseInt(time[0]) + Integer.parseInt(time[1]);
            if (times.contains(currTime))
                return 0;
            times.add(currTime);
            if (currTime <= 720)
                times.add(currTime + 1440);
        }
        Integer prev = null;
        int minimumDiff = Integer.MAX_VALUE;
        for (Integer time : times) {
            if (prev != null)
                minimumDiff = Math.min(minimumDiff, Math.abs(time - prev));
            prev = time;
        }
        return minimumDiff;
    }

    public static void main(String[] args) {
        MinimumTimeDifference m = new MinimumTimeDifference();
        //System.out.println(m.findMinDifference(Arrays.asList("23:59","00:00")));
        System.out.println(m.findMinDifference(Arrays.asList("00:00","04:00","22:00")));
    }
}
