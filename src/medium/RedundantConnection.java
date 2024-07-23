package medium;

public class RedundantConnection {
    public int[] findRedundantConnection(int[][] edges) {
        int N = edges.length;
        int[] parent = new int[N+1];
        int[] rank = new int[N+1];
        for (int i = 0; i < N; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
        for (int[] edge : edges) {
            int src = edge[0];
            int dest = edge[1];
            if (!union(src, dest, parent, rank))
                return edge;
        }
        return new int[]{};
    }

    private int find(int node, int[] parent) {
        if (node != parent[node]) {
            parent[node] = find(parent[node], parent);
        }
        return parent[node];
    }

    private boolean union(int a, int b, int[] parent, int[] rank) {
        int parentA = find(a, parent);
        int parentB = find(b, parent);
        if (parentA == parentB)
            return false;
        if (rank[parentA] > rank[parentB]) {
            parent[parentB] = parentA;
            rank[parentA] += rank[parentB];
        } else {
            parent[parentA] = parentB;
            rank[parentB] += rank[parentA];
        }
        return true;
    }
}
