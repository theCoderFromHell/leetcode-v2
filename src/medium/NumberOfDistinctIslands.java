package medium;

import java.util.ArrayList;
import java.util.List;

public class NumberOfDistinctIslands {
    int[] dx = {0,0,-1,1};
    int[] dy = {-1,1,0,0};
    public int numDistinctIslands(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        boolean[][] visited = new boolean[rows][columns];
        List<List<int[]>> islands = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    List<int[]> island = new ArrayList<>();
                    dfs(grid, rows, columns, visited, island, i, j);
                    beautify(island);
                    if (!alreadyExists(islands, island)) {
                        islands.add(island);
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void beautify(List<int[]> island) {
        int minX = 100, minY = 100;
        int size = island.size();
        for (int i = 0; i < size; i++) {
            minX = Math.min(minX, island.get(i)[0]);
            minY = Math.min(minY, island.get(i)[1]);
        }
        for (int i = 0; i < size; i++) {
            island.get(i)[0] -= minX;
            island.get(i)[1] -= minY;
        }
        island.sort((o1, o2) -> {
            if (o1[0] == o2[0])
                return o1[1] - o2[1];
            return o1[0] - o2[0];
        });
    }

    private boolean alreadyExists(List<List<int[]>> islands, List<int[]> island) {
        int size = islands.size();
        for (int i = 0; i < size; i++) {
            int islandSize = islands.get(i).size();
            if (island.size() != islandSize)
                continue;
            List<int[]> existingIsland = islands.get(i);
            int j = 0;
            while (j < islandSize) {
                if (island.get(j)[0] != existingIsland.get(j)[0] || island.get(j)[1] != existingIsland.get(j)[1])
                    break;
                j++;
            }
            if (j == islandSize)
                return true;
        }
        return false;
    }

    private void dfs(int[][] grid, int rows, int columns, boolean[][] visited, List<int[]> island, int i, int j) {
        visited[i][j] = true;
        island.add(new int[]{i, j});
        for (int k = 0; k < 4; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x >= 0 && x < rows && y >= 0 && y < columns && grid[x][y] == 1 && !visited[x][y])
                dfs(grid, rows, columns, visited, island, x, y);
        }
    }

    public static void main(String[] args) {
        NumberOfDistinctIslands N = new NumberOfDistinctIslands();
        System.out.println(N.numDistinctIslands(new int[][]{{1,1,0,0,0},{1,1,0,0,0},{0,0,0,1,1},{0,0,0,1,1}}));
        System.out.println(N.numDistinctIslands(new int[][]{{1,1,0,1,1},{1,0,0,0,0},{0,0,0,0,1},{1,1,0,1,1}}));
    }
}
