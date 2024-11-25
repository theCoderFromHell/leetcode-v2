package medium;

import java.util.Arrays;

public class CountUnguardedCellsInTheGrid {
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        char[][] grid = new char[m][n];
        for (int i = 0; i < m; i++)
            Arrays.fill(grid[i], 'E');
        for (int[] guard : guards)
            grid[guard[0]][guard[1]] = 'G';
        for (int[] wall : walls)
            grid[wall[0]][wall[1]] = 'W';
        for (int[] guard : guards)
            mark(grid, m, n, guard[0], guard[1]);
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 'E')
                    count++;
            }
        }
        return count;
    }

    private void mark(char[][] grid, int rows, int columns, int i, int j) {
        int x = i-1, y = j;
        while (x >= 0 && grid[x][y] != 'W' && grid[x][y] != 'G') {
            if (grid[x][y] != 'G')
                grid[x][y] = 'S';
            x--;
        }
        x = i+1;
        y = j;
        while (x < rows && grid[x][y] != 'W' && grid[x][y] != 'G') {
            if (grid[x][y] != 'G')
                grid[x][y] = 'S';
            x++;
        }
        x = i;
        y = j-1;
        while (y >= 0 && grid[x][y] != 'W' && grid[x][y] != 'G') {
            if (grid[x][y] != 'G')
                grid[x][y] = 'S';
            y--;
        }
        x = i;
        y = j+1;
        while (y < columns && grid[x][y] != 'W' && grid[x][y] != 'G') {
            if (grid[x][y] != 'G')
                grid[x][y] = 'S';
            y++;
        }
    }

    public static void main(String[] args) {
        CountUnguardedCellsInTheGrid C = new CountUnguardedCellsInTheGrid();
        System.out.println(C.countUnguarded(4, 6, new int[][]{{0,0},{1,1},{2,3}}, new int[][]{{0,1},{2,2},{1,4}}));
    }
}
