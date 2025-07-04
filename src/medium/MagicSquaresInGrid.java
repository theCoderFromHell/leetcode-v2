package medium;

import java.util.HashSet;

public class MagicSquaresInGrid {
    public int numMagicSquaresInside(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int count = 0;
        for (int i = 0; i <= rows - 3; i++) {
            for (int j = 0; j <= columns - 3; j++) {
                if (isMagicSquare(i, j, grid, rows, columns))
                    count++;
            }
        }
        return count;
    }

    private boolean isMagicSquare(int row, int column, int[][] grid, int rows, int columns) {
        HashSet<Integer> numbers = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = grid[row + i][column + j];
                if (value < 1 || value > 9 || numbers.contains(value))
                    return false;
                numbers.add(value);
            }
        }
        int row1 = grid[row][column] + grid[row][column+1] + grid[row][column+2];
        int row2 = grid[row+1][column] + grid[row+1][column+1] + grid[row+1][column+2];
        if (row2 != row1)
            return false;
        int row3 = grid[row+2][column] + grid[row+2][column+1] + grid[row+2][column+2];
        if (row3 != row1)
            return false;

        int column1 = grid[row][column] +  grid[row+1][column] + grid[row+2][column];
        if (column1 != row1)
            return false;
        int column2 = grid[row][column+1] +  grid[row+1][column+1] + grid[row+2][column+1];
        if (column2 != column1)
            return false;
        int column3 = grid[row][column+2] +  grid[row+1][column+2] + grid[row+2][column+2];
        if (column3 != column1)
            return false;

        int diagonal1 = grid[row][column] + grid[row+1][column+1] + grid[row+2][column+2];
        if (diagonal1 != row1)
            return false;
        int diagonal2 = grid[row+2][column] + grid[row+1][column+1] + grid[row][column+2];
        if (diagonal2 != diagonal1)
            return false;
        return true;
    }

    public static void main(String[] args) {
        MagicSquaresInGrid M = new MagicSquaresInGrid();
        System.out.println(M.numMagicSquaresInside(new int[][]{{4,3,8,4},{9,5,1,9},{2,7,6,2}}));
        System.out.println(M.numMagicSquaresInside(new int[][]{{8}}));
//        System.out.println(M.numMagicSquaresInside(new int[][]));
//        System.out.println(M.numMagicSquaresInside(new int[][]));
    }
}
