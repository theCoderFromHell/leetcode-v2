package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class WordBreak {
    public static boolean wordBreak(String s, List<String> wordDict) {
        int N = s.length();
        HashSet<String> dictionary = new HashSet<>(wordDict);
        boolean[][] dp = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N+1; j++) {
                if (dictionary.contains(s.substring(i, j))) {
                    dp[i][j-1] = true;
                    if (i > 0 && dp[0][i - 1])
                        dp[0][j-1] = true;
                }
            }
        }
        return dp[0][N - 1];
    }
    public static void main(String[] args) {
        System.out.println(wordBreak("leetcode", Arrays.asList("leet", "code")));
        System.out.println(wordBreak("applepenapple", Arrays.asList("apple","pen")));
        System.out.println(wordBreak("catsandog", Arrays.asList("cats","dog","sand","and","cat")));
    }
}
