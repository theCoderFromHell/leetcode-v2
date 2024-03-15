package medium;

public class DetonateTheMaximumBombs {
    public static int maximumDetonation(int[][] bombs) {
        int N = bombs.length;
        boolean[][] bombsGrid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                long distanceBetweenCentres = (long) (bombs[i][0] - bombs[j][0]) *(bombs[i][0] - bombs[j][0]) + (long) (bombs[i][1] - bombs[j][1]) *(bombs[i][1] - bombs[j][1]);
                bombsGrid[i][j] = (distanceBetweenCentres <= (long) bombs[i][2] *bombs[i][2]);
                bombsGrid[j][i] = (distanceBetweenCentres <= (long) bombs[j][2] *bombs[j][2]);
            }
        }

        int maxCount = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++)
            maxCount = Math.max(maxCount, dfs(bombsGrid, N, i, new boolean[N]));
        return maxCount;

    }

    private static int dfs(boolean[][] bombsGrid, int N, int node, boolean[] visited) {
        visited[node] = true;
        int nodeCount = 1;
        for (int i = 0; i < N; i++) {
            if (bombsGrid[node][i] && !visited[i])
                nodeCount += dfs(bombsGrid, N, i, visited);
        }
        return nodeCount;
    }

    public static void main(String[] args) {
        System.out.println(maximumDetonation(new int[][]{{2,1,3},{6,1,4}}));
        System.out.println(maximumDetonation(new int[][]{{1,2,3},{2,3,1},{3,4,2},{4,5,3},{5,6,4}}));
        System.out.println(maximumDetonation(new int[][]{{1,1,100000},{100000,100000,1}}));
    }
}
