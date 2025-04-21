package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NumberOfConnectedComponentsInAnUndirectedGraph {
    int[] parent;
    int[] rank;
    public int countComponentsV2(int n, int[][] edges) {
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

    public int countComponents(int n, int[][] edges) {
        int size = edges.length;
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        for (int i = 0; i < n; i++)
            adjList.put(i, new ArrayList<>());
        for (int i = 0; i < size; i++) {
            int src = edges[i][0];
            int dest = edges[i][1];
            adjList.get(src).add(dest);
            adjList.get(dest).add(src);
        }
        int count = 0;
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                count++;
                dfs(i, adjList, visited);
            }
        }
        return count;
    }

    private void dfs(int node, HashMap<Integer, List<Integer>> adjList, boolean[] visited) {
        visited[node] = true;
        List<Integer> neighbours = adjList.get(node);
        for (int neighbour : neighbours)
            if (!visited[neighbour])
                dfs(neighbour, adjList, visited);
    }

    public static void main(String[] args) {
        NumberOfConnectedComponentsInAnUndirectedGraph N = new NumberOfConnectedComponentsInAnUndirectedGraph();
        System.out.println(N.countComponents(5, new int[][]{{0,1},{1,2},{3,4}}));
        System.out.println(N.countComponents(5, new int[][]{{0,1},{1,2},{2,3},{3,4}}));
    }
}
