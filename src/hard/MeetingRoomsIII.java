package hard;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MeetingRoomsIII {
    public static int mostBooked(int n, int[][] meetings) {
        Arrays.sort(meetings, Comparator.comparingInt(o -> o[0]));
        PriorityQueue<Meeting> usedRooms = new PriorityQueue<>((o1, o2) -> {
            if (o1.endsAt == o2.endsAt)
                return o1.roomNo - o2.roomNo;
            return o1.endsAt - o2.endsAt;
        });
        PriorityQueue<Meeting> unusedRooms = new PriorityQueue<>(Comparator.comparingInt(o -> o.roomNo));
        for (int i = 0; i < n; i++)
            unusedRooms.add(new Meeting(i, 0, 0));
        int count = 1;
        int maxCountRoom = 0;
        for (int[] meeting : meetings) {
            while (!usedRooms.isEmpty() && usedRooms.peek().endsAt <= meeting[0]) {
                Meeting m = usedRooms.poll();
                unusedRooms.add(m);
            }
            Meeting m;
            if (unusedRooms.isEmpty()) {
                m = usedRooms.poll();
                int diff = m.endsAt - meeting[0];
                m.endsAt = meeting[1] + diff;
                m.meetingCount = m.meetingCount + 1;
                usedRooms.add(m);
            } else {
                m = unusedRooms.poll();
                m.endsAt = meeting[1];
                m.meetingCount = m.meetingCount + 1;
                usedRooms.add(m);
            }
            if (m.meetingCount > count || (m.meetingCount == count && m.roomNo < maxCountRoom)) {
                count = m.meetingCount;
                maxCountRoom = m.roomNo;
            }
        }
        return maxCountRoom;
    }

    public static void main(String[] args) {
        System.out.println(mostBooked(2, new int[][]{{0,10},{1,5},{2,7},{3,4}}));
        System.out.println(mostBooked(3, new int[][]{{1,20},{2,10},{3,5},{4,9},{6,8}}));
        System.out.println(mostBooked(4, new int[][]{{18,19},{3,12},{17,19},{2,13},{7,10}}));
        System.out.println(mostBooked(3, new int[][]{{3,7},{12,19},{16,17},{1,17},{5,6}}));
    }
}

class Meeting {
    int roomNo;
    int endsAt;
    int meetingCount;

    public Meeting(int roomNo, int endsAt) {
        this.roomNo = roomNo;
        this.endsAt = endsAt;
        this.meetingCount = 1;
    }

    public Meeting(int roomNo, int endsAt, int meetingCount) {
        this.roomNo = roomNo;
        this.endsAt = endsAt;
        this.meetingCount = meetingCount;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "roomNo=" + roomNo +
                ", endsAt=" + endsAt +
                ", meetingCount=" + meetingCount +
                '}';
    }
}
