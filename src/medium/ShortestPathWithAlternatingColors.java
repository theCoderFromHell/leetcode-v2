package medium;

import java.util.*;

// https://leetcode.com/problems/shortest-path-with-alternating-colors/
public class ShortestPathWithAlternatingColors {
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++)
            graph.add(new ArrayList<>());
        int source, destination;
        for (int[] edge : redEdges) {
            source = edge[0];
            destination = edge[1];
            graph.get(source).add(new int[]{destination, 0});
        }
        for (int[] edge : blueEdges) {
            source = edge[0];
            destination = edge[1];
            graph.get(source).add(new int[]{destination, 1});
        }
        int[] distance = new int[n];
        Arrays.fill(distance, -1);
        distance[0] = 0;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][2];
        queue.add(new int[]{0, 0});
        queue.add(new int[]{0, 1});
        visited[0][0] = true;
        visited[0][1] = true;
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] front = queue.poll();
                int node = front[0];
                int color = front[1];
                List<int[]> neighbours = graph.get(node);
                for (int[] neighbour : neighbours) {
                    int neighbourNode = neighbour[0];
                    int neighbourColor = neighbour[1];
                    if (neighbourColor == 1 - color) {
                        if (distance[neighbourNode] == -1)
                            distance[neighbourNode] = level + 1;
                        if (!visited[neighbourNode][neighbourColor]) {
                            visited[neighbourNode][neighbourColor] = true;
                            queue.add(new int[]{neighbourNode, neighbourColor});
                        }
                    }
                }
            }
            level++;
        }
        return distance;
    }

    public static void main(String[] args) {
        ShortestPathWithAlternatingColors S = new ShortestPathWithAlternatingColors();
        // [0, 1, -1]
        System.out.println(Arrays.toString(S.shortestAlternatingPaths(3,
                new int[][]{{0, 1}, {1, 2}}, new int[][]{})));
        // [0, 1, 2]
        System.out.println(Arrays.toString(S.shortestAlternatingPaths(3,
                new int[][]{{0, 1}}, new int[][]{{2, 1}})));
        // [0, 1, 2] — parallel edges, both colors available 0->1
        System.out.println(Arrays.toString(S.shortestAlternatingPaths(3,
                new int[][]{{0, 1}, {1, 2}}, new int[][]{{1, 2}})));
        // [0, -1] — only red edge, no alternating path to node 1
        System.out.println(Arrays.toString(S.shortestAlternatingPaths(2,
                new int[][]{{0, 1}}, new int[][]{{0, 1}})));
    }
}

/*
 * Revision Note — Shortest Path with Alternating Colors (Medium)
 *
 * Pattern: BFS on (node, lastColor) state space
 *
 * Key Insight: Arriving at a node via red is a different state from arriving via blue —
 * encode color into the BFS state so you know which color to take next.
 * Seed node 0 twice ({0, RED} and {0, BLUE}) to allow either color as the first step.
 *
 * Gotchas:
 * - Gate BOTH the distance update AND the enqueue inside the alternating check —
 *   separating them silently allows invalid (non-alternating) paths into the queue
 * - Mark visited[0][0] and visited[0][1] both true at the start to prevent re-enqueuing node 0
 * - Use level-based BFS distance (level + 1), not distance[node] + 1, to avoid stale MAX_VALUE arithmetic
 *
 * Template:
 *   queue.add({0, RED}); queue.add({0, BLUE});
 *   visited[0][RED] = visited[0][BLUE] = true;
 *   while (!queue.isEmpty()) {
 *       for each node in current level:
 *           for each neighbour (v, c) of node:
 *               if (c == 1 - lastColor) {        // alternating check
 *                   if (dist[v] == -1) dist[v] = level + 1;
 *                   if (!visited[v][c]) { visited[v][c]=true; queue.add({v,c}); }
 *               }
 *       level++;
 *   }
 */
