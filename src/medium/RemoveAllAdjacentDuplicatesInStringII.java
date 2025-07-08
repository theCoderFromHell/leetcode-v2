package medium;

import java.util.Stack;

public class RemoveAllAdjacentDuplicatesInStringII {
    public String removeDuplicates(String s, int k) {
        StringBuilder sb = new StringBuilder(s);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < sb.length(); i++) {
            if (i > 0 && sb.charAt(i-1) == sb.charAt(i)) {
                int count = stack.pop();
                if (count + 1 == k) {
                    sb.delete(i + 1 - k, i + 1);
                    i = i - k;
                } else
                    stack.add(count + 1);
            } else
                stack.add(1);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        RemoveAllAdjacentDuplicatesInStringII R = new RemoveAllAdjacentDuplicatesInStringII();
        System.out.println(R.removeDuplicates("abcd", 2));
        System.out.println(R.removeDuplicates("deeedbbcccbdaa", 3));
        System.out.println(R.removeDuplicates("pbbcggttciiippooaais", 2));
    }
}
