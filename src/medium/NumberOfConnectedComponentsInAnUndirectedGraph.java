package medium;

public class NumberOfConnectedComponentsInAnUndirectedGraph {
    int[] parent;
    int[] rank;
    public int countComponents(int n, int[][] edges) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
        int N = edges.length;
        int components = n;
        for (int i = 0; i < N; i++) {
            components -= union(edges[i][0], edges[i][1]);
        }
        return components;
    }


    int find(int node) {
        if (node != parent[node])
            parent[node] = find(parent[node]);
        return parent[node];
    }

    int union(int a, int b) {
        int parentA = find(a);
        int parentB = find(b);
        if (parentA != parentB) {
            if (rank[parentA] > rank[parentB]) {
                parent[parentB] = parentA;
                rank[parentA] += rank[parentB];
            } else {
                parent[parentA] = parentB;
                rank[parentB] += rank[parentA];
            }
            return 1;
        }
        return 0;
    }

    public static void main(String[] args) {
        NumberOfConnectedComponentsInAnUndirectedGraph N = new NumberOfConnectedComponentsInAnUndirectedGraph();
        System.out.println(N.countComponents(5, new int[][]{{0,1},{1,2},{3,4}}));
        System.out.println(N.countComponents(5, new int[][]{{0,1},{1,2},{2,3},{3,4}}));
    }
}
