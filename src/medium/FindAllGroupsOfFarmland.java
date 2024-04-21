package medium;

import java.util.ArrayList;
import java.util.List;

public class FindAllGroupsOfFarmland {
    public int[][] findFarmland(int[][] land) {
        List<int[]> result = new ArrayList<>();
        int rows = land.length;
        int columns = land[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (land[i][j] == 1) {
                    List<int[]> cells = new ArrayList<>();
                    dfs(land, rows, columns, i, j, cells);
                    int minX = i, minY = j, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
                    for (int[] cell : cells) {
                        maxX = Math.max(maxX, cell[0]);
                        maxY = Math.max(maxY, cell[1]);
                    }
                    result.add(new int[]{minX, minY, maxX, maxY});
                }
            }
        }
        return result.toArray(new int[result.size()][]);
    }

    private void dfs(int[][] land, int rows, int columns, int i, int j, List<int[]> cells) {
        land[i][j] = 0;
        cells.add(new int[]{i, j});
        if (i+1 < rows && land[i+1][j] == 1)
            dfs(land, rows, columns, i+1, j, cells);
        if (j+1 < columns && land[i][j+1] == 1)
            dfs(land, rows, columns, i, j+1, cells);
    }

    public static void main(String[] args) {
        FindAllGroupsOfFarmland f = new FindAllGroupsOfFarmland();
        f.findFarmland(new int[][]{
                {1,0,0},
                {0,1,1},
                {0,1,1}
        });
    }
}
