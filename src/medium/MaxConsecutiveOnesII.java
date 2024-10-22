package medium;

public class MaxConsecutiveOnesII {
    public int findMaxConsecutiveOnes(int[] nums) {
        int N = nums.length;
        int start = 0;
        int end = 0;
        int maxLength = 0;
        int currentZeros = 0;
        while (start <= end && end < N) {
            while (end < N && currentZeros <= 1) {
                if (nums[end] == 0) {
                    if (currentZeros == 1)
                        break;
                    else
                        currentZeros++;
                }
                end++;
            }
            maxLength = Math.max(maxLength, end - start);
            if (end < N) {
                if (nums[end] == 0)
                    currentZeros++;
                end++;
            }
            while (currentZeros > 1) {
                if (nums[start] == 0)
                    currentZeros--;
                start++;
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        MaxConsecutiveOnesII M = new MaxConsecutiveOnesII();
        System.out.println(M.findMaxConsecutiveOnes(new int[]{1,0,1,1,0}));
        System.out.println(M.findMaxConsecutiveOnes(new int[]{1,0,1,1,0,1}));
    }
}
