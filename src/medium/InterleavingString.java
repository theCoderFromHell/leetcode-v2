package medium;

public class InterleavingString {
    public boolean isInterleave(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();
        int size = s3.length();
        Boolean[][] dp = new Boolean[m+1][n+1];
        return solve(s1, m, s2, n, s3, size, dp, 0, 0, 0);
    }

    private boolean solve(String s1, int m, String s2, int n, String s3, int size, Boolean[][] dp, int index, int s1Index, int s2Index) {
        if (index == size)
            return s1Index == m && s2Index == n;
        if (s1Index == m)
            return s3.substring(index, size).equals(s2.substring(s2Index, n));
        if (s2Index == n)
            return s3.substring(index, size).equals(s1.substring(s1Index, m));
        if (dp[s1Index][s2Index] != null)
            return dp[s1Index][s2Index];
        boolean isPossible = false;
        if (s3.charAt(index) == s1.charAt(s1Index))
            isPossible |= solve(s1, m, s2, n, s3, size, dp, index+1, s1Index+1, s2Index);
        if (!isPossible)
            if (s3.charAt(index) == s2.charAt(s2Index))
                isPossible = solve(s1, m, s2, n, s3, size, dp, index + 1, s1Index, s2Index + 1);
        dp[s1Index][s2Index] = isPossible;
        return dp[s1Index][s2Index];
    }

    public static void main(String[] args) {
        InterleavingString I = new InterleavingString();
        System.out.println(I.isInterleave("a", "b", "a"));
        System.out.println(I.isInterleave("aabcc", "dbbca", "aadbbcbcac"));
        System.out.println(I.isInterleave("aabcc", "dbbca", "aadbbbaccc"));
        System.out.println(I.isInterleave("", "", ""));
    }
}
