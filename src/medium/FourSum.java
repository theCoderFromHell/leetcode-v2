package medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FourSum {
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        int N = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> results = new ArrayList<>();
        if (nums[0] > target/4 || nums[N-1] < target/4)
            return results;
        for (int i = 0; i < N-3; i++) {
            if (i > 0 && nums[i] == nums[i-1])
                continue;
            for (int j = i+1; j < N-2; j++) {
                if (j > i+1 && nums[j] == nums[j-1])
                    continue;
                long currSum = nums[i] + nums[j];
                List<List<Integer>> pairs = twoSum(nums, j+1, N-1, ((long) target)-currSum);
                for (List<Integer> twoNums : pairs) {
                    results.add(Arrays.asList(nums[i], nums[j], twoNums.get(0), twoNums.get(1)));
                }
            }
        }
        return results;
    }

    private static List<List<Integer>> twoSum(int[] nums, int start, int end, long target) {
        List<List<Integer>> results = new ArrayList<>();
        if (nums[start] > target/2 || nums[end] < target/2)
            return results;
        int low = start, high = end;
        while (low < high) {
            long sum = nums[low] + nums[high];
            if (sum < target || (low > start && nums[low] == nums[low-1]))
                low++;
            else if (sum > target || (high < end && nums[high] == nums[high+1]))
                high--;
            else
                results.add(Arrays.asList(nums[low++], nums[high--]));
        }
        return results;
    }

    public static void main(String[] args) {
        System.out.println(fourSum(new int[]{1,0,-1,0,-2,2}, 0));
        System.out.println(fourSum(new int[]{2,2,2,2,2}, 8));
        System.out.println(fourSum(new int[]{-1000000000,-1000000000,1000000000,-1000000000,-1000000000}, 294967296));


    }
}
