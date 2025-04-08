package medium;

public class StoneGame {
    public boolean stoneGame(int[] piles) {
        int size = piles.length;
        Integer[][] dp = new Integer[size][size];
        int total = 0;
        for (int i = 0; i < size; i++)
            total += piles[i];
        solve(piles, dp, 0, size-1, true);
        return  (dp[0][size-1] > total/2);

    }

    private int solve(int[] piles, Integer[][] dp, int start, int end, boolean isAliceTurn) {
        if (start == end) {
            dp[start][end] = isAliceTurn ? piles[start] : 0;
            return dp[start][end];
        }
        if (dp[start][end] != null)
            return dp[start][end];
        int maxStones = 0;
        if (isAliceTurn) {
            maxStones = Math.max(piles[start] + solve(piles, dp, start+1, end, false),
                    piles[end] + solve(piles, dp, start, end-1, false));
        } else {
            maxStones = Math.max(solve(piles, dp, start+1, end, true),
                    solve(piles, dp, start, end-1, true));
        }
        dp[start][end] = maxStones;
        return dp[start][end];
    }

    public static void main(String[] args) {
        StoneGame S = new StoneGame();
        System.out.println(S.stoneGame(new int[]{5,3,4,5}));
        System.out.println(S.stoneGame(new int[]{3,7,2,3}));
    }
}
