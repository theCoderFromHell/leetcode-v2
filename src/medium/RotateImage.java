package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://leetcode.com/problems/rotate-image/
public class RotateImage {
    public List<Integer> rotate(int[][] matrix) {
        int N = matrix.length;
        List<Integer> result = new ArrayList<>();
        rotate(matrix, 0, 0, N-1, N-1, result);
        return result;
    }

    private void rotate(int[][] matrix, int startX, int startY, int endX, int endY, List<Integer> result) {
        if(startX > endX || startY > endY)
            return;
        int N = endX - startX + 1;
        if(N == 1)
            return;
        int[] storage = new int[N];
        int k = 0;
        for (int i = endX; i >= startX; i--)
            storage[k++] = matrix[i][startY];
        k = 0;
        for (int j = startY; j < endY; j++) {
            int temp = storage[k];
            storage[k] = matrix[startX][j];
            matrix[startX][j] = temp;
            k++;
        }
        k = 0;
        for (int i = startX; i < endX; i++) {
            int temp = storage[k];
            storage[k] = matrix[i][endY];
            matrix[i][endY] = temp;
            k++;
        }
        k = 0;
        for (int j = endY; j > startY; j--) {
            int temp = storage[k];
            storage[k] = matrix[endX][j];
            matrix[endX][j] = temp;
            k++;
        }
        k = 0;
        for (int i = endX; i > startX; i--) {
            int temp = storage[k];
            storage[k] = matrix[i][startY];
            matrix[i][startY] = temp;
            k++;
        }
        rotate(matrix, startX + 1, startY + 1, endX - 1, endY - 1, result);
    }

    /*
     * Revision Note - Rotate Image (Medium)
     *
     * Pattern: Rotate RING BY RING, carrying one edge in a buffer and swapping it around the ring
     *
     * Key Insight: A 90-degree clockwise rotation maps each concentric ring onto itself, so the
     * matrix decomposes into floor(n/2) independent rings and the centre cell of an odd n never
     * moves. Within one ring, the left column (read BOTTOM-TO-TOP) becomes the top row
     * (left-to-right), the top row becomes the right column, and so on - a 4-cycle of edges.
     *
     * This implementation realises that cycle by loading the left column into `storage`
     * bottom-to-top, then walking the four edges in rotation order and SWAPPING each cell with
     * the buffer. After each edge the buffer holds exactly the values that edge displaced, ready
     * for the next edge - so one buffer carries the whole 4-cycle with no second matrix.
     *
     * Gotchas:
     * - Reading the left column BOTTOM-TO-TOP (i from endX down to startX) is what performs the
     *   rotation. Reading it top-to-bottom would transpose the ring instead of rotating it
     * - Each edge loop stops ONE SHORT of its far corner (`j < endY`, `i < endX`, `j > startY`,
     *   `i > startX`). Corners belong to two edges, so including both ends would write a corner
     *   twice and corrupt the cycle. The four half-open ranges tile the ring exactly once
     * - N == 1 must return, not recurse further. An odd n leaves a single centre cell, and
     *   without the guard the edge loops would run zero times but the recursion would continue
     *   past the middle. The startX > endX guard covers even n
     * - The buffer is sized N (the ring's side) though each edge only touches N-1 cells; the
     *   extra slot is harmless, holding a stale corner value that never gets read back
     * - Values can be negative (-1000..1000), but nothing here does arithmetic on them, so there
     *   is no overflow or sentinel concern
     *
     * Complexity: O(n^2) time - every cell is touched exactly once across all rings. O(n) extra
     * space for the buffer, which is the one thing to know about this approach: the canonical
     * answer is O(1).
     *
     * SIGNATURE NOTE: LeetCode declares `public void rotate(int[][] matrix)`. This version
     * returns a List<Integer> that is created, threaded through the recursion, and never written
     * to - vestigial, presumably carried over from another problem. The rotation itself is
     * in-place and correct; the return value is simply always empty.
     *
     * Template (the canonical O(1)-space alternative, worth knowing alongside this):
     *   transpose:        for i in 0..n-1, for j in i+1..n-1: swap(m[i][j], m[j][i])
     *   reverse each row: for each row: reverse it
     *   -> 90 degrees clockwise, no buffer at all
     * This is the 2D echo of 189's three-reversal trick: both get an in-place rotation by
     * COMPOSING two cheap self-inverse operations rather than moving elements to their final
     * positions directly.
     */
    private static int[][] copy(int[][] m) {
        int[][] c = new int[m.length][];
        for (int i = 0; i < m.length; i++) c[i] = m[i].clone();
        return c;
    }

    private static String show(int[][] m) {
        return Arrays.deepToString(m);
    }

    public static void main(String[] args) {
        RotateImage R = new RotateImage();

        int[][] t1 = {{1,2,3},{4,5,6},{7,8,9}};
        R.rotate(t1);
        System.out.println("Test 1: " + show(t1) + " (Expected: [[7, 4, 1], [8, 5, 2], [9, 6, 3]])");

        int[][] t2 = {{5,1,9,11},{2,4,8,10},{13,3,6,7},{15,14,12,16}};
        R.rotate(t2);
        System.out.println("Test 2: " + show(t2) + " (Expected: [[15, 13, 2, 5], [14, 3, 4, 1], [12, 6, 8, 9], [16, 7, 10, 11]])");

        int[][] t3 = {{1}};
        R.rotate(t3);
        System.out.println("Test 3: " + show(t3) + " (Expected: [[1]])");                       // n=1, nothing moves

        int[][] t4 = {{1,2},{3,4}};
        R.rotate(t4);
        System.out.println("Test 4: " + show(t4) + " (Expected: [[3, 1], [4, 2]])");            // n=2, a single ring, no centre

        int[][] t5 = {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},{16,17,18,19,20},{21,22,23,24,25}};
        R.rotate(t5);
        System.out.println("Test 5: " + show(t5) + " (Expected: [[21, 16, 11, 6, 1], [22, 17, 12, 7, 2], [23, 18, 13, 8, 3], [24, 19, 14, 9, 4], [25, 20, 15, 10, 5]])"); // n=5, two rings + fixed centre

        int[][] t6 = {{-1,-2},{-3,1000}};
        R.rotate(t6);
        System.out.println("Test 6: " + show(t6) + " (Expected: [[-3, -1], [1000, -2]])");      // negatives and the value ceiling

        int[][] t7 = {{7,7,7},{7,7,7},{7,7,7}};
        R.rotate(t7);
        System.out.println("Test 7: " + show(t7) + " (Expected: [[7, 7, 7], [7, 7, 7], [7, 7, 7]])"); // all equal - a wrong cycle would still look right, so pair with Test 5

        // four rotations must return to the original
        int[][] t8 = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        int[][] original = copy(t8);
        for (int r = 0; r < 4; r++) R.rotate(t8);
        System.out.println("Test 8: " + Arrays.deepEquals(t8, original) + " (Expected: true)  - four rotations are the identity");

        // the return value is always empty, which the signature note explains
        System.out.println("Test 9: " + R.rotate(new int[][]{{1,2},{3,4}}).isEmpty() + " (Expected: true)  - returned list is vestigial");
    }
}
