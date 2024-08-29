package medium;

public class TwoKeysKeyboard {
    int MAX_NUMBER = 1009;
    public int minSteps(int n) {
        if (n == 1)
            return 0;
        Integer[][] dp = new Integer[n][n];
        return 2 + solve(2, 1, n, dp);
    }

    private int solve(int current, int paste, int target, Integer[][] dp) {
        if (current > target)
            return MAX_NUMBER;
        if (current == target)
            return 0;
        if (dp[current][paste] != null)
            return dp[current][paste];
        int onlyPaste = 1 + solve(current + paste, paste, target, dp);
        int copyAndPaste = 2 + solve(2 * current, current, target, dp);
        dp[current][paste] = Math.min(onlyPaste, copyAndPaste);
        return dp[current][paste];
    }

    public static void main(String[] args) {
        TwoKeysKeyboard T = new TwoKeysKeyboard();
        System.out.println(T.minSteps(15));
        System.out.println(T.minSteps(3));
        System.out.println(T.minSteps(6));
        System.out.println(T.minSteps(200));
        System.out.println(T.minSteps(453));
    }
}
