package medium;

public class GridGame {
    public static long gridGame(int[][] grid) {
        int N = grid[0].length;
        long[] lowerPrefix = new long[N+2];
        long[] upperPostfix = new long[N+2];
        for (int i = 1; i <= N; i++)
            lowerPrefix[i] = lowerPrefix[i-1] + grid[1][i-1];
        for (int i = N; i >= 1; i--)
            upperPostfix[i] = upperPostfix[i + 1] + grid[0][i - 1];

        long result = Long.MAX_VALUE;
        for (int i = 1; i <= N; i++)
            result = Math.min(result, Math.max(lowerPrefix[i-1], upperPostfix[i+1]));
        return result;
    }

    public static void main(String[] args) {
        System.out.println(gridGame(new int[][]{
                {2,5,4}, {1,5,1}
        }));
        System.out.println(gridGame(new int[][]{
                {3,3,1},{8,5,2}
        }));
        System.out.println(gridGame(new int[][]{
                {1,3,1,15},{1,3,3,1}
        }));
    }
}
