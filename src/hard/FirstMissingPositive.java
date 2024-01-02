package hard;

public class FirstMissingPositive {
    public static int firstMissingPositive(int[] nums) {
        int N = nums.length;
        for (int i = 0; i < N; i++)
            if (nums[i] < 0 || nums[i] > N)
                nums[i] = 0;
        for (int i = 0; i < N; i++) {
            if (nums[i] == Integer.MAX_VALUE)
                nums[i] = Integer.MIN_VALUE;
            else if (nums[i] != 0) {
                int value = (nums[i] > 0) ? nums[i] : -1*nums[i];
                if (value > N)
                    continue;
                if (nums[value-1] == 0 || nums[value-1] == Integer.MAX_VALUE-1)
                    nums[value-1] = Integer.MAX_VALUE-1;
                else
                    nums[value-1] = (nums[value-1] > 0) ? -1 * nums[value-1] : nums[value-1];
            }
        }
        for (int i = 0; i < N; i++) {
            if (nums[i] == Integer.MIN_VALUE || (nums[i] != Integer.MAX_VALUE-1 && nums[i] >=0))
                return (i+1);
        }
        return (N+1);
    }

    public static void main(String[] args) {
        System.out.println(firstMissingPositive(new int[] {1,4,2,0,3,4,2,4,2}));


    }
}
