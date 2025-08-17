package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RestoreTheArrayFromAdjacentPairs {
    int index;
    public int[] restoreArray(int[][] adjacentPairs) {
        int size = adjacentPairs.length;
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        for (int i = 0; i < size; i++) {
            int a = adjacentPairs[i][0];
            int b = adjacentPairs[i][1];
            adjList.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
            adjList.computeIfAbsent(b, k -> new ArrayList<>()).add(a);
        }
        int[] result = new int[size+1];
        index = 0;
        for (int node : adjList.keySet()) {
            if (adjList.get(node).size() == 1) {
                dfs(node, null, adjList, result);
                break;
            }
        }
        return result;
    }

    private void dfs(int node, Integer parent, HashMap<Integer, List<Integer>> adjList, int[] result) {
        result[index++] = node;
        List<Integer> neighbours = adjList.get(node);
        for (int neighbour : neighbours) {
            if (parent == null || neighbour != parent)
                dfs(neighbour, node, adjList, result);
        }
    }

    public static void main(String[] args) {
        RestoreTheArrayFromAdjacentPairs R = new RestoreTheArrayFromAdjacentPairs();
        System.out.println(Arrays.toString(R.restoreArray(new int[][]{{2,1},{3,4},{3,2}})));
        System.out.println(Arrays.toString(R.restoreArray(new int[][]{{4,-2},{1,4},{-3,1}})));
        System.out.println(Arrays.toString(R.restoreArray(new int[][]{{100000,-100000}})));
    }
}
