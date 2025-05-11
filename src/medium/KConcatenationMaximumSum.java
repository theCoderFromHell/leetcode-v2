package medium;

public class KConcatenationMaximumSum {
    public int kConcatenationMaxSum(int[] arr, int k) {
        int MOD = 1_000_000_007;
        int size = arr.length;
        long maxEndingHere = 0, maxSoFar = 0;
        for (int i = 0; i < size; i++) {
            maxEndingHere = Math.max(maxEndingHere + arr[i], arr[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        if (k < 2)
            return (int) (maxSoFar % MOD);
        long leftMax = arr[0], rightMax = arr[size-1];
        long currLeft = arr[0], currRight = arr[size-1];
        for (int i = 1; i < size; i++) {
            currLeft += arr[i];
            leftMax = Math.max(leftMax, currLeft);
        }
        long totalSum = currLeft;
        for (int i = size-2; i >= 0; i--) {
            currRight += arr[i];
            rightMax = Math.max(rightMax, currRight);
        }
        int tailHead = (int) ((leftMax % MOD + rightMax % MOD) % MOD);
        if (totalSum <= 0)
            return (int) Math.max(maxSoFar % MOD, tailHead);
        return (int) Math.max((tailHead + (k-2) * (totalSum % MOD)) % MOD, tailHead);
    }

    public static void main(String[] args) {
        KConcatenationMaximumSum K = new KConcatenationMaximumSum();
        System.out.println(K.kConcatenationMaxSum(new int[]{10000,10000,10000,10000,10000,10000,10000,10000,10000,10000}, 100000));
        System.out.println(K.kConcatenationMaxSum(new int[]{1,2}, 3));
        System.out.println(K.kConcatenationMaxSum(new int[]{1,-2,1}, 5));
        System.out.println(K.kConcatenationMaxSum(new int[]{-1,-2}, 7));
    }
}
