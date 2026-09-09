package medium;

// https://leetcode.com/problems/lonely-pixel-i/
public class LonelyPixelI {
    public int findLonelyPixel(char[][] picture) {
        int rows = picture.length;
        int columns = picture[0].length;
        int[] rowCount = new int[rows];
        int[] columnCount = new int[columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (picture[i][j] == 'B') {
                    rowCount[i]++;
                    columnCount[j]++;
                }
            }
        }
        int result = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (picture[i][j] == 'B') {
                    if (rowCount[i] == 1 && columnCount[j] == 1)
                        result++;
                }
            }
        }
        return result;
    }

    /*
     * Revision Note — Lonely Pixel I (Medium)
     * Pattern: Two-pass counting with row and column frequency arrays
     * Key Insight: A pixel is lonely iff its row has exactly one B and its column has exactly
     *              one B. Count both in a single pass, then a second pass tests the pair of
     *              counts — no need to compare pixels against each other.
     * Gotchas:
     *   - Size the arrays from the input (new int[rows] / new int[columns]), never from the
     *     stated constraint. Hardcoding new int[500] works only until the grid is bigger and
     *     silently couples the code to a bound it cannot check.
     *   - rowCount and columnCount must be sized independently — a non-square grid breaks any
     *     version that reuses one length for both.
     *   - picture[0].length assumes at least one row; safe here since 1 <= m per constraints.
     *   - Fixed-size arrays are fine only for intrinsic domains (new int[26] for letters,
     *     new int[10] for digits), not for problem constraints that could change.
     */
    public static void main(String[] args) {
        LonelyPixelI L = new LonelyPixelI();

        // Test 1: LeetCode example 1 — three Bs on a diagonal, each alone in row and column
        char[][] t1 = {{'W','W','B'}, {'W','B','W'}, {'B','W','W'}};
        System.out.println("Test 1: " + L.findLonelyPixel(t1) + " (Expected: 3)");

        // Test 2: LeetCode example 2 — dense grid, no pixel is alone
        char[][] t2 = {{'B','B','B'}, {'B','B','W'}, {'B','B','B'}};
        System.out.println("Test 2: " + L.findLonelyPixel(t2) + " (Expected: 0)");

        // Test 3: single cell containing B
        char[][] t3 = {{'B'}};
        System.out.println("Test 3: " + L.findLonelyPixel(t3) + " (Expected: 1)");

        // Test 4: single cell containing W
        char[][] t4 = {{'W'}};
        System.out.println("Test 4: " + L.findLonelyPixel(t4) + " (Expected: 0)");

        // Test 5: alone in row but sharing a column — not lonely
        char[][] t5 = {{'B','W'}, {'B','W'}};
        System.out.println("Test 5: " + L.findLonelyPixel(t5) + " (Expected: 0)");

        // Test 6: alone in column but sharing a row — not lonely
        char[][] t6 = {{'B','B'}, {'W','W'}};
        System.out.println("Test 6: " + L.findLonelyPixel(t6) + " (Expected: 0)");

        // Test 7: no B at all
        char[][] t7 = {{'W','W','W'}, {'W','W','W'}};
        System.out.println("Test 7: " + L.findLonelyPixel(t7) + " (Expected: 0)");

        // Test 8: one lonely pixel alongside a dense cluster
        char[][] t8 = {{'B','W','W'}, {'W','B','B'}, {'W','B','B'}};
        System.out.println("Test 8: " + L.findLonelyPixel(t8) + " (Expected: 1)");

        // Test 9: non-square wide grid — columns array must be sized separately
        char[][] t9 = {{'W','B','W','W','W'}, {'W','W','W','W','B'}};
        System.out.println("Test 9: " + L.findLonelyPixel(t9) + " (Expected: 2)");

        // Test 10: non-square tall grid — rows array must be sized separately
        char[][] t10 = {{'W','W'}, {'B','W'}, {'W','W'}, {'W','B'}};
        System.out.println("Test 10: " + L.findLonelyPixel(t10) + " (Expected: 2)");
    }
}
