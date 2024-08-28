package medium;

public class SearchA2DMatrixII {
    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int i = 0, j = columns-1;
        while (i < rows && j >= 0) {
            if (matrix[i][j] == target)
                return true;
            else if (matrix[i][j] > target)
                j--;
            else if (matrix[i][j] < target)
                i++;
        }
        return false;
    }

    public boolean searchMatrixV2(int[][] matrix, int target) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        boolean found = false;
        int m = 0, n = columns-1;
        while (m < rows && n >= 0) {
            found = search(matrix, m, n, rows, target);
            if (found)
                return true;
            m++;
            n--;
        }
        return found;
    }

    private boolean search(int[][] matrix, int m, int n, int rows, int target) {
        int size = rows - m + n;
        int[] flattened = new int[size];
        int index = 0;
        for (int k = 0; k <= n; k++)
            flattened[index++] = matrix[m][k];
        for (int k = m+1; k < rows; k++)
            flattened[index++] = matrix[k][n];

        int low = 0, high = size-1, mid;
        while (low <= high) {
            mid = (high - low)/2 + low;
            if (flattened[mid] == target)
                return true;
            if (flattened[mid] < target)
                low = mid + 1;
            else if (flattened[mid] > target)
                high = mid - 1;
        }
        return false;
    }

    public static void main(String[] args) {
        SearchA2DMatrixII S = new SearchA2DMatrixII();
        System.out.println(S.searchMatrix(new int[][]{{-5}}, -5));
        System.out.println(S.searchMatrix(new int[][]{
                {1,4,7,11,15},
                {2,5,8,12,19},
                {3,6,9,16,22},
                {10,13,14,17,24},
                {18,21,23,26,30}
        }, 5));
    }
}
