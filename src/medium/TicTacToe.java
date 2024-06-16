package medium;

public class TicTacToe {
    private int[] rows;
    private int[] columns;
    private int diagonal;
    private int antiDiagonal;
    private int size;

    public TicTacToe(int n) {
        this.rows = new int[n];
        this.columns = new int[n];
        this.diagonal = 0;
        this.antiDiagonal = 0;
        this.size = n;
    }

    public int move(int row, int col, int player) {
        int currPlayer = (player == 1) ? 1 : -1;
        rows[row] += currPlayer;
        columns[col] += currPlayer;
        if (row == col)
            diagonal += currPlayer;
        if (col == size - row - 1)
            antiDiagonal += currPlayer;
        if (Math.abs(rows[row]) == size ||
                Math.abs(columns[col]) == size ||
                Math.abs(diagonal) == size ||
                Math.abs(antiDiagonal) == size) {
            return player;
        }
        return 0;
    }

    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe(3);

    }
}
