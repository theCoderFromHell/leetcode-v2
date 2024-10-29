package medium;

import java.util.HashSet;

public class SplitAStringIntoTheMaxNumberOfUniqueSubstrings {
    int result = 0;
    public int maxUniqueSplit(String s) {
        int length = s.length();
        result = 0;
        HashSet<String> used = new HashSet<>();
        backtrack(s, length, 0, used, 0);
        return result;
    }

    private void backtrack(String s, int length, int index, HashSet<String> used, int count) {
        if (index == length) {
            result = Math.max(result, count);
            return;
        }
        for (int i = index; i < length; i++) {
            String curr = s.substring(index, i+1);
            if (!used.contains(curr)) {
                used.add(curr);
                backtrack(s, length, i+1, used, count+1);
                used.remove(curr);
            }
        }
    }

    public static void main(String[] args) {
        SplitAStringIntoTheMaxNumberOfUniqueSubstrings S = new SplitAStringIntoTheMaxNumberOfUniqueSubstrings();
        System.out.println(S.maxUniqueSplit("ababccc"));
        System.out.println(S.maxUniqueSplit("aba"));
        System.out.println(S.maxUniqueSplit("aa"));
    }
}
