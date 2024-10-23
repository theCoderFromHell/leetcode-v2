package medium;

import java.util.Arrays;
import java.util.Stack;

public class MinimumRemoveToMakeValidParentheses {
    public String minRemoveToMakeValid(String s) {
        int length = s.length();
        Stack<Integer> stack = new Stack<>();
        char[] sChars = s.toCharArray();
        boolean[] valid = new boolean[length];
        Arrays.fill(valid, true);
        for (int i = 0; i < length; i++) {
            if (sChars[i] == '(')
                stack.push(i);
            else if (sChars[i] == ')') {
                if (stack.isEmpty())
                    valid[i] = false;
                else
                    stack.pop();
            }
        }
        while (!stack.isEmpty()) {
            valid[stack.pop()] = false;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < length) {
            if (valid[i])
                sb.append(sChars[i]);
            i++;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MinimumRemoveToMakeValidParentheses M = new MinimumRemoveToMakeValidParentheses();
        System.out.println(M.minRemoveToMakeValid("lee(t(c)o)de)"));
        System.out.println(M.minRemoveToMakeValid("a)b(c)d"));
        System.out.println(M.minRemoveToMakeValid("))(("));
    }
}
