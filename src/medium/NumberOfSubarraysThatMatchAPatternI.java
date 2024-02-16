package medium;

public class NumberOfSubarraysThatMatchAPatternI {
    public static int countMatchingSubarrays(int[] nums, int[] pattern) {
        int N = nums.length;
        int M = pattern.length;
        int[] code = new int[N];
        for (int i = 1; i < N; i++)
            code[i] = Integer.compare(nums[i], nums[i - 1]);
        int result = 0;
        for (int i = 1; i <= N-M; i++) {
            if (match(code, i, pattern, M))
                result++;
        }
        return result;
    }

    private static boolean match(int[] code, int idx, int[] pattern, int M) {
        for (int i = 0; i < M; i++)
            if (code[idx + i] != pattern[i])
                return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println(countMatchingSubarrays(new int[]{1,2,3,4,5,6}, new int[]{1,1}));
        System.out.println(countMatchingSubarrays(new int[]{1,4,4,1,3,5,5,3}, new int[]{1,0,-1}));
    }
}
