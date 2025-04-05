package medium;

import java.util.Arrays;

public class PartitionToKEqualSumSubsets {
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int size = nums.length;
        int sum = 0, longest = 0;
        for (int i = 0; i < size; i++) {
            sum += nums[i];
            longest = Math.max(longest, nums[i]);
        }
        if (sum % k != 0)
            return false;
        int target = sum/k;
        if (longest > target)
            return false;
        boolean[] taken = new boolean[size];
        Arrays.sort(nums);
        return distribute(nums, size, taken, 0, 0, 0, target, k);
    }

    private boolean distribute(int[] nums, int size, boolean[] taken, int sets, int index, int currSum, int target, int k) {
        for (int i = index; i < size; i++) {
            if (taken[i])
                continue;
            if (currSum + nums[i] > target)
                return false;
            currSum += nums[i];
            taken[i] = true;
            if (currSum == target) {
                sets++;
                if (sets == k)
                    return true;
                if (distribute(nums, size, taken, sets, 0, 0, target, k))
                    return true;
                else
                    sets--;
            } else if (currSum < target) {
                if (distribute(nums, size, taken, sets, i + 1, currSum, target, k))
                    return true;
            }
            currSum -= nums[i];
            taken[i] = false;
        }
        return false;
    }

    public static void main(String[] args) {
        PartitionToKEqualSumSubsets P = new PartitionToKEqualSumSubsets();
        System.out.println(P.canPartitionKSubsets(new int[]{4,3,2,3,5,2,1}, 4));
        System.out.println(P.canPartitionKSubsets(new int[]{1,2,3,4}, 3));
        System.out.println(P.canPartitionKSubsets(new int[]{4,3,2,3,5,2,1}, 4));
    }
}
