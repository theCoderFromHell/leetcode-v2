package medium;

import java.util.HashMap;
import java.util.HashSet;

public class LongestSubstringWithAtMostTwoDistinctCharacters {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        int N = s.length();
        int start = 0;
        int end = 0;
        int maxWindow = 0;
        HashMap<Character, Integer> currentWindow = new HashMap<>();
        while (end < N) {
            while (currentWindow.keySet().size() > 2) {
                currentWindow.put(s.charAt(start), currentWindow.getOrDefault(s.charAt(start), 0) - 1);
                if (currentWindow.get(s.charAt(start)) == 0)
                    currentWindow.remove(s.charAt(start));
                start++;
            }
            while (end < N && (currentWindow.keySet().size() < 2 || currentWindow.containsKey(s.charAt(end)))) {
                currentWindow.put(s.charAt(end), currentWindow.getOrDefault(s.charAt(end), 0) + 1);
                end++;
            }
            if (currentWindow.keySet().size() <= 2) {
                maxWindow = Math.max(maxWindow, end - start);
            }
            if (end < N) {
                currentWindow.put(s.charAt(end), currentWindow.getOrDefault(s.charAt(end), 0) + 1);
                end++;
            }
        }
        return maxWindow;
    }

    public static void main(String[] args) {
        LongestSubstringWithAtMostTwoDistinctCharacters L = new LongestSubstringWithAtMostTwoDistinctCharacters();
        System.out.println(L.lengthOfLongestSubstringTwoDistinct("eceba"));
        System.out.println(L.lengthOfLongestSubstringTwoDistinct("ccaabbb"));
        System.out.println(L.lengthOfLongestSubstringTwoDistinct("abcd"));
    }
}
