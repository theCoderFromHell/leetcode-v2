package medium;

import java.util.ArrayList;
import java.util.List;

public class TheKThLexicographicalStringOfAllHappyStringsOfLengthN {
    public String getHappyString(int n, int k) {
        List<String> all = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        generate(sb, 0, n, k,  null, all);
        if (all.size() >= k)
            return all.get(k-1);
        return "";
    }

    private void generate(StringBuilder sb, int index, int size, int k, Character c, List<String> all) {
        if (all.size() >= k)
            return;
        if (index == size) {
            all.add(new String(sb));
            return;
        }
        for (int i = 0; i < 3; i++) {
            char ch = (char) (i + 'a');
            if (c == null || ch != c) {
                sb.append(ch);
                generate(sb, index+1, size, k, ch, all);
                if (all.size() >= k)
                    return;
                sb.deleteCharAt(sb.length() - 1);
            }
        }
    }

    public static void main(String[] args) {
        TheKThLexicographicalStringOfAllHappyStringsOfLengthN T = new TheKThLexicographicalStringOfAllHappyStringsOfLengthN();
        System.out.println(T.getHappyString(1, 3));
        System.out.println(T.getHappyString(1, 4));
        System.out.println(T.getHappyString(3, 9));
        System.out.println(T.getHappyString(2, 5));
    }
}
