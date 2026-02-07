package medium;

public class ScoreAfterFlippingMatrix {
    public int matrixScore(int[][] grid) {
        int rows =  grid.length;
        int columns = grid[0].length;
        for (int row = 0; row < rows; row++) {
            if (grid[row][0] == 0)
                flip(grid, true, row, rows, columns);
        }
        for (int column = 1; column < columns; column++) {
            if (countOnes(grid, column, rows, columns) < (rows+1)/2)
                flip(grid, false, column, rows, columns);
        }
        int sum = 0;
        for (int row = 0; row < rows; row++) {
            int rowValue = 0;
            for (int column = 0; column < columns; column++)
                rowValue = rowValue * 2 + grid[row][column];
            sum += rowValue;
        }
        return sum;
    }

    private int countOnes(int[][] grid, int index, int rows, int columns) {
        int count = 0;
        for (int row = 0; row < rows; row++)
            count += grid[row][index];
        return count;
    }

    private void flip(int[][] grid, boolean isRow, int index, int rows, int columns) {
        if (isRow) {
            for (int col = 0; col < columns; col++)
                grid[index][col] = 1 - grid[index][col];
        } else {
            for (int row = 0; row < rows; row++)
                grid[row][index] = 1 - grid[row][index];
        }
    }

    public static void main(String[] args) {
        ScoreAfterFlippingMatrix S = new ScoreAfterFlippingMatrix();
        System.out.println("Test 1: " + S.matrixScore(new int[][]{{0,0,1,1},{1,0,1,0},{1,1,0,0}}) + " (Expected: 39)");
        System.out.println("Test 2: " + S.matrixScore(new int[][]{{0}}) + " (Expected: 1)");
        System.out.println("Test 3: " + S.matrixScore(new int[][]{{1}}) + " (Expected: 1)");
        System.out.println("Test 4: " + S.matrixScore(new int[][]{{0,1,0,1}}) + " (Expected: 15)");
        System.out.println("Test 5: " + S.matrixScore(new int[][]{{0},{1},{0}}) + " (Expected: 3)");
        System.out.println("Test 6: " + S.matrixScore(new int[][]{{0,0},{0,0}}) + " (Expected: 6)");
        System.out.println("Test 7: " + S.matrixScore(new int[][]{{1,1},{1,1}}) + " (Expected: 6)");
        System.out.println("Test 8: " + S.matrixScore(new int[][]{{0,1},{1,1}}) + " (Expected: 5)");
    }
}
