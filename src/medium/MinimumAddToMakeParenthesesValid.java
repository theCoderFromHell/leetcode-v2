package medium;

public class MinimumAddToMakeParenthesesValid {
    public int minAddToMakeValid(String s) {
        int N = s.length();
        int extra = 0;
        int open = 0;
        for (int i = 0; i < N; i++) {
            switch (s.charAt(i)) {
                case '(' :
                    open++;
                    break;
                case ')' :
                    if (open <= 0)
                        extra++;
                    else
                        open--;
            }
        }
        return open + extra;
    }

    public static void main(String[] args) {
        MinimumAddToMakeParenthesesValid M = new MinimumAddToMakeParenthesesValid();
        System.out.println(M.minAddToMakeValid("())"));
        System.out.println(M.minAddToMakeValid("((("));
    }
}
