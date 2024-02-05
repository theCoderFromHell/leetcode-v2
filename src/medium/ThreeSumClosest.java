package medium;

import java.util.Arrays;

public class ThreeSumClosest {
    public static int threeSumClosest(int[] nums, int target) {
        int N = nums.length;
        Arrays.sort(nums);
        int closest = Integer.MAX_VALUE;
        for (int i = 0; i < N-2; i++) {
            int left = i+1;
            int right = N-1;
            int sum = 0;
            while (right < N && left < right) {
                sum = nums[i] + nums[left] + nums[right];
                if (sum == target)
                    return sum;
                if (Math.abs(target - sum) < Math.abs(target - closest))
                    closest = sum;
                if (sum < target) {
                    left++;
                    while (left > 0 && left < N && nums[left - 1] == nums[left])
                        left++;
                } else {
                    right--;
                    while (right >= 0 && right< N-1 && nums[right] == nums[right+1])
                        right--;
                }
            }

        }
        return closest;
    }

    public static void main(String[] args) {
        System.out.println(threeSumClosest(new int[]{-1,2,1,-4}, 1));
        System.out.println(threeSumClosest(new int[]{0,0,0}, 1));
        //System.out.println(threeSumClosest(new int[]{}, ));

    }
}
