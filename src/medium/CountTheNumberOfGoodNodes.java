package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CountTheNumberOfGoodNodes {
    int count;
    public int countGoodNodes(int[][] edges) {
        int size = edges.length;
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        int maxNode = 0;
        for (int i = 0; i < size; i++) {
            int src = edges[i][0];
            int dest = edges[i][1];
            maxNode = Math.max(maxNode, src);
            maxNode = Math.max(maxNode, dest);
            if (!adjList.containsKey(src))
                adjList.put(src, new ArrayList<>());
            if (!adjList.containsKey(dest))
                adjList.put(dest, new ArrayList<>());
            adjList.get(src).add(dest);
            adjList.get(dest).add(src);
        }
        count = 0;
        boolean[] visited = new boolean[maxNode+1];
        dfs(0, visited, adjList);
        return count;
    }

    private int dfs(int i, boolean[] visited, HashMap<Integer, List<Integer>> adjList) {
        visited[i] = true;
        int subTreeSize = 1;
        Integer size = null;
        boolean isGoodNode = true;
        for (int neighbor : adjList.getOrDefault(i, new ArrayList<>())) {
            if (visited[neighbor])
                continue;
            int numberOfNodes = dfs(neighbor, visited, adjList);
            if (size == null)
                size = numberOfNodes;
            else if (numberOfNodes != size)
                isGoodNode = false;
            subTreeSize += numberOfNodes;
        }
        if (isGoodNode)
            count++;
        return subTreeSize;
    }

    public static void main(String[] args) {
        CountTheNumberOfGoodNodes C = new CountTheNumberOfGoodNodes();
        System.out.println(C.countGoodNodes(new int[][]{{0,1},{0,2},{1,3},{1,4},{2,5},{2,6}}));
        System.out.println(C.countGoodNodes(new int[][]{{0,1},{1,2},{2,3},{3,4},{0,5},{1,6},{2,7},{3,8}}));
    }
}
