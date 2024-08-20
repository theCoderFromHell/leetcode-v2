package medium;

import java.util.ArrayList;
import java.util.List;

public class ReorderRoutesToMakeAllPathsLeadToTheCityZero {
    int count = 0;
    public int minReorder(int N, int[][] connections) {
        List<List<int[]>> adjList = new ArrayList<>();
        for (int i = 0; i < N; i++)
            adjList.add(new ArrayList<>());
        for (int[] connection : connections) {
            int source = connection[0];
            int destination = connection[1];
            adjList.get(source).add(new int[]{destination, 1});
            adjList.get(destination).add(new int[]{source, 0});
        }
        dfs(0, -1, adjList);
        return count;
    }

    private void dfs(int node, int parent, List<List<int[]>> adjList) {
        List<int[]> neighbors = adjList.get(node);
        for (int[] neighbor : neighbors) {
            if (neighbor[0] != parent) {
                count += neighbor[1];
                dfs(neighbor[0], node, adjList);
            }
        }
    }

    public static void main(String[] args) {
        ReorderRoutesToMakeAllPathsLeadToTheCityZero R = new ReorderRoutesToMakeAllPathsLeadToTheCityZero();
        System.out.println(R.minReorder(6, new int[][]{{0,1},{1,3},{2,3},{4,0},{4,5}}));
        System.out.println(R.minReorder(5, new int[][]{{1,0},{1,2},{3,2},{3,4}}));
        System.out.println(R.minReorder(6, new int[][]{{0,2},{0,3},{4,1},{4,5},{5,0}}));
        System.out.println(R.minReorder(6, new int[][]{{0,2},{0,1},{2,3},{2,4},{1,5}}));
    }
}
