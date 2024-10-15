package medium;

import java.util.PriorityQueue;

public class MaximalScoreAfterApplyingKOperations {
    public long maxKelements(int[] nums, int k) {
        int N = nums.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> (b-a));
        for (int i = 0; i < N; i++)
            pq.add(nums[i]);
        long result = 0;
        while (!pq.isEmpty() && k-- > 0) {
            int max = pq.poll();
            pq.add((int) (Math.ceil((double)max / 3)));
            result += max;
        }
        return result;
    }

    public static void main(String[] args) {
        MaximalScoreAfterApplyingKOperations M = new MaximalScoreAfterApplyingKOperations();
        System.out.println(M.maxKelements(new int[]{10,10,10,10,10}, 5));
        System.out.println(M.maxKelements(new int[]{1,10,3,3,3}, 3));
    }
}
