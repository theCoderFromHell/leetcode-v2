package medium;

import java.util.ArrayList;
import java.util.List;

public class LetterCasePermutation {
    public List<String> letterCasePermutation(String s) {
        int size =  s.length();
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder(s);
        generate(sb, 0, size, result);
        return result;
    }

    private void generate(StringBuilder s, int index, int size, List<String> result) {
        if (index == size) {
            result.add(new String(s));
            return;
        }
        if (Character.isLetter(s.charAt(index))) {
            generate(s, index + 1, size, result);
            s.setCharAt(index, flipCharacterCase(s.charAt(index)));
            generate(s, index + 1, size, result);
        } else
            generate(s, index + 1, size, result);
    }

    private char flipCharacterCase(char c) {
        if (Character.isLowerCase(c))
            return Character.toUpperCase(c);
        else if  (Character.isUpperCase(c))
            return Character.toLowerCase(c);
        return c;
    }

    public static void main(String[] args) {
        LetterCasePermutation L = new LetterCasePermutation();
        System.out.println(L.letterCasePermutation("a1b2"));
        System.out.println(L.letterCasePermutation("3z4"));
    }
}
