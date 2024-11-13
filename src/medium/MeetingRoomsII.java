package medium;

import java.util.*;

public class MeetingRoomsII {
    public int minMeetingRoomsV2(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparing(o -> o[0]));
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.naturalOrder());
        int count = 0;
        for (int[] interval : intervals) {
            if (pq.isEmpty() || pq.peek() > interval[0])
                count++;
            else
                pq.poll();
            pq.add(interval[1]);
        }
        return count;
    }

    public int minMeetingRooms(int[][] intervals) {
        int N = intervals.length;
        List<int[]> events = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            events.add(new int[]{intervals[i][0], 0});
            events.add(new int[]{intervals[i][1], 1});
        }
        events.sort((o1, o2) -> {
            if (o1[0] == o2[0])
                return o2[1] - o1[1];
            return o1[0] - o2[0];
        });
        int curr = 0;
        int result = 0;
        for (int i = 0; i < 2 * N; i++) {
            if (events.get(i)[1] == 0)
                curr++;
            else if (events.get(i)[1] == 1)
                curr--;
            result = Math.max(result, curr);
        }
        return result;
    }

    public static void main(String[] args) {
        MeetingRoomsII M = new MeetingRoomsII();
        System.out.println(M.minMeetingRooms(new int[][]{{0,30},{5,10},{15,20}}));
    }
}
