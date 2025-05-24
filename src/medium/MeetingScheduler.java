package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class MeetingScheduler {
    public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        int size1 = slots1.length;
        int size2 = slots2.length;
        TreeMap<Integer, Integer> times = getMap(slots1, size1, slots2, size2);
        int[] prev = null;
        int count = 0;
        for (int time : times.keySet()) {
            if (prev != null && prev[1] >= 2 && time - prev[0] > duration)
                return Arrays.asList(prev[0], prev[0] + duration);
            count += times.get(time);
            prev = new int[]{time, count};
        }
        return new ArrayList<>();
    }

    private TreeMap<Integer, Integer> getMap(int[][] slots1, int size1, int[][] slots2, int size2) {
        TreeMap<Integer,Integer> times = new TreeMap<>();
        for (int i = 0; i < size1; i++) {
            int start = slots1[i][0];
            int end = slots1[i][1];
            times.put(start, times.getOrDefault(start, 0) + 1);
            times.put(end+1, times.getOrDefault(end+1, 0) - 1);
        }
        for (int i = 0; i < size2; i++) {
            int start = slots2[i][0];
            int end = slots2[i][1];
            times.put(start, times.getOrDefault(start, 0) + 1);
            times.put(end+1, times.getOrDefault(end+1, 0) - 1);
        }
        return times;
    }

    public static void main(String[] args) {
        MeetingScheduler M = new MeetingScheduler();
        System.out.println(M.minAvailableDuration(new int[][]{{10,50},{60,120},{140,210}}, new int[][]{{0,15},{60,70}}, 8));
        System.out.println(M.minAvailableDuration(new int[][]{{10,50},{60,120},{140,210}}, new int[][]{{0,15},{60,70}}, 12));
    }
}
