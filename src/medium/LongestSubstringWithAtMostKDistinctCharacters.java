package medium;

import java.util.HashMap;

public class LongestSubstringWithAtMostKDistinctCharacters {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int N = s.length();
        int start = 0;
        int end = 0;
        int maxWindow = 0;
        HashMap<Character, Integer> currentWindow = new HashMap<>();
        while (end < N) {
            while (currentWindow.keySet().size() > k) {
                currentWindow.put(s.charAt(start), currentWindow.getOrDefault(s.charAt(start), 0) - 1);
                if (currentWindow.get(s.charAt(start)) == 0)
                    currentWindow.remove(s.charAt(start));
                start++;
            }
            while (end < N && (currentWindow.keySet().size() < k || currentWindow.containsKey(s.charAt(end)))) {
                currentWindow.put(s.charAt(end), currentWindow.getOrDefault(s.charAt(end), 0) + 1);
                end++;
            }
            if (currentWindow.keySet().size() <= k) {
                maxWindow = Math.max(maxWindow, end - start);
            }
            if (end < N) {
                currentWindow.put(s.charAt(end), currentWindow.getOrDefault(s.charAt(end), 0) + 1);
                end++;
            }
        }
        return maxWindow;
    }
}
