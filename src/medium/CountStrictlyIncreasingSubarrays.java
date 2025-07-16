package medium;

public class CountStrictlyIncreasingSubarrays {
    public long countSubarrays(int[] nums) {
        int size = nums.length;
        int start = 0, end = 0;
        long count = 0;
        while (end < size) {
            while (end+1 < size && nums[end] < nums[end+1])
                end++;
            int length = end-start+1;
            count += ((long) length * (length+1))/2;
            start = end = end+1;
        }
        return count;
    }

    public static void main(String[] args) {
        CountStrictlyIncreasingSubarrays C = new CountStrictlyIncreasingSubarrays();
        System.out.println(C.countSubarrays(new int[]{1,3,5,4,4,6}));
        System.out.println(C.countSubarrays(new int[]{1,2,3,4,5}));
    }
}
