package medium;

import java.util.Arrays;
import java.util.Random;

// https://leetcode.com/problems/matrix-block-sum/
public class MatrixBlockSum {
    public int[][] matrixBlockSum(int[][] mat, int k) {
        int rows = mat.length;
        int columns = mat[0].length;
        int[][] prefixSums = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            int sum = 0;
            for (int j = 0; j < columns; j++) {
                sum += mat[i][j];
                prefixSums[i][j] = sum;
            }
        }
        int[][] result = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            int xStart = Math.max(0, i-k);
            int xEnd = Math.min(rows-1, i+k);
            for (int j = 0; j < columns; j++) {
                int yStart = Math.max(0, j-k);
                int yEnd = Math.min(columns-1, j+k);
                int sum = 0;
                for (int row = xStart; row <= xEnd; row++) {
                    sum += prefixSums[row][yEnd] - (yStart == 0 ? 0 : prefixSums[row][yStart - 1]);
                }
                result[i][j] = sum;
            }
        }
        return result;
    }

    /*
     * Revision Note — Matrix Block Sum (Medium)
     *
     * Pattern: Row-wise prefix sum + clamped block bounds
     *
     * Key Insight: Prefix each ROW independently, then each answer sums one O(1) horizontal
     * slice per row in the block's vertical range. Clamp all four bounds, since k can exceed
     * the matrix dimensions in every direction.
     *
     * Gotchas:
     * - k can be up to 100 while the matrix is 1x1, so i-k goes deeply negative and i+k far
     *   past the last row. Every corner needs max(0,..) / min(dim-1,..)
     * - yStart == 0 needs a guard before reading prefixSums[row][yStart-1]; a padded table
     *   removes that (see below)
     * - Bounds are INCLUSIVE here (xEnd = min(rows-1, i+k)), so the row loop uses <=
     * - No overflow: 100x100 cells x 100 max value = 1e6, well inside int
     *
     * This version is O(m*n*k) — the row loop still walks the vertical direction. At the
     * limits (m=n=k=100) that is ~2e6 operations, comfortably accepted.
     *
     * The O(m*n) version prefixes BOTH directions, so k disappears entirely:
     *   build (padded (m+1)x(n+1), zero row and column):
     *     p[i+1][j+1] = mat[i][j] + p[i][j+1] + p[i+1][j] - p[i][j]
     *       (add above + left, subtract the overlap counted twice)
     *   query rows r1..r2, cols c1..c2:
     *     p[r2+1][c2+1] - p[r1][c2+1] - p[r2+1][c1] + p[r1][c1]
     *       (big box, minus strip above, minus strip left, plus the corner removed twice)
     * The +1 padding is what earns the simplicity: r1 == 0 reads the zero row instead of
     * needing a guard — and here you clamp on all four sides, so it saves four guards.
     *
     * Rule of thumb: a prefix pass removes one dimension of work per dimension precomputed.
     * Rows only -> O(k) per cell. Both -> O(1) per cell.
     */
    // O(m*n*k^2) reference, used only to cross-check
    private static int[][] bruteForce(int[][] mat, int k) {
        int m = mat.length, n = mat[0].length;
        int[][] out = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                for (int r = Math.max(0, i - k); r <= Math.min(m - 1, i + k); r++)
                    for (int c = Math.max(0, j - k); c <= Math.min(n - 1, j + k); c++)
                        out[i][j] += mat[r][c];
        return out;
    }

    public static void main(String[] args) {
        MatrixBlockSum M = new MatrixBlockSum();

        int[][] grid = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println("Test 1: " + Arrays.deepToString(M.matrixBlockSum(grid, 1))
                + " (Expected: [[12, 21, 16], [27, 45, 33], [24, 39, 28]])");
        System.out.println("Test 2: " + Arrays.deepToString(M.matrixBlockSum(grid, 2))
                + " (Expected: [[45, 45, 45], [45, 45, 45], [45, 45, 45]])"); // k covers whole matrix

        System.out.println("Test 3: " + Arrays.deepToString(M.matrixBlockSum(new int[][]{{5}}, 100))
                + " (Expected: [[5]])");                                      // 1x1, k far exceeds bounds

        System.out.println("Test 4: " + Arrays.deepToString(M.matrixBlockSum(new int[][]{{1, 2}, {3, 4}}, 1))
                + " (Expected: [[10, 10], [10, 10]])");

        System.out.println("Test 5: " + Arrays.deepToString(M.matrixBlockSum(new int[][]{{1, 2, 3, 4, 5}}, 1))
                + " (Expected: [[3, 6, 9, 12, 9]])");                          // single row

        System.out.println("Test 6: " + Arrays.deepToString(M.matrixBlockSum(new int[][]{{1}, {2}, {3}}, 1))
                + " (Expected: [[3], [6], [5]])");                             // single column

        // cross-check against brute force on random matrices and k values
        boolean agree = true;
        Random rnd = new Random(5);
        for (int t = 0; t < 400; t++) {
            int m = 1 + rnd.nextInt(8), n = 1 + rnd.nextInt(8), k = 1 + rnd.nextInt(10);
            int[][] g = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++) g[i][j] = 1 + rnd.nextInt(100);
            if (!Arrays.deepEquals(M.matrixBlockSum(g, k), bruteForce(g, k))) agree = false;
        }
        System.out.println("Test 7: " + agree + " (Expected: true)  — matches brute force, 400 random grids");
    }
}
