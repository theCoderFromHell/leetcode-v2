package medium;

import java.util.Arrays;
import java.util.HashSet;

public class ExtraCharactersInAString {
    public int minExtraChar(String s, String[] dictionary) {
        int size = s.length();
        HashSet<String> wordDictionary = new HashSet<>(Arrays.asList(dictionary));
        int[] dp = new int[size+1];
        dp[0] = 0;
        int minExtra;
        for (int end = 0; end < size; end++) {
            minExtra = end + 1;
            for (int start = 0; start <= end; start++) {
                minExtra = Math.min(minExtra, dp[start] + (wordDictionary.contains(s.substring(start, end+1)) ? 0 : end - start + 1));
            }
            dp[end+1] = minExtra;
        }
        return dp[size];
    }

    public static void main(String[] args) {
        ExtraCharactersInAString E = new ExtraCharactersInAString();
        System.out.println(E.minExtraChar("leetscode", new String[]{"leet","code","leetcode"}));
        System.out.println(E.minExtraChar("sayhelloworld", new String[]{"hello","world"}));
    }
}
