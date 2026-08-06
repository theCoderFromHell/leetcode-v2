package medium;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

// https://leetcode.com/problems/loud-and-rich/
public class LoudAndRich {
    public int[] loudAndRich(int[][] richer, int[] quiet) {
        int n = quiet.length;
        HashMap<Integer, HashSet<Integer>> adjList = new HashMap<>();
        for (int i = 0; i < n; i++) {
            adjList.put(i, new HashSet<>());
        }
        int size = richer.length;
        for (int i = 0; i < size; i++) {
            int rich = richer[i][0];
            int poor = richer[i][1];
            adjList.get(poor).add(rich);
        }
        int[] result = new int[n];
        Integer[] dp = new Integer[n];
        for (int i = 0; i < n; i++) {
            result[i] = dfs(i, adjList, quiet, dp);
        }
        return result;
    }

    private int dfs(int node, HashMap<Integer, HashSet<Integer>> adjList, int[] quiet, Integer[] dp) {
        if (dp[node] != null)
            return dp[node];
        dp[node] = node;
        HashSet<Integer> neighbours = adjList.get(node);
        for (int neighbour : neighbours) {
            int q = dfs(neighbour, adjList, quiet, dp);
            if (quiet[q] < quiet[dp[node]])
                dp[node] = q;
        }
        return dp[node];
    }

    /*
     * Revision Note — Loud and Rich (Medium)
     * Pattern: Memoized DFS on a DAG (poor → rich directed graph)
     * Key Insight: Build edges in the poor→rich direction so DFS from each person
     *              traverses all richer ancestors. Return the INDEX of the quietest
     *              person found, not their quiet value. Memoization makes it O(n+E)
     *              by ensuring each node is computed exactly once.
     * Gotchas:
     *   - Return the person INDEX (not the quiet value) — confusing these caused
     *     multiple bugs; compare quiet values via quiet[index] at the call site.
     *   - Propagate dp[node] (the memoized result) forward, not the raw neighbour
     *     variable — the quietest person may be many hops up the richer chain.
     *   - Edge direction matters: poor→rich lets DFS climb toward richer people;
     *     rich→poor would traverse the wrong direction.
     */
    public static void main(String[] args) {
        LoudAndRich L = new LoudAndRich();

        // Test 1: LeetCode example 1
        System.out.println("Test 1: " + Arrays.toString(L.loudAndRich(
                new int[][]{{1,0},{2,1},{3,1},{3,7},{4,3},{5,3},{6,3}},
                new int[]{3,2,5,4,6,1,7,0}))
                + " (Expected: [5, 5, 2, 5, 4, 5, 6, 7])");

        // Test 2: no richer relationships — each person is their own answer
        System.out.println("Test 2: " + Arrays.toString(L.loudAndRich(
                new int[][]{},
                new int[]{0, 1}))
                + " (Expected: [0, 1])");

        // Test 3: single person
        System.out.println("Test 3: " + Arrays.toString(L.loudAndRich(
                new int[][]{},
                new int[]{5}))
                + " (Expected: [0])");

        // Test 4: linear chain 1→2→0 (1 richest), quietest is person 1
        System.out.println("Test 4: " + Arrays.toString(L.loudAndRich(
                new int[][]{{2,0},{1,2}},
                new int[]{5,1,3}))
                + " (Expected: [1, 1, 1])");

        // Test 5: richest person is also quietest — all point to them
        System.out.println("Test 5: " + Arrays.toString(L.loudAndRich(
                new int[][]{{0,1},{0,2}},
                new int[]{0,5,5}))
                + " (Expected: [0, 0, 0])");

        // Test 6: quietest person is the poorest — no one updates away from themselves
        System.out.println("Test 6: " + Arrays.toString(L.loudAndRich(
                new int[][]{{0,1}},
                new int[]{5,0}))
                + " (Expected: [0, 1])");
    }
}
