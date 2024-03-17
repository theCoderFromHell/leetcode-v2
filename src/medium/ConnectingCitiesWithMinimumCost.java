package medium;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ConnectingCitiesWithMinimumCost {
    public int minimumCost(int n, int[][] connections) {
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparing(o-> o.cost));
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i <= n ; i++)
            adj.add(new ArrayList<>());
        for (int[] connection : connections) {
            adj.get(connection[0]).add(new Edge(connection[0], connection[1], connection[2]));
            adj.get(connection[1]).add(new Edge(connection[1], connection[0], connection[2]));
        }
        boolean[] visited = new boolean[n+1];
        visited[1] = true;
        pq.addAll(adj.get(1));
        int count = n-1;
        int cost = 0;
        while (!pq.isEmpty() && count > 0) {
            Edge edge = pq.poll();
            if (!visited[edge.destination]) {
                visited[edge.destination] = true;
                cost += edge.cost;
                count--;
                for (Edge e : adj.get(edge.destination)) {
                    if (!visited[e.destination])
                        pq.add(e);
                }
            }
        }
        return count == 0 ? cost : -1;
    }
    
    class Edge {
        int source;
        int destination;
        int cost;

        public Edge(int source, int destination, int cost) {
            this.source = source;
            this.destination = destination;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "source=" + source +
                    ", destination=" + destination +
                    ", cost=" + cost +
                    '}';
        }
    }
}
