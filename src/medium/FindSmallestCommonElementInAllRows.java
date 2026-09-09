package medium;

// https://leetcode.com/problems/find-smallest-common-element-in-all-rows/
public class FindSmallestCommonElementInAllRows {
    public int smallestCommonElement(int[][] mat) {
        int rows = mat.length;
        int columns = mat[0].length;
        for (int j = 0; j < columns; j++) {
            int value = mat[0][j];
            if (inAllRows(value, mat, rows, columns))
                return value;
        }
        return -1;
    }

    private boolean inAllRows(int value, int[][] mat, int rows, int columns) {
        for (int i = 1; i < rows; i++) {
            if (!inRow(value, mat, i, columns))
                return false;
        }
        return true;
    }

    private boolean inRow(int value, int[][] mat, int i, int columns) {
        int[] row = mat[i];
        int low = 0, high = columns-1, mid;
        while (low <= high) {
            mid = low + (high - low)/2;
            if (row[mid] == value)
                return true;
            if (value < row[mid])
                high = mid - 1;
            else
                low = mid + 1;
        }
        return false;
    }

    /*
     * Revision Note — Find Smallest Common Element in All Rows (Medium)
     * Pattern: Scan row 0 in sorted order + binary search each candidate in every other row
     * Key Insight: Row 0 is sorted ascending, so walking it left to right and returning the FIRST
     *              value found in all rows automatically yields the smallest — the sort order
     *              replaces an explicit min-tracking pass. Any common element must appear in row 0.
     * Gotchas:
     *   - Iterate columns of row 0, not rows. Scanning rows instead would force collecting every
     *     common value and taking the min afterwards.
     *   - Binary search midpoint must be low + (high - low)/2, not (low + high)/2, to stay
     *     overflow-safe (habit matters even though values here cap at 10^4).
     *   - Rows are STRICTLY increasing, so no duplicates within a row — binary search needs no
     *     leftmost-occurrence handling.
     *   - mat[0].length assumes at least one row; safe since 1 <= m per constraints.
     * Alternative: counting array over the bounded value range (1 <= mat[i][j] <= 10^4) — a value
     *              seen mat.length times must appear once per row. O(m*n) time, O(10^4) space,
     *              drops the log factor but only works because the range is bounded.
     */
    public static void main(String[] args) {
        FindSmallestCommonElementInAllRows F = new FindSmallestCommonElementInAllRows();

        // Test 1: LeetCode example 1 — 5 is the only value in all four rows
        int[][] t1 = {{1,2,3,4,5}, {2,4,5,8,10}, {3,5,7,9,11}, {1,3,5,7,9}};
        System.out.println("Test 1: " + F.smallestCommonElement(t1) + " (Expected: 5)");

        // Test 2: LeetCode example 2 — both 2 and 3 are common, 2 is smaller
        int[][] t2 = {{1,2,3}, {2,3,4}, {2,3,5}};
        System.out.println("Test 2: " + F.smallestCommonElement(t2) + " (Expected: 2)");

        // Test 3: no common element at all
        int[][] t3 = {{1,2,3}, {4,5,6}};
        System.out.println("Test 3: " + F.smallestCommonElement(t3) + " (Expected: -1)");

        // Test 4: single cell
        int[][] t4 = {{7}};
        System.out.println("Test 4: " + F.smallestCommonElement(t4) + " (Expected: 7)");

        // Test 5: single row — every value qualifies, smallest is the first
        int[][] t5 = {{1,2,3,4,5}};
        System.out.println("Test 5: " + F.smallestCommonElement(t5) + " (Expected: 1)");

        // Test 6: single column, all rows agree
        int[][] t6 = {{5}, {5}, {5}};
        System.out.println("Test 6: " + F.smallestCommonElement(t6) + " (Expected: 5)");

        // Test 7: single column, rows disagree
        int[][] t7 = {{1}, {2}};
        System.out.println("Test 7: " + F.smallestCommonElement(t7) + " (Expected: -1)");

        // Test 8: identical rows — first value wins
        int[][] t8 = {{1,2,3}, {1,2,3}, {1,2,3}};
        System.out.println("Test 8: " + F.smallestCommonElement(t8) + " (Expected: 1)");

        // Test 9: common element is not the first column value of row 0
        int[][] t9 = {{1,5,10}, {5,10,15}, {5,20,30}};
        System.out.println("Test 9: " + F.smallestCommonElement(t9) + " (Expected: 5)");

        // Test 10: pairwise overlaps exist but nothing spans all three rows
        int[][] t10 = {{1,2}, {2,3}, {3,4}};
        System.out.println("Test 10: " + F.smallestCommonElement(t10) + " (Expected: -1)");

        // Test 11: value at the upper constraint bound
        int[][] t11 = {{1,10000}, {9999,10000}};
        System.out.println("Test 11: " + F.smallestCommonElement(t11) + " (Expected: 10000)");
    }
}
