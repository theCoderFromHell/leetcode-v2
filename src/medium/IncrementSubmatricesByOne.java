package medium;

// https://leetcode.com/problems/increment-submatrices-by-one/

import java.util.Arrays;

public class IncrementSubmatricesByOne {
    public int[][] rangeAddQueries(int n, int[][] queries) {
        int[][] diffMatrix = new int[n+1][n+1];
        for (int[] query : queries) {
            int x1 = query[0];
            int y1 = query[1];
            int x2 = query[2];
            int y2 = query[3];
            diffMatrix[x1][y1] += 1;
            diffMatrix[x1][y2+1] -= 1;
            diffMatrix[x2+1][y1] -= 1;
            diffMatrix[x2+1][y2+1] += 1;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                diffMatrix[i][j] += diffMatrix[i][j-1];
            }
        }
        for (int j = 0; j < n; j++) {
            for (int i = 1; i < n; i++) {
                diffMatrix[i][j] += diffMatrix[i-1][j];
            }
        }
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++)
            result[i] = Arrays.copyOfRange(diffMatrix[i], 0, n);
        return result;
    }

    public static void main(String[] args) {
        IncrementSubmatricesByOne I = new IncrementSubmatricesByOne();
        System.out.println(Arrays.deepToString(I.rangeAddQueries(3, new int[][]{{0, 0, 1, 1}, {1, 1, 2, 2}, {0, 2, 2, 2}})));
        // [[1,1,1],[1,2,1],[0,1,1]]
        System.out.println(Arrays.deepToString(I.rangeAddQueries(2, new int[][]{{0, 0, 1, 1}})));
        // [[1,1],[1,1]]
        System.out.println(Arrays.deepToString(I.rangeAddQueries(3, new int[][]{{0, 0, 2, 2}, {0, 0, 2, 2}})));
        // [[2,2,2],[2,2,2],[2,2,2]] — same query twice
        System.out.println(Arrays.deepToString(I.rangeAddQueries(1, new int[][]{{0, 0, 0, 0}})));
        // [[1]] — single cell
    }
}
