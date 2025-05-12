package medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MinimumCostToReachCityWithDiscounts {
    int minToll;
    public int minimumCost(int n, int[][] highways, int discounts) {
        minToll = Integer.MAX_VALUE;
        int size = highways.length;
        HashMap<Integer, List<int[]>> adjList = new HashMap<>();
        for (int i = 0; i < n; i++)
            adjList.put(i, new ArrayList<>());
        for (int i = 0; i < size; i++) {
            int cityA = highways[i][0];
            int cityB = highways[i][1];
            int toll = highways[i][2];
            adjList.get(cityA).add(new int[]{cityB, toll});
            adjList.get(cityB).add(new int[]{cityA, toll});
        }
        List<Integer> includedTolls = new ArrayList<>();
        boolean[] visited = new boolean[n];
        dfs(0, n, adjList, discounts, includedTolls, visited);
        return minToll == Integer.MAX_VALUE ? -1 : minToll;
    }

    private void dfs(int city, int n, HashMap<Integer, List<int[]>> adjList, int discounts, List<Integer> includedTolls, boolean[] visited) {
        if (city == n-1) {
            List<Integer> tollsCopy = new ArrayList<>(includedTolls);
            tollsCopy.sort(Collections.reverseOrder());
            int totalToll = 0;
            for (int toll : tollsCopy) {
                if (discounts > 0) {
                    toll = toll / 2;
                    discounts--;
                }
                totalToll += toll;
            }
            minToll = Math.min(minToll, totalToll);
            return;
        }
        visited[city] = true;
        List<int[]> neighbours = adjList.get(city);
        for (int[] neighbour : neighbours) {
            int neighbourCity = neighbour[0];
            int toll = neighbour[1];
            if (!visited[neighbourCity]) {
                includedTolls.addLast(toll);
                dfs(neighbourCity, n, adjList, discounts, includedTolls, visited);
                includedTolls.removeLast();
            }
        }
        visited[city] = false;
    }

    public static void main(String[] args) {
        MinimumCostToReachCityWithDiscounts M = new MinimumCostToReachCityWithDiscounts();
        System.out.println(M.minimumCost(5, new int[][]{{0,1,4},{2,1,3},{1,4,11},{3,2,3},{3,4,2}}, 1));
        System.out.println(M.minimumCost(4, new int[][]{{1,3,17},{1,2,7},{3,2,5},{0,1,6},{3,0,20}}, 20));
        System.out.println(M.minimumCost(4, new int[][]{{0,1,3},{2,3,2}}, 0));
    }
}
