package medium;

import java.util.ArrayList;
import java.util.List;

public class WordSearchWithPath {
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
        List<String> resultPath = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] == word.charAt(idx) && dfs(board, i, j, M, N, word, idx, size, visited, resultPath)) {
                    resultPath.add("[" + i + "," +  j + "]");
                    resultPath = resultPath.reversed();
                    System.out.println(resultPath);
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean dfs(char[][] board, int x, int y, int M, int N, String word, int idx, int size, boolean[][] visited, List<String> resultPath) {
        visited[x][y] = true;
        if (idx == size-1)
            return true;
        idx++;
        for (int k = 0; k < 4; k++)
            if (x + dx[k] >= 0 && x + dx[k] < M && y + dy[k] >= 0 && y + dy[k] < N && board[x + dx[k]][y + dy[k]] == word.charAt(idx))
                if (!visited[x + dx[k]][y + dy[k]] && dfs(board, x + dx[k], y + dy[k], M, N, word, idx, size, visited, resultPath)) {
                    resultPath.add("[" + (x + dx[k]) + "," +  (y + dy[k]) + "]");
                    return true;
                }
        visited[x][y] = false;
        return false;
    }

    public static void main(String[] args) {
        System.out.println(exist(new char[][]{
                {'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}
        }, "ABCCED"));
    }
}
