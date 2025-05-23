package hard;

public class StoneGameIII {
    public String stoneGameIII(int[] stoneValue) {
        int size = stoneValue.length;
        Integer[] dp = new Integer[size];
        int alicePts = solve(stoneValue, size, dp, 0);
        if (alicePts == 0)
            return "Tie";
        return (alicePts > 0) ? "Alice" : "Bob";
    }

    private int solve(int[] stoneValue, int size, Integer[] dp, int index) {
        if (index == size)
            return 0;
        if (dp[index] != null)
            return dp[index];
        int maxPoints = stoneValue[index] - solve(stoneValue, size, dp, index+1);
        if (index <= size - 2)
            maxPoints = Math.max(maxPoints,
                    stoneValue[index] + stoneValue[index+1] - solve(stoneValue, size, dp, index+2));
        if (index <= size - 3)
            maxPoints = Math.max(maxPoints,
                    stoneValue[index] + stoneValue[index+1] + stoneValue[index+2] - solve(stoneValue, size, dp, index+3));
        dp[index] = maxPoints;
        return dp[index];
    }

    public static void main(String[] args) {
        StoneGameIII S = new StoneGameIII();
        System.out.println(S.stoneGameIII(new int[]{1,2,3,7}));
        System.out.println(S.stoneGameIII(new int[]{1,2,3,-9}));
        System.out.println(S.stoneGameIII(new int[]{1,2,3,6}));
    }
}
