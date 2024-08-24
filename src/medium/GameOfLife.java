package medium;

public class GameOfLife {
    int[] dx = {0,0,-1,-1,-1,1,1,1};
    int[] dy = {-1,1,-1,0,1,-1,0,1};
    public void gameOfLife(int[][] board) {
        int rows = board.length;
        int columns = board[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int liveCells = getLive(board, i, j, rows, columns);
                if (board[i][j] == 1) {
                    if (liveCells < 2 || liveCells > 3)
                        board[i][j] = -1;
                } else if (board[i][j] == 0) {
                    if (liveCells == 3)
                        board[i][j] = 2;
                }
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == -1)
                    board[i][j] = 0;
                if (board[i][j] == 2)
                    board[i][j] = 1;
            }
        }
    }

    private int getLive(int[][] board, int i, int j, int rows, int columns) {
        int count = 0;
        for (int k = 0; k < 8; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x >= 0 && x < rows && y >= 0 && y < columns && (board[x][y] == 1 || board[x][y] == -1))
                count++;
        }
        return count;
    }

    public static void main(String[] args) {
        GameOfLife G = new GameOfLife();
        G.gameOfLife(new int[][]{{0,1,0},{0,0,1},{1,1,1},{0,0,0}});

    }
}
