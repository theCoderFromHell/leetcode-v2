package medium;

public class LastStoneWeightII {
    public int lastStoneWeightII(int[] stones) {
        int size = stones.length;
        int sum = 0;
        for (int i = 0; i < size; i++)
            sum += stones[i];
        Integer[][] dp = new Integer[size][sum+1];
        int maxSum = solve(stones, size, 0, 0, dp, sum);
        return sum - 2*maxSum;
    }

    private int solve(int[] stones, int size, int index, int currSum, Integer[][] dp, int total) {
        if (index == size)
            return currSum;
        if (dp[index][currSum] != null)
            return dp[index][currSum];
        if (currSum + stones[index] > total/2)
            dp[index][currSum] = solve(stones, size, index+1, currSum, dp, total);
        else
            dp[index][currSum] = Math.max(solve(stones, size, index+1, currSum + stones[index], dp, total),
                    solve(stones, size, index+1, currSum, dp, total));
        return dp[index][currSum];
    }

    public static void main(String[] args) {
        LastStoneWeightII L = new LastStoneWeightII();
        System.out.println(L.lastStoneWeightII(new int[]{2,7,4,1,8,1}));
        System.out.println(L.lastStoneWeightII(new int[]{31,26,33,21,40}));
        System.out.println(L.lastStoneWeightII(new int[]{}));
        System.out.println(L.lastStoneWeightII(new int[]{}));

    }
}
