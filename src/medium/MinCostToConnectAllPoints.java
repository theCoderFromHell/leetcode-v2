package medium;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class MinCostToConnectAllPoints {
    public int minCostConnectPoints(int[][] points) {
        int size = points.length;
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparing(o-> o.cost));
        UnionFind uf = new UnionFind(size);
        for (int i = 0; i < size; i++) {
            int[] pointA = points[i];
            for (int j = i+1; j < size; j++) {
                int[] pointB = points[j];
                pq.add(new Edge(i, j, Math.abs(pointA[0]-pointB[0]) + Math.abs(pointA[1]-pointB[1])));
            }
        }
        int count = size-1;
        int cost = 0;
        while (!pq.isEmpty() && count > 0) {
            Edge e = pq.poll();
            if (uf.union(e.source, e.destination)) {
                cost += e.cost;
                count--;
            }
        }
        return cost;
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
    class UnionFind {
        int size;
        int[] parent;
        int[] rank;
        public UnionFind(int size) {
            this.size = size;
            this.parent = new int[size];
            this.rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        public int find(int node) {
            if (parent[node] != node)
                parent[node] = find(parent[node]);
            return parent[node];
        }
        public boolean union(int a, int b) {
            int parentA = find(a);
            int parentB = find(b);
            if (parentA == parentB)
                return false;
            if (rank[parentA] >= rank[parentB]) {
                parent[parentB] = parentA;
                rank[parentA]++;
            } else {
                parent[parentA] = parentB;
                rank[parentB]++;
            }
            return true;
        }

        @Override
        public String toString() {
            return "UnionFind{" +
                    "size=" + size +
                    ", parent=" + Arrays.toString(parent) +
                    ", rank=" + Arrays.toString(rank) +
                    '}';
        }
    }
}

