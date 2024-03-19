package medium;

public class BattleshipsInABoard {
    int[] dx = {0,0,-1,1};
    int[] dy = {-1,1,0,0};
    public int countBattleships(char[][] board) {
        int M = board.length;
        int N = board[0].length;
        boolean[][] visited= new boolean[M][N];
        int count = 0;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == 'X' && !visited[i][j]) {
                    dfs(i, j, board, M, N, visited);
                    count++;
                }
            }
        }
        return count;
    }

    private void dfs(int i, int j, char[][] board, int M, int N, boolean[][] visited) {
        visited[i][j] = true;
        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x >= 0 && y >= 0 && x < M && y < N && board[x][y] == 'X' && !visited[x][y])
                dfs(x, y, board, M, N, visited);
        }
    }
}
