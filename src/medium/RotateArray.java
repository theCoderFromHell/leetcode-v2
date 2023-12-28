package medium;

public class RotateArray {
    public void rotate(int[] nums, int k) {
        int N = nums.length;
        if (N == 1)
            return;
        k = k % N;
        if (k == 0)
            return;
        reverse(nums, 0, N-k-1);
        reverse(nums, N-k, N-1);
        reverse(nums, 0, N-1);
    }

    private void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}
