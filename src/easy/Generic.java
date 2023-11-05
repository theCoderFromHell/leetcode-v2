package easy;

public class Generic {
    public static int findKthLargest(int[] nums, int k) {
        if(nums == null || nums.length == 0)
            return -1;
        int length = nums.length;
        return quickSelect(nums,0, length-1, length-k);
    }

    private static int quickSelect(int[] nums, int start, int end, int tdx) {
        int pdx = start-1;
        int pivot = nums[end];
        for(int i=start; i<end; i++) {
            if (nums[i] < pivot) {
                pdx++;
                swap(nums, i, pdx);
            }
        }
        swap(nums, pdx, end);
        if(pdx == tdx)
            return nums[pdx];


        return pdx;
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {

    }
}

//for(int i=0; i<N; i++) {
//        for(int j=0; j<N; j++) {
//        System.out.print(dp[i][j] + " ");
//        }
//        System.out.println();
//        }