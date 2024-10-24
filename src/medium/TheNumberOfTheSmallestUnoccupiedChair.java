package medium;

import java.util.*;

public class TheNumberOfTheSmallestUnoccupiedChair {
    public int smallestChair(int[][] times, int targetFriend) {
        int N = times.length;
        List<Event> events = new ArrayList<>();
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        HashMap<Integer,Integer> chairs = new HashMap<>();
        for (int i = 0; i < N; i++) {
            pq.add(i);
            events.add(new Event(0, times[i][0], i));
            events.add(new Event(1, times[i][1], i));
        }
        events.sort((o1, o2) -> {
            if (o1.time == o2.time)
                return o2.type - o1.type;
            return o1.time - o2.time;
        });
        for (int i = 0; i < 2 * N; i++) {
            Event event = events.get(i);
            if (event.type == 0) {
                int chair = pq.poll();
                if (event.friend == targetFriend)
                    return chair;
                chairs.put(event.friend, chair);
            } else if (event.type == 1) {
                int chair = chairs.get(event.friend);
                chairs.remove(event.friend);
                pq.add(chair);
            }
        }
        return -1;
    }

    class Event {
        int time;
        int type;
        int friend;

        public Event(int type, int time, int friend) {
            this.type = type;
            this.time = time;
            this.friend = friend;
        }

        public int getTime() {
            return time;
        }
    }

    public static void main(String[] args) {
        TheNumberOfTheSmallestUnoccupiedChair T = new TheNumberOfTheSmallestUnoccupiedChair();
        System.out.println(T.smallestChair(new int[][]{{1,4},{2,3},{4,6}}, 1));
        System.out.println(T.smallestChair(new int[][]{{3,10},{1,5},{2,6}}, 0));
    }

}
