package medium;

import java.util.ArrayList;
import java.util.List;

public class DifferentWaysToAddParentheses {
    public List<Integer> diffWaysToCompute(String expression) {
        int N = expression.length();
        List<Integer> combos = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (isOperator(expression.charAt(i))) {
                List<Integer> leftCombinations = diffWaysToCompute(expression.substring(0, i));
                List<Integer> rightCombinations = diffWaysToCompute(expression.substring(i+1, N));
                for (int left : leftCombinations) {
                    for (int right : rightCombinations) {
                        switch (expression.charAt(i)) {
                            case '+' :
                                combos.add(left + right);
                                break;
                            case '-' :
                                combos.add(left - right);
                                break;
                            default:
                                combos.add(left * right);
                        }
                    }
                }
            }
        }
        if (combos.isEmpty())
            return List.of(Integer.parseInt(expression));
        return combos;
    }

    private boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*');
    }

    public static void main(String[] args) {
        DifferentWaysToAddParentheses d = new DifferentWaysToAddParentheses();
        System.out.println(d.diffWaysToCompute("2-1-1"));
        System.out.println(d.diffWaysToCompute("2*3-4*5"));

    }
}
