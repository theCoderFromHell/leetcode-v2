package medium;

import java.util.*;

public class MaximizeTheNumberOfTargetNodesAfterConnectingTreesI {
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2, int k) {
        int n = edges1.length + 1;
        int m = edges2.length + 1;
        HashMap<Integer, List<Integer>> adj1 = new HashMap<>();
        HashMap<Integer, List<Integer>> adj2 = new HashMap<>();
        for (int i = 0; i < n; i++)
            adj1.put(i, new ArrayList<>());
        for (int i = 0; i < m; i++)
            adj2.put(i, new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            int u = edges1[i][0];
            int v = edges1[i][1];
            adj1.get(u).add(v);
            adj1.get(v).add(u);
        }
        for (int i = 0; i < m - 1; i++) {
            int u = edges2[i][0];
            int v = edges2[i][1];
            adj2.get(u).add(v);
            adj2.get(v).add(u);
        }
        int[] dist1 = new int[n];
        for (int i = 0; i < n; i++)
            dist1[i] = bfs(i, adj1, n, k);
        int maxNodes = 0;
        for (int i = 0; i < m; i++)
            maxNodes = Math.max(maxNodes, bfs(i, adj2, m, k-1));
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = dist1[i] + maxNodes;
        }
        return result;
    }

    private int bfs(int node, HashMap<Integer, List<Integer>> adj, int size, int target) {
        if (target < 0)
            return 0;
        if (target == 0)
            return 1;
        int count = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(node);
        boolean[] visited = new boolean[size];
        visited[node] = true;
        int width = 0;
        while (!queue.isEmpty()) {
            int qSize = queue.size();
            while (qSize-- > 0) {
                Integer top = queue.poll();
                count++;
                for (int neighbour : adj.get(top)) {
                    if (!visited[neighbour]) {
                        visited[neighbour] = true;
                        queue.add(neighbour);
                    }
                }
            }
            width++;
            if (width > target)
                break;
        }
        return count;
    }

    public static void main(String[] args) {
        MaximizeTheNumberOfTargetNodesAfterConnectingTreesI M = new MaximizeTheNumberOfTargetNodesAfterConnectingTreesI();
        System.out.println(Arrays.toString(M.maxTargetNodes(new int[][]{{2,1},{7,3},{0,4},{7,5},{2,6},{0,2},{0,7}}, new int[][]{{3,0},{1,2},{5,1},{6,3},{9,4},{5,6},{7,5},{9,7},{8,9}}, 7)));
        System.out.println(Arrays.toString(M.maxTargetNodes(new int[][]{{0,1}}, new int[][]{{0,1}}, 0)));
        System.out.println(Arrays.toString(M.maxTargetNodes(new int[][]{{0,1},{0,2},{2,3},{2,4}}, new int[][]{{0,1},{0,2},{0,3},{2,7},{1,4},{4,5},{4,6}}, 2)));
        System.out.println(Arrays.toString(M.maxTargetNodes(new int[][]{{0,1},{0,2},{0,3},{0,4}}, new int[][]{{0,1},{1,2},{2,3}}, 1)));
    }
}
