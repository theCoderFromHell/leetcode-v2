package medium;

public class SearchA2DMatrix {

    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        int[] flattened = new int[rows*columns];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                flattened[index++] = matrix[i][j];
            }
        }
        return searchColumn(flattened, flattened.length, target) != -1;
    }


    public boolean searchMatrixV2(int[][] matrix, int target) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int targetRow = searchRow(matrix, rows, columns, target);
        if(targetRow == -1) return false;
        int targetColumn = searchColumn(matrix[targetRow], columns, target);
        return targetColumn != -1;
    }

    private int searchColumn(int[] matrix, int columns, int target) {
        int start = 0, end = columns-1;
        while (start <= end) {
            int mid = (end-start)/2 + start;
            if(matrix[mid] == target)
                return mid;
            if(matrix[mid] < target)
                start++;
            if(matrix[mid] > target)
                end--;
        }
        return -1;
    }

    private int searchRow(int[][] matrix, int rows, int columns, int target) {
        int col = columns-1;
        int start = 0, end = rows-1;
        if(target <= matrix[0][col])
            return 0;
        if(target > matrix[rows-1][col])
            return -1;
        while (start <= end) {
            int mid = (end-start)/2 + start;
            if(matrix[mid][col] == target)
                return mid;
            if(mid > start && matrix[mid-1][col] < target && target < matrix[mid][col])
                return mid;
            if(mid < end && matrix[mid][col] < target && target < matrix[mid+1][col])
                return mid+1;
            if(matrix[mid][col] < target)
                start++;
            if(matrix[mid][col] > target)
                end--;
        }
        return -1;
    }
}
