package hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RussianDollEnvelopes {
    public int maxEnvelopes(int[][] envelopes) {
        int N = envelopes.length;
        Arrays.sort(envelopes, (o1, o2) -> {
            if (o1[0] == o2[0])
                return o2[1] - o1[1];
            return o1[0] - o2[0];
        });
        int[] heights = new int[N];
        for (int i = 0; i < N; i++) {
            heights[i] = envelopes[i][1];
        }
        return longestIncreasingSubsequence(heights, N);
    }

    private int longestIncreasingSubsequence(int[] nums, int length) {
        List<Integer> lis = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            if (lis.isEmpty() || nums[i] > lis.getLast())
                lis.addLast(nums[i]);
            else {
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
        RussianDollEnvelopes R = new RussianDollEnvelopes();
        System.out.println(R.maxEnvelopes(new int[][]{
                {5,4},{6,4},{6,7},{2,3}
        }));
        System.out.println(R.maxEnvelopes(new int[][]{
                {1,1},{1,1},{1,1}
        }));
    }
}
