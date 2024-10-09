package medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MaximumNumberOfEventsThatCanBeAttended {
    public int maxEvents(int[][] events) {
        int N = events.length;
        Arrays.sort(events, Comparator.comparingInt(a -> a[0]));
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        int j = 0;
        int result = 0;
        for (int i = 0; i < 100001; i++) {
            while (j < N && events[j][0] == i)
                pq.add(events[j++]);
            if (pq.isEmpty()) {
                if (j == N)
                    break;
                i = events[j][0] - 1;
            }
            while (!pq.isEmpty() && pq.peek()[1] < i)
                pq.poll();
            if (!pq.isEmpty()) {
                pq.poll();
                result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        MaximumNumberOfEventsThatCanBeAttended M = new MaximumNumberOfEventsThatCanBeAttended();
        System.out.println(M.maxEvents(new int[][]{{1,2},{2,3},{3,4}}));
        System.out.println(M.maxEvents(new int[][]{{1,2},{2,3},{3,4},{1,2}}));
    }
}
