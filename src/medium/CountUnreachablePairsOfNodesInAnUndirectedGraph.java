package medium;

import java.util.ArrayList;
import java.util.List;

public class CountUnreachablePairsOfNodesInAnUndirectedGraph {
    public long countPairs(int n, int[][] edges) {

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++)
            adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }
        boolean[] visited = new boolean[n];
        List<Integer> forestSizes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                 int count = dfs(i, adj, visited);
                 if (count > 0)
                     forestSizes.add(count);

            }
        }
        long total = 0;
        long result = 0;
        for (Integer forestSize : forestSizes)
            total += forestSize;
        int N = forestSizes.size();
        int currSum = 0;
        for (int i = 0; i < N-1; i++) {
            currSum += forestSizes.get(i);
            result += ((total - currSum) * forestSizes.get(i));
        }
        return result;
    }

    private int dfs(int node, List<List<Integer>> adj, boolean[] visited) {
        visited[node] = true;
        int count = 1;
        for (int neighbour : adj.get(node)) {
            if (!visited[neighbour]) {
                count += dfs(neighbour, adj, visited);
            }
        }
        return count;
    }

    public static void main(String[] args) {
        CountUnreachablePairsOfNodesInAnUndirectedGraph c = new CountUnreachablePairsOfNodesInAnUndirectedGraph();
        System.out.println(c.countPairs(7, new int[][]{{0,2},{0,5},{2,4},{1,6},{5,4}}));
    }
}
