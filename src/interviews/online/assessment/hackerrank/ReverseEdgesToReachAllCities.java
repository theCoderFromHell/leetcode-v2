package interviews.online.assessment.hackerrank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReverseEdgesToReachAllCities {
    public List<Integer> countReverseEdges(int gNodes, List<Integer> gFrom, List<Integer> gTo) {
        List<List<int[]>> adjList = new ArrayList<>();
        for (int i = 0; i <= gNodes; i++)
            adjList.add(new ArrayList<>());
        for (int i = 0; i < gFrom.size(); i++) {
            int src = gFrom.get(i);
            int dest = gTo.get(i);
            adjList.get(src).add(new int[]{dest, 0}); // src → dest, no reversal needed
            adjList.get(dest).add(new int[]{src, 1}); // dest → src, reversal needed
        }
        int[] res = new int[gNodes + 1];

        res[1] = dfs(1, 0, adjList);;
        dfsForAll(1, 0, adjList, res);

        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= gNodes; i++)
            result.add(res[i]);
        return result;
    }

    private int dfs(int node, int parent, List<List<int[]>> adjList) {
        int sum = 0;
        List<int[]> neighbours = adjList.get(node);
        for (int[] neighbour : neighbours) {
            int next = neighbour[0];
            int cost = neighbour[1];
            if (next != parent)
                sum += (cost + dfs(next, node, adjList));
        }
        return sum;
    }

    private void dfsForAll(int node, int parent, List<List<int[]>> adjList, int[] result) {
        List<int[]> neighbours = adjList.get(node);
        for (int[] neighbour : neighbours) {
            int next = neighbour[0];
            int cost = neighbour[1];
            if (next != parent) {
                result[next] = result[node] + (cost == 0 ? 1 : -1);
                dfsForAll(next, node, adjList, result);
            }
        }
    }

    public static void main(String[] args) {
        ReverseEdgesToReachAllCities R = new ReverseEdgesToReachAllCities();
        System.out.println(R.countReverseEdges(4, Arrays.asList(1,2,3), Arrays.asList(2,3,4)));
    }
}

/*
A country can be represented by a graph with g_nodes cities connected by g_nodes - 1 unidirectional edges.
The ith edge connects cities g_from[i] to g_to[i] .
if the roads were bidirectional, every node would be reachable from every other node and the resulting graph would be a tree.
For each city i, from 1 to g_nodes , find minimum number of edges that need to be reversed
 so that it is possible to travel from city i to any other city using directed edges.

Complete the method in Java

public List<Integer> countReverseEdges (int gNodes, List<Integer> gFrom, List<Integer> gTo) {}
 */


