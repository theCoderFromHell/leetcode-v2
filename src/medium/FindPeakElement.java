package medium;

public class FindPeakElement {
    public static int findPeakElement(int[] nums) {
        int N = nums.length;
        if (N == 1)
            return 0;
        if (N == 2)
            return (nums[0] < nums[1]) ? 1 : 0;
        int start = 0, end = N-1;
        while (start <= end) {
            int mid = start + (end-start)/2;
            if (start < mid && nums[mid-1] > nums[mid])
                end = mid-1;
            else if (mid < end && nums[mid] < nums[mid+1])
                start = mid+1;
            else
                return mid;
        }
        return -1;
    }
    public static void main(String[] args) {
        System.out.println(findPeakElement(new int[]{1,2,3,1}));
        System.out.println(findPeakElement(new int[]{1,2,1,3,5,6,4}));
    }
}
