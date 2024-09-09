package medium;

public class SpiralMatrixIII {
    int[] dx = {0,1,0,-1};
    int[] dy = {1,0,-1,0};
    public int[][] spiralMatrixIII(int rows, int cols, int rStart, int cStart) {
        int[][] result = new int[rows * cols][2];
        int index = 0;
        int direction = 0;
        int step = 1;
        while (index < rows * cols) {
            for (int d = 0; d < 2; d++) {
                for (int i = 0; i < step; i++) {
                    if (rStart >= 0 && rStart < rows && cStart >= 0 && cStart < cols) {
                        result[index++] = new int[]{rStart, cStart};
                    }
                    rStart = rStart + dx[direction];
                    cStart = cStart + dy[direction];
                }
                direction = (direction + 1) % 4;
            }
            step++;
        }
        return result;
    }

    public static void main(String[] args) {
        SpiralMatrixIII S = new SpiralMatrixIII();
        System.out.println(S.spiralMatrixIII(1,4,0,0));
        System.out.println(S.spiralMatrixIII(5,6,1,4));
    }
}
