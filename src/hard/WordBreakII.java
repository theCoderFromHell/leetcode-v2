package hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class WordBreakII {
    public List<String> wordBreak(String s, List<String> wordDict) {
        int length = s.length();
        HashSet<String> dictionary = new HashSet<>(wordDict);
        List<String> result = new ArrayList<>();
        solve(s, 0, length, dictionary, result, new StringBuilder());
        return result;
    }

    private void solve(String s, int index, int length, HashSet<String> dictionary, List<String> result, StringBuilder stringBuilder) {
        if (index == length) {
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            result.add(new String(stringBuilder));
            return;
        }
        StringBuilder curr = new StringBuilder(stringBuilder);
        for (int i = index; i < length; i++) {
            if (dictionary.contains(s.substring(index, i+1))) {
                curr.append(s, index, i+1).append(" ");
                solve(s, i+1, length, dictionary, result, curr);
                curr = new StringBuilder(stringBuilder);
            }
        }
    }

    public static void main(String[] args) {
        WordBreakII W = new WordBreakII();
        System.out.println(W.wordBreak("catsanddog", Arrays.asList("cat","cats","and","sand","dog")));
        System.out.println(W.wordBreak("pineapplepenapple", Arrays.asList("apple","pen","applepen","pine","pineapple")));
        System.out.println(W.wordBreak("catsandog", Arrays.asList("cats","dog","sand","and","cat")));
    }
}
