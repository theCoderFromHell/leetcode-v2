package medium;

public class FindTheScoreOfAllPrefixesOfAnArray {
    public long[] findPrefixScore(int[] nums) {
        int N = nums.length;
        int maxSofar = 0;
        long[] prefix = new long[N];
        for (int i = 0; i < N; i++) {
            maxSofar = Math.max(maxSofar, nums[i]);
            prefix[i] = (i == 0) ? nums[i] + maxSofar : nums[i] + maxSofar + prefix[i-1];
        }
        return prefix;
    }

    public static void main(String[] args) {
        FindTheScoreOfAllPrefixesOfAnArray F = new FindTheScoreOfAllPrefixesOfAnArray();
        F.findPrefixScore(new int[]{2,3,7,5,10});
        F.findPrefixScore(new int[]{1,1,2,4,8,16});
    }
}
