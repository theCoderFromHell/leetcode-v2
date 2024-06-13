package hard;

import java.util.HashMap;

public class MinimumWindowSubstring {
    public String minWindow(String s, String t) {
        int sLength = s.length();
        int tLength = t.length();
        HashMap<Character,Integer> tMap = new HashMap<>();
        for (int i = 0; i < tLength; i++)
            tMap.put(t.charAt(i), tMap.getOrDefault(t.charAt(i), 0) + 1);
        int start = 0;
        int end = 0;
        int minWindow = Integer.MAX_VALUE;
        Integer minStart = null;
        Integer minEnd = null;
        HashMap<Character,Integer> sMap = new HashMap<>();
        while (end < sLength) {
            while (end < sLength && !containsAll(tMap, sMap)) {
                sMap.put(s.charAt(end), sMap.getOrDefault(s.charAt(end), 0) + 1);
                end++;
            }
            while (start < end && tMap.getOrDefault(s.charAt(start), 0) < sMap.getOrDefault(s.charAt(start), 0)) {
                sMap.put(s.charAt(start), sMap.get(s.charAt(start)) - 1);
                start++;
            }
            if (containsAll(tMap, sMap) && (end - start) < minWindow) {
                minWindow = end - start;
                minStart = start;
                minEnd = end - 1;
            }
            if (start < sLength) {
                sMap.put(s.charAt(start), sMap.get(s.charAt(start)) - 1);
                start++;
            }
        }
        if (minStart != null && minEnd != null)
            return s.substring(minStart, minEnd+1);
        return "";
    }

    private boolean containsAll(HashMap<Character, Integer> tMap, HashMap<Character, Integer> sMap) {
        for (int i = 0; i < 26; i++) {
            Character c = (char) (i + 'A');
            if (sMap.getOrDefault(c, 0) < tMap.getOrDefault(c, 0))
                return false;
        }
        for (int i = 0; i < 26; i++) {
            Character c = (char) (i + 'a');
            if (sMap.getOrDefault(c, 0) < tMap.getOrDefault(c, 0))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        MinimumWindowSubstring m = new MinimumWindowSubstring();
        //System.out.println(m.minWindow("cabwefgewcwaefgcf", "cae"));
        System.out.println(m.minWindow("a", "b"));
        //System.out.println(m.minWindow("a", "aa"));
    }
}
