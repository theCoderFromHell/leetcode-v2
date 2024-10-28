package medium;

public class CountSquareSubmatricesWithAllOnes {
    public int countSquares(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int[][] dp = new int[rows][columns];
        int[] squaresCount = new int[301];
        for (int i = 0; i < rows; i++) {
            dp[i][0] = matrix[i][0];
            if (matrix[i][0] == 1)
                squaresCount[dp[i][0]]++;
        }
        for (int j = 1; j < columns; j++) {
            dp[0][j] = matrix[0][j];
            if (matrix[0][j] == 1)
                squaresCount[dp[0][j]]++;
        }

        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                if (matrix[i][j] == 1) {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                    squaresCount[dp[i][j]]++;
                }
            }
        }
        int result = 0;
        int current = 0;
        for (int size = 300; size >= 1; size--) {
            if (squaresCount[size] != 0) {
                int count = squaresCount[size];
                result += (count + current);
                current += count;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        CountSquareSubmatricesWithAllOnes C = new CountSquareSubmatricesWithAllOnes();
        System.out.println(C.countSquares(new int[][]{
                {0,1,1,1},
                {1,1,1,1},
                {0,1,1,1}
        }));
        System.out.println(C.countSquares(new int[][]
                {{1,0,1},{1,1,0},{1,1,0}}
        ));
    }
}
