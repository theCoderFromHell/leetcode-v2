package medium;

public class LengthOfLongestFibonacciSubsequence {
    public int lenLongestFibSubseq(int[] arr) {
        int size = arr.length;
        int[][] dp = new int[size][size];
        int maxLength = 0;
        for (int i = 2; i < size; i++) {
            int start = 0;
            int end = i-1;
            while (start < end) {
                if (arr[start] + arr[end] < arr[i])
                    start++;
                else if (arr[start] + arr[end] > arr[i])
                    end--;
                else {
                    dp[end][i] = dp[start][end] + 1;
                    maxLength = Math.max(maxLength, dp[end][i]);
                    start++;
                    end--;
                }
            }
        }
        return maxLength == 0 ? maxLength : maxLength + 2;
    }

    public static void main(String[] args) {
        LengthOfLongestFibonacciSubsequence L = new LengthOfLongestFibonacciSubsequence();
        System.out.println(L.lenLongestFibSubseq(new int[]{1,2,3,4,5,6,7,8}));
        System.out.println(L.lenLongestFibSubseq(new int[]{1,3,7,11,12,14,18}));
        System.out.println(L.lenLongestFibSubseq(new int[]{}));
    }
}
