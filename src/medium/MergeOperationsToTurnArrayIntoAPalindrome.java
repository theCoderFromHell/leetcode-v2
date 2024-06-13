package medium;

public class MergeOperationsToTurnArrayIntoAPalindrome {
    public int minimumOperations(int[] nums) {
        int N = nums.length;
        int start = 0;
        int end = N-1;
        int count = 0;
        while (start < end) {
            if (nums[start] != nums[end]) {
                if (nums[start] > nums[end]) {
                    nums[end-1] += nums[end];
                    end--;
                } else {
                    nums[start+1] += nums[start];
                    start++;
                }
                count++;
            } else {
                start++;
                end--;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        MergeOperationsToTurnArrayIntoAPalindrome m = new MergeOperationsToTurnArrayIntoAPalindrome();
        System.out.println(m.minimumOperations(new int[]{4,3,2,1,2,3,1}));
        System.out.println(m.minimumOperations(new int[]{1,2,3,4}));
        System.out.println(m.minimumOperations(new int[]{1,2,3,4,5,6}));
    }
}
