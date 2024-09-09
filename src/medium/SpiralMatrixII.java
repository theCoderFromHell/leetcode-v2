package medium;

public class SpiralMatrixII {
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        spiralOrder(matrix, 0, 0, n-1, n-1, 1);
        return matrix;
    }

    private void spiralOrder(int[][] matrix, int startX, int startY, int endX, int endY, int value) {
        if(startX > endX || startY > endY)
            return;
        int rows = endX - startX + 1;
        int columns = endY - startY + 1;
        if (rows == 1) {
            for (int j = startY; j <= endY ; j++) {
                matrix[startX][j] = value++;
            }
            return;
        }
        if (columns == 1) {
            for (int i = startX; i <= endX ; i++) {
                matrix[i][startY] = value++;
            }
            return;
        }
        for (int j = startY; j < endY; j++) {
            matrix[startX][j] = value++;
        }
        for (int i = startX; i < endX; i++) {
            matrix[i][endY] = value++;
        }
        for (int j = endY; j > startY; j--) {
            matrix[endX][j] = value++;
        }
        for (int i = endX; i > startX; i--) {
            matrix[i][startY] = value++;
        }

        spiralOrder(matrix, startX + 1, startY + 1, endX - 1, endY - 1, value);
    }
}
