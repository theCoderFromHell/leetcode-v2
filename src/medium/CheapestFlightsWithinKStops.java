package medium;

import java.util.*;

public class CheapestFlightsWithinKStops {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        HashMap<Integer, List<int[]>> adjList = new HashMap<>();
        for (int[] flight : flights) {
            List<int[]> neighbors = adjList.getOrDefault(flight[0], new ArrayList<>());
            neighbors.add(new int[]{flight[1], flight[2]});
            adjList.put(flight[0], neighbors);
        }
        int[] minFare = new int[n+1];
        for (int i = 0; i <= n; i++)
            minFare[i] = Integer.MAX_VALUE;
        minFare[src] = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{src, -1, 0});
        while (!queue.isEmpty()) {
            int[] top = queue.poll();
            int node = top[0];
            int stops = top[1];
            int cost =  top[2];
            if (node == dst)
                continue;
            if (stops >= k)
                continue;
            if (!adjList.containsKey(node))
                continue;
            for (int[] neighbors : adjList.get(node)) {
                int neighbor = neighbors[0];
                int fare = neighbors[1];
                if (cost + fare < minFare[neighbor]) {
                    minFare[neighbor] = cost + fare;
                    queue.add(new int[]{neighbor, stops+1, minFare[neighbor]});
                }
            }
        }
        return minFare[dst] == Integer.MAX_VALUE ? -1 : minFare[dst];
    }

    public static void main(String[] args) {
        CheapestFlightsWithinKStops c = new CheapestFlightsWithinKStops();
        System.out.println(c.findCheapestPrice(4, new int[][]{
                {0,1,1},{0,2,5},{1,2,1},{2,3,1}
        }, 0, 3, 1));
    }
}
