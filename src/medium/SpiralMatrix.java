package medium;

import java.util.ArrayList;
import java.util.List;

public class SpiralMatrix {
    public List<Integer> spiralOrder(int[][] matrix) {
        int M = matrix.length;
        int N = matrix[0].length;
        List<Integer> result = new ArrayList<>();
        spiralOrder(matrix, 0, 0, M-1, N-1, result);
        return result;
    }

    private void spiralOrder(int[][] matrix, int startX, int startY, int endX, int endY, List<Integer> result) {
        if(startX > endX || startY > endY)
            return;
        int rows = endX - startX + 1;
        int columns = endY - startY + 1;
        if(rows == 1) {
            for (int j = startY; j <= endY ; j++)
                result.add(matrix[startX][j]);
            return;
        }
        if(columns == 1) {
            for (int i = startX; i <= endX ; i++)
                result.add(matrix[i][startY]);
            return;
        }
        for (int j = startY; j < endY; j++) {
            result.add(matrix[startX][j]);
        }
        for (int i = startX; i < endX; i++) {
            result.add(matrix[i][endY]);
        }
        for (int j = endY; j > startY; j--) {
            result.add(matrix[endX][j]);
        }
        for (int i = endX; i > startX; i--) {
            result.add(matrix[i][startY]);
        }
        spiralOrder(matrix, startX + 1, startY + 1, endX - 1, endY - 1, result);
    }
}
