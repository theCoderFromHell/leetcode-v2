package easy;

public class CheckIfEveryRowAndColumnContainsAllNumbers {
    public boolean checkValid(int[][] matrix) {
        int N = matrix.length;
        int[][] hash = new int[2*N][N+1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                hash[i][matrix[i][j]] = 1;
                hash[j+N][matrix[i][j]] = 1;
            }
        }
        for (int i = 0; i < 2 * N; i++) {
            for (int j = 1; j <= N; j++) {
                if(hash[i][j] != 1)
                    return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {

    }
}
