package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MinimumNumberOfVerticesToReachAllNodes {
    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        for (int i = 0; i < n; i++)
            adjList.put(i, new ArrayList<>());
        int size = edges.size();
        for (int i = 0; i < size; i++) {
            int src = edges.get(i).get(0);
            int dest = edges.get(i).get(1);
            adjList.get(src).add(dest);
        }
        boolean[] reachable = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!reachable[i])
                dfs(i, adjList, reachable);
        }
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!reachable[i])
                result.add(i);
        }
        return result;
    }

    private void dfs(int node, HashMap<Integer, List<Integer>> adjList, boolean[] reachable) {
        List<Integer> neighbours = adjList.get(node);
        for (int neighbour : neighbours) {
            if (!reachable[neighbour]) {
                reachable[neighbour] = true;
                dfs(neighbour, adjList, reachable);
            }
        }
    }
}
