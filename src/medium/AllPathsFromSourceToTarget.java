package medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllPathsFromSourceToTarget {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        int N = graph.length;
        for (int i = 0; i < N; i++) {
            int[] children = graph[i];
            List<Integer> neighbours = new ArrayList<>();
            for (int child : children)
                neighbours.add(child);
            adjList.put(i, neighbours);
        }
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> curr = new ArrayList<>();
        curr.add(0);
        dfs(0, -1, N-1, adjList, result, curr);
        return result;
    }

    private void dfs(int node, int parent, int target, HashMap<Integer, List<Integer>> adjList, List<List<Integer>> result, List<Integer> curr) {
        if (node == target) {
            List<Integer> combo = new ArrayList<>(curr);
            result.add(combo);
            return;
        }
        List<Integer> neighbours = adjList.get(node);
        for (int neighbour : neighbours) {
            if (neighbour != parent) {
                curr.addLast(neighbour);
                dfs(neighbour, node, target, adjList, result, curr);
                curr.removeLast();
            }
        }
    }

    public static void main(String[] args) {
        AllPathsFromSourceToTarget A = new AllPathsFromSourceToTarget();
        System.out.println(A.allPathsSourceTarget(new int[][]{{1,2},{3},{3},{}}));
        System.out.println(A.allPathsSourceTarget(new int[][]{{4,3,1},{3,2,4},{3},{4},{}}));
//        System.out.println(A.allPathsSourceTarget(new int[][]));
//        System.out.println(A.allPathsSourceTarget(new int[][]));
    }
}
