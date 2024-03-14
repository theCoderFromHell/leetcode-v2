package medium;

import common.Node;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MeetingRoomsII {
    public int minMeetingRooms(int[][] intervals) {
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
}
