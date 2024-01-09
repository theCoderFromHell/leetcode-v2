package medium;

import java.util.ArrayList;
import java.util.Stack;

public class RemovingStarsFromAString {
    public static String removeStars(String s) {
        int N = s.length();
        Stack<Character> chars = new Stack<>();
        for (int i = 0; i < N; i++) {
            if (s.charAt(i) == '*' && !chars.empty())
                chars.pop();
            else
                chars.add(s.charAt(i));
        }
        StringBuilder result = new StringBuilder();
        while (!chars.empty())
            result.append(chars.pop());
        return (result.toString().isBlank() || (result.isEmpty())) ? "" : result.reverse().toString();
    }
    public static void main(String[] args) {
        System.out.println(removeStars("leet**cod*e"));
        System.out.println(removeStars("erase*****"));
    }
}
