package medium;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ZeroOneMatrix {

    static int[] dx = {0,0,-1,1};
    static int[] dy = {-1,1,0,0};

    public static int[][] updateMatrix(int[][] mat) {
        int M = mat.length;
        int N = mat[0].length;
        int[][] result = new int[M][N];
        int[][] visited = new int[M][N];
        Queue<ZeroOneMatrixCell> queue = new LinkedList<>();

        for (int i = 0; i < M; i++)
            Arrays.fill(result[i], Integer.MAX_VALUE);

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (mat[i][j] == 0) {
                    result[i][j] = 0;
                    ZeroOneMatrixCell cell = new ZeroOneMatrixCell(i, j, 0);
                    queue.add(cell);
                    visited[i][j] = 1;
                }
            }
        }
        while (!queue.isEmpty()) {
            ZeroOneMatrixCell cell = queue.poll();
            int i = cell.x;
            int j = cell.y;
            for (int k = 0; k < 4; k++) {
                int newX = i + dx[k];
                int newY = j + dy[k];
                if (newX >= 0 && newY >= 0 && newX < M && newY < N && mat[newX][newY] == 1 && visited[newX][newY] == 0) {
                    int distance = Math.min(result[newX][newY], result[i][j] + 1);
                    queue.add(new ZeroOneMatrixCell(newX, newY, distance));
                    result[newX][newY] = distance;
                    visited[newX][newY] = 1;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(updateMatrix(new int[][]{{0,0,0},{0,1,0},{1,1,1}}));
    }
}
class ZeroOneMatrixCell {
    int x;
    int y;
    int distance;

    public ZeroOneMatrixCell(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "ZeroOneMatrixCell{" +
                "x=" + x +
                ", y=" + y +
                ", distance=" + distance +
                '}';
    }
}
/*
for (int k = 0; k < 4; k++) {
                        int newX = i + dx[k];
                        int newY = j + dy[k];
                        if (newX >= 0 && newY >= 0 && newX < M && newY < N && mat[newX][newY] == 1) {
                            result[newX][newY] = 1;
                        }
                    }
 */