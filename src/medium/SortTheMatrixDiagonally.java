package medium;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortTheMatrixDiagonally {
    public int[][] diagonalSort(int[][] mat) {
        int rows = mat.length;
        int columns = mat[0].length;
        for (int diff = rows; diff >= -1 * columns; diff--) {
            List<Integer> diagonal = new ArrayList<>();
            int row = Math.max(diff, 0);
            int column = Math.abs(diff - row);
            while (row < rows && column < columns) {
                diagonal.add(mat[row][column]);
                row++;
                column++;
            }
            diagonal.sort(Comparator.naturalOrder());
            int index = 0;
            row = Math.max(diff, 0);
            column = Math.abs(diff - row);
            while (row < rows && column < columns) {
                mat[row][column] = diagonal.get(index++);
                row++;
                column++;
            }
        }
        return mat;
    }
}
