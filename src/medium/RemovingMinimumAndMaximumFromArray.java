package medium;

public class RemovingMinimumAndMaximumFromArray {
    public int minimumDeletions(int[] nums) {
        int size = nums.length;
        if (size == 1)
            return 1;
        int minElement = Integer.MAX_VALUE;
        int minIndex = -1;
        int maxElement = Integer.MIN_VALUE;
        int maxIndex = -1;
        for (int i = 0; i < size; i++) {
            if (nums[i] < minElement) {
                minElement = nums[i];
                minIndex = i;
            }
            if (nums[i] > maxElement) {
                maxElement = nums[i];
                maxIndex = i;
            }
        }
        int a = Math.min(minIndex, maxIndex);
        int b = Math.max(minIndex, maxIndex);
        return Math.min((size-b) + a+1, Math.min(b+1, size-a));
    }

    public static void main(String[] args) {
        RemovingMinimumAndMaximumFromArray R = new RemovingMinimumAndMaximumFromArray();
        System.out.println(R.minimumDeletions(new int[]{2,10,7,5,4,1,8,6}));
        System.out.println(R.minimumDeletions(new int[]{0,-4,19,1,8,-2,-3,5}));
        System.out.println(R.minimumDeletions(new int[]{101}));
    }
}
