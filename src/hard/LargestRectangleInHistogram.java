package hard;

import java.util.Stack;

public class LargestRectangleInHistogram {
    public int largestRectangleArea(int[] heights) {
        int N = heights.length;
        int maxArea = Integer.MIN_VALUE;
        Stack<int[]> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            int prev = i;
            while (!stack.empty() && heights[i] < stack.peek()[1]) {
                maxArea = Math.max(maxArea, stack.peek()[1] * (i - stack.peek()[0]));
                prev = stack.peek()[0];
                stack.pop();
            }
            stack.push(new int[]{prev, heights[i]});
        }
        while (!stack.empty()) {
            int[] top = stack.pop();
            maxArea = Math.max(maxArea, top[1] * (N - top[0]));
        }
        return maxArea;
    }

    public static void main(String[] args) {
        LargestRectangleInHistogram LH = new LargestRectangleInHistogram();
        System.out.println(LH.largestRectangleArea(new int[]{2,1,5,6,2,3}));
        System.out.println(LH.largestRectangleArea(new int[]{2,4}));
    }
}
