package interviews.oa.hackerrank;

import java.util.ArrayList;
import java.util.List;

public class RemoveNodesOnTheLongestDiameter {
    public List<Integer> removeNodesOnTheLongestDiameter (int n, int[][] edges) {
        List<Integer>[] tree = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++)
            tree[i] = new ArrayList<>();
        for (int [] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            tree[u].add(v);
            tree[v].add(u);
        }
        int[] dist1 = new int[n + 1];
        dfs(1, 0, -1, dist1, tree);
        int u = findFarthest(dist1);

        int[] distFromU = new int[n + 1];
        dfs(u, 0, -1, distFromU, tree);
        int v = findFarthest(distFromU);
        int diameter = distFromU[v];

        int[] distFromV = new int[n + 1];
        dfs(v, 0, -1, distFromV, tree);

        List<Integer> notOnLongestDiameter = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (distFromU[i] + distFromV[i] != diameter)
                notOnLongestDiameter.add(i);
        }
        return notOnLongestDiameter;
    }

    private void dfs(int node, int depth, int parent, int[] dist, List<Integer>[] tree) {
        dist[node] = depth;
        for (int neighbour : tree[node]) {
            if (neighbour != parent)
                dfs(neighbour, depth + 1, node, dist, tree);
        }
    }

    private int findFarthest(int[] dist) {
        int maxIdx = 1;
        for (int i = 1; i < dist.length; i++) {
            if (dist[i] > dist[maxIdx])
                maxIdx = i;
        }
        return maxIdx;
    }
}
