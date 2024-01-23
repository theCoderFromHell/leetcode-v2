package medium;

public class NumberOfZeroFilledSubarrays {

    public static long zeroFilledSubarray(int[] nums) {
        int N = nums.length;
        int i = 0;
        long result = 0;
        while (i < N) {
            int count = 0;
            if (nums[i] == 0) {
                while (i < N && nums[i] == 0) {
                    count++;
                    i++;
                }
                result += ((long) count * (count + 1)) / 2;
            } else
                i++;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(zeroFilledSubarray(new int[]{1,3,0,0,2,0,0,4}));
        System.out.println(zeroFilledSubarray(new int[]{0,0,0,0,0,0}));
    }
}
