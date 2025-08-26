package interviews.online.assessment.hackerrank;

import java.util.List;
import java.util.Stack;

public class StrengthOfSubarrays {
    private static final int MOD = 1000000007;

    public int getSubarrayStrengthSum(List<Integer> arr) {
        int n = arr.size();
        int[] left = new int[n];
        int[] right = new int[n];
        Stack<Integer> stack = new Stack<>();

        // Compute left boundaries: first left index with value greater than arr[i]
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr.get(stack.peek()) <= arr.get(i)) {
                stack.pop();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear();

        // Compute right boundaries: first right index with value greater than arr[i] (strictly)
        // Actually, to avoid double counting, we use non-strict in right? But here we want to break at values >= to the right.
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr.get(stack.peek()) < arr.get(i)) {
                stack.pop();
            }
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        long result = 0;
        long inv2 = modInverse(2, MOD);

        for (int i = 0; i < n; i++) {
            long nL = i - left[i];
            long nR = right[i] - i;
            long totalLengthSum = (nL * nR) % MOD;
            totalLengthSum = (totalLengthSum * (right[i] - left[i])) % MOD;
            totalLengthSum = (totalLengthSum * inv2) % MOD;

            long contribution = (arr.get(i) * totalLengthSum) % MOD;
            result = (result + contribution) % MOD;
        }

        return (int) result;
    }

    private long modInverse(long a, int m) {
        return power(a, m - 2, m);
    }

    private long power(long x, long y, int m) {
        if (y == 0)
            return 1;
        long p = power(x, y / 2, m) % m;
        p = (p * p) % m;
        if (y % 2 == 0)
            return p;
        else
            return (p * x) % m;
    }
}

/*
Given an array of integers,
the strength of the array is given by the product of the length of the array and maximum element of the array.
For a given array calculate the sum of strengths of all subarrays modulo 10^9 +7

Write an efficient method in Java

public int getSubarrayStrengthSum(List<Integer> arr) {

}
 */
