package medium;

import java.util.ArrayList;
import java.util.HashSet;

public class NumberOfOperationsToMakeNetworkConnected {
    public static int makeConnected(int N, int[][] connections) {
        ArrayList<HashSet<Integer>> adj = new ArrayList<>(N);
        for (int i = 0; i < N; i++)
            adj.add(new HashSet<>());
        int size = connections.length;
        for (int i = 0; i < size; i++) {
            adj.get(connections[i][0]).add(connections[i][1]);
            adj.get(connections[i][1]).add(connections[i][0]);
        }
        boolean[] visited = new boolean[N];
        int components = 0;
        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                components++;
                dfs(i, adj, N, visited);
            }
        }
        int redundantEdges = size - (N - components);
        if (redundantEdges < 0 || redundantEdges < components-1)
            return -1;
        return components-1;
    }

    private static void dfs(int node, ArrayList<HashSet<Integer>> adj, int N, boolean[] visited) {
        visited[node] = true;
        for (int next : adj.get(node)) {
            if (!visited[next])
                dfs(next, adj, N, visited);
        }
    }

    public static void main(String[] args) {
        System.out.println(makeConnected(11, new int[][]{{1,4},{0,3},{1,3},{3,7},{2,7},{0,1},{2,4},{3,6},{5,6},{6,7},{4,7},{0,7},{5,7}}));
    }
}
