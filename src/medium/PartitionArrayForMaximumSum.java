package medium;

public class PartitionArrayForMaximumSum {
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int size = arr.length;
        Integer[] dp = new Integer[size];
        return solve(arr, size, dp, k, 0);
    }

    private int solve(int[] arr, int size, Integer[] dp, int k, int index) {
        if (index >= size)
            return 0;
        if (dp[index] != null)
            return dp[index];
        int start = index;
        int end = Math.min(size-1, start + k - 1);
        int currMax = 0;
        int maxSum = 0;
        for (int i = start; i <= end; i++) {
            currMax = Math.max(currMax, arr[i]);
            maxSum = Math.max(maxSum, currMax * (i - start + 1) + solve(arr, size, dp, k, i+1));
        }
        dp[index] = maxSum;
        return dp[index];
    }

    public static void main(String[] args) {
        PartitionArrayForMaximumSum P = new PartitionArrayForMaximumSum();
        System.out.println(P.maxSumAfterPartitioning(new int[]{1,15,7,9,2,5,10}, 3));
        System.out.println(P.maxSumAfterPartitioning(new int[]{1,4,1,5,7,3,6,1,9,9,3}, 4));
        System.out.println(P.maxSumAfterPartitioning(new int[]{1}, 1));
    }
}
