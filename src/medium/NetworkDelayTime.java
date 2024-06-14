package medium;

import java.util.*;

public class NetworkDelayTime {
    public int networkDelayTime(int[][] times, int n, int k) {
        HashMap<Integer, List<int[]>> adjList = new HashMap<>();
        for (int[] time : times) {
            List<int[]> neighbors = adjList.getOrDefault(time[0], new ArrayList<>());
            neighbors.add(new int[]{time[1], time[2]});
            adjList.put(time[0], neighbors);
        }
        int[] minTime = new int[n+1];
        for (int i = 0; i <= n; i++)
            minTime[i] = Integer.MAX_VALUE;
        minTime[k] = 0;
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparing(a -> a[1]));
        queue.add(new int[] {k, 0});
        while (!queue.isEmpty()) {
            int[] data = queue.poll();
            int node = data[0];
            int timeFromSource = data[1];
            if (minTime[node] < timeFromSource)
                continue;
            List<int[]> neighbors = adjList.getOrDefault(node, new ArrayList<>());
            for (int[] value : neighbors) {
                int neighbor = value[0];
                int timeFromThisNode = value[1];
                if (timeFromSource + timeFromThisNode < minTime[neighbor]) {
                    minTime[neighbor] = timeFromSource + timeFromThisNode;
                    queue.add(new int[]{neighbor, minTime[neighbor]});
                }
            }
        }
        int maxTime = minTime[1];
        for (int i = 1; i <= n; i++) {
            if (minTime[i] == Integer.MAX_VALUE)
                return -1;
            maxTime = Math.max(maxTime, minTime[i]);
        }
        return maxTime;
    }
}
