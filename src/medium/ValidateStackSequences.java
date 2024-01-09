package medium;

import java.util.HashSet;
import java.util.Stack;

public class ValidateStackSequences {
    public static boolean validateStackSequences(int[] pushed, int[] popped) {
        int N = pushed.length;
        int idx = 0;
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        while (idx < N) {
            while (i < N && !stack.contains(popped[idx]))
                stack.add(pushed[i++]);
            if (!stack.contains(popped[idx]) || stack.peek() != popped[idx])
                return false;
            else {
                stack.pop();
                idx++;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        System.out.println(validateStackSequences(new int[]{1,2,3,4,5}, new int[]{4,5,3,2,1}));
        System.out.println(validateStackSequences(new int[]{1,2,3,4,5}, new int[]{4,3,5,1,2}));
        System.out.println(validateStackSequences(new int[]{}, new int[]{}));
    }
}
