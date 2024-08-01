package hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CriticalConnectionsInANetwork {
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        HashMap<Edge, Boolean> edges = new HashMap<>();
        Integer[] rank = new Integer[n];
        for (int i = 0; i < n; i++) {
            rank[i] = null;
            adjList.put(i, new ArrayList<>());
        }

        for (List<Integer> edge : connections) {
            int source = Math.min(edge.get(0), edge.get(1));
            int destination = Math.max(edge.get(0), edge.get(1));
            edges.put(new Edge(source, destination), true);

            List<Integer> neighborsOfSrc = adjList.get(source);
            neighborsOfSrc.add(destination);
            adjList.put(source, neighborsOfSrc);

            List<Integer> neighborsOfDest = adjList.get(destination);
            neighborsOfDest.add(source);
            adjList.put(destination, neighborsOfDest);
        }

        dfs(0, 0, adjList, rank, edges);
        List<List<Integer>> result = new ArrayList<>();
        for (Edge edge : edges.keySet()) {
            if (edges.get(edge))
                result.add(Arrays.asList(edge.source, edge.destination));
        }
        return result;
    }

    private int dfs(int node, int distance, HashMap<Integer, List<Integer>> adjList, Integer[] rank,
                    HashMap<Edge, Boolean> edges) {
        if (rank[node] != null)
            return rank[node];
        rank[node] = distance;
        int minRank = distance+1;
        List<Integer> neighbors = adjList.get(node);
        for (int neighbor : neighbors) {
            if (rank[neighbor] != null && rank[neighbor] == distance-1)
                continue;
            int minRankFromNeighbor = dfs(neighbor, distance+1, adjList, rank, edges);
            if (minRankFromNeighbor <= distance) {
                edges.put(new Edge(Math.min(node, neighbor), Math.max(node, neighbor)), false);
            }
            minRank = Math.min(minRank, minRankFromNeighbor);
        }
        return minRank;
    }

    class Edge {
        int source;
        int destination;

        public Edge(int source, int destination) {
            this.source = source;
            this.destination = destination;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge edge = (Edge) o;
            return source == edge.source && destination == edge.destination;
        }

        @Override
        public int hashCode() {
            int result = source;
            result = 31 * result + destination;
            return result;
        }
    }

    public static void main(String[] args) {
        CriticalConnectionsInANetwork C = new CriticalConnectionsInANetwork();
        System.out.println(C.criticalConnections(4, Arrays.asList(
                Arrays.asList(0,1),
                Arrays.asList(1,2),
                Arrays.asList(2,0),
                Arrays.asList(1,3)
        )));
    }
}
