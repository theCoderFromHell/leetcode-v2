package interviews.oa.hackerrank;

public class CountPalindromicSubsequences {
    static final int MOD = 1_000_000_007;

    public static long getPalindromesCount(String s) {
        int n = s.length();
        int[][] prefixCount = new int[2][n]; // prefixCount[ch][i] = count of ch before i
        long[][] prefixPair = new long[2][2]; // running count of pairs before current index

        int[][] suffixCount = new int[2][n];
        long[][] suffixPair = new long[2][2];

        // Build prefix counts and pairs
        int[] count = new int[2];
        for (int i = 0; i < n; i++) {
            int ch = s.charAt(i) - '0';
            prefixCount[0][i] = count[0];
            prefixCount[1][i] = count[1];
            // update pair counts
            for (int a = 0; a < 2; a++)
                prefixPair[a][ch] += count[a];
            count[ch]++;
        }

        // Build suffix counts and pairs
        count[0] = count[1] = 0;
        for (int i = n - 1; i >= 0; i--) {
            int ch = s.charAt(i) - '0';
            suffixCount[0][i] = count[0];
            suffixCount[1][i] = count[1];
            for (int a = 0; a < 2; a++)
                suffixPair[ch][a] += count[a];
            count[ch]++;
        }

        // Build full prefixPairsAt[i]: number of pairs (a,b) before i
        long[][] prefixPairsAt = new long[n][4]; // index mapping (a*2+b)
        long[][] suffixPairsAt = new long[n][4];
        count[0] = count[1] = 0;
        long[][] prefixPairRun = new long[2][2];
        for (int i = 0; i < n; i++) {
            for (int a = 0; a < 2; a++) {
                for (int b = 0; b < 2; b++)
                    prefixPairsAt[i][a * 2 + b] = prefixPairRun[a][b];
            }
            int ch = s.charAt(i) - '0';
            for (int a = 0; a < 2; a++)
                prefixPairRun[a][ch] += count[a];
            count[ch]++;
        }

        count[0] = count[1] = 0;
        long[][] suffixPairRun = new long[2][2];
        for (int i = n - 1; i >= 0; i--) {
            for (int a = 0; a < 2; a++) {
                for (int b = 0; b < 2; b++)
                    suffixPairsAt[i][a * 2 + b] = suffixPairRun[a][b];
            }
            int ch = s.charAt(i) - '0';
            for (int b = 0; b < 2; b++)
                suffixPairRun[ch][b] += count[b];
            count[ch]++;
        }

        // Count palindromes
        long ans = 0;
        for (int mid = 2; mid <= n - 3; mid++) {
            for (int a = 0; a < 2; a++) {
                for (int b = 0; b < 2; b++) {
                    long leftPairs = prefixPairsAt[mid][a*2 + b];
                    long rightPairs = suffixPairsAt[mid][b*2 + a];
                    ans = (ans + (leftPairs % MOD) * (rightPairs % MOD)) % MOD;
                }
            }
        }
        return ans;
    }
}
/*
Given a string containing only 0 and 1, count the number of subsequences of exactly length 5 that are palindromes.
Return the answer modulo 10^9 +7
 */
