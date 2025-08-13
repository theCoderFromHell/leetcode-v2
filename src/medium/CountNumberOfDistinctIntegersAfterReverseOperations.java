package medium;

import java.util.HashSet;

public class CountNumberOfDistinctIntegersAfterReverseOperations {
    public int countDistinctIntegers(int[] nums) {
        int size = nums.length;
        HashSet<Integer> numbers = new HashSet<>();
        for (int i = 0; i < size; i++) {
            numbers.add(nums[i]);
            numbers.add(reverse(nums[i]));
        }
        return numbers.size();
    }

    private Integer reverse(int num) {
        int value = 0;
        while (num > 0) {
            value = 10 * value + num % 10;
            num /= 10;
        }
        return value;
    }

    public static void main(String[] args) {
        CountNumberOfDistinctIntegersAfterReverseOperations C = new CountNumberOfDistinctIntegersAfterReverseOperations();
        System.out.println(C.countDistinctIntegers(new int[]{1,13,10,12,31}));
        System.out.println(C.countDistinctIntegers(new int[]{2,2,2}));
        System.out.println(C.countDistinctIntegers(new int[]{820, 28}));
    }
}
