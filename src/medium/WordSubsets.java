package medium;

import java.util.ArrayList;
import java.util.List;

public class WordSubsets {
    public List<String> wordSubsets(String[] words1, String[] words2) {
        int[] target = new int[26];
        for (String word : words2) {
            int[] currTarget = new int[26];
            int length = word.length();
            for (int i = 0; i < length; i++)
                currTarget[word.charAt(i) - 'a']++;
            for (int i = 0; i < 26; i++)
                target[i] = Math.max(target[i], currTarget[i]);
        }
        List<String> result = new ArrayList<>();
        for (String word : words1) {
            int[] currTarget = new int[26];
            int length = word.length();
            for (int i = 0; i < length; i++)
                currTarget[word.charAt(i) - 'a']++;
            boolean isUniversal = true;
            for (int i = 0; i < 26; i++) {
                if (currTarget[i] < target[i]) {
                    isUniversal = false;
                    break;
                }
            }
            if (isUniversal)
                result.add(word);
        }
        return result;
    }

    public static void main(String[] args) {
        WordSubsets W = new WordSubsets();
        System.out.println(W.wordSubsets(new String[]{"amazon","apple","facebook","google","leetcode"}, new String[]{"e","o"}));
        System.out.println(W.wordSubsets(new String[]{"amazon","apple","facebook","google","leetcode"}, new String[]{"lc","eo"}));
        System.out.println(W.wordSubsets(new String[]{"acaac","cccbb","aacbb","caacc","bcbbb"}, new String[]{"c","cc","b"}));
    }
}
