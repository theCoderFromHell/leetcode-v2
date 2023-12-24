package medium;

public class WordSearch {

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};
    public static boolean exist(char[][] board, String word) {
        if (word == null || word.isEmpty() || word.isBlank())
            return true;
        if (board == null || board.length == 0 || board[0].length == 0)
            return false;
        int M = board.length;
        int N = board[0].length;
        boolean[][] visited = new boolean[M][N];
        int size = word.length();
        int idx = 0;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == word.charAt(idx) && dfs(board, i, j, M, N, word, idx, size, visited))
                    return true;
            }
        }
        return false;
    }

    private static boolean dfs(char[][] board, int x, int y, int M, int N, String word, int idx, int size, boolean[][] visited) {
        visited[x][y] = true;
        if (idx == size-1)
            return true;
        idx++;
        for (int k = 0; k < 4; k++)
            if (x + dx[k] >= 0 && x + dx[k] < M && y + dy[k] >= 0 && y + dy[k] < N && board[x + dx[k]][y + dy[k]] == word.charAt(idx))
                if (!visited[x + dx[k]][y + dy[k]] && dfs(board, x + dx[k], y + dy[k], M, N, word, idx, size, visited))
                    return true;
        visited[x][y] = false;
        return false;
    }

    public static void main(String[] args) {
        System.out.println(exist(new char[][]{
                {'a', 'b'},{'c', 'd'}
        }, "abcd"));
    }
}
