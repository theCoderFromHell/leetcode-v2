package medium;

import java.util.HashMap;

public class FlipColumnsForMaximumNumberOfEqualRows {
    public int maxEqualRowsAfterFlips(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        HashMap<String,Integer> countPatterns = new HashMap<>();
        for (int i = 0; i < rows; i++) {
            StringBuilder p = new StringBuilder();
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == matrix[i][0])
                    p.append("*");
                else
                    p.append("#");
            }
            String pattern = p.toString();
            countPatterns.put(pattern, countPatterns.getOrDefault(pattern, 0) + 1);
        }
        int maxValue = 0;
        for (String pattern : countPatterns.keySet())
            maxValue = Math.max(maxValue, countPatterns.get(pattern));

        return maxValue;
    }

    public static void main(String[] args) {
        FlipColumnsForMaximumNumberOfEqualRows F = new FlipColumnsForMaximumNumberOfEqualRows();
        System.out.println(F.maxEqualRowsAfterFlips(new int[][]{{0,1},{1,1}}));
        System.out.println(F.maxEqualRowsAfterFlips(new int[][]{{0,1},{1,0}}));
        System.out.println(F.maxEqualRowsAfterFlips(new int[][]{{0,0,0},{0,0,1},{1,1,0}}));
    }
}
