package medium;

import java.util.HashMap;

public class EqualRowAndColumnPairs {
    public int equalPairs(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        HashMap<String,Integer> rowStrings = new HashMap<>();
        for (int[] row : grid) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < columns-1; i++)
                sb.append(row[i]).append("+");
            sb.append(row[columns-1]);
            String currRow = sb.toString();
            rowStrings.put(currRow, rowStrings.getOrDefault(currRow, 0) + 1);
        }
        int count = 0;
        for (int i = 0; i < columns; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < rows-1; j++)
                sb.append(grid[j][i]).append("+");
            sb.append(grid[rows-1][i]);
            String currRow = sb.toString();
            if (rowStrings.containsKey(currRow))
                count += rowStrings.get(currRow);
        }
        return count;
    }

    public static void main(String[] args) {
        EqualRowAndColumnPairs E = new EqualRowAndColumnPairs();
        System.out.println(E.equalPairs(new int[][]{{3,2,1},{1,7,6},{2,7,7}}));
        System.out.println(E.equalPairs(new int[][]{{3,1,2,2},{1,4,4,5},{2,4,2,2},{2,4,2,2}}));
    }
}
