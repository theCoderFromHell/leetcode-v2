package medium;

import java.util.LinkedList;
import java.util.Queue;

public class WallsAndGates {
    int[] dx = {-1,1,0,0};
    int[] dy = {0,0,-1,1};
    private Integer GATE = 0;
    private Integer EMPTY_ROOM = Integer.MAX_VALUE;
    public void wallsAndGates(int[][] rooms) {
        int M = rooms.length;
        int N = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (rooms[i][j] == GATE)
                    queue.add(new int[]{i, j});
            }
        }
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            for (int k = 0; k < 4; k++) {
                int x = cell[0] + dx[k];
                int y = cell[1] + dy[k];
                if (x >= 0 && x < M && y >= 0 && y < N && rooms[x][y] == EMPTY_ROOM) {
                    rooms[x][y] = rooms[cell[0]][cell[1]] + 1;
                    queue.add(new int[]{x, y});
                }
            }
        }
    }
}
