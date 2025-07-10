package medium;

public class CountServersThatCommunicate {
    public int countServers(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] == 1) {
                    boolean canCommunicate = false;
                    for (int k = 0; k < columns; k++) {
                        if (k != j && grid[i][k] == 1) {
                            canCommunicate = true;
                            break;
                        }
                    }
                    if (!canCommunicate) {
                        for (int k = 0; k < rows; k++) {
                            if (k != i && grid[k][j] == 1) {
                                canCommunicate = true;
                                break;
                            }
                        }
                    }
                    if (canCommunicate)
                        count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        CountServersThatCommunicate C = new CountServersThatCommunicate();
        System.out.println(C.countServers(new int[][]{{1,0},{0,1}}));
        System.out.println(C.countServers(new int[][]{{1,0},{1,1}}));
        System.out.println(C.countServers(new int[][]{{1,1,0,0},{0,0,1,0},{0,0,1,0},{0,0,0,1}}));
    }
}
