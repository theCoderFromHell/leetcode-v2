package hard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class OptimizeWaterDistributionInAVillage {
    public static int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparing(o-> o.cost));
        boolean[] visited = new boolean[n + 1];
        visited[0] = true;
        int N = wells.length;
        for (int i = 0; i < N; i++)
            pq.add(new Edge(0, i + 1, wells[i]));
        List<List<Edge>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++)
            adj.add(new ArrayList<>());
        for (int[] pipe : pipes) {
            adj.get(pipe[0]).add(new Edge(pipe[0], pipe[1], pipe[2]));
            adj.get(pipe[1]).add(new Edge(pipe[1], pipe[0], pipe[2]));
        }
        int count = n;
        int cost = 0;
        while(!pq.isEmpty() && count > 0) {
            Edge e = pq.poll();
            if (!visited[e.destination]) {
                cost += e.cost;
                count--;
                visited[e.destination] = true;
                List<Edge> neighbours = adj.get(e.destination);
                for (Edge edge : neighbours) {
                    if (!visited[edge.destination])
                        pq.add(edge);
                }
            }
        }
        return cost;
    }

    public static void main(String[] args) {
        System.out.println(minCostToSupplyWater(3, new int[]{1,2,2}, new int[][]{{1,2,1},{2,3,1}}));
        System.out.println(minCostToSupplyWater(2, new int[]{1,1}, new int[][]{{1,2,1},{1,2,2}}));
    }
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