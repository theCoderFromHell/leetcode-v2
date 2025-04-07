package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class GraphValidTree {
    public boolean validTree(int n, int[][] edges) {
        int size = edges.length;
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        HashSet<Integer> unvisited = new HashSet<>();
        for (int i = 0; i < n; i++) {
            adjList.put(i, new ArrayList<>());
            unvisited.add(i);
        }
        for (int i = 0; i < size; i++) {
            int start = edges[i][0];
            int end = edges[i][1];
            adjList.get(start).add(end);
            adjList.get(end).add(start);
        }
        if (dfs(0, adjList, unvisited, -1))
            return unvisited.isEmpty();
        return false;
    }

    private boolean dfs(int node, HashMap<Integer, List<Integer>> adjList, HashSet<Integer> unvisited, int parent) {
        unvisited.remove(node);
        List<Integer> neighbours = adjList.get(node);
        for (int neighbour : neighbours) {
            if (!unvisited.contains(neighbour)) {
                if (neighbour != parent)
                    return false;
            } else
                if (!dfs(neighbour, adjList, unvisited, node))
                    return false;
        }
        return true;
    }

    public static void main(String[] args) {
        GraphValidTree G = new GraphValidTree();
        System.out.println(G.validTree(5, new int[][]{{0,1},{0,2},{0,3},{1,4}}));
        System.out.println(G.validTree(5, new int[][]{{0,1},{1,2},{2,3},{1,3},{1,4}}));
    }
}
