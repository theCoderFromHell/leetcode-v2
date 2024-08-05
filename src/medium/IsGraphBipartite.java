package medium;

import java.util.Arrays;

public class IsGraphBipartite {
    public boolean isBipartite(int[][] graph) {
        int N = graph.length;
        int[] colour = new int[N];
        Arrays.fill(colour, -1);
        for (int i = 0; i < N; i++) {
            if (colour[i] == -1)
                if (!dfs(graph, i, colour, 0))
                    return false;
        }
        return true;
    }

    private boolean dfs(int[][] graph, int node, int[] colour, int currentColour) {
        if (colour[node] == (currentColour ^ 1))
            return false;
        colour[node] = currentColour;
        int[] neighbours = graph[node];
        for (int neighbour : neighbours) {
            if (colour[neighbour] == currentColour)
                return false;
            if (colour[neighbour] == -1)
                if (!dfs(graph, neighbour, colour, currentColour ^ 1))
                    return false;
        }
        return true;
    }

    public static void main(String[] args) {
        IsGraphBipartite I = new IsGraphBipartite();
        System.out.println(I.isBipartite(new int[][]{
                {1,2,3},{0,2},{0,1,3},{0,2}
        }));
    }
}
