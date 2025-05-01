package medium;

import java.util.HashMap;

public class MaxSumOfAPairWithEqualSumOfDigits {
    public int maximumSum(int[] nums) {
        int size = nums.length;
        HashMap<Integer, int[]> digitSumMap = new HashMap<>();
        int maxSum = -1;
        for (int i = 0; i < size; i++) {
            int digitSum = digitSum(nums[i]);
            int[] numbersWithDigitSum = digitSumMap.getOrDefault(digitSum, new int[]{-1,-1});
            if (nums[i] > numbersWithDigitSum[1]) {
                numbersWithDigitSum[0] = numbersWithDigitSum[1];
                numbersWithDigitSum[1] = nums[i];
            } else if (nums[i] > numbersWithDigitSum[0])
                numbersWithDigitSum[0] = nums[i];
            if (numbersWithDigitSum[0] != -1 && numbersWithDigitSum[1] != -1)
                maxSum = Math.max(maxSum, numbersWithDigitSum[0] + numbersWithDigitSum[1]);
            digitSumMap.put(digitSum, numbersWithDigitSum);
        }
        return maxSum;
    }

    private int digitSum(int num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }

    public static void main(String[] args) {
        MaxSumOfAPairWithEqualSumOfDigits M = new MaxSumOfAPairWithEqualSumOfDigits();
        System.out.println(M.maximumSum(new int[]{229,398,269,317,420,464,491,218,439,153,482,169,411,93,147,50,347,210,251,366,401}));
        System.out.println(M.maximumSum(new int[]{18,43,36,13,7}));
        System.out.println(M.maximumSum(new int[]{10,12,19,14}));
    }
}
