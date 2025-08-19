package medium;

import java.util.PriorityQueue;

public class MaximumProductAfterKIncrements {
    private int MOD = 1000000007;
    public int maximumProduct(int[] nums, int k) {
        int size = nums.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < size; i++)
            pq.add(nums[i]);
        while (k-- > 0)
            pq.add(pq.poll() + 1);
        long result = 1;
        while (!pq.isEmpty())
            result = (result * pq.poll()) % MOD;
        return (int) result;
    }

    public static void main(String[] args) {
        MaximumProductAfterKIncrements M = new MaximumProductAfterKIncrements();
        System.out.println(M.maximumProduct(new int[]{24,5,64,53,26,38}, 54));
        System.out.println(M.maximumProduct(new int[]{0,4}, 5));
        System.out.println(M.maximumProduct(new int[]{6,3,3,2}, 2));
    }
}
