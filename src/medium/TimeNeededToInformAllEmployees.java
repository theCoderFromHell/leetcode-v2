package medium;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TimeNeededToInformAllEmployees {
    public int numOfMinutes(int N, int headID, int[] manager, int[] informTime) {
        List<List<Integer>> adjList = new ArrayList<>(N);
        for (int i = 0; i < N; i++)
            adjList.add(new ArrayList<>());
        for (int i = 0; i < N; i++) {
            if (manager[i] != -1)
                adjList.get(manager[i]).add(i);
        }
        Queue<InfoTime> queue = new LinkedList<>();
        queue.add(new InfoTime(headID, 0));
        int maxTime = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                InfoTime staff = queue.poll();
                if (adjList.get(staff.id) != null && !adjList.isEmpty()) {
                    int timeRequiredToInform = staff.time + informTime[staff.id];
                    for (int reporter : adjList.get(staff.id)) {
                        queue.add(new InfoTime(reporter, timeRequiredToInform));
                        maxTime = Math.max(maxTime, timeRequiredToInform);
                    }
                }
            }
        }
        return maxTime;
    }

    class InfoTime{
        int id;
        int time;

        public InfoTime(int id, int time) {
            this.id = id;
            this.time = time;
        }

        @Override
        public String toString() {
            return "InfoTime{" +
                    "id=" + id +
                    ", time=" + time +
                    '}';
        }
    }

    public static void main(String[] args) {
        TimeNeededToInformAllEmployees T = new TimeNeededToInformAllEmployees();
        System.out.println(T.numOfMinutes(6, 2, new int[]{2,2,-1,2,2,2}, new int[]{0,0,1,0,0,0}));
    }
}
