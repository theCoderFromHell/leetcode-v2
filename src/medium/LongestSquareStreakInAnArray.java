package medium;

public class LongestSquareStreakInAnArray {
    public int longestSquareStreak(int[] nums) {
        int N = nums.length;
        int[] hash = new int[100002];
        for (int i = 0; i < N; i++)
            hash[nums[i]] = 1;
        int result = -1;
        for (int i = 0; i < N; i++) {
            int curr = nums[i];
            int sqRoot = (int) Math.sqrt(curr);
            if (sqRoot * sqRoot == curr && hash[sqRoot] == 1)
                continue;
            int count = 1;
            // 316.xx is square root of 100000
            while (curr < 317 && hash[curr * curr] == 1) {
                count++;
                curr = curr * curr;
            }
            if (count > 1)
                result = Math.max(result, count);
        }
        return result;
    }

    public static void main(String[] args) {
        LongestSquareStreakInAnArray L = new LongestSquareStreakInAnArray();
        System.out.println(L.longestSquareStreak(new int[]{4,3,6,16,8,2}));
        System.out.println(L.longestSquareStreak(new int[]{2,3,5,6,7}));
    }
}
