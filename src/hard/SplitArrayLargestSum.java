package hard;

public class SplitArrayLargestSum {
    public int splitArray(int[] nums, int k) {
        int size = nums.length;
        int sum = 0;
        int maxValue = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            sum += nums[i];
            maxValue = Math.max(maxValue, nums[i]);
        }
        if (k == 1)
            return sum;
        int start = maxValue, end = sum;
        int mid;
        int result = Integer.MAX_VALUE;
        while (start <= end) {
            mid = start + (end - start)/2;
            if (isPossible(nums, size, k, mid)) {
                result = Math.min(result, mid);
                end = mid-1;
            } else
                start = mid+1;
        }
        return result;
    }

    private boolean isPossible(int[] nums, int size, int k, int value) {
        int count = 1;
        int current = 0;
        int index = 0;
        while (index < size) {
            if (current + nums[index] <= value)
                current += nums[index++];
            else {
                count++;
                current = 0;
            }
        }
        return count <= k;
    }

    public static void main(String[] args) {
        SplitArrayLargestSum S = new SplitArrayLargestSum();
        System.out.println(S.splitArray(new int[]{7,2,5,10,8}, 2));
        System.out.println(S.splitArray(new int[]{1,2,3,4,5}, 2));
    }
}
