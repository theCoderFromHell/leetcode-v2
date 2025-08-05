package medium;

import java.util.LinkedList;
import java.util.Queue;

public class NearestExitFromEntranceInMaze {
    int[][] directions = new int[][]{{0,-1}, {0,1}, {-1,0}, {1,0}};
    public int nearestExit(char[][] maze, int[] entrance) {
        int rows = maze.length;
        int columns = maze[0].length;
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][columns];
        queue.add(new int[]{entrance[0], entrance[1], 0});
        visited[entrance[0]][entrance[1]] = true;
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int column = cell[1];
            int steps = cell[2];
            if (maze[row][column] == '.'
                    && (row == 0 || column == 0 || row == rows-1 || column == columns-1)
                    && (row != entrance[0] || column != entrance[1]))
                return steps;
            for (int[] direction : directions) {
                int x = row + direction[0];
                int y = column + direction[1];
                if (x >= 0 && x < rows && y >= 0 && y < columns && maze[x][y] == '.' && !visited[x][y]) {
                    visited[x][y] = true;
                    queue.add(new int[]{x, y, steps + 1});
                }
            }
        }
        return -1;
    }
}
