package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PossibleBipartition {
    public boolean possibleBipartition(int n, int[][] dislikes) {
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        for (int i = 1; i <= n; i++)
            adjList.put(i, new ArrayList<>());
        int size = dislikes.length;
        for (int i = 0; i < size; i++) {
            adjList.get(dislikes[i][0]).add(dislikes[i][1]);
            adjList.get(dislikes[i][1]).add(dislikes[i][0]);
        }
        int[] colours = new int[n+1];
        Arrays.fill(colours, -1);
        for (int i = 1; i <= n; i++) {
            if (colours[i] == -1)
                if (!dfs(i, adjList, colours, 0))
                    return false;
        }
        return true;
    }

    private boolean dfs(int node, HashMap<Integer, List<Integer>> adjList, int[] colours, int colour) {
        colours[node] = colour;
        List<Integer> neighbours = adjList.get(node);
        for (int neighbour : neighbours) {
            if (colours[neighbour] == colour)
                return false;
            if (colours[neighbour] == -1)
                if (!dfs(neighbour, adjList, colours, 1 - colour))
                    return false;
        }
        return true;
    }

    public static void main(String[] args) {
        PossibleBipartition P = new PossibleBipartition();
        System.out.println(P.possibleBipartition(4, new int[][]{{1,2},{1,3},{2,4}}));
        System.out.println(P.possibleBipartition(3, new int[][]{{1,2},{1,3},{2,3}}));
    }
}
