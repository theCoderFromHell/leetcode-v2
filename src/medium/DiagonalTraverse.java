package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// https://leetcode.com/problems/diagonal-traverse/
public class DiagonalTraverse {
    public int[] findDiagonalOrder(int[][] mat) {
        int rows = mat.length;
        int columns = mat[0].length;
        int maxValue = rows + columns - 2;
        int[] result = new int[rows * columns];
        int index = 0;
        boolean flag = true;
        for (int sum = 0; sum <= maxValue; sum++) {
            int j;
            if (flag) {
                // Start with minimum value of j
                j = Math.max(0, sum - rows + 1);
                while (j < columns && sum - j >= 0) {
                    result[index++] = mat[sum - j][j];
                    j++;
                }
            } else {
                // Start with maximum value of j
                j = Math.min(columns-1, sum);
                while (j >= 0 && sum - j < rows) {
                    result[index++] = mat[sum - j][j];
                    j--;
                }
            }
            flag = !flag;
        }
        return result;
    }

    /*
     * Revision Note — Diagonal Traverse (Medium)
     *
     * Pattern: Group cells by the coordinate sum r+c, alternating direction per group
     *
     * Key Insight: Every cell on a diagonal shares the same r+c, so the sum IS the group key
     * — same shape as (r/3)*3 + c/3 naming the Sudoku box. There are rows+columns-1 diagonals.
     * Walk each one by iterating the column j and deriving the row as sum-j, flipping
     * direction every diagonal: even sums run up-right (j ascending), odd run down-left.
     *
     * Gotchas:
     * - The column bounds clamp against DIFFERENT dimensions at each end:
     *     j from  max(0, sum - rows + 1)   to   min(columns - 1, sum)
     *   Mixing up which of rows/columns belongs where reads off the end of the array. The
     *   degenerate shapes pin it down — a 1xN and an Nx1 each keep one bound permanently active
     * - Direction MUST alternate. Emitting every diagonal the same way gives
     *   [1,2,4,3,5,7,...] instead of [1,2,4,7,5,3,...] — correct groups, wrong order within them
     * - Even diagonals go up-right, starting from the BOTTOM of the diagonal. Diagonal 0 is a
     *   single cell so it cannot tell you the convention; check diagonal 2 instead
     * - Constraint is m*n <= 1e4, not m,n <= 1e4 — the matrix can be 1e4 x 1 but never large
     *   in both dimensions, so result[] sized rows*columns is always safe
     *
     * Template:
     *   for sum in 0 .. rows+columns-2:
     *     if sum even:  j = min(columns-1, sum);        while j >= 0 && sum-j < rows:  emit; j--
     *     else:         j = max(0, sum - rows + 1);     while j < columns && sum-j >= 0: emit; j++
     *     // emit = result[index++] = mat[sum - j][j]
     *
     * V2 — bucket every cell into a list indexed by r+c, reverse the even-indexed lists, then
     * concatenate. Makes the grouping key completely explicit and needs no bound reasoning at
     * all. Same O(m*n) time, but O(m*n) extra space
     * plus boxing on every element, so the direct walk above is the one to submit.
     */
    public int[] findDiagonalOrderV2(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        List<List<Integer>> diag = new ArrayList<>();
        for (int i = 0; i < m + n - 1; i++)
            diag.add(new ArrayList<>());
        for (int r = 0; r < m; r++)
            for (int c = 0; c < n; c++)
                diag.get(r + c).add(mat[r][c]);
        int[] out = new int[m * n];
        int k = 0;
        for (int i = 0; i < diag.size(); i++) {
            List<Integer> list = diag.get(i);
            if (i % 2 == 0)
                Collections.reverse(list);   // even diagonals run up-right
            for (int v : list)
                out[k++] = v;
        }
        return out;
    }

    public static void main(String[] args) {
        DiagonalTraverse D = new DiagonalTraverse();

        System.out.println("Test 1: " + Arrays.toString(D.findDiagonalOrder(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}))
                + " (Expected: [1, 2, 4, 7, 5, 3, 6, 8, 9])");
        System.out.println("Test 2: " + Arrays.toString(D.findDiagonalOrder(new int[][]{{1, 2}, {3, 4}}))
                + " (Expected: [1, 2, 3, 4])");
        System.out.println("Test 3: " + Arrays.toString(D.findDiagonalOrder(new int[][]{{1}}))
                + " (Expected: [1])");                                    // single cell
        System.out.println("Test 4: " + Arrays.toString(D.findDiagonalOrder(new int[][]{{1, 2, 3, 4}}))
                + " (Expected: [1, 2, 3, 4])");                           // single row
        System.out.println("Test 5: " + Arrays.toString(D.findDiagonalOrder(new int[][]{{1}, {2}, {3}, {4}}))
                + " (Expected: [1, 2, 3, 4])");                           // single column
        System.out.println("Test 6: " + Arrays.toString(D.findDiagonalOrder(new int[][]{{1, 2, 3}, {4, 5, 6}}))
                + " (Expected: [1, 2, 4, 5, 3, 6])");                     // wider than tall
        System.out.println("Test 7: " + Arrays.toString(D.findDiagonalOrder(new int[][]{{1, 2}, {3, 4}, {5, 6}}))
                + " (Expected: [1, 2, 3, 5, 4, 6])");                     // taller than wide
    }
}
