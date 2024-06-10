package medium;

public class CountSubIslands {
    int[] dx = {-1,1,0,0};
    int[] dy = {0,0,-1,1};
    int LAND = 1;
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int M = grid1.length;
        int N = grid2[0].length;
        int count = 0;
        boolean[][] visited = new boolean[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid2[i][j] == LAND &&  !visited[i][j]) {
                    if (dfs(grid1, grid2, M, N, visited, i, j))
                        count++;
                }
            }
        }
        return count;
    }

    private boolean dfs(int[][] grid1, int[][] grid2, int M, int N, boolean[][] visited, int i, int j) {
        visited[i][j] = true;
        boolean result = true;
        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x >= 0 && x < M && y >= 0 && y < N && !visited[x][y] && grid2[x][y] == LAND)
                if (!dfs(grid1, grid2, M, N, visited, x, y))
                    result = false;
        }
        return grid1[i][j] == LAND && result;
    }

    public static void main(String[] args) {
        CountSubIslands c = new CountSubIslands();
        System.out.println(c.countSubIslands(new int[][]{
                {1,1,1,0,0},{0,1,1,1,1},{0,0,0,0,0},{1,0,0,0,0},{1,1,0,1,1}},
                new int[][]{
                        {1,1,1,0,0},{0,0,1,1,1},{0,1,0,0,0},{1,0,1,1,0},{0,1,0,1,0}}));
    }
}
