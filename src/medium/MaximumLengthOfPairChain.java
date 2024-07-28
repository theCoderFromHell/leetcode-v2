package medium;

import java.util.Arrays;
import java.util.Comparator;

public class MaximumLengthOfPairChain {

    public int findLongestChain(int[][] pairs) {
        int N = pairs.length;
        Arrays.sort(pairs, Comparator.comparingInt(a -> a[1]));
        int maxSoFar = Integer.MIN_VALUE;
        int result = 0;
        for (int i = 0; i < N; i++) {
            if (pairs[i][0] > maxSoFar) {
                result++;
                maxSoFar = pairs[i][1];
            }
        }
        return result;
    }

    public int findLongestChainV2(int[][] pairs) {
        int N = pairs.length;
        int[] dp = new int[N];
        Arrays.sort(pairs, Comparator.comparingInt(a -> a[1]));
        Arrays.fill(dp, 1);
        int maxLength = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (pairs[j][1] < pairs[i][0] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    maxLength = Math.max(maxLength, dp[i]);
                }
            }
        }
        return maxLength;
    }
}
