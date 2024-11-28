package medium;

public class MaximumSubarraySumWithOneDeletion {
    public int maximumSum(int[] arr) {
        int size = arr.length;
        int maxWithNoDeletion = arr[0];
        int maxWithDeletion = arr[0];
        int maxSubArray = arr[0];
        for (int i = 1; i < size; i++) {
            maxWithDeletion = Math.max(maxWithDeletion + arr[i], maxWithNoDeletion);
            maxWithNoDeletion = Math.max(maxWithNoDeletion + arr[i], arr[i]);
            maxSubArray = Math.max(maxSubArray, Math.max(maxWithDeletion, maxWithNoDeletion));
        }
        return maxSubArray;
    }

    public static void main(String[] args) {
        MaximumSubarraySumWithOneDeletion M = new MaximumSubarraySumWithOneDeletion();
        System.out.println(M.maximumSum(new int[]{2,1,-2,-5,-2}));
        System.out.println(M.maximumSum(new int[]{1,-2,0,3}));
        System.out.println(M.maximumSum(new int[]{1,-2,-2,3}));
        System.out.println(M.maximumSum(new int[]{-1,-1,-1,-1}));
    }
}
