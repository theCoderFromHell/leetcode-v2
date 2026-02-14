package medium;

public class FindTheMinimumAreaToCoverAllOnesI {
    public int minimumArea(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
        boolean hasOne = false;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] == 1) {
                    hasOne = true;
                    minX = Math.min(minX, i);
                    maxX = Math.max(maxX, i);
                    minY = Math.min(minY, j);
                    maxY = Math.max(maxY, j);
                }
            }
        }
        if (!hasOne)
            return 0;
        return (maxX - minX + 1) * (maxY - minY + 1);
    }

    public static void main(String[] args) {
        FindTheMinimumAreaToCoverAllOnesI F = new FindTheMinimumAreaToCoverAllOnesI();
        System.out.println(F.minimumArea(new int[][]{{0,1,0},{1,0,1}}));
        System.out.println(F.minimumArea(new int[][]{{1,0},{0,0}}));
        System.out.println(F.minimumArea(new int[][]{{0,0},{0,0}}));
        System.out.println(F.minimumArea(new int[][]{{1,1,1},{1,1,1},{1,1,1}}));
        System.out.println(F.minimumArea(new int[][]{{0,0,0},{0,1,0},{0,0,0}}));
        System.out.println(F.minimumArea(new int[][]{{1,0,0,0},{0,0,0,0},{0,0,0,1}}));
        System.out.println(F.minimumArea(new int[][]{{0,1},{1,0}}));
    }
}
