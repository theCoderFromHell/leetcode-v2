package medium;

import java.util.HashSet;

public class OptimalPartitionOfString {
    public int partitionString(String s) {
        int N = s.length();
        Integer[][] dp = new Integer[N][N];
        for (int i = 0; i < N; i++)
            dp[i][i] = 0;
        return solve(s, N, dp, 0, N-1);
    }

    private int solve(String s, int N, Integer[][] dp, int start, int end) {
        if (dp[start][end] != null)
            return dp[start][end];
        HashSet<Character> hashSet = new HashSet<>();
        hashSet.add(s.charAt(start));
        int minSteps = Integer.MAX_VALUE;
        for (int i = start+1; i <= end; i++) {
            minSteps = Math.min(minSteps, 1 + solve(s, N, dp, i, end));
            if (hashSet.contains(s.charAt(i)))
                break;
        }
        return minSteps;
    }

    public static void main(String[] args) {
        OptimalPartitionOfString o = new OptimalPartitionOfString();
        System.out.println(o.partitionString("abacaba"));
        System.out.println(o.partitionString("ssssss"));
    }
}
