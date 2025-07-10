package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindChampionII {
    public int findChampion(int n, int[][] edges) {
        int size = edges.length;
        HashMap<Integer,List<Integer>> adjList = new HashMap<>();
        for (int i = 0; i < n; i++)
            adjList.put(i, new ArrayList<>());
        for (int i = 0; i < size; i++)
            adjList.get(edges[i][0]).add(edges[i][1]);
        boolean winnerFound = false;
        int winner = -1;
        for (int i = 0; i < n; i++) {
            boolean[] visited = new boolean[n];
            if (dfs(i, adjList, visited) == n) {
                if (winnerFound)
                    return -1;
                winnerFound = true;
                winner = i;
            }
        }
        return winner;
    }

    private int dfs(int node, HashMap<Integer, List<Integer>> adjList, boolean[] visited) {
        visited[node] = true;
        List<Integer> neighbours = adjList.get(node);
        int count = 0;
        for (int neighbour : neighbours) {
            if (!visited[neighbour])
                count += dfs(neighbour, adjList, visited);
        }
        return 1 + count;
    }

    public static void main(String[] args) {
        FindChampionII F = new FindChampionII();
        System.out.println(F.findChampion(3, new int[][]{{0,1},{1,2}}));
        System.out.println(F.findChampion(4, new int[][]{{0,2},{1,3},{1,2}}));
    }
}
