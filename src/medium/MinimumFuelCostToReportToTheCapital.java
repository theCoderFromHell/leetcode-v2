package medium;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/minimum-fuel-cost-to-report-to-the-capital/
public class MinimumFuelCostToReportToTheCapital {
    long result;
    public long minimumFuelCost(int[][] roads, int seats) {
        int size = roads.length;
        int n = 0;
        for (int i = 0; i < size; i++)
            n = Math.max(n, Math.max(roads[i][0], roads[i][1]));
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adjList.add(new ArrayList<>());
        }
        for (int i = 0; i < size; i++) {
            int source = roads[i][0];
            int destination = roads[i][1];
            adjList.get(source).add(destination);
            adjList.get(destination).add(source);
        }
        result = 0;
        List<Integer> neighbours = adjList.get(0);
        for (int neighbour : neighbours)
            dfs(neighbour, adjList, seats, 0);
        return result;
    }

    private int dfs(int node, List<List<Integer>> adjList, int seats, int parent) {
        List<Integer> neighbours = adjList.get(node);
        int total = 1;
        for (int neighbour : neighbours) {
            if (neighbour == parent)
                continue;
            total += dfs(neighbour, adjList, seats, node);
        }
        result += ((total + seats - 1) / seats);
        return total;
    }

    /*
     * Revision Note — Minimum Fuel Cost to Report to the Capital (Medium)
     *
     * Pattern: Post-order DFS on a tree — aggregate subtree counts bottom-up
     *
     * Key Insight: Each edge costs ceil(representatives_below / seats) litres —
     * so DFS each subtree, return its representative count, and accumulate fuel
     * at every non-root node.
     *
     * Gotchas:
     * - Use long for result — total fuel can exceed Integer.MAX_VALUE for large n with seats=1
     * - Skip the root node (node 0) when adding fuel — it doesn't travel anywhere
     *   (cleanest fix: iterate root's neighbours directly instead of calling dfs(0,...))
     * - Ceiling division: (total + seats - 1) / seats
     *
     * Template:
     *   int dfs(node, parent):
     *       total = 1
     *       for each child (neighbour != parent):
     *           total += dfs(child, node)
     *       result += ceil(total / seats)   // fuel for this edge to parent
     *       return total
     *   // Call: for each neighbour of root: dfs(neighbour, 0)
     */
    public static void main(String[] args) {
        MinimumFuelCostToReportToTheCapital M = new MinimumFuelCostToReportToTheCapital();
        // Star graph, all go directly to capital — 3
        System.out.println(M.minimumFuelCost(new int[][]{{0,1},{0,2},{0,3}}, 5));
        // Carpooling chain — 7
        System.out.println(M.minimumFuelCost(new int[][]{{3,1},{3,2},{1,0},{0,4},{0,5},{4,6}}, 2));
        // Single node, no roads — 0
        System.out.println(M.minimumFuelCost(new int[][]{}, 1));
        // Linear chain, seats=1, no carpooling — 3
        System.out.println(M.minimumFuelCost(new int[][]{{0,1},{1,2},{2,3}}, 1));
        // Linear chain, seats=3, all carpool — 3
        System.out.println(M.minimumFuelCost(new int[][]{{0,1},{1,2},{2,3}}, 3));
        // Exact seat fill: 2 reps, 2 seats — 1
        System.out.println(M.minimumFuelCost(new int[][]{{0,1},{0,2}}, 2));
    }
}
