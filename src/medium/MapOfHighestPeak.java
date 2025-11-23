package medium;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MapOfHighestPeak {
    public int[] dx = {-1, 1, 0, 0};
    public int[] dy = {0, 0, -1, 1};
    public int[][] highestPeak(int[][] isWater) {
        int rows =  isWater.length;
        int columns = isWater[0].length;
        int[][] result = new int[rows][columns];
        for (int i = 0; i < rows; i++)
            Arrays.fill(result[i], -1);
        Queue<int[]> q = new LinkedList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (isWater[i][j] == 1) {
                    q.add(new int[]{i, j});
                    result[i][j] = 0;
                }
            }
        }
        int level = 1;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int[] cell = q.poll();
                int row = cell[0];
                int column = cell[1];
                for (int i = 0; i < 4; i++) {
                    int newRow = row + dx[i];
                    int newColumn = column + dy[i];
                    if (newRow >= 0 && newRow < rows && newColumn >= 0 && newColumn < columns) {
                        if (result[newRow][newColumn] == -1) {
                            result[newRow][newColumn] = level;
                            q.add(new int[]{newRow, newColumn});
                        }
                    }
                }
            }
            level++;
        }
        return result;
    }

    public static void main(String[] args) {
        MapOfHighestPeak M = new MapOfHighestPeak();
        System.out.println(Arrays.deepToString(M.highestPeak(new int[][]{{0, 1}, {0, 0}})));
        System.out.println(Arrays.deepToString(M.highestPeak(new int[][]{{0,0,1},{1,0,0},{0,0,0}})));
    }
}
