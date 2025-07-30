package medium;

public class MinimumOperationsToMakeBinaryArrayElementsEqualToOneI {
    public int minOperations(int[] nums) {
        int size = nums.length;
        int count = 0;
        int index = 2;
        while (index < size) {
            if (nums[index - 2] == 0) {
                count++;
                nums[index - 2] = 1 - nums[index - 2];
                nums[index - 1] = 1 - nums[index - 1];
                nums[index] = 1 - nums[index];
            }
            index++;
        }
        for (int i = 0; i < size; i++)
            if (nums[i] != 1)
                return -1;
        return count;
    }
}
