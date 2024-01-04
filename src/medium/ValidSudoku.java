package medium;

import java.util.HashMap;
import java.util.HashSet;

public class ValidSudoku {
    public boolean isValidSudoku(char[][] board) {
        HashSet<String> hash = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char value = board[i][j];
                if (value != '.') {
                    if (!hash.add(value + "in row " + i))
                        return false;
                    if (!hash.add(value + "in columns " + j))
                        return false;
                    if (!hash.add(value + "in box " + (i/3) + "-" + (j/3)))
                        return false;
                }
            }
        }
        return true;
    }
    public static void main(String[] args) {

    }
}
