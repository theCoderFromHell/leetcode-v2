package medium;

import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathInBinaryMatrix {
    int[] dx = {0,0,1,1,1,-1,-1,-1};
    int[] dy = {-1,1,-1,0,1,-1,0,1};
    public int shortestPathBinaryMatrix(int[][] grid) {
        int N = grid.length;
        if (grid[0][0] == 1)
            return -1;
        Queue<Cell> queue = new LinkedList<>();
        boolean[][] visited = new boolean[N][N];
        queue.add(new Cell(0, 0, 1));
        visited[0][0] = true;
        int minDistance = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            Cell top = queue.poll();
            if (top.x == N-1 && top.y == N-1) {
                minDistance = Math.min(minDistance, top.distance);
                continue;
            }
            for (int k = 0; k < 8; k++) {
                int i = top.x + dx[k];
                int j = top.y + dy[k];
                if (i >= 0 && i < N && j >= 0 && j < N && !visited[i][j] && grid[i][j] == 0) {
                    visited[i][j] = true;
                    queue.add(new Cell(i, j, top.distance + 1));
                }
            }
        }
        return (minDistance == Integer.MAX_VALUE) ? -1 : minDistance;
    }

    class Cell {
        int x;
        int y;
        int distance;

        public Cell(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "x=" + x +
                    ", y=" + y +
                    ", distance=" + distance +
                    '}';
        }
    }

    public static void main(String[] args) {
        ShortestPathInBinaryMatrix S = new ShortestPathInBinaryMatrix();
        System.out.println(S.shortestPathBinaryMatrix(new int[][]{
                {0,1},
                {1,0}
        }));
        System.out.println(S.shortestPathBinaryMatrix(new int[][]{
                {0,0,0},
                {1,1,0},
                {1,1,0}
        }));
        System.out.println(S.shortestPathBinaryMatrix(new int[][]{
                {1,0,0},
                {1,1,0},
                {1,1,0}
        }));
    }
}
