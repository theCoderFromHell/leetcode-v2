package medium;

import java.util.Arrays;
import java.util.Stack;

public class The132Pattern {
    public static boolean find132pattern(int[] nums) {
        int N = nums.length;
        int[] minOnLeft = new int[N];
        int min = 0;
        Arrays.fill(minOnLeft, Integer.MAX_VALUE);
        for (int i = 1; i < N; i++) {
            if (nums[min] < nums[i])
                minOnLeft[i] = min;
            if (nums[min] > nums[i])
                min = i;
        }
        Stack<Integer> stack = new Stack<>();
        stack.add(N-1);
        int i = N-2;
        while (i >= 0) {
            while (!stack.empty() && nums[i] > nums[stack.peek()]) {
                int number = nums[stack.peek()];
                stack.pop();
                if (minOnLeft[i] != Integer.MAX_VALUE && nums[minOnLeft[i]] < number)
                    return true;
            }
            stack.add(i);
            i--;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(find132pattern(new int[]{1,2,3,4}));
        System.out.println(find132pattern(new int[]{3,1,4,2}));
        System.out.println(find132pattern(new int[]{-1,3,2,0}));
        System.out.println(find132pattern(new int[]{3,5,0,3,4}));
    }
}
