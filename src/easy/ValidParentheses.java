package easy;

import java.util.Stack;

public class ValidParentheses {

    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        int length = s.length();
        int i=0;
        while(i < length) {
            if(isOpening(s.charAt(i))) {
                stack.push(s.charAt(i));
            } else if (isClosing(s.charAt(i))){
                if(stack.empty()) return false;
                if(!matchEachOther(stack.peek(), s.charAt(i))) {
                    return false;
                }
                stack.pop();
            }
            i++;
        }

        return stack.empty();
    }

    private static boolean isOpening(char charAt) {
        return charAt == '(' || charAt == '{' || charAt == '[';
    }
    private static boolean isClosing(char charAt) {
        return charAt == ')' || charAt == '}' || charAt == ']';
    }
    private static boolean matchEachOther(char a, char b) {
        return ((a == '(' && b == ')') || (a == '{' && b == '}') || (a == '[' && b == ']'));
    }

    public static void main(String[] args) {
        System.out.println(isValid("{{)}()"));
        System.out.println(isValid("([{}])()"));
        System.out.println(isValid("()(){()}"));
    }
}
