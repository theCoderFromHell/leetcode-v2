package medium;

public class MinimumMovesToEqualArrayElements {
    public int minMoves(int[] nums) {
        int N = nums.length;
        int minElement = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++)
            minElement = Math.min(minElement, nums[i]);
        int result = 0;
        for (int i = 0; i < N; i++)
            result += (nums[i] - minElement);
        return result;
    }

    public static void main(String[] args) {
        MinimumMovesToEqualArrayElements M = new MinimumMovesToEqualArrayElements();
        System.out.println(M.minMoves(new int[]{1,2,3}));
        System.out.println(M.minMoves(new int[]{1,1,1}));
        System.out.println(M.minMoves(new int[]{1,4,6,7}));
    }
}
