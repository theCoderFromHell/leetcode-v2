package medium;

public class DifferenceBetweenOnesAndZerosInRowAndColumn {
    public int[][] onesMinusZeros(int[][] grid) {
        int M = grid.length;
        int N = grid[0].length;
        int[] rows = new int[M];
        int[] columns = new int[N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                rows[i] += grid[i][j];
                columns[j] += grid[i][j];
            }
        }
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                grid[i][j] = 2 * rows[i] + 2 * columns[j] - M - N;
        return grid;
    }
}
