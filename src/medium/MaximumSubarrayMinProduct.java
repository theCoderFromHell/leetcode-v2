package medium;

import java.util.Stack;

public class MaximumSubarrayMinProduct {
    int MOD = 1000000007;
    public int maxSumMinProduct(int[] nums) {
        int N = nums.length;
        long[] prefix = new long[N+1];
        prefix[0] = 0;
        for (int i = 1; i <= N; i++)
            prefix[i] = prefix[i-1] + nums[i-1];

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{0,0});
        long result = Integer.MIN_VALUE;
        for (int i = 1; i < N; i++) {
            int thisStart = i;
            while (!stack.empty() && nums[i] < nums[stack.peek()[1]]) {
                int[] pair = stack.pop();
                int start = pair[0];
                int index = pair[1];
                long sum = prefix[i] - prefix[start];
                result = Math.max(result, (nums[index] * sum));
                thisStart = start;
            }
            stack.push(new int[]{thisStart, i});
        }

        while (!stack.empty()) {
            int[] pair = stack.pop();
            int start = pair[0];
            int index = pair[1];
            long sum = prefix[N] - prefix[start];
            result = Math.max(result, (nums[index] * sum));
        }
        return (int) (result % MOD);
    }

    public static void main(String[] args) {
        MaximumSubarrayMinProduct M = new MaximumSubarrayMinProduct();
        System.out.println(M.maxSumMinProduct(new int[]{1,2,3,2}));
    }
}
