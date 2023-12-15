package medium;

import java.util.Arrays;

public class MinimumNumberOfStepsToMakeTwoStringsAnagram {
    public static int minSteps(String s, String t) {
        if (t.equals(s))
            return 0;
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        Arrays.sort(sChars);
        Arrays.sort(tChars);
        if (new String(sChars).equals(new String(tChars)))
            return 0;
        int[] sHash = new int[26];
        int[] tHash = new int[26];
        for (char c : s.toCharArray())
            sHash[c - 'a']++;
        for (char c : t.toCharArray())
            tHash[c - 'a']++;
        int result = 0;
        for (int i = 0; i < 26; i++) {
            if (sHash[i] < tHash[i])
                result += (tHash[i]-sHash[i]);
        }
        return result;
    }
    public static void main(String[] args) {
        System.out.println(minSteps("bab", "aba"));
        System.out.println(minSteps("leetcode", "practice"));
        System.out.println(minSteps("anagram", "mangaar"));
    }
}
