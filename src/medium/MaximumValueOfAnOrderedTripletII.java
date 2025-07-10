package medium;

public class MaximumValueOfAnOrderedTripletII {
    public long maximumTripletValue(int[] nums) {
        int size = nums.length;
        long result = 0;
        int[] left = new int[size];
        int[] right = new int[size];
        for (int i = 1; i < size; i++) {
            left[i] = Math.max(left[i-1], nums[i-1]);
            right[size-1-i] = Math.max(right[size-i], nums[size-i]);
        }
        for (int i = 0; i < size - 1; i++)
            result = Math.max(result, (long) (left[i] - nums[i]) * right[i]);
        return result;
    }

    public static void main(String[] args) {
        MaximumValueOfAnOrderedTripletII M = new MaximumValueOfAnOrderedTripletII();
        System.out.println(M.maximumTripletValue(new int[]{12,6,1,2,7}));
        System.out.println(M.maximumTripletValue(new int[]{1,10,3,4,19}));
        System.out.println(M.maximumTripletValue(new int[]{1,2,3}));
        System.out.println(M.maximumTripletValue(new int[]{1000000,1,1000000}));
    }
}
