package medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReverseSubstringsBetweenEachPairOfParentheses {
    public String reverseParentheses(String s) {
        int size = s.length();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < size; i++) {
            char c = s.charAt(i);
            if (c != ')')
                stack.push(c);
            else {
                List<Character> chars = new ArrayList<>();
                while (!stack.empty() && stack.peek() != '(')
                    chars.add(stack.pop());
                stack.pop();
                int length = chars.size();
                for (int j = 0; j < length; j++)
                    stack.push(chars.get(j));
            }
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.empty())
            sb.append(stack.pop());
        return sb.reverse().toString();
    }

    public static void main(String[] args) {
        ReverseSubstringsBetweenEachPairOfParentheses R = new ReverseSubstringsBetweenEachPairOfParentheses();
        System.out.println(R.reverseParentheses("(abcd)"));
        System.out.println(R.reverseParentheses("(u(love)i)"));
        System.out.println(R.reverseParentheses("(ed(et(oc))el)"));
    }
}
