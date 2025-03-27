package medium;

public class SubarrayProductLessThanK {

    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k <= 1)
            return 0;
        int size = nums.length;
        long product = 1;
        int result = 0;
        for (int left = 0, right = 0; right < size; right++) {
            product *= nums[right];
            while (left < size && product >= k) {
                product /= nums[left];
                left++;
            }
            result += (right - left + 1);
        }
        return result;
    }

    public int numSubarrayProductLessThanKV2(int[] nums, int k) {
        int size = nums.length;
        int start = 0;
        int result = 0;
        for (int end = size-1; end >= 0; end--) {
            start = end;
            long product = 1;
            while (start >= 0 && product * nums[start] < k) {
                product *= nums[start];
                start--;
            }
            result += (end - start);
        }
        return result;
    }

    public static void main(String[] args) {
        SubarrayProductLessThanK S = new SubarrayProductLessThanK();
        System.out.println(S.numSubarrayProductLessThanK(new int[]{10,5,2,6}, 100));
        System.out.println(S.numSubarrayProductLessThanK(new int[]{1,2,3}, 0));
        //System.out.println(S.numSubarrayProductLessThanK(new int[]{}, ));
    }
}
