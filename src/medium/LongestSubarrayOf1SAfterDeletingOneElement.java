package medium;

public class LongestSubarrayOf1SAfterDeletingOneElement {
    public int longestSubarray(int[] nums) {
        int size = nums.length;
        if (allOnes(nums, size))
            return size-1;
        int start = 0, end = 0;
        int currZeroCount = 0;
        int maxLength = 0;
        while (end < size) {
            while (end < size && currZeroCount <= 1) {
                if (nums[end] == 0) {
                    if (currZeroCount == 0)
                        currZeroCount++;
                    else
                        break;
                }
                end++;
            }
            maxLength = Math.max(maxLength, end - start - 1);
            if (end < size) {
                if (nums[end] == 0)
                    currZeroCount++;
                end++;
            }
            while (start <= end && currZeroCount > 1) {
                if (nums[start] == 0)
                    currZeroCount--;
                start++;
            }
        }
        return maxLength;
    }

    private boolean allOnes(int[] nums, int size) {
        for (int i = 0; i < size; i++) {
            if (nums[i] != 1)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        LongestSubarrayOf1SAfterDeletingOneElement L = new LongestSubarrayOf1SAfterDeletingOneElement();
        System.out.println(L.longestSubarray(new int[]{}));
        System.out.println(L.longestSubarray(new int[]{1,1,0,1}));
        System.out.println(L.longestSubarray(new int[]{0,1,1,1,0,1,1,0,1}));
        System.out.println(L.longestSubarray(new int[]{1,1,1}));
    }
}
