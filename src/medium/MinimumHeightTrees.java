package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MinimumHeightTrees {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> minHeightRoots = new ArrayList<>();
        if (n <= 2) {
            for (int i = 0; i < n; i++)
                minHeightRoots.add(i);
            return minHeightRoots;
        }
        HashMap<Integer, HashSet<Integer>> adjList = new HashMap<>();
        for (int i = 0; i < n; i++)
            adjList.put(i, new HashSet<>());
        int size = edges.length;
        for (int i = 0; i < size; i++) {
            int start = edges[i][0];
            int end = edges[i][1];
            adjList.get(start).add(end);
            adjList.get(end).add(start);
        }
        List<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (adjList.get(i).size() == 1)
                leaves.add(i);
        }
        int nodesRemaining = n;
        while (nodesRemaining > 2) {
            nodesRemaining -= leaves.size();
            List<Integer> newLeaves = new ArrayList<>();
            for (Integer leaf : leaves) {
                int node = adjList.get(leaf).iterator().next();
                adjList.get(node).remove(leaf);
                if (adjList.get(node).size() == 1)
                    newLeaves.add(node);
            }
            leaves = newLeaves;
        }
        minHeightRoots.addAll(leaves);
        return minHeightRoots;
    }

    public static void main(String[] args) {
        MinimumHeightTrees M = new MinimumHeightTrees();
        System.out.println(M.findMinHeightTrees(4, new int[][]{{1,0},{1,2},{1,3}}));
        System.out.println(M.findMinHeightTrees(6, new int[][]{{3,0},{3,1},{3,2},{3,4},{5,4}}));
    }
}
