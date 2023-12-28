package medium;

public class ZigzagConversion {
    public static String convert(String s, int numRows) {
        if (numRows == 1)
            return s;
        int N = s.length();
        int x = 2 * numRows - 2;
        int y = numRows - 1;
        int numColumns = (N / x) * y;
        if (N % x != 0)
            numColumns += (N % x <= numRows) ? 1 : (1 + ((N % x) % numRows));
        char[][] grid = new char[numRows][numColumns];
        int idx = 0;
        for (int i = 0; i < numRows; i++)
            for (int j = 0; j < numColumns; j++)
                grid[i][j] = '*';
        int i = 0, j = 0;
        while (idx < N) {
            while (idx < N && i < numRows) {
                grid[i][j] = s.charAt(idx++);
                i++;
            }
            i -= 2;
            j++;
            while (idx < N && i >= 1 && j < numColumns) {
                grid[i][j] = s.charAt(idx++);
                i--;
                j++;
            }
        }
        StringBuilder result = new StringBuilder();
        for (i = 0; i < numRows; i++)
            for (j = 0; j < numColumns; j++)
                if (grid[i][j] != '*')
                    result.append(grid[i][j]);
        return result.toString();
    }
    public static void main(String[] args) {
        System.out.println(convert("PAYPALISHIRING", 1));
        System.out.println(convert("PAYPALISHIRING", 2));
        System.out.println(convert("PAYPALISHIRING", 3));
        System.out.println(convert("PAYPALISHIRING", 4));
    }
}

//        if (numRows == 2) {
//            StringBuilder result1 = new StringBuilder();
//            StringBuilder result2 = new StringBuilder();
//            for (int i = 0; i < N; i++) {
//                if (i % 2 == 0)
//                    result1.append(s.charAt(i));
//                else
//                    result2.append(s.charAt(i));
//            }
//            return result1.append(result2).toString();
//        }