package medium;

public class NextGreaterElementIII {
    public int nextGreaterElement(int n) {
        char[] nums = String.valueOf(n).toCharArray();
        int size = nums.length;
        int i = size-1;
        while (i > 0 && nums[i-1] >= nums[i])
            i--;
        if (i == 0)
            return -1;
        int j = i;
        while (j < size) {
            if (nums[j] <= nums[i-1])
                break;
            j++;
        }
        j--;
        char temp = nums[i-1];
        nums[i-1] = nums[j];
        nums[j] = temp;
        int start = i, end = size-1;
        while (start < end) {
            temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
        try {
            return Integer.parseInt(new String(nums));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        NextGreaterElementIII N = new NextGreaterElementIII();
        System.out.println(N.nextGreaterElement(12443322));
        System.out.println(N.nextGreaterElement(12));
        System.out.println(N.nextGreaterElement(21));
        System.out.println(N.nextGreaterElement(342));
        System.out.println(N.nextGreaterElement(5384));
        System.out.println(N.nextGreaterElement(287));
    }
}
