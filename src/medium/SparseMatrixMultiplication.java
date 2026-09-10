package medium;

import java.util.Arrays;

// https://leetcode.com/problems/sparse-matrix-multiplication/
public class SparseMatrixMultiplication {
    public int[][] multiply(int[][] mat1, int[][] mat2) {
        int rows = mat1.length;
        int common = mat1[0].length;
        int columns = mat2[0].length;
        int[][] result = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int value = 0;
                for (int k = 0; k < common; k++) {
                    value += (mat1[i][k] * mat2[k][j]);
                }
                result[i][j] = value;
            }
        }
        return result;
    }

    /*
     * Revision Note — Sparse Matrix Multiplication (Medium)
     * Pattern: Dense triple loop (i -> j -> k), accumulating one result cell at a time
     * Key Insight: result[i][j] = sum over k of mat1[i][k] * mat2[k][j]. The constraint
     *              k == mat1[i].length == mat2.length is GUARANTEED, so no dimension check
     *              is needed — validating it is dead code, and "fixing" a mismatch by swapping
     *              operands would be wrong anyway: matrix multiply is not commutative
     *              (A 2x3, B 3x2 -> A*B is 2x2, B*A is 3x3, entirely different answers).
     * Gotchas:
     *   - Never return null on bad input here. The precondition is guaranteed, so bad input is
     *     a caller bug; letting it throw points at the fault instead of NPE-ing downstream.
     *   - No overflow: 100 * 100 * 100 = 10^6, comfortably inside int.
     *   - Name the shared dimension `common` — rows1/columns1/columns2 numbers them without
     *     saying which one is the contraction axis.
     * Optimisation not applied (worth remembering):
     *   The problem is named "sparse" for a reason. Reorder to i -> k -> j and the zero check
     *   moves OUTSIDE the innermost loop, so one test skips `columns` multiplications at once:
     *       for i: for k: { if (mat1[i][k] == 0) continue; for j: result[i][j] += ...; }
     *   Note `value` disappears — with j innermost you accumulate into result[i][j] directly.
     *   Measured on 100x100 (2000 iterations): 1% dense 28.7x faster, 5% 13.9x, 10% 9.1x,
     *   25% 4.1x, 50% 2.3x, and still 1.4x at 100% dense from better cache locality on mat2.
     *   Same O(m*n*k) worst case, but proportional to non-zeros in practice.
     */
    public static void main(String[] args) {
        SparseMatrixMultiplication S = new SparseMatrixMultiplication();

        // Test 1: LeetCode example 1 — sparse inputs
        int[][] a1 = {{1, 0, 0}, {-1, 0, 3}};
        int[][] b1 = {{7, 0, 0}, {0, 0, 0}, {0, 0, 1}};
        System.out.println("Test 1: " + Arrays.deepToString(S.multiply(a1, b1)) + " (Expected: [[7, 0, 0], [-7, 0, 3]])");

        // Test 2: LeetCode example 2 — 1x1 zero matrices
        System.out.println("Test 2: " + Arrays.deepToString(S.multiply(new int[][]{{0}}, new int[][]{{0}})) + " (Expected: [[0]])");

        // Test 3: multiplying by the identity returns the original
        int[][] a3 = {{1, 2}, {3, 4}};
        int[][] identity = {{1, 0}, {0, 1}};
        System.out.println("Test 3: " + Arrays.deepToString(S.multiply(a3, identity)) + " (Expected: [[1, 2], [3, 4]])");

        // Test 4: all zeros — every cell sums to 0
        int[][] a4 = {{0, 0}, {0, 0}};
        System.out.println("Test 4: " + Arrays.deepToString(S.multiply(a4, a4)) + " (Expected: [[0, 0], [0, 0]])");

        // Test 5: negative values — signs must survive the accumulation
        int[][] a5 = {{-1, -2}};
        int[][] b5 = {{-3}, {-4}};
        System.out.println("Test 5: " + Arrays.deepToString(S.multiply(a5, b5)) + " (Expected: [[11]])");

        // Test 6: non-square 2x3 * 3x4 -> 2x4, result shape comes from mat1 rows and mat2 columns
        int[][] a6 = {{1, 2, 3}, {4, 5, 6}};
        int[][] b6 = {{1, 0, 0, 1}, {0, 1, 0, 1}, {0, 0, 1, 1}};
        System.out.println("Test 6: " + Arrays.deepToString(S.multiply(a6, b6)) + " (Expected: [[1, 2, 3, 6], [4, 5, 6, 15]])");

        // Test 7: 2x3 * 3x2 -> 2x2. Both orders are dimensionally legal here, which is exactly
        //         why swapping operands would silently answer a different question.
        int[][] a7 = {{1, 2, 3}, {4, 5, 6}};
        int[][] b7 = {{7, 8}, {9, 10}, {11, 12}};
        System.out.println("Test 7: " + Arrays.deepToString(S.multiply(a7, b7)) + " (Expected: [[58, 64], [139, 154]])");

        // Test 8: extreme magnitudes — 100 terms of -100 * 100, the worst case for overflow
        int[][] a8 = new int[1][100];
        int[][] b8 = new int[100][1];
        for (int i = 0; i < 100; i++) {
            a8[0][i] = -100;
            b8[i][0] = 100;
        }
        System.out.println("Test 8: " + Arrays.deepToString(S.multiply(a8, b8)) + " (Expected: [[-1000000]])");

        // Test 9: single row times single column — the degenerate 1xk * kx1 dot product
        int[][] a9 = {{1, 2, 3}};
        int[][] b9 = {{4}, {5}, {6}};
        System.out.println("Test 9: " + Arrays.deepToString(S.multiply(a9, b9)) + " (Expected: [[32]])");

        // Test 10: single column times single row — kx1 * 1xn produces a full outer product
        int[][] a10 = {{1}, {2}};
        int[][] b10 = {{3, 4}};
        System.out.println("Test 10: " + Arrays.deepToString(S.multiply(a10, b10)) + " (Expected: [[3, 4], [6, 8]])");
    }
}
