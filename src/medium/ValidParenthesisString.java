package medium;

import java.util.Stack;

public class ValidParenthesisString {

    public boolean checkValidString(String s) {
        int size = s.length();
        Stack<Integer> opens = new Stack<>();
        Stack<Integer> stars = new Stack<>();
        for (int i = 0; i < size; i++) {
            switch (s.charAt(i)) {
                case '(' :
                    opens.push(i);
                    break;
                case ')' :
                    if (!opens.empty())
                        opens.pop();
                    else if (!stars.empty())
                        stars.pop();
                    else
                        return false;
                    break;
                case '*' :
                    stars.push(i);
                    break;
            }
        }
        while (!opens.empty() && !stars.empty()) {
            int lastOpen = opens.pop();
            int lastStar = stars.pop();
            if (lastOpen > lastStar)
                return false;
        }
        return opens.empty();
    }
    public boolean checkValidStringV2(String s) {
        int size = s.length();
        Boolean[][] dp = new Boolean[size][size];
        return solve(s, size, 0, 0, dp);
    }

    private boolean solve(String s, int size, int index, int openCount, Boolean[][] dp) {
        if (index == size)
            return openCount == 0;
        if (dp[index][openCount] != null)
            return dp[index][openCount];
        boolean isValid = false;
        switch (s.charAt(index)) {
            case '(' :
                isValid = solve(s, size, index+1, openCount+1, dp);
                break;
            case ')' :
                if (openCount > 0)
                    isValid = solve(s, size, index+1, openCount-1, dp);
                break;
            case '*' :
                isValid |= solve(s, size, index+1, openCount+1, dp);
                if (openCount > 0)
                    isValid |= solve(s, size, index+1, openCount-1, dp);
                isValid |= solve(s, size, index+1, openCount, dp);
                break;
        }
        dp[index][openCount] = isValid;
        return dp[index][openCount];
    }

    public static void main(String[] args) {
        ValidParenthesisString V = new ValidParenthesisString();
        System.out.println(V.checkValidString("()"));
        System.out.println(V.checkValidString("(*)"));
        System.out.println(V.checkValidString("(*))"));
    }
}
