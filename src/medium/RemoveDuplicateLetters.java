package medium;

import java.util.HashMap;
import java.util.Stack;

public class RemoveDuplicateLetters {
    public String removeDuplicateLetters(String s) {
        int N = s.length();
        boolean[] visited = new boolean[26];
        Stack<Character> stack = new Stack<>();
        HashMap<Character, Integer> lastSeen = new HashMap<>();
        for (int i = 0; i < N; i++)
            lastSeen.put(s.charAt(i), i);
        for (int i = 0; i < N; i++) {
            char c = s.charAt(i);
            if (visited[c - 'a'])
                continue;
            while (!stack.empty() && c < stack.peek() && lastSeen.get(stack.peek()) > i) {
                visited[stack.peek() - 'a'] = false;
                stack.pop();
            }
            visited[c - 'a'] = true;
            stack.push(c);
        }
        StringBuilder sb = new StringBuilder();
        for (Character c : stack)
            sb.append(c);
        return sb.toString();
    }
}
