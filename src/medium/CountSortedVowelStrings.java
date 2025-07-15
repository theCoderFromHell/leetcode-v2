package medium;

import java.util.Arrays;

public class CountSortedVowelStrings {
    public int countVowelStrings(int n) {
        if (n == 1)
            return 5;
        int[] dp = new int[5];
        Arrays.fill(dp, 1);
        for (int k = 1; k < n; k++) {
            int curr = 0;
            for (int i = 0; i < 5; i++) {
                curr += dp[i];
                dp[i] = curr;
            }
        }
        return dp[0] + dp[1] + dp[2] + dp[3] + dp[4];
    }

    public static void main(String[] args) {
        CountSortedVowelStrings C = new CountSortedVowelStrings();
        System.out.println(C.countVowelStrings(2));
        System.out.println(C.countVowelStrings(3));
        System.out.println(C.countVowelStrings(4));
        System.out.println(C.countVowelStrings(5));
        System.out.println(C.countVowelStrings(33));
        System.out.println(C.countVowelStrings(50));
    }
}
