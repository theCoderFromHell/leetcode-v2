package hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LongestDuplicateSubstring {
    int MOD = 1000_000_007;
    int ROOT = 26;
    public String longestDupSubstring(String s) {
        int length = s.length();
        int start = 1;
        int end = length;
        int mid;
        int[] charToNums = new int[length];
        for (int i = 0; i < length; i++)
            charToNums[i] = s.charAt(i) - 'a';
        String result = "";
        while (start <= end) {
            mid = start + (end - start)/2;
            String curr = duplicateFound(s, charToNums, length, mid);
            if (curr != null) {
                result = curr;
                start = mid + 1;
            }
            else
                end = mid - 1;
        }
        return result;
    }

    private String duplicateFound(String s, int[] text, int length, int window) {
        long h = 0;
        HashMap<Long, List<Integer>> hashList = new HashMap<>();
        long maxRoot = 1;
        for (int i = 0; i < window; i++)
            maxRoot = (maxRoot * ROOT) % MOD;
        for (int i = 0; i < window; i++)
            h = (h * ROOT + text[i]) % MOD;
        hashList.putIfAbsent(h, new ArrayList<>());
        hashList.get(h).add(0);

        for (int start = 1; start <= length - window ; start++) {
            h = ((h * ROOT - (text[start-1] * maxRoot) % MOD + MOD) % MOD + text[start + window -1]) % MOD;
            List<Integer> possibleMatches = hashList.get(h);
            if (possibleMatches != null && !possibleMatches.isEmpty()) {
                String pattern = s.substring(start, start + window);
                for (int match : possibleMatches) {
                    String curr = s.substring(match, match + window);
                    if (pattern.equals(curr))
                        return pattern;
                }
            }
            hashList.putIfAbsent(h, new ArrayList<>());
            hashList.get(h).add(start);
        }
        return null;
    }

    public static void main(String[] args) {
        LongestDuplicateSubstring L = new LongestDuplicateSubstring();
        System.out.println(L.longestDupSubstring("banana"));
        System.out.println(L.longestDupSubstring("abcd"));
    }
}
