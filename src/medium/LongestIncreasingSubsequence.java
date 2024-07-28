package medium;

import java.util.ArrayList;
import java.util.List;

public class LongestIncreasingSubsequence {
    public int lengthOfLIS(int[] nums) {
        int N = nums.length;
        List<Integer> lis = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            if (lis.isEmpty() || nums[i] > lis.getLast()) {
                lis.add(nums[i]);
            } else {
                int index = binarySearch(lis, 0, lis.size()-1, nums[i]);
                lis.set(index, nums[i]);
            }
        }
        return lis.size();
    }

    private int binarySearch(List<Integer> nums, int start, int end, int target) {
        int mid;
        while (start < end) {
            mid = start + (end - start)/2;
            if (nums.get(mid) == target)
                return mid;
            if (nums.get(mid) < target)
                start = mid+1;
            else
                end = mid;
        }
        return start;
    }

    public static void main(String[] args) {
        LongestIncreasingSubsequence L = new LongestIncreasingSubsequence();
        System.out.println(L.lengthOfLIS(new int[]{10,9,2,5,3,7,101,18}));
        System.out.println(L.lengthOfLIS(new int[]{0,1,0,3,2,3}));
        System.out.println(L.lengthOfLIS(new int[]{7,7,7,7,7,7,7}));

    }

}
