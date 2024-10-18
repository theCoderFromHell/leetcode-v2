package medium;

import java.util.ArrayList;
import java.util.List;

public class LexicographicalNumbers {
    public List<Integer> lexicalOrder(int n) {
        List<Integer> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        solve(sb, result, n);
        return result;
    }

    private boolean solve(StringBuilder curr, List<Integer> result, int max) {
        for (int i = 0; i <= 9; i++) {
            if (i == 0 && curr.isEmpty())
                continue;
            curr.append(i);
            int value = Integer.parseInt(curr.toString());
            if (value > max) {
                curr.deleteCharAt(curr.length()-1);
                return false;
            }
            result.add(value);
            solve(curr, result, max);
            curr.deleteCharAt(curr.length()-1);
        }
        return true;
    }

    public static void main(String[] args) {
        LexicographicalNumbers L = new LexicographicalNumbers();
        System.out.println(L.lexicalOrder(13));
        System.out.println(L.lexicalOrder(2));
        System.out.println(L.lexicalOrder(122));
    }
}
