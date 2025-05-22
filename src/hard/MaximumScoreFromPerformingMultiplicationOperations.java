package hard;

public class MaximumScoreFromPerformingMultiplicationOperations {
    public int maximumScore(int[] nums, int[] multipliers) {
        int M = multipliers.length;
        int N = nums.length;
        Integer[][] table = new Integer[M][M];
        return dp(nums, 0, 0, multipliers, M, N, table);
    }

    private int dp(int[] nums, int start, int index, int[] multipliers, int M, int N, Integer[][] table) {
        if (index == M)
            return  0;
        if (start == N-1-(index - start))
            return multipliers[index] * nums[start];
        if (table[start][index] != null)
            return table[start][index];
        table[start][index] = Math.max(
                multipliers[index] * nums[start] + dp(nums, start + 1, index + 1, multipliers, M, N, table),
                    multipliers[index] * nums[N-1-(index - start)] + dp(nums, start, index + 1, multipliers, M, N, table));
        return table[start][index];
    }

    public static void main(String[] args) {
        MaximumScoreFromPerformingMultiplicationOperations m = new MaximumScoreFromPerformingMultiplicationOperations();
        System.out.println(m.maximumScore(new int[]{-5,-3,-3,-2,7,1}, new int[]{-10,-5,3,4,6}));
        System.out.println(m.maximumScore(new int[]{1,2,3}, new int[]{3,2,1}));
    }
}
