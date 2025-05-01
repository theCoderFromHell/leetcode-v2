package medium;

import java.util.Arrays;
import java.util.Stack;

public class NextGreaterElementII {
    public int[] nextGreaterElements(int[] nums) {
        int size = nums.length;
        int[] nextGreater = new int[size];
        Arrays.fill(nextGreater, -1);
        int index = 0;
        Stack<Integer> stack = new Stack<>();
        boolean circle = false;
        while (index < size) {
            while (!stack.isEmpty() && nums[index] > nums[stack.peek()]) {
                int s = stack.pop();
                nextGreater[s] = nums[index];
            }
            stack.push(index);
            index++;
            if (!circle && index == size) {
                index = 0;
                circle = true;
            }
        }
        return nextGreater;
    }

    public static void main(String[] args) {
        NextGreaterElementII N = new NextGreaterElementII();
        System.out.println(Arrays.toString(N.nextGreaterElements(new int[]{1,2,1})));
        System.out.println(Arrays.toString(N.nextGreaterElements(new int[]{1,2,3,4,3})));
    }
}
