package interviews.adobe;

import java.util.HashSet;

public class AdobeOne {
    int[] dx = {0,0,-1,1};
    int[] dy = {-1, 1, 0, 0};
    int minStep = Integer.MAX_VALUE;

    public int minSteps(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        HashSet<String> visited = new HashSet<>();
        navigate(grid, rows, columns, rows-1, 0, 0, visited);
        return minStep;
    }

    public void navigate(int[][] grid, int rows, int columns, int x, int y, int steps, HashSet<String> visited) {
        visited.add(x + "-" + y);
        if (x == 0 && y == columns-1 && grid[x][y] == 0) {
            System.out.println("minStep before " + minStep);
            minStep = Math.min(minStep, steps);
            System.out.println("minStep after " + minStep);

        }
        //TODO : slip-down case

        if (grid[x][y] == 1) {
            int next_X = x+1;
            int next_Y = y;
            grid[x][y] = 0;
            if (next_X >= 0 && next_X < rows && next_Y >= 0 && next_Y < columns) {
                navigate(copyOfGrid(grid), rows, columns, next_X, next_Y, steps+1, visited);
            } else
                navigate(copyOfGrid(grid), rows, columns, x, y, steps+1, visited);
        } else {
            for (int k = 0; k < 4; k++) {
                int next_X = x + dx[k];
                int next_Y = y + dy[k];
                if (next_X >= 0 && next_X < rows && next_Y >= 0 && next_Y < columns &&!visited.contains(next_X + "-" + next_Y)) {
                    navigate(grid, rows, columns, next_X, next_Y, steps+1, visited);
                }
            }
        }
        visited.remove(x + "-" + y);
    }

    private int[][] copyOfGrid(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int[][] gridCopy = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                gridCopy[i][j] = grid[i][j];
            }
        }
        return gridCopy;
    }

    public static void main(String[] args) {
        AdobeOne solution = new AdobeOne();
        int[][] grid = new int[][] {
                {0, 1, 0},
                {0, 0, 1},
                {0, 1, 1}
        };
        System.out.println(solution.minSteps(grid));
    }
}

