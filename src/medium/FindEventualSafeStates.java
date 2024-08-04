package medium;

import java.util.*;

public class FindEventualSafeStates {
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int N = graph.length;
        boolean[] safeNodes = new boolean[N];
        for (int i = 0; i < N; i++) {
            if (graph[i].length == 0)
                safeNodes[i] = true;
        }
        boolean[] visited = new boolean[N];
        for (int i = 0; i < N; i++) {
            if (!visited[i] && !safeNodes[i]) {
                dfs(graph, i, safeNodes, visited);
            }
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (safeNodes[i])
                result.add(i);
        }
        return result;
    }

    private boolean dfs(int[][] graph, int node, boolean[] safeNodes, boolean[] visited) {
        visited[node] = true;
        int[] neighbors = graph[node];
        boolean isSafe = true;
        for (int neighbor : neighbors) {
            if (safeNodes[neighbor])
                continue;
            if (visited[neighbor])
                isSafe = false;
            else {
                isSafe = isSafe && dfs(graph, neighbor, safeNodes, visited);
            }
        }
        safeNodes[node] = isSafe;
        return isSafe;
    }

    public static void main(String[] args) {
        FindEventualSafeStates F = new FindEventualSafeStates();
        System.out.println(F.eventualSafeNodes(new int[][]{
                {1,2},{2,3},{5},{0},{5},{},{}
        }));
    }
}
