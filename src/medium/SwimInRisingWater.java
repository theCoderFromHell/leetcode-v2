package medium;

public class SwimInRisingWater {
    int[] dx = {0,0,-1,1};
    int[] dy = {-1,1,0,0};
    public int swimInWater(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int high = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                high = Math.max(high, grid[i][j]);
            }
        }
        int result = high;
        int low = 0, mid;
        while (low <= high) {
            mid = low + (high - low)/2;
            boolean[][] visited = new boolean[rows][columns];
            if (mid >= grid[0][0] && dfs(grid, rows, columns, 0, 0, visited, mid)) {
                result = Math.min(result, mid);
                high = mid-1;
            } else
                low = mid + 1;
        }
        return result;
    }

    private boolean dfs(int[][] grid, int rows, int columns, int i, int j, boolean[][] visited, int time) {
        if (i == rows-1 && j == columns-1)
            return true;
        visited[i][j] = true;
        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x >= 0 && x < rows && y >= 0 && y < columns && !visited[x][y] && time >= grid[x][y]) {
                 if (dfs(grid, rows, columns, x, y, visited, time))
                     return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SwimInRisingWater S = new SwimInRisingWater();
        System.out.println(S.swimInWater(new int[][]{{0,2},{1,3}}));
        System.out.println(S.swimInWater(new int[][]{{0,1,2,3,4},{24,23,22,21,5},{12,13,14,15,16},{11,17,18,19,20},{10,9,8,7,6}}));
        System.out.println(S.swimInWater(new int[][]{{7,23,21,9,5},{3,20,8,18,15},{14,13,1,0,22},{2,10,24,17,12},{6,16,19,4,11}}));
    }
}
