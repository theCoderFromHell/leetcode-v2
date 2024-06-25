package medium;

public class PathWithMinimumEffort {
    int[][] directions = {{0,-1}, {0, 1}, {-1, 0}, {1, 0}};
    public int minimumEffortPath(int[][] heights) {
        int low = 0;
        int high = 1000000;
        int result = Integer.MAX_VALUE;
        while (low <= high) {
            int mid = low + (high - low)/2;
            if (canReach(heights, mid)) {
                result = Math.min(result, mid);
                high = mid - 1;
            } else
                low = mid + 1;
        }
        return result;
    }

    private boolean canReach(int[][] heights, int K) {
        int rows = heights.length;
        int columns = heights[0].length;
        boolean[][] visited = new boolean[rows][columns];
        return dfs(heights, rows, columns, visited, 0, 0, K);
    }

    private boolean dfs(int[][] heights, int rows, int columns, boolean[][] visited, int r, int c, int K) {
        if (r == rows-1 && c == columns-1)
            return true;
        visited[r][c] = true;
        for (int[] direction : directions) {
            int x = r + direction[0];
            int y = c + direction[1];
            if (x >= 0 && x < rows && y >= 0 && y < columns && !visited[x][y] && Math.abs(heights[x][y] - heights[r][c]) <= K) {
                if (dfs(heights, rows, columns, visited, x, y, K))
                    return true;
            }
        }
        return false;
    }
}
