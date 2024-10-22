package medium;

public class MaxConsecutiveOnesIII {
    public int longestOnes(int[] nums, int k) {
        int N = nums.length;
        int start = 0;
        int end = 0;
        int maxLength = 0;
        int currentZeros = 0;
        while (start <= end && end < N) {
            while (end < N && currentZeros <= k) {
                if (nums[end] == 0) {
                    if (currentZeros == k)
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
            while (currentZeros > k) {
                if (nums[start] == 0)
                    currentZeros--;
                start++;
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        MaxConsecutiveOnesIII M = new MaxConsecutiveOnesIII();
        System.out.println(M.longestOnes(new int[]{1,1,1,0,0,0,1,1,1,1,0}, 2));
        System.out.println(M.longestOnes(new int[]{0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1}, 3));
    }
}
