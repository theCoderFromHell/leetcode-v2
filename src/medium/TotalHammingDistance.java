package medium;

public class TotalHammingDistance {
    public int totalHammingDistance(int[] nums) {
        int N = nums.length;
        int[] setBitCount = new int[32];
        for (int i = 0; i < N; i++) {
            int index = 0;
            while (nums[i] > 0) {
                setBitCount[index] += (nums[i] & 1);
                index++;
                nums[i] = nums[i] >> 1;
            }
        }
        int result = 0;
        for (int k : setBitCount)
            result += (k * (N - k));

        return result;
    }

    public static void main(String[] args) {
        TotalHammingDistance T = new TotalHammingDistance();
        System.out.println(T.totalHammingDistance(new int[] {4,14,2}));
        System.out.println(T.totalHammingDistance(new int[] {4,14,4}));
    }
}
