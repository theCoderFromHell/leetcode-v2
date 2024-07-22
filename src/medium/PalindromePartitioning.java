package medium;

import java.util.ArrayList;
import java.util.List;

public class PalindromePartitioning {
    public List<List<String>> partition(String s) {
        int N = s.length();
        if (N == 1)
            return List.of(List.of(s));
        boolean[][] dp = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            dp[i][i] = true;
            if (i+1 < N && s.charAt(i) == s.charAt(i+1))
                dp[i][i+1] = true;
        }
        for (int gap = 2; gap < N; gap++) {
            for (int i = 0; i < N; i++) {
                if (i + gap < N) {
                    if (s.charAt(i) == s.charAt(i + gap) && dp[i+1][i+gap-1])
                        dp[i][i+gap] = true;
                }
            }
        }
        List<List<String>> result = new ArrayList<>();
        ArrayList<String> partitions = new ArrayList<>();
        solve(s, N, dp, 0, partitions, result);
        return result;
    }

    private void solve(String s, int N, boolean[][] dp, int index, ArrayList<String> partitions, List<List<String>> result) {
        if (index == N) {
            result.add(new ArrayList<>(partitions));
            return;
        }
        for (int i = index; i < N; i++) {
            if (dp[index][i]) {
                partitions.addLast(s.substring(index, i+1));
                solve(s, N, dp, i+1, partitions, result);
                partitions.removeLast();
            }
        }
    }

    public static void main(String[] args) {
        PalindromePartitioning P = new PalindromePartitioning();
        System.out.println(P.partition("aab"));
        System.out.println(P.partition("a"));
    }
}
