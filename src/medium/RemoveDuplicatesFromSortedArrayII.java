package medium;
public class RemoveDuplicatesFromSortedArrayII {
    public static int removeDuplicates(int[] nums) {
        int N = nums.length;
        int left = 0, right = 0;
        while (right < N) {
            int count = 1;
            while (right < N-1 && nums[right] == nums[right+1]) {
                count++;
                right++;
            }
            int length = Math.min(2, count);
            for(int i = right-count+1; i < right-count+1+length; i++)
                nums[left++] = nums[i];
            right++;
        }
        return left;
    }
    public static void main(String[] args) {
        System.out.println(removeDuplicates(new int[]{1,1,1,2,2,3}));
        System.out.println(removeDuplicates(new int[]{0,0,1,1,1,1,2,3,3}));
    }
}
