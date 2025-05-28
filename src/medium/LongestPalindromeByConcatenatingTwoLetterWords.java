package medium;

import java.util.HashMap;
import java.util.HashSet;

public class LongestPalindromeByConcatenatingTwoLetterWords {
    public int longestPalindrome(String[] words) {
        int size = words.length;
        int length = 0;
        HashMap<String,Integer> wordMap = new HashMap<>();
        HashSet<String> used = new HashSet<>();
        for (int i = 0; i < size; i++)
            wordMap.put(words[i], wordMap.getOrDefault(words[i], 0) + 1);
        boolean sameLetterWordFound = false;
        for (int i = 0; i < size; i++) {
            String word = words[i];
            if (!wordMap.containsKey(word))
                continue;
            if (word.charAt(0) == word.charAt(1) && wordMap.getOrDefault(word, 0) < 2) {
                if (!sameLetterWordFound) {
                    sameLetterWordFound = true;
                    length += 2;
                }
                continue;
            }
            String reverse = reverse(word);
            if (wordMap.containsKey(reverse)) {
                length += 4;
                int frequency = wordMap.get(word);
                if (frequency > 1)
                    wordMap.put(word, wordMap.get(word) - 1);
                else
                    wordMap.remove(word);
                frequency = wordMap.get(reverse);
                if (frequency > 1)
                    wordMap.put(reverse, wordMap.get(reverse) - 1);
                else
                    wordMap.remove(reverse);
            }
        }
        return length;
    }

    private String reverse(String word) {
        return String.valueOf(word.charAt(1)) + word.charAt(0);
    }

    public static void main(String[] args) {
        LongestPalindromeByConcatenatingTwoLetterWords L = new LongestPalindromeByConcatenatingTwoLetterWords();
        System.out.println(L.longestPalindrome(new String[]{"dd","aa","bb","dd","aa","dd","bb","dd","aa","cc","bb","cc","dd","cc"}));
        System.out.println(L.longestPalindrome(new String[]{"lc","cl","gg"}));
        System.out.println(L.longestPalindrome(new String[]{"ab","ty","yt","lc","cl","ab"}));
        System.out.println(L.longestPalindrome(new String[]{"cc","ll","xx"}));
    }
}
