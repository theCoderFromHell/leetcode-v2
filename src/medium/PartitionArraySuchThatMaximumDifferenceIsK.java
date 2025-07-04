package medium;

import java.util.Arrays;

public class PartitionArraySuchThatMaximumDifferenceIsK {
    public int partitionArray(int[] nums, int k) {
        int size = nums.length;
        Arrays.sort(nums);
        int result = 0;
        int index = 0;
        int prev = 0;
        while (index < size) {
            while (index < size && nums[index] - nums[prev] <= k)
                index++;
            result++;
            prev = index;
        }
        return result;
    }

    public static void main(String[] args) {
        PartitionArraySuchThatMaximumDifferenceIsK P = new PartitionArraySuchThatMaximumDifferenceIsK();
        System.out.println(P.partitionArray(new int[]{3,6,1,2,5}, 2));
        System.out.println(P.partitionArray(new int[]{1,2,3}, 1));
        System.out.println(P.partitionArray(new int[]{2,2,4,5}, 0));
        System.out.println(P.partitionArray(new int[]{3,5,2,1,6,4}, 5));
        System.out.println(P.partitionArray(new int[]{1,2,3,4}, 1));
        System.out.println(P.partitionArray(new int[]{1,2,3,4,5}, 2));
        System.out.println(P.partitionArray(new int[]{1,2,3,4,5,6}, 2));
    }
}
