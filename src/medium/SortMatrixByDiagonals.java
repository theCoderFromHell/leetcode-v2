package medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortMatrixByDiagonals {
    public int[][] sortMatrix(int[][] grid) {
        int size = grid.length;
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int row = i;
            int column = 0;
            while (row < size && column < size) {
                values.add(grid[row][column]);
                row++;
                column++;
            }
            Collections.sort(values, Collections.reverseOrder());
            row = i;
            column = 0;
            int index = 0;
            while (row < size && column < size) {
                grid[row][column] = values.get(index++);
                row++;
                column++;
            }
            values.clear();
        }
        for (int i = 1; i < size; i++) {
            int row = 0;
            int column = i;
            while (row < size && column < size) {
                values.add(grid[row][column]);
                row++;
                column++;
            }
            Collections.sort(values);
            row = 0;
            column = i;
            int index = 0;
            while (row < size && column < size) {
                grid[row][column] = values.get(index++);
                row++;
                column++;
            }
            values.clear();
        }
        return grid;
    }
}
