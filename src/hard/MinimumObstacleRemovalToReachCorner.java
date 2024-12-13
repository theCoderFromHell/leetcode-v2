package hard;

import java.util.*;

public class MinimumObstacleRemovalToReachCorner {
    int[] dx = {0,1,0,-1};
    int[] dy = {1,0,-1,0};
    public int minimumObstacles(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        Deque<int[]> dq = new ArrayDeque<>();
        dq.add(new int[]{0, 0, grid[0][0]});
        grid[0][0] = -1;
        while (!dq.isEmpty()) {
            int[] top = dq.pollFirst();
            if (top[0] == rows-1 && top[1] == columns-1)
                return top[2];
            int i = top[0];
            int j = top[1];
            int obstacles = top[2];
            for (int k = 0; k < 4; k++) {
                int x = i + dx[k];
                int y = j + dy[k];
                if (x >= 0 && x < rows && y >= 0 && y < columns && grid[x][y] != -1) {
                    if (grid[x][y] == 0)
                        dq.addFirst(new int[]{x, y, obstacles});
                    else
                        dq.addLast(new int[]{x, y, obstacles + 1});
                    grid[i][j] = -1;
                }
            }
        }
        return -1;
    }

    public int minimumObstaclesV2(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int[][] minCost = new int[rows][columns];
        for (int i = 0; i < rows; i++)
            Arrays.fill(minCost[i], Integer.MAX_VALUE);
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        pq.add(new int[]{0, 0, grid[0][0]});
        minCost[0][0] = grid[0][0];
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int x = top[0];
            int y = top[1];
            int obstacles = top[2];
            for (int k = 0; k < 4; k++) {
                int i = x + dx[k];
                int j = y + dy[k];
                if (i >= 0 && i< rows && j >= 0 && j < columns && minCost[i][j] > obstacles + grid[x][y]) {
                    minCost[i][j] = obstacles + grid[x][y];
                    pq.add(new int[]{i, j, obstacles + grid[x][y]});
                }
            }
        }
        return minCost[rows-1][columns-1];
    }

    public static void main(String[] args) {
        MinimumObstacleRemovalToReachCorner M = new MinimumObstacleRemovalToReachCorner();
        System.out.println(M.minimumObstacles(new int[][]{{0,1,0,0,0},{0,1,0,1,0},{0,0,0,1,0}}));
        System.out.println(M.minimumObstacles(new int[][]{{0,1,1},{1,1,0},{1,1,0}}));
        System.out.println(M.minimumObstacles(new int[][]{{0,0},{0,1},{1,1},{1,0}}));
    }
}
