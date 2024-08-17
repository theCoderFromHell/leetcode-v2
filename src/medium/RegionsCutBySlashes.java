package medium;

public class RegionsCutBySlashes {
    int[] dx = {-1,1,0,0};
    int[] dy = {0,0,-1,1};
    public int regionsBySlashes(String[] grid) {
        int N = grid.length;
        int[][] biggerGrid = new int[3*N][3*N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int row = i * 3;
                int column = j * 3;
                if (grid[i].charAt(j) == '/') {
                    biggerGrid[row][column+2] = 1;
                    biggerGrid[row+1][column+1] = 1;
                    biggerGrid[row+2][column] = 1;
                } else if (grid[i].charAt(j) == '\\') {
                    biggerGrid[row][column] = 1;
                    biggerGrid[row+1][column+1] = 1;
                    biggerGrid[row+2][column+2] = 1;
                }
            }
        }
        int count = 0;
        boolean[][] visited = new boolean[3*N][3*N];
        for (int i = 0; i < 3 * N; i++) {
            for (int j = 0; j < 3 * N; j++) {
                if (biggerGrid[i][j] == 0 && !visited[i][j]) {
                    count++;
                    dfs(biggerGrid, i, j, 3*N, visited);
                }
            }
        }
        return count;
    }

    private void dfs(int[][] biggerGrid, int i, int j, int N, boolean[][] visited) {
        visited[i][j] = true;
        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x >= 0 && x < N && y >= 0 && y < N && biggerGrid[x][y] == 0 && !visited[x][y])
                dfs(biggerGrid, x, y, N, visited);
        }
    }

    public static void main(String[] args) {
        RegionsCutBySlashes R = new RegionsCutBySlashes();
        System.out.println(R.regionsBySlashes(new String[]{"/\\","\\/"}));
    }
}
