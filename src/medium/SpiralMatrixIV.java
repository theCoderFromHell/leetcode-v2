package medium;

import common.ListNode;

import java.util.Arrays;

public class SpiralMatrixIV {
    public int[][] spiralMatrix(int m, int n, ListNode head) {
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++)
            Arrays.fill(matrix[i], -1);
        spiralOrder(matrix, 0, 0, m-1, n-1, head);
        return matrix;
    }

    private void spiralOrder(int[][] matrix, int startX, int startY, int endX, int endY, ListNode node) {
        if(node == null || startX > endX || startY > endY)
            return;
        int rows = endX - startX + 1;
        int columns = endY - startY + 1;
        if (rows == 1) {
            for (int j = startY; j <= endY && node != null; j++) {
                matrix[startX][j] = node.val;
                node = node.next;
            }
            return;
        }
        if (columns == 1) {
            for (int i = startX; i <= endX && node != null; i++) {
                matrix[i][startY] = node.val;
                node = node.next;
            }
            return;
        }
        for (int j = startY; j < endY && node != null; j++) {
            matrix[startX][j] = node.val;
            node = node.next;
        }
        for (int i = startX; i < endX && node != null; i++) {
            matrix[i][endY] = node.val;
            node = node.next;
        }
        for (int j = endY; j > startY && node != null; j--) {
            matrix[endX][j] = node.val;
            node = node.next;
        }
        for (int i = endX; i > startX && node != null; i--) {
            matrix[i][startY] = node.val;
            node = node.next;
        }

        spiralOrder(matrix, startX + 1, startY + 1, endX - 1, endY - 1, node);
    }
}
