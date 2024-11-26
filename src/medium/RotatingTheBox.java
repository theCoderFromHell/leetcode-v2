package medium;

import java.util.Arrays;

public class RotatingTheBox {
    char STONE = '#';
    char OBSTACLE = '*';
    char EMPTY = '.';
    public char[][] rotateTheBox(char[][] box) {
        int rows = box.length;
        int columns = box[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = columns-1; j >= 0; j--) {
                if (box[i][j] == STONE)
                    move(j, box[i], columns);
            }
        }
        char[][] rotated = new char[columns][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                rotated[j][rows - 1 - i] = box[i][j];
            }
        }
        return rotated;
    }

    private void move(int j, char[] box, int columnLength) {
        int originalY = j;
        while (j+1 < columnLength && box[j+1] == EMPTY)
            j++;
        box[originalY] = EMPTY;
        box[j] = STONE;
    }

    public static void main(String[] args) {
        RotatingTheBox R = new RotatingTheBox();
        System.out.println(Arrays.deepToString(R.rotateTheBox(new char[][]{
                {'#', '.', '#'}})));
        System.out.println(Arrays.deepToString(R.rotateTheBox(new char[][]{
                {'#','.','*','.'},
                {'#','#','*','.'}})));
        System.out.println(Arrays.deepToString(R.rotateTheBox(new char[][]{
                {'#','#','*','.','*','.'},
                {'#','#','#','*','.','.'},
                {'#','#','#','.','#','.'}})));
    }
}
