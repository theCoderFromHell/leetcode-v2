package medium;

import java.util.ArrayList;
import java.util.List;

public class MinimumTimeToCollectAllApplesInATree {
    int time = 0;
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < n; i++)
            adjList.add(new ArrayList<>());
        int size = edges.length;
        for (int i = 0; i < size; i++) {
            int source = edges[i][0];
            int destination = edges[i][1];
            adjList.get(source).add(destination);
            adjList.get(destination).add(source);
        }
        time = 0;
        dfs(0, adjList, hasApple, -1);
        return time;
    }

    private boolean dfs(int node, List<List<Integer>> adjList, List<Boolean> hasApple, int parent) {
        List<Integer> neighbours = adjList.get(node);
        boolean appleHere = false;
        for (int neighbour : neighbours) {
            if (neighbour != parent) {
                if (dfs(neighbour, adjList, hasApple, node)) {
                    time += 2;
                    appleHere = true;
                }
            }
        }
        return (hasApple.get(node) || appleHere);
    }

    public static void main(String[] args) {
        MinimumTimeToCollectAllApplesInATree M = new MinimumTimeToCollectAllApplesInATree();
        System.out.println(M.minTime(7, new int[][]{{0,1},{0,2},{1,4},{1,5},{2,3},{2,6}}, List.of(false,false,true,false,true,true,false)));
        System.out.println(M.minTime(7, new int[][]{{0,1},{0,2},{1,4},{1,5},{2,3},{2,6}}, List.of(false,false,true,false,false,true,false)));
        System.out.println(M.minTime(7, new int[][]{{0,1},{0,2},{1,4},{1,5},{2,3},{2,6}}, List.of(false,false,false,false,false,false,false)));
        System.out.println(M.minTime(4, new int[][]{{0,1},{1,2},{0,3}}, List.of(true,true,true,true)));
        System.out.println(M.minTime(5, new int[][]{{0,1},{0,2},{1,3},{1,4}}, List.of(false,false,false,false,true)));
        System.out.println(M.minTime(1, new int[][]{}, List.of(false)));
        System.out.println(M.minTime(3, new int[][]{{0,1},{1,2}}, List.of(false,false,true)));
    }
}
