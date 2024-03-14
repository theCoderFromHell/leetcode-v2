package easy;

import java.util.Arrays;
import java.util.Comparator;

public class MeetingRooms {
    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        int N = intervals.length;
        for (int i = 1; i < N; i++)
            if (intervals[i][0] < intervals[i - 1][1])
                return false;
        return true;
    }
}
