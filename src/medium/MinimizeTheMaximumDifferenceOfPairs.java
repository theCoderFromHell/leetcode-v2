package medium;

import java.util.Arrays;

public class MinimizeTheMaximumDifferenceOfPairs {
    public int minimizeMax(int[] nums, int p) {
        int size = nums.length;
        Arrays.sort(nums);
        int low = 0, high = nums[size-1] - nums[0];
        int mid;
        int minDiff = Integer.MAX_VALUE;
        while (low <= high) {
            mid = low + (high - low)/2;
            if (works(mid, nums, size, p)) {
                minDiff = Math.min(minDiff, mid);
                high = mid - 1;
            } else
                low = mid + 1;
        }
        return minDiff == Integer.MAX_VALUE ? 0 : minDiff;
    }

    private boolean works(int mid, int[] nums, int size, int p) {
        int index = 0;
        int count = 0;
        while (index < size-1) {
            if (nums[index+1] - nums[index] <= mid) {
                count++;
                if (count == p)
                    return true;
                index++;
            }
            index++;
        }
        return false;
    }

    public static void main(String[] args) {
        MinimizeTheMaximumDifferenceOfPairs M = new MinimizeTheMaximumDifferenceOfPairs();
        System.out.println(M.minimizeMax(new int[]{10,1,2,7,1,3}, 2));
        System.out.println(M.minimizeMax(new int[]{4,2,1,2}, 1));
    }
}
