package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CountTheNumberOfCompleteComponents {
    public int countCompleteComponents(int n, int[][] edges) {
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
        int result = 0;
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                List<Integer> componentNodes = new ArrayList<>();
                dfs(i, adjList, visited, componentNodes);
                int componentSize = componentNodes.size();
                boolean isCompleteComponent = true;
                for (int node : componentNodes) {
                    if (adjList.get(node).size() != componentSize - 1) {
                        isCompleteComponent = false;
                        break;
                    }
                }
                if (isCompleteComponent)
                    result++;
            }
        }
        return result;
    }

    private void dfs(int node, HashMap<Integer, List<Integer>> adjList, boolean[] visited, List<Integer> componentNodes) {
        visited[node] = true;
        componentNodes.add(node);
        List<Integer> neighbours = adjList.get(node);
        for (int neighbour : neighbours) {
            if (!visited[neighbour])
                dfs(neighbour, adjList, visited, componentNodes);
        }
    }

    public static void main(String[] args) {
        CountTheNumberOfCompleteComponents C = new CountTheNumberOfCompleteComponents();
        System.out.println(C.countCompleteComponents(6, new int[][]{{0,1},{0,2},{1,2},{3,4}}));
        System.out.println(C.countCompleteComponents(6, new int[][]{{0,1},{0,2},{1,2},{3,4},{3,5}}));
    }
}
