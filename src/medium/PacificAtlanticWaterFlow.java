package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacificAtlanticWaterFlow {
    private int[] dx = {0,0,-1,1};
    private int[] dy = {-1,1,0,0};
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> result = new ArrayList<>();
        int rows = heights.length;
        int columns = heights[0].length;
        int[][] dp = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                dp[i][j] = -1;
            }
        }
        for (int i = 0; i < rows; i++) {
            dp[i][0] = 1;
            dp[i][columns-1] = (dp[i][columns-1] == 1 || dp[i][columns-1] == 3) ? 3 : 2;
        }
        for (int j = 0; j < columns; j++) {
            dp[0][j] = (dp[0][j] >= 2) ? 3 : 1;
            dp[rows-1][j] = (dp[rows-1][j] == 1 || dp[rows-1][j] == 3) ? 3 : 2;
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                boolean[][] visited = new boolean[rows][columns];
                dfs(heights, rows, columns, i, j, dp, visited);
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (dp[i][j] == 3)
                    result.add(new ArrayList<>(Arrays.asList(i, j)));
            }
        }

        return result;
    }

    private int dfs(int[][] heights, int rows, int columns, int i, int j, int[][] dp, boolean[][] visited) {

        boolean pacific = (dp[i][j] == 1 || dp[i][j] == 3);
        boolean atlantic = (dp[i][j] == 2 || dp[i][j] == 3);
        visited[i][j] = true;
        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x >= 0 && x < rows && y >= 0 && y < columns && heights[x][y] <= heights[i][j] && !visited[x][y]) {
                int value = dfs(heights, rows, columns, x, y, dp, visited);
                pacific = pacific || (value == 1 || value == 3);
                atlantic = atlantic || (value == 2 || value == 3);
            }
        }

        if (pacific && atlantic)
            dp[i][j] = 3;
        else if (pacific)
            dp[i][j] = 1;
        else if (atlantic)
            dp[i][j] = 2;
        else
            dp[i][j] = 0;
        return dp[i][j];
    }

    public static void main(String[] args) {
        PacificAtlanticWaterFlow P = new PacificAtlanticWaterFlow();
//        System.out.println(P.pacificAtlantic(new int[][]{
//                {1,2,2,3,5},
//                {3,2,3,4,4},
//                {2,4,5,3,1},
//                {6,7,1,4,5},
//                {5,1,1,2,4}
//        }));
        System.out.println(P.pacificAtlantic(new int[][]{
                {13},{4},{19},{10},{1},{11},{5},{17},{3},{10},{1},{0},{1},{4},{1},{3},{6},{13},{2},{16},{7},{6},{3},{1},{9},{9},{13},{10},{9},{10},{6},{2},{11},{17},{13},{0},{19},{7},{13},{3},{9},{2}
        }));

    }
}
