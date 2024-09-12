package medium;

import java.util.Stack;

public class BasicCalculatorII {
    public int calculate(String s) {
        int result = 0;
        Stack<Integer> stack = new Stack<>();
        int current = 0;
        int op = '+';
        int N = s.length();
        for (int i = 0; i < N; i++) {
            int currChar = s.charAt(i);
            if (Character.isDigit(currChar))
                current = current * 10 + (currChar - '0');
            if ((!Character.isDigit(currChar) &&
                    !Character.isWhitespace(currChar))
                    || i == N-1) {
                switch (op) {
                    case '+' :
                        stack.push(current);
                        break;
                    case '-' :
                        stack.push(-1 * current);
                        break;
                    case '*' :
                        stack.push(stack.pop() * current);
                        break;
                    case '/' :
                        stack.push(stack.pop() / current);
                        break;
                }
                op = currChar;
                current = 0;
            }
        }
        while (!stack.empty())
            result += stack.pop();
        return result;
    }

    public static void main(String[] args) {
        BasicCalculatorII B = new BasicCalculatorII();
        System.out.println(B.calculate("3+2*2"));
        System.out.println(B.calculate(" 3/2 "));
        System.out.println(B.calculate(" 3+5 / 2 "));

    }
}
