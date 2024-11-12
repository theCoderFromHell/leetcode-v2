package hard;

import java.util.Stack;

public class MaximalRectangle {
    public int maximalRectangle(char[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int[] histogram = new int[columns];
        int maxRectangle = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++)
                histogram[j] = (matrix[i][j] == '1') ? (histogram[j] + 1) : 0;
            maxRectangle = Math.max(maxRectangle, maxHistogram(histogram, columns));
        }
        return maxRectangle;
    }

    private int maxHistogram(int[] histogram, int size) {
        Stack<int[]> stack = new Stack<>();
        int maxArea = 0;
        for (int i = 0; i < size; i++) {
            int prev = i;
            while (!stack.isEmpty() && histogram[i] <= stack.peek()[1]) {
                int[] top = stack.pop();
                prev = top[0];
                maxArea = Math.max(maxArea, top[1] * (i - top[0]));
            }
            stack.push(new int[]{prev, histogram[i]});
        }
        while (!stack.isEmpty()) {
            int[] top = stack.pop();
            maxArea = Math.max(maxArea, top[1] * (size - top[0]));
        }
        return maxArea;
    }

    public static void main(String[] args) {
        MaximalRectangle M = new MaximalRectangle();
        System.out.println(M.maximalRectangle(new char[][]{{'1','0','1','0','0'},{'1','0','1','1','1'},{'1','1','1','1','1'},{'1','0','0','1','0'}}));
        System.out.println(M.maximalRectangle(new char[][]{{'0','1'},{'1','0'}}));
    }
}
