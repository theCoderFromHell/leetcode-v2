package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAndReplacePattern {
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        int size = words.length;
        List<String> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (matchesPattern(words[i], pattern))
                result.add(words[i]);
        }
        return result;
    }

    private boolean matchesPattern(String word, String pattern) {
        int size = word.length();
        int[] charMap = new int[26];
        Arrays.fill(charMap, -1);
        int[] reverseCharMap = new int[26];
        Arrays.fill(reverseCharMap, -1);
        for (int i = 0; i < size; i++) {
            int w = word.charAt(i) - 'a';
            int p = pattern.charAt(i) - 'a';
            if (charMap[w] != -1 && charMap[w] != p)
                return false;
            if (reverseCharMap[p] != -1 && reverseCharMap[p] != w)
                return false;
            charMap[w] = p;
            reverseCharMap[p] = w;
        }
        return true;
    }

    public static void main(String[] args) {
        FindAndReplacePattern F = new FindAndReplacePattern();
        System.out.println(F.findAndReplacePattern(new String[]{"abc","deq","mee","aqq","dkd","ccc"}, "abb"));
        System.out.println(F.findAndReplacePattern(new String[]{"a","b","c"}, "a"));
        System.out.println(F.findAndReplacePattern(new String[]{"ef","fq","ao","at","lx"}, "ya"));
    }
}
